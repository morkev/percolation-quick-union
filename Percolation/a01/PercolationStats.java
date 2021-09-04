package a01;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 1. All sites blocked -> Percolation
 * 2. While we don't have percolation: choose a site uniformly at random among all blocked sites.
 * (Use StdRandom.Uniform). Open this site (Use Percolation.open())
 * 3. # of sites opened/N*N at percolation = estimate of the percolation threshold.
 * 4. Repeat steps 1 through 3, average the results.

 * 7. 
 */

public class PercolationStats {
    //private int sitesOpened;    //Count the number of sites that are opened until percolation
    private int size;    //total number of sites
    private int count;    //counts number of sites opened
    private double[] results; /*# of sites opened/N*N at percolation. Used to find average of 
    multiple iterations (aka T).*/
    
    /**
     * Perform T independent experiments on an N­by­N grid.
     * While we don't have percolation, choose a site (i and j) uniformly at random among all blocked sites.
     * 
     * @param N = Number of sites of 1 side of the grid
     * @param T = Number of iterations
     */
    public PercolationStats(int N, int T) {
        //Throw exception if N or T is less than 0
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException ("N and T must be greater than 0.");
        }
        //Number of sites
        size = N * N;
        //Create instance of Percolation. We need to use percolates().
        Percolation perc = new Percolation(N);
        //Implement for loop that will iterate T times.
        for(int t = 0; t < T; t++) {
            //While we don't have percolation, choose a site uniformly at random and open the site
            while(!perc.percolates()) {
                //[0,N), +1 so we can get N's integer value
                int i = StdRandom.uniform(N)+1;
                int j = StdRandom.uniform(N)+1;
                //TODO: might have to move this under isOpen
                perc.open(i, j);
                //If the site was opened, increase count
                if (!perc.isOpen(i, j)) {
                    count++;
                }
            }
            /* After we have percolation: (# of sites opened)/(Number of sites) = estimate of the percolation threshold.
             * Repeat T times. 
             */
            results[t] = count/size;
            }
        
    }

    /**
     * Mean: [(fraction of open sites) + (fraction of open sites)+...(fraction of open sites T times)] / T times
     * 
     * @return
     */
    public double mean() {
        return 0;
    }

    /**
     * Standard Deviation: [(fraction of open sites #1 - Mean)^2 +(fraction of open sites #2 - Mean)^2+
     * ...(fraction of open sites T times - Mean)^2] / T times - 1
     * TODO: Might have to squre root the final outcome to just get the stddev. Function above have stddev^2
     * @return
     */
    public double stddev() {
        return 0;
    }

    /**
     * Low Confidence: [(mean) - ((1.96 * sqrt of stddev)/())]
     * @return
     */
    public double confidenceLow() {
        return 0;
    }
    

    public double confidenceHigh() {
        return 0;
    }
    
}
