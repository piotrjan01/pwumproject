package um.tree;


public class TreeBuilder {
	
	static final double stopCriteriumTolerance = 0.1; 

	public static Node buildTheTree(ExamplesSet es, Node tree) {
		if (es.isMajorityOfOneCategory(stopCriteriumTolerance)) {
			//Choosing the category for the leaf
			tree.setCategory(es.getMostFrequentCategory());
			return tree;
		}
		ContinousTest t = chooseTest(es);
		return null;
	}
	
	static ContinousTest chooseTest(ExamplesSet examples) {
		//TODO: implement
		return null;
	}
	
	
}
