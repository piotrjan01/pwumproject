package um.old;

import javax.vecmath.Vector3d;

import simbad.sim.Arch;
import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

public class LabHighWayEnv extends EnvironmentDescription {
	
	public static final float SCALE = 1/50f;

	public LabHighWayEnv() {
		setWorldSize(500*SCALE);
		add(new Arch(new Vector3d(3, 0, -3), this));
		add(new Wall(new Vector3d(0, 0, 0), 6, 1, 1, this));
		add(new MiniRobot(new Vector3d(0, 0, 0), "Testing robot", SCALE));
	}
	
}
