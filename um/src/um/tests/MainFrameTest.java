package um.tests;

import um.gui.MainFrame;
import um.tree.Category;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.RobotAttribute;
import um.tree.TestingCategory;
import um.tree.TreeBuilder;

public class MainFrameTest {

	public static void main(String[] args) {
		testShowTheTree();
	}
	
	public static void testShowTheTree() {
		
		ExamplesSet s = new ExamplesSet();

        Category c1 = new TestingCategory("c1");
        Category c2 = new TestingCategory("c2");

        s.addExample(new Example(new RobotAttribute(new double [] {1, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {4, 4}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 4}), c1));
        s.addExample(new Example(new RobotAttribute(new double [] {3, 2}), c1));
        
        s.addExample(new Example(new RobotAttribute(new double [] {2, 5}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {7, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {2, 7}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {5, 6}), c2));
        s.addExample(new Example(new RobotAttribute(new double [] {9, 8}), c2));
        
        MainFrame.showTheTree(TreeBuilder.buildTheTree(s, null));
		
	}

}
