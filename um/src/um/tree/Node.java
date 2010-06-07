package um.tree;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

public class Node implements Serializable {
	
	private static final long serialVersionUID = 1L;

	static int nodesCount = 0;
	
	private int id;
	
	private Category cat = null;
	
	/**
	 * Remembers how many leafs of given category there are in a subtree
	 */
	private HashMap<Category, Integer> categoryCount;
	
	private ContinousTest test = null;
	
	private Node parent = null;
	
	private Node trueChild = null;
	
	private Node falseChild = null;
	
	public Category getCat() {
		return cat;
	}

	/**
	 * Equivalent with marking the node as a leaf node
	 * @param cat
	 */
	public void setCategory(Category cat) {
		this.cat = cat;
		if (parent!=null) parent.addToSubtreeCategoryCount(cat);
	}
	
	/**
	 * Equivalent with marking the node as a leaf node.
	 * Doesn't update the count of categories in subtrees
	 * @param cat
	 */
	public void setCategorySilently(Category cat) {
		this.cat = cat;
	}
	
	public void removeCategoryCount(HashMap<Category, Integer> cc) {
		if (! isLeafNode()) {
			for (Category c : cc.keySet()) {
				if (categoryCount.containsKey(c)) {
					Integer i = categoryCount.get(c);
					i -= cc.get(c);
					categoryCount.put(c, i);
				}
			}
		}
		if (parent != null) parent.removeCategoryCount(cc);
	}
	
	private void addToSubtreeCategoryCount(Category cat) {
		if (categoryCount.containsKey(cat)) {
			Integer i = categoryCount.get(cat);
			i++;
			categoryCount.put(cat, i);
		}
		else categoryCount.put(cat, 1);
		if (parent != null) parent.addToSubtreeCategoryCount(cat);
	}
	
	public Category getSubtreeCategory() {
		int maxCount = Integer.MIN_VALUE;
		boolean unique = true;
		Category ret = null;
		for (Category c : categoryCount.keySet()) {
			if (categoryCount.get(c) >= maxCount) {
				if (categoryCount.get(c) == maxCount) {
					unique = false;
				}
				else {
					maxCount = categoryCount.get(c);
					ret = c;
					unique = true;
				}
				
			}
		}
		if ( ! unique ) return null;
		return ret;
	}

	public Node(Node parent) {
		this.parent = parent;
		id = nodesCount++;
	}
	
	public void addChild(boolean testResult, Node child) {
		if (testResult) trueChild = child;
		else falseChild = child;
		return;
	}
	
	public boolean hasChildren() {
		return (hasTrueChild() && hasFalseChild());
	}

    public boolean hasTrueChild() {
        return trueChild != null;
    }

    public Node getTrueChild() {
        return trueChild;
    }

    public boolean hasFalseChild() {
        return falseChild != null;
    }

    public Node getFalseChild() {
        return falseChild;
    }
    
    public Category classify(AttributeList al) {
    	if ( isLeafNode() ) return cat;
    	boolean tr = getTest().testAttribute(al);
    	if ( tr ) return trueChild.classify(al);
    	else return falseChild.classify(al);
    }
    
    public boolean isTestNode() {
    	return getTest() != null;
    }
    
    public boolean isLeafNode() {
    	return (getTest() == null && ! hasChildren());
    }
    
    public double getClassificationError(ExamplesSet ex) {
    	int count = ex.examples.size();
    	int errorCount = 0;
    	for (Example e : ex.examples) {
    		Category classified = classify(e.attr); 
    		if (classified == null) {
    			classified = classify(e.attr);
    		}
    		if ( ! classified.equals(e.cat)) errorCount++;
    	}
    	return (double)errorCount / (double)count;
    }
    
    public void getAllTestNodes(Vector<Node> nodes) {
    	if (isLeafNode()) return;
    	if (trueChild != null) trueChild.getAllTestNodes(nodes);
    	if (falseChild != null) falseChild.getAllTestNodes(nodes);
    	nodes.add(this);
    	return;
    }
    
    public int getNodeCount() {
    	if (isLeafNode()) return 1;
    	int ret = 1;
    	if (trueChild != null) ret += trueChild.getNodeCount();
    	if (falseChild != null) ret += falseChild.getNodeCount();
    	return ret;
    }
    
    public void replaceChild(Node child, Node newChild) {
    	if (trueChild == child) trueChild = newChild;
    	else if (falseChild == child) falseChild = newChild;
    	else return;
    }
    
	
	@Override
	public String toString() {
		String details;
		if (isTestNode()) details = " test=("+getTest()+") cat=("+getSubtreeCategory()+")";
		else details = " leaf-node cat=("+cat+")";
		return "id="+id+details;
	}

	/**
	 * Equivalent with marking the node as test node
	 * @param test
	 */
	public void setTest(ContinousTest test) {
		this.test = test;
		categoryCount = new HashMap<Category, Integer>();
	}

	public ContinousTest getTest() {
		return test;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public HashMap<Category, Integer> getCategoryCount() {
		return categoryCount;
	}
	
	public void saveToFile(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Node readFromFile(String fileName) {
    	Node r = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            r = (Node)ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
	
	
}
