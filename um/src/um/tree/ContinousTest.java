package um.tree;


/**
 * The results of this test are true or false
 * @author piotrrr
 *
 */
public class ContinousTest extends BinaryTest {
	
	
	static BinaryTest chooseTest(ExamplesSet examples) {
		return null;
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
	public boolean testAttribute(Attribute a) {
		try {
			if (a.getAttributeValue(index) < theta) return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
