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
	
	@Override
	public boolean equals(Object arg0) {
		
		RobotCategory c = (RobotCategory)arg0;
		if (c.speed == speed && c.rot == rot) return true;
		return false;
//		return true;
	}
	
	@Override
	public int hashCode() {
		return new Double(speed+rot+Math.PI).hashCode(); 
	}

    @Override
    public String toString() {
        return " rot="+rot+" speed="+speed;
    }



}
