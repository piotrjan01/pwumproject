package um.robots;

import java.text.DecimalFormat;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.DefaultKinematic;
import simbad.sim.RangeSensorBelt;
import simbad.sim.RobotFactory;
import um.common.Dbg;

/**
 * The robot that will be used as a teacher
 * @author piotrrr
 *
 */
public class TeacherRobot extends Agent {
	
	/**
	 * The minimal distance in any direction from the robot
	 */
	RobotMeasure omniMinDist = new RobotMeasure(0, 0.5, 3);
	
	DecimalFormat format;
	
	float scale;
	
	RangeSensorBelt sonars;
	
	RobotAttribute rot = new RobotAttribute(0, 0, 70);
	
	RobotAttribute speed = new RobotAttribute(100, 1, 200);
	
	enum Dir { FF, FL, FR };
	
	void addSonarBeltSensor()
	  {
		int nbsensors = 6;
		
		Vector3d [] positions = new Vector3d[nbsensors];
		Vector3d [] directions = new Vector3d[nbsensors];
		
		double h = this.getHeight();
        
		double angle = Math.PI/6;
		
        setSensorPositionAndDirection(positions, directions, 0, 0, true);
        setSensorPositionAndDirection(positions, directions, 0, 1, false);
        
        setSensorPositionAndDirection(positions, directions, angle, 2, true);
        setSensorPositionAndDirection(positions, directions, angle, 3, false);

        setSensorPositionAndDirection(positions, directions, -angle, 4, true);
        setSensorPositionAndDirection(positions, directions, -angle, 5, false);
        
		sonars = new RangeSensorBelt(positions, directions, RangeSensorBelt.TYPE_SONAR, RangeSensorBelt.FLAG_SHOW_FULL_SENSOR_RAY);
		sonars.setUpdateOnEachFrame(true);    
	    
	    Vector3d pos = new Vector3d(0.0D, h*1.1, 0.0D);
	    this.addSensorDevice(sonars, pos, 0.0D);
	    return;
	  }
	
	void setSensorPositionAndDirection(Vector3d [] pos, Vector3d [] dir, double rotation, int arrayInd, boolean isLeft) {
	    double r = this.getRadius();
	    Transform3D tr = new Transform3D();
	    Vector3d thePos = new Vector3d(0, 0, 0);
        Vector3d frontDir = new Vector3d(1, 0, 0);
        
        if (isLeft) thePos.z = -r;
        else thePos.z = r;
        
            tr.setIdentity();
            tr.rotY(rotation);
            Vector3d p = new Vector3d(thePos);
            tr.transform(p);
            pos[arrayInd] = p;
            Vector3d d = new Vector3d(frontDir);
            tr = new Transform3D();
            tr.rotY(rotation);
            tr.transform(d);
            dir[arrayInd] = d;
	}


	public TeacherRobot(Vector3d pos, String name, float scale) {
		super(pos, name);
		this.scale = scale;
		setColor(new Color3f(255, 255, 255));
		
		height = 1.5f*scale;
		radius = 2f*scale;
		mass = 1000f*scale;
		
		addSonarBeltSensor();
		
		 // display format for numbers
        format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setPositivePrefix("+");
        format.setMinimumIntegerDigits(1);
		
	}
	
	double getMinDist(double minAngle, double maxAngle) {
		double min = Double.MAX_VALUE;
		for (int i=0; i<sonars.getNumSensors(); i++) {
			if (sonars.getSensorAngle(i) >= minAngle &&
					sonars.getSensorAngle(i) <= maxAngle) {
				if (sonars.getMeasurement(i) < min)
					min = sonars.getMeasurement(i);
			}
		}
		return min/scale;
	}
	
	Dir getBestDirection() {
		Dir ret = Dir.FF;
		double maxDist;
		
		maxDist = Math.min(sonars.getMeasurement(0), sonars.getMeasurement(1));
		
		if (Math.min(sonars.getMeasurement(2), sonars.getMeasurement(3)) > maxDist) {
			maxDist = Math.min(sonars.getMeasurement(2), sonars.getMeasurement(3));
			ret = Dir.FL;
		}
		
		if (Math.min(sonars.getMeasurement(4), sonars.getMeasurement(5)) > maxDist) {
			maxDist = Math.min(sonars.getMeasurement(4), sonars.getMeasurement(5));
			ret = Dir.FL;
		}
		
		return ret;
	}
	
	
	
	@Override
	protected void performBehavior() {
		super.performBehavior();
		
		Dir dir = getBestDirection();
		
		Dbg.prn("direction: "+dir);
		
		switch (dir) {
		case FF:
			rot.setValuePercent(0);
			break;
		case FL:
			rot.setValuePercent(-1);
			break;
		case FR:
			rot.setValuePercent(1);
			break;
		}
		
		setTranslationalVelocity(speed.val*scale/3.6);
		setRotationalVelocity(Math.toRadians(rot.val));
	}
	
	@Override
	public String asString() {
	       Transform3D transf = new Transform3D();
	       getTranslationTransform(transf);
	       Vector3d t = new Vector3d();
	       transf.get(t);
	       double speed = ((DefaultKinematic)getKinematicModel()).getTranslationalVelocity();
	       double rot = ((DefaultKinematic)getKinematicModel()).getRotationalVelocity();
	       
	      return 
	      "class = " + this.getClass().getName() + "\n" + 
	      "name          \t= " + name + "\n" + 
	      "counter       \t= " + getCounter() + "\n" +
	      "lifetime      \t= " + format.format(getLifeTime()) + " s\n" +
	      "collision     \t= " + this.collisionDetected()+ "\n" +
	      "x             \t= " + format.format(t.x / scale) + " m\n" +
	      "y             \t= " + format.format(t.y / scale) + " m\n" +
	      "z             \t= " + format.format(t.z / scale) + " m\n"+
	      "odometer      \t= " + format.format(odometer  / scale) + " m\n" +
	      "speed      \t= " + format.format(3.6*speed / scale) + " km/h\n"+
	      "rotation   \t= " + format.format(Math.toDegrees(rot)) + " deg/s\n";
	}

}
