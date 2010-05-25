package um.old;

import java.text.DecimalFormat;

import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import simbad.sim.Agent;
import simbad.sim.DefaultKinematic;

public class MiniRobot extends Agent {
	
	DecimalFormat format;
	
	float scale;
	

	public MiniRobot(Vector3d pos, String name, float scale) {
		super(pos, name);
		this.scale = scale;
		setColor(new Color3f(255, 255, 255));
		
		//TODO: configurable and different shape!
		height = 1.5f*scale;
		radius = 2f*scale;
		mass = 1000f*scale;
		
		 // display format for numbers
        format = new DecimalFormat();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setPositivePrefix("+");
        format.setMinimumIntegerDigits(1);
		
	}
	
	@Override
	protected void performPreBehavior() {
		super.performPreBehavior();
	}
	
	@Override
	protected void initBehavior() {
		super.initBehavior();		
	}
	
	@Override
	protected void performBehavior() {
		
		
		super.performBehavior();
		if (collisionDetected()) {
			setTranslationalVelocity(0);
			setRotationalVelocity(0);
		}
		else {
			setTranslationalVelocity(0.99);
			if (getCounter() % 100 == 0) {
				setRotationalVelocity(Math.PI/4 * (0.5 - Math.random()) );
			}
		}
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
