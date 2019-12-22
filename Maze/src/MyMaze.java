public class MyMaze {
	private int row;
	private int col;
	private int size;
	private DisjSets dsets;
	
	public MyMaze(int row, int col){
		this.row=row;
		this.col=col;
		this.size = this.row*this.col;
		this.dsets = new DisjSets(this.size);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public DisjSets getDsets() {
		return dsets;
	}
	
	public int[] getRandAdjCells(){
		int cell1 = (int) (Math.random() * ( size - 0 )) + 0;
		int cell2 = size;
		int adjDir[] = {1,-1,col,(-1*col)};
		while(cell2 < 0 || cell2>=size){
			int dir = (int) (Math.random() * ( 4 - 0 )) + 0;
			if(cell1%col==0 && adjDir[dir]==-1){continue;}
			else if((cell1+1)%col==0 && adjDir[dir]==1){continue;}
			else if((cell1+1)%col==0 && adjDir[dir]==1){continue;}
			//else if(dsets.visitStatus(cell1+adjDir[dir])){continue;}
			cell2 = cell1+adjDir[dir];
		}
		return new int[]{cell1, cell2};
	}
	
	public boolean isJoined(int cell1, int cell2){
		if(cell1<0||cell2<0||cell1>=size||cell2>=size){
			return false;
		}
		return dsets.find(cell1)==dsets.find(cell2);
	}
	
	public boolean pathExist(){
		return dsets.find(0)==dsets.find(size-1);
	}
	
	public boolean mergeCells(int cell1, int cell2){
		if(isJoined(cell1, cell2)){
			return false;
		}
		dsets.union(cell1, cell2);
		return true;
	}
	
	public void printMaze(){
		for(int i=0;i<size;i++){
			if(i%col==0){System.out.println();}
			System.out.print(dsets.getRoot(i)+" ");
		}
	}
	
	public void printMazeGUI(){
		for(int i=0;i<col;i++){
			System.out.print(" _");
		}
		for(int i=0;i<size;i++){
			if(i%col==0){System.out.println();}
			if(i%col==0 || ((dsets.getRoot(i)!=i-1)
							&& (i!=dsets.getRoot(i-1)))){System.out.print("|");}
			else{System.out.print(" ");}
			if((i+col)>=size || ((dsets.getRoot(i)!=i+col)
					&& (i!=dsets.getRoot(i+col)))){System.out.print("_");}
			else{System.out.print(" ");}
			if((i+1)%col==0){System.out.print("|");}
		}
	}
}
