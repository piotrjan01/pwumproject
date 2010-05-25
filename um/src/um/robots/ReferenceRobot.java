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
public class ReferenceRobot extends Agent {
	
	/**
	 * The minimal distance in any direction from the robot
	 */
	RobotMeasure omniMinDist = new RobotMeasure(0, 0.5, 3);
	
	DecimalFormat format;
	
	float scale;
	
	RangeSensorBelt sonars;
	
	RobotAttribute rot = new RobotAttribute(0, 0, 70);
	
	RobotAttribute speed = new RobotAttribute(100, 1, 200);


	public ReferenceRobot(Vector3d pos, String name, float scale) {
		super(pos, name);
		this.scale = scale;
		setColor(new Color3f(255, 255, 255));
		
		height = 1.5f*scale;
		radius = 2f*scale;
		mass = 1000f*scale;
		
		sonars = RobotFactory.addSonarBeltSensor(this, 18);
		sonars.setUpdateOnEachFrame(true);
		
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
	
	SensorReading getMaxFrontDistAngle() {
		double max = Double.MIN_VALUE;
		double angle = 0;
		int mi=-1;
		for (int i=0; i<sonars.getNumSensors(); i++) {
			double sa = sonars.getSensorAngle(i);
			if (sa >= Math.PI*3/2 || sa <= Math.PI/2) {
				if (sonars.getMeasurement(i) > max){
					max = sonars.getMeasurement(i);
					angle = sonars.getSensorAngle(i);
					mi = i;
				}
			}
		}
		int hi = mi+1;
		int li = mi-1;
		if (li < 0) li = sonars.getNumSensors()-1;
		if (hi>=sonars.getNumSensors()) hi=0;
		return new SensorReading(angle, max, sonars.getSensorAngle(li), sonars.getSensorAngle(hi));
	}
	
	SensorReading getMinFrontDistAngle() {
		double min = Double.MAX_VALUE;
		double angle = 0;
		int mi=-1;
		for (int i=0; i<sonars.getNumSensors(); i++) {
			double sa = sonars.getSensorAngle(i);
			if (sa >= Math.PI*3/2 || sa <= Math.PI/2) {
				if (sonars.getMeasurement(i) < min){
					min = sonars.getMeasurement(i);
					angle = sonars.getSensorAngle(i);
					mi = i;
				}
				
			}
		}
		int hi = mi+1;
		int li = mi-1;
		if (li < 0) li = sonars.getNumSensors()-1;
		if (hi>=sonars.getNumSensors()) hi=0;
		return new SensorReading(angle, min, sonars.getSensorAngle(li), sonars.getSensorAngle(hi));
	}
	
	double getMinLeft() {
		return getMinDist(Math.PI/4, Math.PI*3/4);
	}
	
	double getMinRight() {
		return getMinDist(Math.PI*5/4, Math.PI*7/4);
	}
	
	double getMinFront() {
		return Math.min(getMinDist(0, Math.PI/4), getMinDist(Math.PI*7/4, Math.PI*2));
	}
	
	
	@Override
	protected void performBehavior() {
		super.performBehavior();
		omniMinDist.val = getMinDist(0, 2*Math.PI);
		SensorReading max = getMaxFrontDistAngle();
		
		if ( ! omniMinDist.isOk()) {
			speed.setValuePercent(omniMinDist.getMeasureRating()*0.5);
			SensorReading min = getMinFrontDistAngle();
			double newang;
			if (min.ang < max.ang) newang = max.higherAngle;
			else newang = max.lowerAngle;

			Dbg.prn("min="+Math.toDegrees(min.ang)+" max="+Math.toDegrees(max.ang)+" newang="+Math.toDegrees(newang));
			
			if (newang > 0) {
				Dbg.prn("close, going left.");
				rot.setValuePercent(1);
			}
			else if (newang < 0) {
				Dbg.prn("close, going right.");
				rot.setValuePercent(-1);
			}
			else {
				Dbg.prn("close, going ok");
				rot.setValuePercent(0);
			}
			
		}
		else {
			speed.setValuePercent(1);
			if (max.ang > 0) {
				Dbg.prn("going left.");
				rot.setValuePercent(1);
			}
			else if (max.ang < 0) {
				Dbg.prn("going right.");
				rot.setValuePercent(-1);
			}
			else {
				rot.setValuePercent(0);
			}
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
	      "rotation   \t= " + format.format(Math.toDegrees(rot)) + " deg/s\n"+
	      "leftDist: \t= "+format.format(getMinLeft()) + "m\n"+
	      "rightDist: \t= "+format.format(getMinRight()) + "m\n"+
	      "frontDist: \t= "+format.format(getMinFront())+ "m\n";
	}

}
