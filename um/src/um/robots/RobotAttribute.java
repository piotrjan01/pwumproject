package um.robots;

public class RobotAttribute {
	
	public double val;
	
	public double minVal;
	
	public double maxVal;
	
	public RobotAttribute(double initVal, double minVal, double maxVal) {
		val = initVal;
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
	public void setValuePercent(double percent) {
		val = minVal + (maxVal-minVal)*percent;
	}

}
