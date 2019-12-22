import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class Kruskals {
	
	private int numberOfCities;
	
	public static void main(String[] args){
		Kruskals kruskals = new Kruskals();
		printEdges(kruskals.getEdges());
		System.out.println("----------------------------------");
		printEdges(kruskals.applyKruskal());
	}
	
	private List<Edge> applyKruskal(){
		List<Edge> mst = new ArrayList<>();
		List<Edge> edges = getEdges();
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(getEdgeComparator());
		pq.addAll(edges);
		DisjSets dset = new DisjSets(getNumberOfCities());
		Map<String, Integer> map = getCityMap(edges);
		while(mst.size()!=getNumberOfCities()-1){
			Edge edge = pq.poll();
			int x = dset.find(map.get(edge.startCity));
			int y = dset.find(map.get(edge.endCity));
			if(x!=y){
				mst.add(edge);
				dset.union(x, y);
			}
		}
		return mst;
	}
	
	private Map<String, Integer> getCityMap(List<Edge> edges){
		Map<String, Integer> res = new HashMap<>();
		int idx=0;
		for(Edge edge:edges){
			if(!res.containsKey(edge.startCity)){
				res.put(edge.startCity, idx++);
			}
			if(!res.containsKey(edge.endCity)){
				res.put(edge.endCity, idx++);
			}
		}
		return res;
	}
	
	private Comparator<Edge> getEdgeComparator(){
		Comparator<Edge> edgeComparator = new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.distance-o2.distance;
			}
		};
		return edgeComparator;
	}
	
	private static void printEdges(List<Edge> edges){
		int totalDistance = 0;
		for(Edge edge:edges){
			System.out.println(edge.startCity+"->"+edge.endCity+"="+edge.distance);
			totalDistance+=edge.distance;
		}
		System.out.println("Total distance = "+totalDistance);
	}
	
	private int getNumberOfCities(){
		return this.numberOfCities;
	}
	
	private List<Edge> getEdges(){
		this.numberOfCities=0;
		List<Edge> edges = new ArrayList<>();
		Set<String> visited = new HashSet<>();
		File dataset = new File("C:/Users/Prashant  Yadav/workspace/DataStructureAssignments/resources/data.csv");
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(dataset));
			String line;
			while((line = br.readLine())!=null){
				StringTokenizer st = new StringTokenizer(line,",");
				String startCity = st.nextToken();
				this.numberOfCities++;
				while(st.hasMoreElements()){
					String endCity = st.nextToken();
					if(startCity.isEmpty() || endCity.isEmpty()){
						continue;
					}
					int distance = Integer.parseInt(st.nextToken());
					if(!visited.contains(endCity+"-"+startCity)){
						visited.add(startCity+"-"+endCity);
						Edge edge = new Edge(startCity, endCity, distance);
						edges.add(edge);
					}
				}
			}
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return edges;
	}
	
	public class DisjSets {
		private int s[];
		public DisjSets(int numElements) {
			this.s = new int[numElements];
			for (int i = 0; i < s.length; i++) {
				s[i] = -1;
			}
		}

		public void union(int root1, int root2) {
			s[root2] = root1; // Make root1 new root
		}

		public int find(int x) {
			if (s[x] < 0)
				return x;
			return find(s[x]);
		}
		
		public int getRoot(int i){
			return this.s[i];
		}
	}
	
	// Edge representation in the graph.
	class Edge {
		private String startCity; 
		private String endCity;
		private int distance;

		public Edge(String startCity, String endCity, int distance) {
			this.startCity = startCity;
			this.endCity = endCity;
			this.distance = distance;
		}
	}
}
