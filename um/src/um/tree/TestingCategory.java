package um.tree;

public class TestingCategory implements Category {
	
	String name;
	
	public TestingCategory(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		TestingCategory tc = (TestingCategory) obj;
		if (tc.name.equals(name)) return true;
		return false;
	}
	
}
