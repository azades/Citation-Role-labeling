
		import java.util.ArrayList;
		import java.util.Collection;
		import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

	
				public class TreeNode {
					
					private Set children;
					private TreeNode parent;

					public TreeNode() {
					super();
					}


					public TreeNode getParent() {
					return parent;
					}

					public void setParent(TreeNode parent) {
					this.parent = parent;
					}

					public Set getChildren() {
					if(null == children) {
					//Don't really want to be passing back nulls if we can help it.
					children = new HashSet();
					}
					return children;
					}

					public void addChild(TreeNode child) {
					//Again trap nulls as they are the devil
					if(null != child) {
					getChildren().add(child);
					child.setParent(this);
					}
					}

					
					}
				
		
	