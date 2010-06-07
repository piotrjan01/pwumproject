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
		Node newNode = new Node(parent);
		if (es.isMajorityOfOneCategory(stopCriteriumTolerance)) {
			newNode.setCategory(es.getMostFrequentCategory());
			return newNode;
		}
		
		ContinousTest test = ContinousTest.chooseTest(es);
		//if we can't choose a good test, we mark it as a leaf
		if (test == null) {
			newNode.setCategory(es.getMostFrequentCategory());
			return newNode;
		}
		
		//It's a test node
		newNode.setTest(test);
		
		ExamplesSet sTrue = es.getExamplesSubsetForSpecifiedTestAndResult(test, true);
		newNode.addChild(true, buildTheTree(sTrue, newNode));
		
		ExamplesSet sFalse = es.getExamplesSubsetForSpecifiedTestAndResult(test, false);
		newNode.addChild(false, buildTheTree(sFalse, newNode));

		return newNode;
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
