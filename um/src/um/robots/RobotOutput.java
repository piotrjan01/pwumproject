package um.robots;

public class RobotOutput {
	
	public double val;
	
	public double minVal;
	
	public double maxVal;
	
	public RobotOutput(double initVal, double minVal, double maxVal) {
		val = initVal;
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
	public void setValuePercent(double percent) {
		val = minVal + (maxVal-minVal)*percent;
	}

}
