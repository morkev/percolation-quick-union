package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Estimates the value of the percolation 
 * threshold via Monte Carlo simulation. 
 * 
 * Conditions:
 * 	● N-by-N grid of sites (each square represents a site).
 * 	● White site = open; Black site = closed.
 * 	● Each site is open with probability p (or blocked with probability 1-p).
 *	● System percolates iff top and bottom are connected by open sites.
 * 
 * @authors Kevin Mora && 
 */
public class Percolation {
	private int n;
	private int size;
	private int topRow;
	private int bottomRow;
	private boolean[][] grid;
	private int numberOpen = 0;
	private WeightedQuickUnionUF unionFind;
	//private WeightedQuickUnionUF unionFind2;
	
	/**
	 * Create an N-­by-­N grid, with all sites blocked.
	 * 
	 * ● Initialize all sites to be blocked.
	 * ● Repeat the following until the system percolates:
	 * 		○ Choose a site (row i, column j) uniformly at random among all blocked sites.
	 * 		○ Open the site (row i, column j).
	 * ● The fraction of sites that are opened when the system percolates 
	 *   provides an estimate of the percolation threshold.
	 * 
	 * @param N of type integer.
	 */
	public Percolation(int N){
		if(N < 0) {
			throw new IllegalArgumentException("N must be greater than 0.");
		}
		
		this.n = N;
		size = n * n;
		//indicates if block is open or closed
		grid = new boolean[N][N];
		
		unionFind = new WeightedQuickUnionUF(size + 2);
		//unionFind = new WeightedQuickUnionUF((size + 1));
		
		topRow = size;
		bottomRow = size + 1;
		
		for (int i = 0; i < n; i++) {
		    unionFind.union(i, topRow);
		    //unionFind.union(i, topRow);
		    unionFind.union(size - i - 1, bottomRow);
		}
	}
	
	public void open(int i, int j){
	    if (i > n - 1 || i < 0 || j > n - 1 || j < 0) {
	    	throw new IndexOutOfBoundsException("Index out of bounds.");
	    }
	    grid[i][j] = true;
	    
	    if (i - 1 >= 0 && grid[i - 1][j]) {
	        unionFind.union(location(i - 1, j), location(i, j));
	    }
	    if (i + 1 < n && grid[i + 1][j]) {
	        unionFind.union(location(i + 1, j), location(i, j));
	    }
	    if (j - 1 >= 0 && grid[i][j - 1]) {
	        unionFind.union(location(i, j - 1), location(i, j));
	    }
	    if (j + 1 < n && grid[i][j + 1]) {
	        unionFind.union(location(i, j + 1), location(i, j));
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
			 throw new IndexOutOfBoundsException("Index out of bounds.");
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
			throw new IndexOutOfBoundsException("Index out of bounds.");
	    }
	    if (!isOpen(i, j)) {
	    	return false;
	    }
	    return unionFind.connected(topRow, location(i, j));
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
	public boolean percolates(){
		return unionFind.connected(topRow, bottomRow);
	}
	
	/**
	 * 
	 * @return
	 */
	public String numberOfOpenSites() {
		return "" + numberOpen;
	}

}
