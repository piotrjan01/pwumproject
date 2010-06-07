package um.tree;

import java.io.Serializable;

public interface Category extends Serializable {

	@Override
	public boolean equals(Object obj);
	
	@Override
	public int hashCode();
	
}
