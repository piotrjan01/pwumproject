package um.tree;


public class TreeBuilder {
	
	static final double stopCriteriumTolerance = 0.1; 

	public static Node buildTheTree(ExamplesSet es, Node tree) {
		if (es.isMajorityOfOneCategory(stopCriteriumTolerance)) {
			//Choosing the category for the leaf
			tree.setCat(es.getMostFrequentCategory());
			return tree;
		}
		Test t = chooseTest(es);
		return null;
	}
	
	static Test chooseTest(ExamplesSet examples) {
		//TODO: implement
		return null;
	}
	
	
}
