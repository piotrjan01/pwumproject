package um.tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import um.tree.Category;
import um.tree.ContinousTest;
import um.tree.Example;
import um.tree.ExamplesSet;
import um.tree.RobotAttribute;
import um.tree.RobotCategory;

public class ExamplesSetTest {
	
	ExamplesSet s;
	
	Random r = new Random();
	
	@Before
	public void before() {
		
	}

	@Test
	public void testGetMostFrequentCategory() {
		
		s = new ExamplesSet();
		
		Category c = s.getMostFrequentCategory();
		
		assertTrue(c == null);
		
		for (int i=0; i<30; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						new RobotCategory(r.nextDouble(), r.nextDouble())
			));
		
		RobotCategory cat = new RobotCategory(r.nextDouble(), r.nextDouble());
		for (int i=0; i<15; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						cat
			));
		
		cat = new RobotCategory(r.nextDouble(), r.nextDouble());
		for (int i=0; i<16; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						cat
			));
		
		c = s.getMostFrequentCategory();
		
		assertTrue(c.equals(cat));
		
		cat = new RobotCategory(r.nextDouble(), r.nextDouble());
		for (int i=0; i<15; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						cat
			));
		
		c = s.getMostFrequentCategory();
		
		assertTrue( ! c.equals(cat));
		
	}

	@Test
	public void testIsMajorityOfOneCategory() {
		s = new ExamplesSet();
		
		assertTrue( ! s.isMajorityOfOneCategory(1));
		assertTrue( ! s.isMajorityOfOneCategory(0));
		
		for (int i=0; i<70; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						new RobotCategory(r.nextDouble(), r.nextDouble())
			));
		
		RobotCategory cat = new RobotCategory(r.nextDouble(), r.nextDouble());
		for (int i=0; i<30; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						cat
			));
		//15 : 30
		assertTrue( ! s.isMajorityOfOneCategory(0.2));
		assertTrue( ! s.isMajorityOfOneCategory(0.69));
		assertTrue( s.isMajorityOfOneCategory(0.71));
	
	}
	
	@Test
	public void getExamplesCountWithCategory() {
		s = new ExamplesSet();
		RobotCategory cat = new RobotCategory(r.nextDouble(), r.nextDouble());
		
		assertTrue(s.getExamplesCountWithCategory(cat) == 0);
		assertTrue(s.getExamplesCountWithCategory(null) == 0);
		
		for (int i=0; i<70; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						new RobotCategory(r.nextDouble(), r.nextDouble())
			));
		
		
		for (int i=0; i<30; i++) 
			s.addExample(new Example(
						new RobotAttribute(getRandDoubleTable(10)),
						cat
			));
		
		assertTrue(s.getExamplesCountWithCategory(cat) == 30);
		cat = new RobotCategory(r.nextDouble()+5, r.nextDouble()+5);
		assertTrue(s.getExamplesCountWithCategory(cat) == 0);
		
		s.addExample(new Example(new RobotAttribute(getRandDoubleTable(10)), cat));
		assertTrue(s.getExamplesCountWithCategory(cat) == 1);
	}
	
	@Test
	public void getExamplesCountWithSpecifiedTestAndResult() {
		
		for (int j=0; j<10; j++) {
			
			s = new ExamplesSet();
			
			RobotAttribute a1 = new RobotAttribute(getRandDoubleTable(10));
			RobotAttribute a2 = new RobotAttribute(getRandDoubleTable(10));
			int ai = r.nextInt(10);
			double a1av=0;
			double a2av=0;
			try {
				a1av = a1.getAttributeValue(ai);
				a2av = a2.getAttributeValue(ai);
			} catch (Exception e) {
				fail("Too many attributes in test?");
				e.printStackTrace();
			}
			
			ContinousTest t = new ContinousTest(Math.abs(a1av-a2av), ai);
			
			boolean a1tr = t.testAttribute(a1);
			boolean a2tr = t.testAttribute(a2);
			
			assertTrue(0 == s.getExamplesCountWithSpecifiedTestAndResult(t, a1tr));
			
			for (int i=0; i<70; i++) 
				s.addExample(new Example(
							a1,
							new RobotCategory(r.nextDouble(), r.nextDouble())
				));
			
			for (int i=0; i<30; i++) 
				s.addExample(new Example(
							a2,
							new RobotCategory(r.nextDouble()+5, r.nextDouble()+5)
				));
			
			if (a1tr == a2tr)
				assertTrue(100 == s.getExamplesCountWithSpecifiedTestAndResult(t, a1tr));
			else {
				assertTrue(30 == s.getExamplesCountWithSpecifiedTestAndResult(t, a1tr));
				assertTrue(70 == s.getExamplesCountWithSpecifiedTestAndResult(t, a2tr));
			}
		}
		
	}
	
	
	@Test
	public void getExamplesCountWithSpecifiedCategoryTestAndResult() {
		for (int j=0; j<10; j++) {
			
			s = new ExamplesSet();
			
			RobotAttribute a1 = new RobotAttribute(getRandDoubleTable(10));
			RobotAttribute a2 = new RobotAttribute(getRandDoubleTable(10));
			int ai = r.nextInt(10);
			double a1av=0;
			double a2av=0;
			try {
				a1av = a1.getAttributeValue(ai);
				a2av = a2.getAttributeValue(ai);
			} catch (Exception e) {
				fail("Too many attributes in test?");
				e.printStackTrace();
			}
			
			ContinousTest t = new ContinousTest(Math.abs(a1av-a2av), ai);
			
			boolean a1tr = t.testAttribute(a1);
			boolean a2tr = t.testAttribute(a2);
			
			RobotCategory cat = new RobotCategory(r.nextDouble(), r.nextDouble());
			
			
			assertTrue(0 == s.getExamplesCountWithSpecifiedCategoryTestAndResult(cat, t, a1tr));
			
			for (int i=0; i<70; i++) 
				s.addExample(new Example(
							a1,
							new RobotCategory(r.nextDouble(), r.nextDouble())
				));
			for (int i=0; i<70; i++) 
				s.addExample(new Example(
							a1,
							cat
				));
			
			for (int i=0; i<30; i++) 
				s.addExample(new Example(
							a2,
							new RobotCategory(r.nextDouble()+5, r.nextDouble()+5)
				));
			for (int i=0; i<30; i++) 
				s.addExample(new Example(
							a2,
							cat
				));
			
			if (a1tr == a2tr)
				assertTrue(100 == s.getExamplesCountWithSpecifiedCategoryTestAndResult(cat, t, a1tr));
			else {
				assertTrue(30 == s.getExamplesCountWithSpecifiedCategoryTestAndResult(cat, t, a1tr));
				assertTrue(70 == s.getExamplesCountWithSpecifiedCategoryTestAndResult(cat, t, a2tr));
			}
		}
	}
	
	@Test
	public void getSetEnthropyWithSpecifiedTestAndResult() {
		s = new ExamplesSet();
		
		double [] atr1 = {1,2};
		double [] atr2 = {4,4};
		double [] atr3 = {3,2};
		double [] atr4 = {2,1};
		double [] atr5 = {3,2};
		
		RobotCategory c1 = new RobotCategory(1, 1);
		RobotCategory c2 = new RobotCategory(2, 2);
		
		s.addExample(new Example(new RobotAttribute(atr1), c1));
		s.addExample(new Example(new RobotAttribute(atr2), c1));
		s.addExample(new Example(new RobotAttribute(atr3), c2));
		s.addExample(new Example(new RobotAttribute(atr4), c2));
		s.addExample(new Example(new RobotAttribute(atr5), c2));
		
		ContinousTest test = new ContinousTest(3, 0);
		
		double et1 = -0.5*Math.log(0.5)-0.5*Math.log(0.5);
		double et2 = -1d/3d*Math.log(1d/3d)-2d/3d*Math.log(2d/3d);
		
		double et1c = s.getSetEnthropyWithSpecifiedTestAndResult(test, true);
		double et2c = s.getSetEnthropyWithSpecifiedTestAndResult(test, false);
		
		assertTrue(et1c == et1);
		assertTrue(et2c == et2);
		
	}
	
	@Test
	public void getSetEnthropyWithSpecifiedTest() {
		fail("to do");
	}
	
	
	
	
	
	
	
	
	private double [] getRandDoubleTable(int size) {
		double [] ret = new double [size];
		for (int i=0; i<size; i++) ret[i] = r.nextDouble();
		return ret;
	}
	

}
