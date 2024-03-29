package um.tree;

public class RobotAttribute implements AttributeList {

	private static final long serialVersionUID = 1L;
	
	double [] measurements;
	
	public RobotAttribute(double [] measurements) {
		this.measurements = measurements.clone();
	}

	@Override
	public double getAttributeValue(int index) {
		if (index < 0 || index >= measurements.length) return 0;
		return measurements[index];
	}

	@Override
	public int getNumberOfAttributes() {
		return measurements.length;
	}
	
	@Override
	public boolean equals(Object obj) {
		RobotAttribute ral = (RobotAttribute)obj;
		if (ral.measurements.length != measurements.length) return false;
		for (int i=0; i<measurements.length; i++)
			if (ral.measurements[i] != measurements[i]) return false;
		return true;
	}
	
	@Override
	public String toString() {
		String r = "[ ";
		for (double d : measurements)
			r += d+", ";
		r += " ]";
		return r;
	}

	
}
