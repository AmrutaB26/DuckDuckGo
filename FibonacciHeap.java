import java.util.HashMap;
import java.util.Map;

public class FibonacciHeap {

	//Pointer to element with max value in fibonacci heap
	Node maxPointer = null;
	
	//Insert element next to max element in the top level doubly linked list
	public void insert(Node newNode) {
		if(maxPointer == null) {
			newNode.right = newNode.left = newNode;
			maxPointer = newNode;
		}
		else {
			 newNode.right = maxPointer.right;
			 maxPointer.right.left = newNode;
			 newNode.left = maxPointer;
			 maxPointer.right = newNode;
			 if(maxPointer.data < newNode.data) {
					maxPointer = newNode;
			 } 
		}
	}
	
	//Remove element with max value from fibonacci heap
	 public Node removeMax() {
		 Node removedNode = maxPointer;
		 
		 //Return null if no element left in heap
		 if(maxPointer == null) {
			 return removedNode;
		 }
		 else {
			 
			 //Insert each child of element to be removed in the top level doubly linked list
			 Node child = maxPointer.child;
			 if(child != null) {
				 do {
					 child.parent = null;
					 Node nchild = child.right;
					 insert(child);
					 child = nchild;
				 }while(child != maxPointer.child);
				 
				 //Set degree and child field of removed element as null
				 maxPointer.child = null;
				 maxPointer.degree = 0;
			 }
			 
			 //Remove element to be removed from top level doubly linked list
			 Node startNode = maxPointer.right;
			 if(startNode != maxPointer) {
				 maxPointer.right.left = maxPointer.left;
				 maxPointer.left.right = maxPointer.right;
				 maxPointer.left = maxPointer.right = null;
				 
				 //Pairwise combine all top level elements starting with right element of removed max node
				 maxPointer = pairwiseCombine(startNode);
			 }
			 else {
				 
				 //Set maxPointer to null if no elements left after removing max element
				 maxPointer.right = maxPointer.left = null;
				 maxPointer = null;
			 }
		 }
		 return removedNode;
	 }
	 
	 //Combine elements with same degree from top level doubly linked list 
	 private Node pairwiseCombine(Node start) {
		 Map<Integer,Node> map = new HashMap<Integer,Node>();
		 Node iterator = start;
		 do {
			Node next = iterator.right;
			
			//Insert element in degree table if no other element has same degree else merge with same degree element
			if(map.containsKey(iterator.degree)) {
				merge(map.get(iterator.degree),iterator,map);
			}
			else {
				map.put(iterator.degree,iterator);
			}	
			iterator = next;
		 }while(iterator != start);
		 Node max = null;
		 Node last = null;
		 
		 //Connect all elements from degree table to form top level list
		 for(Map.Entry<Integer, Node> entry: map.entrySet()) {
				 if(max == null || entry.getValue().data > max.data) {
					 max = entry.getValue();
				 }
				 if(last == null ) {
					 entry.getValue().left = entry.getValue().right = entry.getValue();
					 last = entry.getValue();
				 }
				 else {
					 entry.getValue().left = last;
					 entry.getValue().right = last.right;
					 last.right.left = entry.getValue();
					 last.right = entry.getValue();
					 last = entry.getValue();
				 }
				 
			 } 
		 return max;
	 }
	 
	 //Merge two elements having same degree by making one element child of the other
	 private void merge(Node node1, Node node2, Map<Integer,Node> map) {
		 
		 //Swap node1 and node2 if node1<node2 so that node1 is always the parent of node2
		 if(node1.data < node2.data) {
			 Node temp = node1;
			 node1 = node2;
			 node2 = temp;
		 }
		 node2.parent = node1;
		 node2.childCut = false;
		 
		 //Set node2 as child of node1 and increase degree field of node1
		 if(node1.child == null) {
			 node1.child = node2;
			 node2.left= node2.right = node2;
		 }
		 else {
			 node2.right = node1.child;
			 node2.left = node1.child.left;
			 node1.child.left.right = node2;
			 node1.child.left = node2;
			 node1.child = node2;
		 }
		 node1.degree++;
		 
		 //Insert the new degree element into degree table and remove earlier inserted element
		 map.remove(node2.degree);
		 if(map.containsKey(node1.degree)) {
			 merge(map.get(node1.degree), node1,map);
		 }
		 else {
			map.put(node1.degree, node1);
			return;
		 }
	 }
	 
	 //Increase value field of given Node
	 public void increaseKey(Node node,int data) {
		 node.data = data+node.data;
		 
		 //Remove node if final value > parent value
		 if(node.parent != null && node.parent.data < node.data) {
			 cut(node);
		 }
		 else if(node.parent == null && maxPointer.data < node.data) {
			 
			//Update max pointer if node is in lop level list and value exceeds maxPointer value
			maxPointer = node;
		 }
	 }
	 
	 //Remove given element and insert in top level doubly linked list
	 private void cut(Node node) {
		 Node parent = node.parent;
		 
		 //Update child pointer of parent node
		 if(parent.child == node) {
			 parent.child = node.right != node ? node.right: null;
		 }
		 parent.degree--;
		 node.parent = null;
		 
		 //Remove node from fibonacci heap
		 if(node.right != node) {
			 node.right.left = node.left;
			 node.left.right = node.right;
		 }
		 
		 //Insert it back in the top level doubly linked list
		 insert(node);
		 node.childCut = false;
		 
		 //Call cascading cut for the parent of current node
		 cascadingCut(parent);
	 }
	 
	 //Remove parent element if childCut is true and insert into top level doubly linked list
	 private void cascadingCut(Node node) {
		 if(node.parent == null) {
			 return;
		 }
		 else if(node.childCut == false) {
			 node.childCut = true;
		 }
		 else {
			 cut(node);
		 }
	 }

}
