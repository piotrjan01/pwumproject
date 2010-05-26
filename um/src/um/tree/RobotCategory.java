package um.tree;

public class RobotCategory implements Category {
	
	double speed;
	
	double rot;
	
	public RobotCategory(double speed, double rot) {
		this.speed = speed;
		this.rot = rot;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getRot() {
		return rot;
	}

	public void setRot(double rot) {
		this.rot = rot;
	}

}
