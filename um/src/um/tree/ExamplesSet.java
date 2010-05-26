package um.tree;

import java.util.HashMap;
import java.util.Vector;

public class ExamplesSet {
	
	Vector<Example> examples;
	
	HashMap<Category, Integer> categoryCount;
	
	public ExamplesSet() {
		examples = new Vector<Example>();
		categoryCount = new HashMap<Category, Integer>();
	}
	
	public void addExample(Example e) {
		if (categoryCount.containsKey(e.cat)) {
			Integer i = categoryCount.get(e.cat);
			i++;
		}
		else categoryCount.put(e.cat, 1);
		examples.add(e);
	}
	
	public Category getMostFrequentCategory() {
		Category cat = null;
		int max = -1;
		for (Category c : categoryCount.keySet()) {
			if (categoryCount.get(c) > max) { 
				cat = c;
				max = categoryCount.get(c);
			}
		}
		return cat;
	}
	
	public boolean isMajorityOfOneCategory(double tolerance) {
		double minCount = examples.size()*(1-tolerance);
		for (Category cat : categoryCount.keySet()) {
			if (categoryCount.get(cat) > minCount) return true;
		}
		return true;
	}

}
