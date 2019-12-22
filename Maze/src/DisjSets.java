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