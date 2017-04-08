Resources

	pmmlmodels reference from:
	
	http://dmg.org/pmml/pmml_examples/KNIME_PMML_4.1_Examples/single_iris_svm.xml
	http://dmg.org/pmml/pmml_examples/KNIME_PMML_4.1_Examples/ensemble_iris_dectree.xml

	datasets reference from:
	http://dmg.org/pmml/pmml_examples/index.html#Iris

Topology
	PMMLRTA-platform:
	DatasetsReaderSpout	->	SVMPMMLBolt	or PMMLRTAResultBolt	->	PMMLRTAResultBolt

Spout
	DatasetsReaderSpout:	Read the datasets from \resources\datasets\Iris.csv

Bolt
	SVMPMMLBolt:	Create the evaluator from model "resources\pmmlmodels\single_iris_svm.pmml" and do the classification
	TreePMMLBolt:	Create the evaluator from model "resources\pmmlmodels\ensemble_iris_dectree.pmml" and do the classification
	PMMLRTAResultBolt: Output the Result of classificasion