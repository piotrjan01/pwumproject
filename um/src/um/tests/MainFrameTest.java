package um.tests;

import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.Vector;

import um.gui.MainFrame;
import um.tree.Category;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.Node;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;
import um.tree.TestingCategory;
import um.tree.TreeTools;

public class MainFrameTest {

	public static void main(String[] args) {
//		testShowTheTree();
//		trimTreeTest1();
		trimTreeTest2();
	}
	
	static Random r = new Random();
	
	public static void testShowTheTree() {
		
		ExamplesSet s = new ExamplesSet();

        Category c1 = new TestingCategory("c1");
        Category c2 = new TestingCategory("c2");

        s.addExample(new Example(new RobotAttribute(new double [] {1, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 4}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 4}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c2));
        
        s.addExample(new Example(new RobotAttribute(new double [] {2, 5}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {7, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 7}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {5, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {9, 8}), c2));
        
        MainFrame.showTheTree(TreeTools.buildTheTree(s, null));
		
	}
	
	public static void trimTreeTest1() {
		ExamplesSet s = new ExamplesSet();

        Category c1 = new TestingCategory("c1");
        Category c2 = new TestingCategory("c2");
        Category c3 = new TestingCategory("c3");

        s.addExample(new Example(new RobotAttribute(new double [] {1, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {1, 3}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {1, 4}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 1}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 2}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 3}), c3));
        s.addExample(new Example(new RobotAttribute(new double [] {1, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 4}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 4}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 5}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {7, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 7}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {5, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {9, 8}), c2));
        
        Node r = TreeTools.buildTheTree(s, null);
        
        MainFrame.showTheTree(r);
        
        TreeTools.trimTree(r, s);
        
        MainFrame.showTheTree(r);
	}
	
	
	public static void trimTreeTest2() {
		System.out.println("Starting...");
		Vector<Example> ex = new Vector<Example>();
		for (int i=0; i<300; i++) {
			ex.add( new Example(new RobotAttribute(getRandDoubleTable(2)), 
					new RobotCategory(r.nextInt(1), r.nextInt(1))));
		}
		for (int i=0; i<80; i++) {
			ex.add( new Example(new RobotAttribute(getRandDoubleTable(2)), 
					new RobotCategory(r.nextInt(2), r.nextInt(2))));
		}
		
		ExamplesSet es = new ExamplesSet();
		ExamplesSet ts = new ExamplesSet();
		
		for (int i=0; i<ex.size(); i++) {
			if (i % 3 == 0)
				ts.addExample(ex.elementAt(i));
			else es.addExample(ex.elementAt(i));
		}
		
		Node tree = TreeTools.buildTheTree(es, null);
		
		double error = tree.getClassificationError(ts);
		Vector<Node> ns = new Vector<Node>();
		tree.getAllTestNodes(ns);
		System.out.println("Error = "+error);
		System.out.println("nodes = "+ns.size());
		
		TreeTools.trimTree(tree, ts);
		error = tree.getClassificationError(ts);
		System.out.println("Error after= "+error);
		ns = new Vector<Node>();
		tree.getAllTestNodes(ns);
		System.out.println("nodes = "+ns.size());
		
		MainFrame.showTheTree(tree);
		
	}
	
	static private double [] getRandDoubleTable(int size) {
		double [] ret = new double [size];
		for (int i=0; i<size; i++) ret[i] = r.nextDouble();
		return ret;
	}

}
