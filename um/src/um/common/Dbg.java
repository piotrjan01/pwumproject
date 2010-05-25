package um.common;

/**
 * Some static debug methods
 * @author Piotr Gwizda³a
 */
public class Dbg {
	
	public static boolean debug = false;

	public static void prn(Object src, Object o) {
		if (!debug) return;
		Dbg.prn(src.getClass().toString()+": "+o.toString());
	}
	
	public static void prn(Object o) {
		if (!debug) return;
		System.out.println(o.toString());
	}
	
	public static void err(Object o) {
		if (!debug) return;
		System.err.println(o.toString());
	}
	
}
