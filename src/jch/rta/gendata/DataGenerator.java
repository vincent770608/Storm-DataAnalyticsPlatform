package jch.rta.gendata;

import java.text.DecimalFormat;

public class DataGenerator 
{
//	public static void main(String args[])
//	{
//		DataGenerator datagen = new DataGenerator();
//		String data = datagen.generateData();
//		System.out.println(data);
		
//		Dataformatparser dataform = new Dataformatparser();
//		ObjectNode json = dataform.parseCSVtoJSON(data);
//		System.out.println(json);
		
//	}
	/**
	 * 
	 * @return auto generate msg
	 */
	public String generateData()
	{	
		int genmsgnum=5;
		String genmsg ="";
		DecimalFormat df=new DecimalFormat("#.###");
		for(int i=0;i<genmsgnum;i++)
		{
			String randnum = String.valueOf(df.format(Math.random()));
				
			if(genmsg=="")
			{
				genmsg = randnum;
			}
			else genmsg = genmsg+","+randnum;
		}

		return genmsg;
	}
}
