package jch.rta.pmml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;

import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.ModelEvaluator;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.model.ImportFilter;
import org.jpmml.model.JAXBUtil;
import org.jpmml.model.PMMLUtil;
import org.jpmml.model.visitors.LocatorTransformer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PMMLBoltUtil {	
	/**
	 * 
	 * @param file .pmml model file
	 * @return InputStream
	 * @throws IOException
	 * @throws SAXException
	 * @throws JAXBException
	 */
	public static Evaluator createEvaluator(String file) throws IOException, SAXException, JAXBException 
	{
		//try(InputStream is = new FileInputStream(file))
		try(InputStream is = PMMLBoltUtil.class.getResourceAsStream(file))
		{
			return createEvaluator(is);
		}
	}

	/**
	 * 
	 * @param is .pmml model InputStream
	 * @return pmml model Evaluator
	 * @throws SAXException
	 * @throws JAXBException
	 */
	public static Evaluator createEvaluator(InputStream is) throws SAXException, JAXBException 
	{

//		Source source = ImportFilter.apply(new InputSource(is));
//		PMML pmml = JAXBUtil.unmarshalPMML(source);

		PMML pmml = PMMLUtil.unmarshal(is);
		LocatorTransformer locatorTransformer = new LocatorTransformer();
		pmml.accept(locatorTransformer);

		ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();

		ModelEvaluator<?> modelEvaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
		Evaluator evaluator = (Evaluator) modelEvaluator;
        evaluator.verify();
		return evaluator;
	}
}
