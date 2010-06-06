package um.tree;

import java.util.Vector;


public class TreeTools {
	
	static final double stopCriteriumTolerance = 0.1; 

	/**
	 * 
	 * UWAGI:
	 * - sa tylko 2 kategorie
	 * - nie ma listy uzytych testow, bo w kazdym poddrzewie lista testow jest
	 * juz dobra
	 * 
	 * @param es
	 * @param parent
	 * @return
	 */
	public static Node buildTheTree(ExamplesSet es, Node parent) {
		Node tree = new Node(parent);
		if (es.isMajorityOfOneCategory(stopCriteriumTolerance)) {
			tree.setCategory(es.getMostFrequentCategory());
			return tree;
		}
		
		//It's a test node
		tree.setTest(ContinousTest.chooseTest(es));
		
		ExamplesSet sTrue = es.getExamplesSubsetForSpecifiedTestAndResult(tree.getTest(), true);
		tree.addChild(true, buildTheTree(sTrue, tree));
		
		ExamplesSet sFalse = es.getExamplesSubsetForSpecifiedTestAndResult(tree.getTest(), false);
		tree.addChild(false, buildTheTree(sFalse, tree));

		return tree;
	}
	
	public static void trimTree(Node root, ExamplesSet ts) {
		double error = root.getClassificationError(ts);
		Vector<Node> nodes = new Vector<Node>();
		root.getAllTestNodes(nodes);
		for (Node n : nodes) {
			if (n.getParent()==null) continue;
			Category subtreeCat = n.getSubtreeCategory();
			if (subtreeCat == null) continue;
			
			Node parent = n.getParent();
			Node temp = new Node(parent);
			parent.replaceChild(n, temp);
			temp.setCategorySilently(subtreeCat);
			double newError = root.getClassificationError(ts);
			if (newError <= error) {
				temp.removeCategoryCount(n.getCategoryCount());
			}
			else {
				parent.replaceChild(temp, n);
			}
		}
	}
	
	
}
