package um.tests;

import javax.vecmath.Vector3d;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import simbad.gui.Simbad;
import simbad.sim.EnvironmentDescription;
import um.env.EnvFactory;
import um.old.MiniRobot;


public class EnvFactoryTest {


	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadFromImageFile() {
		EnvironmentDescription ed = EnvFactory.readFromImageFile("map.png");
		ed.add(new MiniRobot(new Vector3d(0, 0, 0), "Testing robot", 1));
		new Simbad(ed, false);
//		fail("Not yet implemented");
		
	}

}
