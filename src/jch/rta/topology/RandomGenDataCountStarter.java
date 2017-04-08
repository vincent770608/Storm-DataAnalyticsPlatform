package jch.rta.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import jch.rta.bolt.RandomGenDataCountBolt;
import jch.rta.spout.RandomGenDataSenderSpout;

public class RandomGenDataCountStarter {

	public static void main(String [] arg) throws Exception
	{
		// TODO Auto-generated method stub
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout", new RandomGenDataSenderSpout());
		builder.setBolt("countbolt", new RandomGenDataCountBolt()).shuffleGrouping("spout");

		Config conf = new Config();

		conf.setDebug(false);
		//conf.setMaxTaskParallelism(3);
		LocalCluster cluster = new LocalCluster();
		
		cluster.submitTopology("RTA-platform", conf, builder.createTopology());
				
		//cluster.shutdown();
		//cluster.killTopology("RTA-platform");
	}
}
