package jch.rta.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import jch.rta.bolt.PMMLRTAResultBolt;
import jch.rta.bolt.SVMPMMLBolt;
import jch.rta.bolt.TreePMMLBolt;
import jch.rta.spout.DatasetsReaderSpout;

public class PMMLRTAStarter 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		TopologyBuilder topologyBuilder = new TopologyBuilder();
		topologyBuilder.setSpout("spout", new DatasetsReaderSpout());
//		
		topologyBuilder.setBolt("svmbolt", new SVMPMMLBolt()).shuffleGrouping("spout", "streamid1");
		topologyBuilder.setBolt("treebolt", new TreePMMLBolt()).shuffleGrouping("spout", "streamid2");
		topologyBuilder.setBolt("emitterbolt", new PMMLRTAResultBolt(),2).fieldsGrouping("svmbolt", new Fields("SVMResults")).fieldsGrouping("treebolt", new Fields("TreeResults"));

//		topologyBuilder.setBolt("svmbolt", new RTATreeBolt()).shuffleGrouping("spout");
//		topologyBuilder.setBolt("emitterbolt", new RTAResultEmitBolt()).shuffleGrouping("svmbolt");
		
		Config config = new Config();
		config.setDebug(false);

		StormTopology topology = topologyBuilder.createTopology();

		LocalCluster localCluster = new LocalCluster();
		localCluster.submitTopology("PMMLRTA-platform", config, topology);
		
		//cluster.shutdown();
		//cluster.killTopology("PMMLRTA-platform");
	}
}
