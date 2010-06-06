package um.tests;


import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import um.tree.ContinousTest;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;


public class ContinousTestTest {

	public ExamplesSet s;

	@Test
	public void chooseTest() {
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

		Vector<ContinousTest> bl = new Vector<ContinousTest>();
		ContinousTest t1 = (ContinousTest) ContinousTest.chooseTest(s, bl);
		bl.add(t1);
		ContinousTest t2 = (ContinousTest) ContinousTest.chooseTest(s, bl);
		
		assertFalse(t1.equals(t2));
	
	}
	
}
