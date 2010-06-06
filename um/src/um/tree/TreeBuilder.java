package um.tree;


public class TreeBuilder {
	
	static final double stopCriteriumTolerance = 0.1; 

	public static Node buildTheTree(ExamplesSet es, Node parent) {
		Node tree = new Node(parent);
		if (es.isMajorityOfOneCategory(stopCriteriumTolerance)) {
			//Choosing the category for the leaf
			tree.setCategory(es.getMostFrequentCategory());
			return tree;
		}
		tree.test = ContinousTest.chooseTest(es, null);
		
		ExamplesSet sTrue = es.getExamplesSubsetForSpecifiedTestAndResult(tree.test, true);
		tree.addChild(true, buildTheTree(sTrue, tree));
		
		ExamplesSet sFalse = es.getExamplesSubsetForSpecifiedTestAndResult(tree.test, false);
		tree.addChild(false, buildTheTree(sFalse, tree));

		return tree;
	}
	
	
}
