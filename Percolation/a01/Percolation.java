package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * @author kevinmora
 *
 */
public class Percolation {
	private int n;
	private int size;
	private int topRow;
	private int bottomRow;
	private boolean[][] grid; //indicates if block is open or closed
	private int numberOpen = 0;
	private WeightedQuickUnionUF unionFind;
	private WeightedQuickUnionUF unionFind2;
	
	//private StdRandom rand;
	
	/**
	 * 
	 * @param N
	 */
	public Percolation(int N){
		if(n < 0) {
			throw new IllegalArgumentException
				("N must be greater than 0.");
		}
		
		this.n = N;
		size = n * n;
		
		grid = new boolean[N][N];
		
		unionFind = new WeightedQuickUnionUF((size + 2));
		unionFind2 = new WeightedQuickUnionUF((size + 1));
		
		topRow = size;
		bottomRow = size + 1;
		
		for (int i = 0; i < n; i++) {
		    unionFind.union(i, topRow);
		    unionFind2.union(i, topRow);
		    unionFind.union(size - i - 1, bottomRow);
		}
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 */
// FROM GITHUB REPO – IDEA #1	
	// DEBbug
	//add 2d array kind of ENum for 1/0 (+-) 
	// inrange bool for main condition/verification
	// 		if excep throw indexoutB
	//uniFind1/2
	
//	public void open(int i, int j){
//		if (i > n - 1 || i < 0 || j > n - 1 || j < 0) {
//		throw new IndexOutOfBoundsException
//			("Index out of bounds.");
//	    }
//	    grid[i][j] = true;
//	    
//	    if (i - 1 >= 0 && grid[i - 1][j]) {
//			unionFind.union(location(i - 1, j), location(i, j));
//			unionFind2.union(location(i - 1, j), location(i, j));
//	    }
//	    
//	    if (i + 1 < n && grid[i + 1][j]) {
//			unionFind.union(location(i + 1, j), location(i, j));
//			unionFind2.union(location(i + 1, j), location(i, j));
//	    }
//	    
//	    if (j - 1 >= 0 && grid[i][j - 1]) {
//			unionFind.union(location(i, j - 1), location(i, j));
//			unionFind2.union(location(i, j - 1), location(i, j));
//	    }
//	    
//	    if (j + 1 < n && grid[i][j + 1]) {
//			unionFind.union(location(i, j + 1), location(i, j));
//			unionFind2.union(location(i, j + 1), location(i, j));
//	    }
//	    numberOpen++;
//	}
	
	static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	boolean inRange(int i, int j) {
	    return i >= 0 && i < n && j >= 0 && j < n;
	}

	public void open(int i, int j) {
	    if (!inRange(i, j))
	        throw new IndexOutOfBoundsException("Index out of bounds.");
	    grid[i][j] = true;
	    for (int[] dir : DIRECTIONS) {
	        int ii = i + dir[0], jj = j + dir[1];
	        if (inRange(ii, jj) && grid[ii][jj]) {
	            unionFind.union(location(ii, jj), location(i, j));
	            unionFind2.union(location(ii, jj), location(i, j));
	        }
	    }
	    numberOpen++;
	}

	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j){
		 if (i > n - 1 || i < 0 || j > n - 1 || j < 0) {
			 throw new IndexOutOfBoundsException
			 	("Index out of bounds.");
		 }
		 return grid[i][j];
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j){	
		if (i > n - 1 || i < 0 || j > n - 1 || j < 0) {
			throw new IndexOutOfBoundsException
				("Index out of bounds.");
	    }
	    if (!isOpen(i, j)) {
	    	return false;
	    }
	    return unionFind2.connected(topRow, location(i, j));
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean percolates(){
		return unionFind.connected(topRow, bottomRow);
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private int location(int i, int j) {
		return (n * i) + j;
	}

	/**
	 * 
	 * @return
	 */
	public String numberOfOpenSites() {
		return "" + numberOpen;
	}

}