package um.tree;

public class RobotAttribute implements Attribute {

	double [] measurements;
	
	public RobotAttribute(double [] measurements) {
		this.measurements = measurements.clone();
	}

	@Override
	public double getAttributeValue(int index) throws Exception {
		if (index < 0 || index >= measurements.length) throw new Exception("No such attribute index: "+index);
		return 0;
	}
	
	
}
