public class Node {

	int degree = 0;
	int data;
	Node left, right, parent, child = null;
	boolean childCut;

	//Constructor to create Node objects with initial value
	Node(int newData){
		data = newData;		
	}
	
	//Get degree of a Node
	public int getDegree() {
		return degree;
	}

	//Set degree of a Node
	public void setDegree(int degree) {
		this.degree = degree;
	}

	//Get value from a Node
	public int getData() {
		return data;
	}

	//Set value field of a Node
	public void setData(int data) {
		this.data = data;
	}

	//Get left Node of current Node
	public Node getLeft() {
		return left;
	}

	//Set left Node of current Node
	public void setLeft(Node left) {
		this.left = left;
	}

	//Get right Node of current Node
	public Node getRight() {
		return right;
	}

	//Set right Node of current Node
	public void setRight(Node right) {
		this.right = right;
	}

	//Get parent of current Node
	public Node getParent() {
		return parent;
	}

	//Set parent of current Node
	public void setParent(Node parent) {
		this.parent = parent;
	}

	//Get child of current Node
	public Node getChild() {
		return child;
	}

	//Set child of current Node
	public void setChild(Node child) {
		this.child = child;
	}

	//Get childCut flag of Node
	public boolean isChildCut() {
		return childCut;
	}

	//Set childCut flag of Node
	public void setChildCut(boolean childCut) {
		this.childCut = childCut;
	}	
}
