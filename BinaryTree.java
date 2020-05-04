//This work is mine unless otherwise cited -Robert McMaster

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class BinaryTree{

	private Node root;

	public BinaryTree(int n){

		root = new Node(n);
	}

	public int getRoot(){

		return root.getValue();
	}

	public void add(int n){

		add(n, root);
	}

	private void add(int n, Node node){

		if(n < node.getValue()){

			if(node.hasLeftChild()) add(n, node.getLeftChild());
			else node.addLeftChild( new Node(n, node) );
		}

		if(n > node.getValue()){
			if(node.hasRightChild()) add(n, node.getRightChild());
			else node.addRightChild( new Node(n, node) );
		}
	}

	public String search(int n){

		int tries = search(n, root, 1);
		if(tries < 0) return "The number " + n + " was not found after " + (tries/-1) + " tries";
		else return "The number " + n + " was found after " + tries + " tries";
	}
	private int search(int target, Node n, int i){
		if( target == n.getValue() ) return i;
		if(  target > n.getValue() ){
			if( n.hasRightChild() ) return search(target, n.getRightChild(), i+1);
			else return -i;
		}
		if(  target < n.getValue() ){

			if( n.hasLeftChild() ) return search(target, n.getLeftChild(), i+1);
			else return -i;
		}
		return 0;
	}

	public int size(){
		return 1 + traverseSize(root);
	}

	private int traverseSize(Node n){
		if( n.hasChildren() ) return 2 + traverseSize( n.getLeftChild() ) + traverseSize( n.getRightChild() );
		if( n.hasLeftChild() ) return 1 + traverseSize( n.getLeftChild() );
		if( n.hasRightChild() ) return 1 + traverseSize( n.getRightChild() );

		return 0;
	}

	public int depth(){
		return depth(root) - 1;
	}

	private int depth(Node n){
		if(n == null) return 0;
		else return 1 + Math.max( depth(n.getLeftChild()), depth(n.getRightChild()) );
	}

	public void drawTree(Graphics g, int width, int height){
		traverseGraphics(g, root, width , height);
	}

	private void traverseGraphics(Graphics g, Node n, int width, int height){
		drawNode(g, n, width, height);

		if( n.hasLeftChild() ) traverseGraphics(g, n.getLeftChild(), width, height);
		if( n.hasRightChild() ) traverseGraphics(g, n.getRightChild(), width, height);
	}

	private void drawNode(Graphics g, Node n, int width, int height){

		g.drawString(n.getValue() + "", n.getX(width) - 10*( (n.getValue() + "").length() ), n.getY(height)-10);
		Graphics2D g2 = (Graphics2D)(g);
		g2.setStroke(new BasicStroke(3) );
		if( n.hasLeftChild() ) g.drawLine(n.getX(width), n.getY(height)+10, n.getLeftChild().getX(width), n.getLeftChild().getY(height)-40);
		if( n.hasRightChild() ) g.drawLine(n.getX(width), n.getY(height)+10, n.getRightChild().getX(width), n.getRightChild().getY(height)-40);

		g = (Graphics)(g2);
	}
	public class Node{

		private Node leftChild, rightChild;
		private int value;
		private Node parent;

		public Node(int v, Node p, Node l, Node r){
			value = v;
			parent = p;
			leftChild = l;
			rightChild = r;
		}

		public Node(int v, Node p){
			this(v, p, null, null);
		}

		public Node(int v){
			this(v, null, null, null);
		}


		public void addRightChild(Node r){
			rightChild = r;
		}

		public void addLeftChild(Node l){
			leftChild = l;
		}

		public Node getLeftChild(){
			return leftChild;
		}

		public Node getRightChild(){
			return rightChild;
		}

		public int getValue(){
			return value;
		}

		public Node getParent(){
			return parent;
		}

		public boolean hasParent(){
			return parent != null;
		}

		public boolean hasChildren(){
			return hasLeftChild() && hasRightChild();
		}

		public boolean hasLeftChild(){
			return leftChild != null;
		}

		public boolean hasRightChild(){
			return rightChild != null;
		}


		public int getDepth(){
			return getDepth(this);
		}

		private int getDepth(Node n){
			if( n.hasParent() ) return 1 + getDepth( n.getParent() );
			else return 0;
		}

		public Node getRoot(){
			Node position1 = this;
			while(position1.hasParent()) position1 = position1.getParent();

			return position1;
		}

		public int getX(int width){

			int counter = 1;
			int total1 = (int)(double)(width/Math.pow(2, counter));

			Node position = getRoot();

			while(!position.equals(this)){

				counter++;

				if(value < position.getValue()){

					total1 -= width/Math.pow(2, counter);
					position = position.getLeftChild();
				}

				else if(value > position.getValue()){

					total1 += width/Math.pow(2, counter);
					position = position.getRightChild();
				}
			}

			return total1;
		}

		public int getY(int height){

			return (height*(getDepth()+1))/7;
		}

		public String toString(){

			return value + "";
		}

		public boolean equals(Node n){

			return value == n.getValue();
		}
	}
}
