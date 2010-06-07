package um.robots;

import java.text.DecimalFormat;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.DefaultKinematic;
import simbad.sim.RangeSensorBelt;
import um.common.Dbg;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.Node;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;

/**
 * The robot that will be used as a teacher
 * @author piotrrr
 *
 */
public class DecisionTreeRobot extends Agent {
	
	DecimalFormat format;
	
	float scale;
	
	RangeSensorBelt sonars;
	
	RobotOutput rot = new RobotOutput(0, 0, 90);
	
	RobotOutput speed = new RobotOutput(100, 1, 200);
	
	String treeFilename = null;
	
	Node dt = null;
	
	
	public DecisionTreeRobot(Vector3d pos, String name, float scale, String treeFilename) {
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
		
        if (treeFilename != null) {
        	this.treeFilename = treeFilename;
        	dt = Node.readFromFile(treeFilename);
        }
	}
	
 
	
	
	void addSonarBeltSensor()
	  {
		int nbsensors = 10;
		
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
        
        setSensorPositionAndDirection(positions, directions, 2*angle, 6, true);
        setSensorPositionAndDirection(positions, directions, 2*angle, 7, false);
        
        setSensorPositionAndDirection(positions, directions, -2*angle, 8, true);
        setSensorPositionAndDirection(positions, directions, -2*angle, 9, false);
        
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
	
	@Override
	protected void performBehavior() {
		super.performBehavior();
		
		double [] measures = new double [sonars.getNumSensors()];
		for (int i=0; i<measures.length; i++) measures[i]=sonars.getMeasurement(i);
		RobotAttribute attr = new RobotAttribute(measures);
		RobotCategory cat = (RobotCategory) dt.classify(attr);
		
		setTranslationalVelocity(cat.getSpeed());
		setRotationalVelocity(cat.getRot());
		
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
