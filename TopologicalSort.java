public class TopologicalSort {
	
	private VertexNode visitingOrder;
	private static int N;
	
	private void pushVertex(Vertex vertex){
		if(visitingOrder==null){
			visitingOrder = new VertexNode();
			visitingOrder.vertex = vertex;
			visitingOrder.nextVertex = null;
		}else{
			VertexNode temp = new VertexNode();
			temp.vertex = vertex;
			temp.nextVertex = visitingOrder;
			visitingOrder = temp;
		}
	}
	
	private void popVertex(){
		if(visitingOrder==null){return;}
		visitingOrder = visitingOrder.nextVertex;
	}
	
	private Vertex[] createGraph() {
		// Create 10 Nodes
		Vertex nodeA = new Vertex('a');
		Vertex nodeB = new Vertex('b');
		Vertex nodeC = new Vertex('c');
		Vertex nodeD = new Vertex('d');
		Vertex nodeE = new Vertex('e');
		Vertex nodeF = new Vertex('f');
		Vertex nodeG = new Vertex('g');
		Vertex nodeH = new Vertex('h');
		Vertex nodeI = new Vertex('i');
		Vertex nodeJ = new Vertex('j');
		// Add out edges for each node.
		nodeA.addOutEdge(nodeI);
		nodeA.addOutEdge(nodeB);
		nodeA.addOutEdge(nodeC);
		nodeA.addOutEdge(nodeD);
		nodeI.addOutEdge(nodeJ);
		nodeI.addOutEdge(nodeB);
		nodeB.addOutEdge(nodeJ);
		nodeB.addOutEdge(nodeG);
		nodeC.addOutEdge(nodeG);
		nodeC.addOutEdge(nodeE);
		nodeC.addOutEdge(nodeD);
		nodeD.addOutEdge(nodeE);
		nodeD.addOutEdge(nodeH);
		nodeJ.addOutEdge(nodeG);
		nodeE.addOutEdge(nodeF);
		nodeE.addOutEdge(nodeH);
		nodeF.addOutEdge(nodeG);
		nodeF.addOutEdge(nodeH);
		nodeG.addOutEdge(nodeH);
//		nodeG.addOutEdge(nodeE);    //Please Uncomment this edge to make a cycle
		return new Vertex[] { nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG, nodeH, nodeI, nodeJ };
	}

	// Print each vertex and its adjacent vertexes with eadge weights
	private void printGraphArray(Vertex[] graph) {
		if (graph == null) {
			return;
		}
		for (Vertex node : graph) {
			System.out.println("Node : " + node.val);
			System.out.print("Outgoing edges to Nodes -> ");
			EdgeNode edgeNode = node.firstEdge;
			while (edgeNode != null) {
				System.out.print(edgeNode.edge.end.val+"   ");
				edgeNode = edgeNode.nextEdgeNode;
			}
			System.out.println();
		}
	}
	
	private void printVisitingOrder(Vertex[] graph){
		System.out.println("Vertex\tTopological Order");
		for(Vertex vertex:graph){
			System.out.println(vertex.val+"\t"+vertex.topoNum);
		}
	}
	
	private void dfsTopologicalSort(Vertex[] graph){
		for(int i=0;i<graph.length;i++){
			if(!graph[i].isVisited){
				if(dfsTopologicalSort(graph[i])){
					break;
				}
			}
		}
	}
	
	private boolean dfsTopologicalSort(Vertex vertex) {
		if(vertex==null){return false;}
		boolean isCycleDetected = false;
		vertex.isVisited=true;
		pushVertex(vertex);
		EdgeNode edges = vertex.firstEdge;
		while(edges!=null){
			Vertex nextVertex = edges.edge.end;
			if(!nextVertex.isVisited){
				if(dfsTopologicalSort(nextVertex)){
					return true;
				}
			}else{
				isCycleDetected = checkForCycle(nextVertex);
				if(isCycleDetected){
					System.out.println("Cycle detected at node - "+nextVertex.val);
					break;
				}
			}
			edges = edges.nextEdgeNode;
		}
		vertex.topoNum = TopologicalSort.N--;
		popVertex();
		System.out.println("Visited : - " +vertex.val);
		return isCycleDetected;
	}

	private boolean checkForCycle(Vertex vertex) {
		VertexNode temp = visitingOrder;
		while(temp!=null && temp.nextVertex!=null){
			if(temp.vertex.equals(vertex)){
				return true;
			}
			temp = temp.nextVertex;
		}
		return false;
	}

	public static void main(String[] args) {
		TopologicalSort tSort = new TopologicalSort();
		Vertex[] graph = tSort.createGraph();
		N = graph.length;
		tSort.printGraphArray(graph);
		tSort.dfsTopologicalSort(graph);
		tSort.printVisitingOrder(graph);
	}	

	// A single Node in graph
	class Vertex {
		private char val; // Value for that node.
		private EdgeNode firstEdge; // First edge Node which contains edge
		private EdgeNode lastEdge; // Last edge to keep track where to add a new edge
		private boolean isVisited; //Visit status of the node
		private int topoNum; //Topological number
		/**
		 * @param val
		 */
		public Vertex(char val) {
			this.val = val;
			this.firstEdge = null;
			this.lastEdge = null;
			this.isVisited = false;
		}

		/**
		 * @param endNode
		 * Initialize an edge and add it at the lastEdgeNode. Update
		 * lastEdgeNode pointer.
		 */
		public void addOutEdge(Vertex endNode) {
			Edge edge = new Edge(endNode);
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

	// Edge representation in the graph.
	class Edge {
		Vertex end; // Edge end. We don't need start node as we are storing only
					// out edges.

		public Edge(Vertex end) {
			this.end = end;
		}
	}

	// Edge Node to use it as a linked list. We can replace it with arraylist in
	// java
	class EdgeNode {
		Edge edge;
		EdgeNode nextEdgeNode;
	}
	
	class VertexNode{
		Vertex vertex;
		VertexNode nextVertex;
	}
}
