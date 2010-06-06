package um.tree;

import java.util.Vector;


/**
 * The results of this test are true or false
 * @author piotrrr
 *
 */
public class ContinousTest extends BinaryTest {
	
	
	
	public static ContinousTest chooseTest(ExamplesSet examples) {
		AttributeList al = examples.examples.firstElement().attr; 
		int attrCount = al.getNumberOfAttributes();
		
		ContinousTest bestTest = null;
		double minEnthropy = Double.MAX_VALUE;
		
		for (int i=0; i<attrCount; i++) {
			double [] avs = examples.getAscSortedAttributes(i);
			for (int j=1; j<avs.length; j++) 
				avs[j-1] += (avs[j]-avs[j-1]) / 2;
			for (double d : avs) {
				ContinousTest t = new ContinousTest(d, i);
				double entr = examples.getSetEnthropyWithSpecifiedTest(t);
				if (entr < minEnthropy) {
					bestTest = t;
					minEnthropy = entr;
				}
			}
		}
		assert bestTest!=null;
		return bestTest;
	}
	
	/**
	 * The value that is tested against the attribute value ( ai(x) < theta)
	 */
	double theta;
	
	/**
	 * The index of the attribute value
	 */
	int index;
	
	public ContinousTest(double theta, int attributeIndex) {
		this.theta = theta;
		index = attributeIndex;
	}

	@Override
	public boolean testAttribute(AttributeList a) {
		try {
			if (a.getAttributeValue(index) < theta) return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object arg0) {
		ContinousTest t = (ContinousTest)arg0;
		if (t.index == index && t.theta == theta) return true;
		return false;
	}
	
	@Override
	public String toString() {
		
		return "attr"+index+" < "+theta;
	}

}
