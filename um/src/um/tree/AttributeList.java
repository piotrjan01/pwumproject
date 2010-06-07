package um.tree;

import java.io.Serializable;

public interface AttributeList extends Serializable {
	
	double getAttributeValue(int index);
	
	int getNumberOfAttributes();
	
}
