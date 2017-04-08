package jch.rta.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

public class PMMLRTAResultBolt implements IRichBolt 
{
	private OutputCollector collector = null;
	public void cleanup() 
	{
		// TODO Auto-generated method stub
		System.out.println("RTAResult cleanup");
	}

	public void execute(Tuple tuple) 
	{
		// TODO Auto-generated method stub
		System.out.println("RTAResult " + tuple);
	}

	public void prepare(Map map, TopologyContext context, OutputCollector collector) 
	{
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) 
	{
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Result"));
	}

	public Map<String, Object> getComponentConfiguration() 
	{
		// TODO Auto-generated method stub
		return null;
	}

}
