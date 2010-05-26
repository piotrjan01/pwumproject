package um.tree;

public class RobotAttribute implements Attribute {

	double [] measurements;
	
	public RobotAttribute(double [] measurements) {
		this.measurements = measurements.clone();
	}
	
}
