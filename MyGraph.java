public class MyGraph {
	//Creates a graph with 10 Vertices and 20 Edges
	private Node[] createGraph() {
		//Create 10 Nodes
		Node nodeA = new Node('a');
		Node nodeB = new Node('b');
		Node nodeC = new Node('c');
		Node nodeD = new Node('d');
		Node nodeE = new Node('e');
		Node nodeF = new Node('f');
		Node nodeG = new Node('g');
		Node nodeH = new Node('h');
		Node nodeI = new Node('i');
		Node nodeJ = new Node('j');
		//Add out edges for each node.
		nodeA.addOutEdge(nodeI, 6);
		nodeA.addOutEdge(nodeB, 9);
		nodeA.addOutEdge(nodeC, 14);
		nodeA.addOutEdge(nodeD, 15);
		nodeI.addOutEdge(nodeJ, 10);
		nodeI.addOutEdge(nodeB, 2);
		nodeB.addOutEdge(nodeJ, 12);
		nodeB.addOutEdge(nodeG, 24);
		nodeC.addOutEdge(nodeG, 18);
		nodeC.addOutEdge(nodeE, 30);
		nodeC.addOutEdge(nodeD, 5);
		nodeD.addOutEdge(nodeE, 20);
		nodeD.addOutEdge(nodeH, 44);
		nodeJ.addOutEdge(nodeG, 6);
		nodeE.addOutEdge(nodeG, 2);
		nodeE.addOutEdge(nodeF, 11);
		nodeE.addOutEdge(nodeH, 16);
		nodeF.addOutEdge(nodeG, 6);
		nodeF.addOutEdge(nodeH, 6);
		nodeG.addOutEdge(nodeH, 19);
		nodeA.distFromSource = 0; //Change distance from source for nodeA to 0 to make it source.
		return new Node[] { nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ };
	}
	
	//Print each vertex and its adjacent vertexes with eadge weights
	private void printGraphArray(Node[] graph) {
		if (graph == null) {
			return;
		}
		for (Node node : graph) {
			System.out.println("Node : " + node.val + " Distance from source : " + node.distFromSource);
			System.out.println("Child\tEdge Weight");
			EdgeNode edgeNode = node.firstEdge;
			while (edgeNode != null) {
				System.out.println(edgeNode.edge.end.val + " \t" + edgeNode.edge.weight);
				edgeNode = edgeNode.nextEdgeNode;
			}
		}
	}
	
	//Print each vertex, its parent and shortest distance from source.
	private void printTree(Node[] graph) {
		System.out.println("Node\tDistance from Source\tParent");
		for (Node node : graph) {
			if (node.parent != null)
				System.out.println(node.val + "\t\t" + node.distFromSource + "\t\t" + node.parent.val);
			else
				System.out.println(node.val + "\t\t" + node.distFromSource + "\t\t" + "N/A");
		}
	}
	
	//Driver Method.
	public static void main(String args[]) {
		MyGraph myGraph = new MyGraph();
		Node[] graphArr = myGraph.createGraph();  //Create Graph
		myGraph.buildMinHeap(graphArr, graphArr.length - 1);	//Build Min heap based on distance from source.
		myGraph.printGraphArray(graphArr);   //Print Graph
		Node[] path = myGraph.applyDijkstra(graphArr);	//Apply Dijkstra to find shortest distance from source.
		myGraph.printTree(path);	//Print shortest path tree.
	}
	
	
	/**
	 * Iterate over each vertex to find out the shortest distance between source and each vertex
	 * Swap this vertex with last element in heap and reduce heapsize.
	 * Call buildMinHeap method to balance heap again.
	 */
	public Node[] applyDijkstra(Node[] priorityQ) {
		Node[] path = new Node[priorityQ.length];
		int i = 0, heapSize = priorityQ.length - 1;
		while (i < path.length) {
			Node currNode = priorityQ[0];
			path[i] = currNode;
			EdgeNode edgeNode = currNode.firstEdge;
			while (edgeNode != null) {
				Edge edge = edgeNode.edge;
				if ((currNode.distFromSource + edge.weight) < edge.end.distFromSource) {
					edge.end.distFromSource = currNode.distFromSource + edge.weight;
					edge.end.parent = currNode;
				}
				edgeNode = edgeNode.nextEdgeNode;
			}
			Node temp = priorityQ[heapSize];
			priorityQ[heapSize] = currNode;
			priorityQ[0] = temp;
			buildMinHeap(priorityQ, heapSize);
			heapSize--;
			i++;
		}
		return path;
	}
	
	//Build min heap for given heap size.
	private void buildMinHeap(Node[] a, int heapSize) {
		for (int i = heapSize / 2; i >= 0; i--)
			minHeapify(a, i, heapSize);
	}

	private void minHeapify(Node[] a, int index, int heapSize) {
		int l = 2 * index + 1;
		int r = l + 1;
		int largest = index;
		if (l < heapSize && a[l].distFromSource < a[index].distFromSource) {
			largest = l;
		}
		if (r < heapSize && a[r].distFromSource < a[largest].distFromSource)
			largest = r;
		if (largest != index) {
			Node temp = a[index];
			a[index] = a[largest];
			a[largest] = temp;
			minHeapify(a, largest, heapSize);
		}
	}
	
	//A single Node in graph
	class Node {
		private char val;  //Value for that node.
		private int distFromSource; //Its distance from source
		private Node parent; //Its parent in a path.
		private EdgeNode firstEdge;   //First edge Node which contains edge
		private EdgeNode lastEdge;  //Last edge to keep track where to add a new edge
		
		/**
		 * @param val
		 * By default distFromSource will be infinity i.e Integer.MAX_VALUE-2147483647
		 * Rest of the pointers will be NULL.
		 */
		public Node(char val) {
			this.val = val;
			this.firstEdge = null;
			this.lastEdge = null;
			this.distFromSource = Integer.MAX_VALUE;
			this.parent = null;
		}
		
		/**
		 * @param endNode
		 * @param edgeWeight
		 * Initialize an edge and add it at the lastEdgeNode.
		 * Update lastEdgeNode pointer.
		 */
		public void addOutEdge(Node endNode, int edgeWeight) {
			Edge edge = new Edge(edgeWeight);
			edge.end = endNode;
			if (this.firstEdge == null) {
				this.firstEdge = new EdgeNode();
				this.lastEdge = new EdgeNode();
				this.firstEdge.edge = edge;
				this.lastEdge = this.firstEdge;
			} else {
				EdgeNode tempNode = new EdgeNode();
				tempNode.edge = edge;
				tempNode.nextEdgeNode = null;
				this.lastEdge.nextEdgeNode = tempNode;
				this.lastEdge = this.lastEdge.nextEdgeNode;
			}
		}
	}
	
	//Edge representation in the graph.
	class Edge {
		int weight; //edge Weight
		Node end;	// Edge end. We don't need start node as we are storing only out edges.

		public Edge(int weight) {
			this.weight = weight;
		}
	}
	
	//Edge Node to use it as a linked list. We can replace it with arraylist in java
	class EdgeNode {
		Edge edge;
		EdgeNode nextEdgeNode;
	}
}
