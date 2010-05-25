package um.env;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;
import um.common.Dbg;

/**
 * Creates an environment from the PNG image
 * @author piotrrr
 *
 */
public class EnvFactory {
	
	public static float wallThickness = 0.1f;
	
	public static float wallHeight = 2f;
	
	/**
	 * Image must be PNG or JPG.
	 * Each pixel on the image corresponds to 10cm in the robot's world.
	 * One meter is 10 pixels.
	 * 
	 * 
	 * 
	 * @param path
	 * @return
	 */
	public static EnvironmentDescription readFromImageFile(String path) {
		EnvironmentDescription ret = new EnvironmentDescription();
		
		SimpleImage img = new SimpleImage(path, false);
		int h = img.getHeight();
		int w = img.getWidth();
		int size = Math.max(w, h);
		//! We don't check if image is a square...
		ret.setWorldSize(size/10);
		Dbg.prn("world size = "+ret.worldSize);
		Dbg.prn("w x h = "+w+" x "+h);
		
		int pix = img.getRawPixel(0, 0);
		
		Dbg.prn("pix at 0,0: "+pix);
		
		
		for (int y=0; y<h; y++) {
			for (int x = 0; x<w; x++) {
				if (img.isBlack(x, y)) 
					detectAndAddWall(ret, img, x, y);
				
			}
		}
		
		return ret;
	}
	
	public static Vector3d getRobotStartPosFromImageFile(String path) {
		SimpleImage img = new SimpleImage(path, false);
		int h = img.getHeight();
		int w = img.getWidth();
		int size = Math.max(w, h);
		for (int y=0; y<h; y++) {
			for (int x = 0; x<w; x++) {
				if (img.isGreen(x, y)) 
				return new Vector3d((x-size/2f)/10f, 0, (y-size/2f)/10f);
			}
		}
		return new Vector3d(0,0,0);
	}
	
	//TODO: naprawic to!
	// huh?
	private static void detectAndAddWall(EnvironmentDescription ed, SimpleImage img, float x, float y) {
		Dbg.prn("Wall detected @ "+x+", "+y);
		float scoreE, scoreS;
		scoreE = scoreS = 0;
		boolean goE, goS;
		goE = goS = true;
		
		int d = 0;
		while (goE || goS ){
			if (img.isBlack(x+d, y)) scoreE++;
			else goE = false;
			if (img.isBlack(x, y+d)) scoreS++;
			else goS = false;
			d++;
		}
		
		Dbg.prn("sE: "+scoreE+" sS: "+scoreS);
		
		float size = Math.max(img.getWidth(), img.getHeight());
		Vector3d trans = new Vector3d(-size/20, 0, -size/20);
		
		if (scoreE >= scoreS) {
			for (int i=0; i<scoreE; i++) img.setWhite(x+i, y);
			Dbg.prn("E");
			Vector3f ext = new Vector3f(scoreE/10f, wallThickness, wallThickness);
			Vector3d pos = new Vector3d((x+scoreE/2f)/10f, 0, y/10f+ext.z/2);
			pos.x += trans.x; pos.y += trans.y; pos.z += trans.z;
			ed.add(new Box(pos,ext, ed));
		}
		else {
			
			for (int i=0; i<scoreS; i++) img.setWhite(x, y+i);
			Dbg.prn("S");
			Vector3f ext = new Vector3f(wallThickness, wallThickness, scoreS/10f);
			Vector3d pos = new Vector3d(x/10f+ext.x/2, 0, (y+scoreS/2f)/10f);
			pos.x += trans.x; pos.y += trans.y; pos.z += trans.z;
			ed.add(new Box(pos,ext, ed));
		}
		
	}

}










