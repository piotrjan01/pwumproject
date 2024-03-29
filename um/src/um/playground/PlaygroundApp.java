package um.playground;

import simbad.gui.Simbad;
import simbad.sim.EnvironmentDescription;
import um.env.EnvFactory;
import um.robots.DecisionTreeRobot;
import um.robots.ReferenceRobot;
import um.robots.TeacherRobot;

/**
 * The main class for the application
 * @author piotrrr
 *
 */
public class PlaygroundApp {
	public static void main(String[] args) {
		float scale = 1/50f;
		EnvFactory.wallHeight *= scale;
		String map = "map3.png";
		EnvironmentDescription ed = EnvFactory.readFromImageFile(map);
//		ed.add(new TeacherRobot(EnvFactory.getRobotStartPosFromImageFile(map), "Ref. robot", scale, "test-examples.dat"));
		ed.add(new DecisionTreeRobot(EnvFactory.getRobotStartPosFromImageFile(map), "DT robot", scale, "test-tree.tre"));
		
		new Simbad(ed, false);
	}

}
