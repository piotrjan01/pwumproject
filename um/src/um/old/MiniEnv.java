package um.old;

import javax.vecmath.Vector3d;

import simbad.sim.Arch;
import simbad.sim.EnvironmentDescription;

public class MiniEnv extends EnvironmentDescription {

	public MiniEnv() {
		setUsePhysics(true);
		add(new Arch(new Vector3d(3, 0, -3), this));
		add(new MiniRobot(new Vector3d(0, 0, 0), "Testing robot", 1));
	}
	
}
