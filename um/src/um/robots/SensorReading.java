package um.robots;

public class SensorReading {
	double lowerAngle;
	double higherAngle;
	double ang;
	double dist;
	public SensorReading(double ang, double dist, double lowerAngle, double higherAngle) {
		this.ang = normalizeAngle(ang);
		this.dist = dist;
		this.lowerAngle = normalizeAngle(lowerAngle);
		this.higherAngle = normalizeAngle(higherAngle);
	}
	
	double normalizeAngle(double ang) {
		if (ang > Math.PI) return ang-Math.PI*2;
		return ang;
	}
	
}
