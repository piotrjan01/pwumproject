package um.tree;

public class Node {
	
	static final int TRUE = 0;
	
	static final int FALSE = 1;

	static int nodesCount = 0;
	
	int id;
	
	Category cat = null;
	
	ContinousTest test = null;
	
	Node parent = null;
	
	Node [] children = { null, null};
	
	public Category getCat() {
		return cat;
	}

	public void setCategory(Category cat) {
		this.cat = cat;
	}

	public Node(Node parent) {
		this.parent = parent;
		id = nodesCount++;
	}
	
	public void addChild(boolean testResult, Node child) {
		if (testResult) children[TRUE] = child;
		else children[FALSE] = child;
		return;
	}
	
	public boolean hasChildren() {
		return (hasTrueChild() && hasFalseChild());
	}

    public boolean hasTrueChild() {
        return children[TRUE] != null;
    }

    public Node getTrueChild() {
        return children[TRUE];
    }

    public boolean hasFalseChild() {
        return children[FALSE] != null;
    }

    public Node getFalseChild() {
        return children[FALSE];
    }
    
    public Category classify(AttributeList al) {
    	if ( ! hasChildren() ) return cat;
    	boolean tr = test.testAttribute(al);
    	if ( tr ) return children[TRUE].classify(al);
    	else return children[FALSE].classify(al);
    }
    
    
	
	@Override
	public String toString() {
		String ch1 = (children[TRUE]==null ? "null" : ""+children[TRUE].id);
		String ch2 = (children[FALSE]==null ? "null" : ""+children[FALSE].id);
		return "id="+id+" test=("+test+") cat=("+cat+")";
	}
	
	
}
