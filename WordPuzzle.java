package puzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class WordPuzzle {
	private Set<String> set;
	private Set<String> prefix;
	private int max;
	public WordPuzzle() {
		this.set = new HashSet<>();
		this.max = Integer.MIN_VALUE;
		this.prefix = new HashSet<>();
		File dic = new File("C:/Users/Prashant  Yadav/Downloads/dictionary.txt");
		FileReader reader=null;
		BufferedReader br=null;
		try{
			reader = new FileReader(dic);
			br = new BufferedReader(reader);
			br.lines().forEach(s->{
				set.add(s);
				max=max>s.length()?max:s.length();
				for(int i=0;i<s.length()-1;i++){
					prefix.add(s.substring(0,i+1));
				}
				});
			System.out.println(set.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void printGrid(char[][] grid){
		int n = grid.length;
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args){
		WordPuzzle wp = new WordPuzzle();
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		sc.close();
		char grid[][] = new char[n][n];
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				Random random = new Random();
				grid[i][j] = (char) (random.nextInt(122 - 97 + 1) + 97);
			}
		}
		/*char grid[][]={
				{'a','a','h'},
				{'a','b','s'},
				{'a','a','h'}
		};*/
		wp.printGrid(grid);
		int count=0;
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				count+=wp.searchWord(grid, i,j);
			}
		}
		System.out.println(count);
	}
	
	public int searchWord(char[][] grid,  int row, int col){
		int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}};
		int count=0;
		String s = "";
		s+=grid[row][col];
		if(set.contains(s)){count++;
		//System.out.println(s);
		}
		for(int[] dir:dirs){
			int i=row, j=col;
			s="";
			s+=grid[row][col];
			while((i+dir[0])<grid.length && (i+dir[0])>=0 && (j+dir[1])<grid[0].length-1 && (j+dir[1])>=0){
				i+=dir[0];
				j+=dir[1];
				s+=(grid[i][j]);
				if(s.length()>max){break;}
				//System.out.println(s);
				if(set.contains(s)){count++;
				//System.out.println(s);
				}
			}
		}
		//System.out.println(row+" "+col+" "+count);
		return count;
	}
}
