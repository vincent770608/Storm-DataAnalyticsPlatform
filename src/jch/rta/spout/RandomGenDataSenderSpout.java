package jch.rta.spout;

import java.util.ArrayList;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import jch.rta.gendata.DataGenerator;

public class RandomGenDataSenderSpout implements IRichSpout {
	private SpoutOutputCollector collector;
	TopologyContext context;


	int count=0;
	ArrayList<String> textList = new ArrayList<String>();
	
	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) 
	{
		declarer.declare(new Fields("text"));
	}

	@Override
	public void open(Map configMap, TopologyContext context, SpoutOutputCollector outputCollector) 
	{
		this.context = context;	
		this.collector = outputCollector;
		
		System.out.println("RTADataSenderSpout open ");
		
		DataGenerator datagen = new DataGenerator();
		for(int i=0;i<100;i++)
		{
			String csvdata = datagen.generateData();
//			Dataformatparser dataform = new Dataformatparser();
//			ObjectNode json = dataform.parseCSVtoJSON(csvdata);
			
			textList.add(csvdata.toString());
		}
	}

	//that method is invoked more than once
	@Override
	public void nextTuple() 
	{	
		if (!textList.isEmpty() && textList.size()>0)
		{
//			if (!textList.get(0).equals(previousText)) {
			//ObjectMapper mapper = new ObjectMapper();
			//JsonNode fne_num = textList.get(0).get("V001");
				System.out.println("FNEListener Spout emit:"+ textList.get(0));
			
				collector.emit(new Values(textList.get(0)));
//				previousText = textList.get(0);
				textList.remove(0);
//			}
		}
	}

	@Override
	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Object arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}



