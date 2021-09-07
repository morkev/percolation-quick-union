package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

       /**
 	* Models a percolation system.
 	* N-by-N grid of sites (each square represents a site).
 	* White site = open; Black site = closed.
 	* Each site is open with probability p (or blocked with probability 1-p).
 	* System percolates iff top and bottom are connected by open sites.
 	* 
	* @author Kevin Mora
	* @author Jordan Bramhall
	*/
	public class Percolation {
	private int n; // stores N
	private int size; // Number of sites in the grid
	private int topRow; // Virtual Top
	private int bottomRow; // Virtual Bottom
	private int numberOpen = 0; // Counts the number of open seats in each frame
	private boolean[][] grid; // Initializes the grid.
	private WeightedQuickUnionUF unionFind; // Create unions and check connections
	
	/**
	 * Create an NxN grid, with all sites blocked.
	 * 
	 * Initialize all sites to be blocked.
	 * Repeat the following until the system percolates:
	 * Choose a site (row i, column j) uniformly at random among all blocked sites.
	 * Open the site (row i, column j).
	 * The fraction of sites that are opened when the system percolates 
	 * provides an estimate of the percolation threshold.
	 * 
	 * @param N of type integer.
	 */
	public Percolation(int N){
		if(N < 0) {
			//Throw exception if N is <= 0
			throw new IllegalArgumentException("N must be greater than 0.");
		}
		this.n = N; // Needed for constructor
		size = N * N; // Create size of grid
		grid = new boolean[N][N]; // Used to indicate if block is open or closed
		
		/* WeightedQuickUnionUF initializes an empty data structure for n number of sites. 
		 * Since we are using virtual top and bottom, we need to add 2 more sites to the grid.*/
		unionFind = new WeightedQuickUnionUF(size + 2); 
		
		/* Size (N*N) is the total number of sites. Sites on grid iterate from 0, so last site
		 * on the grid would be N*N-1. Since we are using a virtual top, we can dynamically assign the
		 * integer value of size to the virtual top.*/
		topRow = size;
		
		// Adding 1 to size for virtual bottom
		bottomRow = size + 1;
		
		// Assigning top and bottom rows of the grid to the virtual top and bottom.
		for (int i = 0; i < n; i++) { //iterating only n times, one side of the grid	
		//Top of the grid is assigned to virtual top 
		// (e.g. if N = 5, we assign sites #0 through #4 to virtual top #25)
			unionFind.union(i, topRow);
		    	// Bottom of the grid is assigned to virtual bottom
			// (e.g. if N = 5, 25 - (sites #0 through #4) - 1 
			// will give us the bottom numbers of the grid.)
		    	unionFind.union(size-i-1, bottomRow);
		}
	}
	
	/**
	 * Opens the site and creates unions 
	 * with adjacent opened sites.
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 */
	public void open(int i, int j){
	    inRange(i, j); // Checks if i and j are within the grid
	    grid[i][j] = true; // Set the site at i and j to open
	    
	    /* 
	     * Check site to the left. If that site is open, create union 
	     * between current site and site to the left (p and q).
	     * If i - 1 is less 0 that means that the site is not on this row (west). 
	     */
	    if (i - 1 >= 0 && grid[i - 1][j]) {
	        unionFind.union(location(i, j), location(i - 1, j));
	    }
	    /* 
	     * Check the site to the right. If site to the right is open, create union. 
	     * If i + 1 is greater than n that means that the site is not on this row (east)
	     */
	    if (i + 1 < n && grid[i + 1][j]) {
	        unionFind.union(location(i, j), location(i + 1, j));
	    }
	    /* 
	     * Check the site downward. If site bellow is open, create union. 
	     * If j - 1 is less than 0 that means that the site is not on this column (south)
	     */
	    if (j - 1 >= 0 && grid[i][j - 1]) {
	        unionFind.union(location(i, j), location(i, j - 1));
	    }
	    /* 
	     * Check the site to the upward. If site is open, create union. 
	     * If j + 1 is greater than n that means that the site is not on this column (north)
	     */
	    if (j + 1 < n && grid[i][j + 1]) {
	        unionFind.union(location(i, j), location(i, j + 1));
	    }
	    // number of open cells increases by 1 after condition (1 of 4)
	    numberOpen++;
	}
	
	/**
	 * Confirms if a site is open or not.
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 */
	public boolean isOpen(int i, int j){
		// Checks if site is out of bounds.
		outOfBounds(i, j);
		/* 
		 * If the site is open, return true; 
		 * else, return false.
		 */
		 if (grid[i][j] == true) {
			 return true;
		 }
		 return false;
	}
	
	/**
	 * Confirms if a site is full or not.
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 */
	public boolean isFull(int i, int j){
	    // Checks if site is out of bounds.
	    outOfBounds(i, j);
	    // If the site isn't open, it can't be filled.
	    if (!isOpen(i, j)) {
	    	return false;
	    }
	    /* 
	     * unionFind.connected() will return 
	     * true if site at i and j is connected 
	     * to the virtual top.
	     */
	    return unionFind.connected(topRow, location(i, j));
	}
	
	/**
	 * Confirms if we have percolation or not.
	 * 
	 * Checks if visual top and bottom are true/open. 
	 * If both are open, we have percolation.
	 * If either are not true, percolation doesn't occur.
	 */
	public boolean percolates(){
		return unionFind.connected(topRow, bottomRow);
	}	
	
	/**
	 * Returns the integer value of 
	 * the site at i and j.
	 * 
	 * e.g. if N = 5, we would have an N*N grid with sites assigned 
     	 * with integer values starting from 0 to 24 with rows and columns 
     	 * of 5 sites. i and j locate where the site is on this grid. 
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 * @return the assigned integer value of that site.
	 */
	private int location(int i, int j){
		return (n * i) + j;
	}
	
	/**
	 * Checks if site at i and j 
	 * is within range.
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 * @return specified condition
	 */
	private boolean inRange(int i, int j){
	    return i >= 0 && i < n && j >= 0 && j < n;
	}
	
	/**
	 * Throws IndexOutOfBoundsException if 
	 * site at i and j is out of bounds.
	 * 
	 * @param i of type integer
	 * @param j of type integer
	 */
	private void outOfBounds(int i, int j) {
	    if (i > n - 1 || i < 0 || j > n - 1 || j < 0) {
		throw new IndexOutOfBoundsException("Index out of bounds.");
	    }
	}
	
	/**
	 * @return String with the integer 
	 * value of numberOpen
	 */
	public String numberOfOpenSites() {
		return "" + numberOpen;
	}
}
