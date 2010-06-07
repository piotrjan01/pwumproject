package um.tree;

import java.io.Serializable;

public class Example implements Serializable {
	
	private static final long serialVersionUID = 1L;

	AttributeList attr;
	
	Category cat;
	
	public Example(AttributeList a, Category c) {
		this.attr = a;
		this.cat = c;
	}

	public AttributeList getAttr() {
		return attr;
	}

	public Category getCat() {
		return cat;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Example e = (Example)arg0;
		if (e.attr.equals(attr) && e.cat.equals(cat)) return true;
		return false;
	}
	
	@Override
	public String toString() {
		return ""+attr+"\n"+cat;
	}
	

}
