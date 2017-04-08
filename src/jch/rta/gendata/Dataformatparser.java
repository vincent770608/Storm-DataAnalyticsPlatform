package jch.rta.gendata;

import java.text.DecimalFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Dataformatparser 
{
	/**
	 * 
	 * @param csvmessage csvformat data
	 * @return objectNode json format data
	 */
	public ObjectNode parseCSVtoJSON(String csvmessage) 
	{
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		
		String [] featurearr = csvmessage.split(",");
		
		for(int i=0; i<featurearr.length; i++) 
        {
//        	double values = Double.parseDouble(featurearr[i]);

        	switch(i)
        	{
        		case 0:
        			objectNode.put("sepal_length", featurearr[i]);
        		case 1:
        			objectNode.put("sepal_width", featurearr[i]);
        		case 2:
        			objectNode.put("petal_length", featurearr[i]);
        		case 3:
        			objectNode.put("petal_width", featurearr[i]);
        		case 4:
        			objectNode.put("class", featurearr[i]);
        	}
        	
        		
        }  
		return objectNode;
	}
}
