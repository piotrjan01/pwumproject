package um.robots;

public class RobotMeasure {

	double val;
	
	double minVal;
	
	double minOkVal;
	
	
	public RobotMeasure(double val,	double minVal,	double minOkVal) {
		this.val = val;
		this.minVal = minVal;
		this.minOkVal = minOkVal;
	}
	
	public boolean isOk() {
		if (val >= minOkVal) return true;
		return false;
	}
	
	public double getMeasureRating() {
		if (val <= minVal) return 0;
		if (val > minVal && val <= minOkVal) {
			return (val-minVal)/(minOkVal-minVal);
		}
		return 1;
		
	}
	
}
