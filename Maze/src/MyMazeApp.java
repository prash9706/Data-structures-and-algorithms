import java.util.Scanner;

public class MyMazeApp {
	
	private MyMaze createMaze(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of rows (1-20) ");
		int r = sc.nextInt();
		System.out.println("Enter number of columns (1-20) ");
		int c = sc.nextInt();
		MyMaze maze = new MyMaze(r, c);
		sc.close();
		createPathInMaze(maze);
		return maze;
	}
	
	private void createPathInMaze(MyMaze maze){
		if(maze==null){
			return;
		}
		do{
		  int adjCells[] = maze.getRandAdjCells();
		  maze.mergeCells(adjCells[0], adjCells[1]);
		}while(!maze.pathExist());
	}
	
	public static void main(String[] args) {
		MyMazeApp mazeApp = new MyMazeApp();
		MyMaze maze =  mazeApp.createMaze();
		System.out.println();
		maze.printMazeGUI();
	}
}
