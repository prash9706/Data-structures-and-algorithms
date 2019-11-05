
public class BinarySearchTree {
	private int[] treeVals = {100, 50, 200, 150, 300, 25, 75, 12, 37, 125, 175, 250, 320, 67, 87, 94, 89,92,88};
	
	public int[] getTreeVals(){
		return treeVals;
	}
	
	/*Insert a node to the tree*/
	public Node insertNode(Node root, int val){
		if(root==null){root=new Node(val); return root;}
		Node node = null;
		if(root.val<val){
			node = insertNode(root.right, val);
			root.right = node;
			node.parent = root;
		}else{
			node = insertNode(root.left,val);
			root.left = node;
			node.parent = root;
		}
		return root;
	}
	
	/*Delete a node and replace it with predecessor*/
	public Node deleteNode(Node root, int val){
		Node targetNode = getNode(root,val);
		Node predecessor = getPredecessor(targetNode);
		if(predecessor==null){
			if(targetNode.parent.left==targetNode){targetNode.parent.left = targetNode.right;}
			else{targetNode.parent.right = targetNode.right;}
		}else {
			if(predecessor.parent.left==predecessor){predecessor.parent.left = predecessor.left;}
			else{predecessor.parent.right=predecessor.left;}
			predecessor.parent = targetNode.parent;
			predecessor.left = targetNode.left;
			predecessor.right = targetNode.right;
			if(targetNode.parent==null){
				root = predecessor;
			}
			else if(targetNode.parent.left==targetNode){targetNode.parent.left = predecessor;}
			else{targetNode.parent.right = predecessor;}
		}
		return root;
	}
	
	/*Get node by value*/
	public Node getNode(Node node, int val){
		if(node==null){return null;}
		if(node.val==val){return node;}
		return node.val>val?getNode(node.left,val):getNode(node.right,val);
	}
	
	/*get predecessor of any node*/
	public Node getPredecessor(Node node){
		Node leftTreeRoot = node.left;
		while(leftTreeRoot!=null && leftTreeRoot.right!=null){
			leftTreeRoot = leftTreeRoot.right;
		}
		return leftTreeRoot;
	}
	
	/*Inorder traversal*/
	public void inorderTraversal(Node node){
		if(node==null){return;}
		inorderTraversal(node.left);
		System.out.print(node.val+" ");
		inorderTraversal(node.right);
	}
	
	public static void main(String[] args){
		BinarySearchTree bst = new BinarySearchTree();
		Node root = null;
		for(int i:bst.getTreeVals()){  //Insert node to the tree
			root=bst.insertNode(root, i);
		}
		bst.inorderTraversal(root);  //Inorder traversal of the tree
		root=bst.deleteNode(root, 100);  //Delete 100 and replace it with predecessor
		System.out.println();
		bst.inorderTraversal(root); //Inorder traversal of the tree
		//System.out.println("\n"+root.val);
	}
}
/*Node class*/
 class Node{
	public Node left;
	public Node right;
	public Node parent;
	public int val;
	public Node(int val){
		this.val = val;
	}
}
