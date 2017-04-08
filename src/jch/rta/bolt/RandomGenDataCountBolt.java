package jch.rta.bolt;

import java.text.DecimalFormat;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class RandomGenDataCountBolt implements IRichBolt
{
	private OutputCollector collector;
	String PREmessage;
	public String SendPRECorrelationID;
	DecimalFormat df=new DecimalFormat("#.##");
	@Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) 
	{
        //declarer.declareStream("count-stream", new Fields("sentence","fan_num"));
		declarer.declare(new Fields("PREmessage"));
    }
   
	@Override
	public void execute(Tuple tuple) 
	{
		// TODO Auto-generated method stub
		String sentence = tuple.getString(0);
		double fan_num =0;
		
		if(sentence!=null && sentence!="")
		{
			for(String word : sentence.split(",")) 
	        {
	        	double num = Double.parseDouble(word);
	        	fan_num += num;
	        }
	        String randnum = String.valueOf(df.format(fan_num));
	        
	        PREmessage = sentence +":"+ randnum;
	        
	        System.out.println("FakedataCount Bolt emit:"+PREmessage);
	        collector.emit(new Values(PREmessage));
		}
//        collector.emit("count-stream", new Values(sentence,fan_num));
//        collector.ack(tuple);
	}
	
	@Override
	public void cleanup() 
	{
		System.out.println("FakedataCountBolt cleanup:"+PREmessage);
	}
	
	@Override
	public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		System.out.println("Prepare FakedataCountBolt");
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
