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
	private MyHashTable<String> customHash;
	private int max;
	private boolean isPrefixHash;
	public WordPuzzle(boolean hashingType) {
		this.set = new HashSet<>();
		this.max = Integer.MIN_VALUE;
		this.customHash = new MyHashTable<>();
		this.isPrefixHash = hashingType;
		File dic = new File("C:/Users/Prashant  Yadav/Downloads/dictionary.txt");
		FileReader reader=null;
		BufferedReader br=null;
		try{
			reader = new FileReader(dic);
			br = new BufferedReader(reader);
			br.lines().forEach(word->{
				set.add(word);
				max=max>word.length()?max:word.length();
				if(isPrefixHash){
					getAllPrefix(word);
				}
				customHash.insert(word, true);
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
	private void getAllPrefix(String word) {
		for(int i=0;i<word.length()-1;i++){
			customHash.insert(word.substring(0,i+1), false);
		}
	}
	public void printGrid(char[][] grid){
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
	}
	public void printDP(String[][][] dp){
		int r = dp.length;
		int c = dp[0].length;
		int d = 8;
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				for(int k=0;k<d;k++){
					System.out.print(dp[i][j][k]+",");
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter number of rows in grid ");
		int r = sc.nextInt();
		System.out.println("Enter number of columns in grid ");
		int c = sc.nextInt();
		System.out.println("Is Prefix Hashing <true/false> ");
		boolean isPrefixHash = sc.nextBoolean();
		sc.close();
		char grid[][] = new char[r][c];
		for(int i=0;i<r;i++){
			for(int j=0;j<c;j++){
				Random random = new Random();
				grid[i][j] = (char) (random.nextInt(122 - 97 + 1) + 97);
			}
		}
		WordPuzzle wp = new WordPuzzle(isPrefixHash);
		/*char grid[][]={
				{'a','a','h'},
				{'a','b','s'},
				{'a','a','h'}
		};*/
		wp.printGrid(grid);
		int count=0;
		long start = System.currentTimeMillis();
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[0].length;j++){
				count+=wp.searchWord(grid, i, j, isPrefixHash);
			}
		}
		long end = System.currentTimeMillis();
		String hashType = isPrefixHash?" with Prefix hashing is ":" without Prefix hashing is ";
		System.out.println("Time taken"+hashType +(end-start)+" milliseconds");
		System.out.println("Number of word found "+count);
	}
	
	public int searchWord(char[][] grid,  int row, int col,  boolean isPrefixHash){
		int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1},{1,1},{-1,-1},{1,-1},{-1,1}};
		int count=0;
		boolean isCellVal = true;
		for(int k=0;k<dirs.length; k++){
			int[] dir = dirs[k];
			int i=row, j=col;
			String s = "";
			while((i)<grid.length && (i)>=0 && (j)<grid[0].length && (j)>=0){
				s+=(grid[i][j]);
				int entryType = customHash.contains(s);
				if(isPrefixHash && entryType==0){
					break;
				}
				if(entryType==1){
					if(i==row&&j==col&&isCellVal){
						count++;
						isCellVal=false;
					}
					else if(i!=row||j!=col){
						count++;
					}
				}
				i+=dir[0];
				j+=dir[1];
			}
		}
		return count;
	}
}
