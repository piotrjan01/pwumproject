package um.tree;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

public class ExamplesSet {
	
	Vector<Example> examples;
	
	/**
	 * Remembers how many examples of given category there are
	 */
	HashMap<Category, Integer> categoryCount;
	
	public ExamplesSet() {
		examples = new Vector<Example>();
		categoryCount = new HashMap<Category, Integer>();
	}
	
	/**
	 * Adds example to example set
	 * @param e example
	 */
	public void addExample(Example e) {
		if (categoryCount.containsKey(e.cat)) {
			Integer i = categoryCount.get(e.cat);
			i++;
			categoryCount.put(e.cat, i);
		}
		else categoryCount.put(e.cat, 1);
		examples.add(e);
	}
	
	/**
	 * @return the most frequent category in example set
	 */
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
	
	/**
	 * Checks if majority of examples are of one category
	 * @param tolerance the tolerance with which we check
	 * @return
	 */
	public boolean isMajorityOfOneCategory(double tolerance) {
		double minCount = examples.size()*(1-tolerance);
		for (Category cat : categoryCount.keySet()) {
			if (categoryCount.get(cat) > minCount) return true;
		}
		return false;
	}
	
	/**
	 * Returns all the categories in the examples set
	 * @return
	 */
	public Vector<Category> getAllCategories() {
		Vector<Category> ret = new Vector<Category>();
		for (Category c : categoryCount.keySet()) ret.add(c);
		return ret;
	}
	
	/**
	 * 
	 * @param cat the category of examples
	 * @return the count of the examples with a given category
	 */
	public int getExamplesCountWithCategory(Category cat) {
		if (categoryCount.containsKey(cat)) {
			return categoryCount.get(cat);
		}
		else return 0;
	}
	
	/**
	 * Returns the number of examples that under given test have a specified result
	 * @param test the test to apply on each example
	 * @param result the result that is expected after applying the test
	 * @return the number of examples that after applying the test give a result
	 */
	public int getExamplesCountWithSpecifiedTestAndResult(ContinousTest test, boolean result) {
		int ret = 0;
		for (Example e : examples) {
			if (test.testAttribute(e.attr) == result) ret++;
		}
		return ret;
	}
	
	/**
	 * Returns the number of examples of given category that under given test have a specified result
	 * @param test the test to apply on each example
	 * @param result the result that is expected after applying the test
	 * @param cat the category
	 * @return the number of examples of category cat that after applying the test give a result
	 */
	public int getExamplesCountWithSpecifiedCategoryTestAndResult(Category cat, ContinousTest test, boolean result) {
		int ret = 0;
		for (Example e : examples) {
			if ( ! e.cat.equals(cat)) continue;
			if (test.testAttribute(e.attr) == result) ret++;
		}
		return ret;
	}
	
	public double getSetEnthropyWithSpecifiedTestAndResult(ContinousTest test, boolean result) {
		double ret = 0;
		for (Category cat : getAllCategories()) {
			double ptrd = getExamplesCountWithSpecifiedCategoryTestAndResult(cat, test, result);
			double ptr = getExamplesCountWithSpecifiedTestAndResult(test, result);
			double x = ptrd/ptr;
			if (x == 0) continue;
			ret = ret - x*Math.log(x);
		}
		return ret;
	}
	
	public double getSetEnthropyWithSpecifiedTest(ContinousTest test) {
		double countP = examples.size();
		
		double ptr1 = getExamplesCountWithSpecifiedTestAndResult(test, true);
		double etr1 = getSetEnthropyWithSpecifiedTestAndResult(test, true); //NaN !!
		
		double ptr2 = getExamplesCountWithSpecifiedTestAndResult(test, false);
		double etr2 = getSetEnthropyWithSpecifiedTestAndResult(test, false); //NaN !!
		
		return (ptr1*etr1/countP)+(ptr2*etr2/countP);
	}
	
	public double getMinAttrVal(int attrIndex) {
		double ret = Double.MAX_VALUE;
		for (Example e : examples) {
			if (e.attr.getAttributeValue(attrIndex) < ret)
				ret = e.attr.getAttributeValue(attrIndex);
		}
		return ret;
	}
	
	public double getMaxAttrVal(int attrIndex) {
		double ret = Double.MIN_VALUE;
		for (Example e : examples) {
			if (e.attr.getAttributeValue(attrIndex) > ret)
				ret = e.attr.getAttributeValue(attrIndex);
		}
		return ret;
	}
	
	public double [] getAscSortedAttributes(int atrInd) {
		TreeSet<Double> s = new TreeSet<Double>();
		for (Example e : examples) {
			s.add(e.attr.getAttributeValue(atrInd));
		}
		double [] ret = new double[s.size()];
		int i=0;
		for (Double d : s) ret[i++] = d; 
		return ret;
	}
	
	public ExamplesSet getExamplesSubsetForSpecifiedTestAndResult(ContinousTest test, boolean result) {
		ExamplesSet ret = new ExamplesSet();
		for (Example e : examples) {
			if (test.testAttribute(e.attr) != result) continue;
			ret.addExample(e);
		}
		return ret;
	}

	public Vector<Example> getExamples() {
		return examples;
	}
	
	
	

}
