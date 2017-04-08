package jch.rta.spout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jch.rta.gendata.Dataformatparser;

public class DatasetsReaderSpout implements IRichSpout
{
	private SpoutOutputCollector collector = null;
	List<String> textList = new ArrayList<String>();
	private int msgcount = 0; 
	public void ack(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public void activate() {
		// TODO Auto-generated method stub
		
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	public void fail(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public void nextTuple() 
	{
		// TODO Auto-generated method stub

		if (!textList.isEmpty() && textList.size()>0)
		{
			Iterator<String> itertextList = textList.iterator();
			
			while(itertextList.hasNext())
			{
				String data = itertextList.next();
				
				msgcount++;
				collector.emit(new Values(data));
				
				if (msgcount % 2 == 1) { 
		            //for streamid1 PMMLRFBolt
		        	this.collector.emit("streamid1", new Values(data));
		        } else { 
		        	
		            //for streamid2 PMMLSVMBolt
		            this.collector.emit("streamid2", new Values(data));
		        } 
	
				System.out.println("DatasetReaderSpout nextTuple: "+data);
				
				itertextList.remove();
			}
		}
		textList.removeAll(textList);
	}

	public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		
		System.out.println("DatasetsReaderSpout open ");
		this.getdata();
	}

	public void getdata()
	{
		String recourcedataset = "/datasets/iris.csv";
		
		InputStream input = DatasetsReaderSpout.class.getResourceAsStream(recourcedataset);
        BufferedReader br=new BufferedReader(new InputStreamReader(input));
        String line = "";

        Dataformatparser formatparser = new Dataformatparser();
        
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode text ;
        
		try 
		{
			for(int i=0;i<100;i++)
			{
				int count=0;
				while ((line = br.readLine()) != null) 
				{
					if(line.length()>0)
					{
						if(count>0)
						{
							line = line.replaceAll("[\r\n]", "");
							text = formatparser.parseCSVtoJSON(line);
		
							String requestJson = mapper.writeValueAsString(text);

							textList.add(requestJson);
						}
					}
					count++;
				}
			}
		} 
		catch (IOException e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declareStream("streamid1", new Fields("svmbolt")); 
	    declarer.declareStream("streamid2", new Fields("treebolt"));
		
//		declarer.declare(new Fields("DataSender"));

	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
