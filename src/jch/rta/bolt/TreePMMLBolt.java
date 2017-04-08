package jch.rta.bolt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jch.rta.pmml.PMMLBoltUtil;

public class TreePMMLBolt implements IRichBolt 
{
	private Evaluator evaluator = null;
	private OutputCollector collector = null;
	
	public void cleanup() 
	{
		// TODO Auto-generated method stub
	}

	public void execute(Tuple tuple) 
	{
		// TODO Auto-generated method stub
		String datamsg = tuple.getString(0);
		ArrayList<Double> result = (ArrayList<Double>) getProbability(datamsg,evaluator);
	    List<ArrayList<?>> listArray = new ArrayList<>();
	    listArray.add(result);
		collector.emit(tuple,new Values(listArray.get(0).toString()));
		collector.ack(tuple);
	}

	public void prepare(Map map, TopologyContext context, OutputCollector collector) 
	{
		// TODO Auto-generated method stub
		this.collector = collector;
		String pmmlfile = "/pmmlmodels/ensemble_iris_dectree.pmml";
		//Evaluator SVMS_evaluator = null;
		try 
		{
			this.evaluator = PMMLBoltUtil.createEvaluator(pmmlfile);
		} catch (IOException | SAXException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.evaluator = SVMS_evaluator;
		System.out.println("Prepare RTATreeBolt done");
	}

	public ArrayList<?> getProbability(String fnemsg,Evaluator evaluator)
	{
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Double> dataList = null;
		try 
		{
			dataList = mapper.readValue(fnemsg, HashMap.class);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        List<InputField> inputFields  = this.evaluator.getInputFields();
        for(int i = 0; i < inputFields.size(); i++)
        {
        	InputField inputField = inputFields.get(i);
//        	System.out.println(inputField);
        	arguments.put(inputField.getName(), inputField.prepare(dataList.get(inputField.getName().toString())));
        	//System.out.println("arguments:" +arguments);
        }
        ArrayList<Object> score = new ArrayList();
        Map<FieldName,?> finalResults = evaluator.evaluate(arguments);

        for(FieldName t : finalResults.keySet())
        {
            if (finalResults.get(t) instanceof Double) 
            {
                score.add((Double) finalResults.get(t));
            }
            else
            {
                score.add(finalResults.get(t));
            }  
        }
        return score;
    }
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) 
	{
		// TODO Auto-generated method stub
		declarer.declare(new Fields("TreeResults"));
	}

	public Map<String, Object> getComponentConfiguration() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
