package um.tests;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.Vector;

import org.junit.Test;

import um.tree.ContinousTest;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.Node;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;
import um.tree.TreeBuilder;

public class TreeBuilderTest {

	private ExamplesSet s;
	
	private Random r = new Random();

	@Test
	public void testBuildTheTree() {
		s = new ExamplesSet();
		
		double [] atr1 = {1,2};
		double [] atr2 = {4,4};
		double [] atr3 = {3,2};
		double [] atr4 = {2,7};
		double [] atr5 = {3,2};
		
		RobotCategory c1 = new RobotCategory(1, 1);
		RobotCategory c2 = new RobotCategory(2, 2);
		
		s.addExample(new Example(new RobotAttribute(atr1), c1));
		s.addExample(new Example(new RobotAttribute(atr2), c1));
		s.addExample(new Example(new RobotAttribute(atr3), c2));
		s.addExample(new Example(new RobotAttribute(atr4), c2));
		s.addExample(new Example(new RobotAttribute(atr5), c2));
		
		TreeBuilder.buildTheTree(s, null);
		
//		System.out.println(root);
	
	}
	
	@Test
	public void bigTreeBuilderTest() {
		
		Vector<Example> ex = new Vector<Example>();
		for (int i=0; i<100; i++) {
			ex.add( new Example(new RobotAttribute(getRandDoubleTable(10)), 
					new RobotCategory(r.nextDouble(), r.nextDouble())));
		}
		
		ExamplesSet es = new ExamplesSet();
		
		for (Example e : ex) es.addExample(e);
		
		Node tree = TreeBuilder.buildTheTree(es, null);
		
		for (Example e : ex) {
			assertTrue(e.getCat().equals(tree.classify(e.getAttr())));
		}
		
	}
	
	private double [] getRandDoubleTable(int size) {
		double [] ret = new double [size];
		for (int i=0; i<size; i++) ret[i] = r.nextDouble();
		return ret;
	}

}
