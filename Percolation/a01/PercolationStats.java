package a01;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Estimates the percolation threshold. Calculates the mean, 
 * standard deviation, and low and high confidence intervals.
 * 
 * @author Jordan Bramhall
 * @author Kevin Mora
 */
public class PercolationStats {
    private int size; //total number of sites
    private double[] fracOpenSites; //Stores the fraction of the  # of opened sites/N*N at percolation in an array.
    private int repeat; //number of times we repeat simulation, T
    
    /**
     * Monte Carlo simulation.
     * Perform T independent experiments on an NxN grid.
     * While we don't have percolation, choose a site (i and j)
     * uniformly at random among all blocked sites.
     * 
     * @param N - Number of sites of 1 side of the grid
     * @param T - Number of times we repeat the simulation
     */
    public PercolationStats(int N, int T) {
        //Throw exception if N or T is less than 0
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException ("N and T must be greater than 0.");
        }
        fracOpenSites = new double[T];
        this.repeat= T; // Needed for constructor
        size = N * N; // Number of sites
        fracOpenSites = getStats(N,T);
    }
    
    /**
     * Chooses a site uniformly at random among all blocked sites.
     * Opens the site at i and j.
     * The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
     * 
     * @param n of type integer
     * @param t of type integer
     * @return
     */
    private double[] getStats(int n, int t) {
        double[] stats = new double[t]; //takes in the value of t
        
        // Open sites at random until percolation occurs
        for(int k =0; k<t; k++){
            Percolation perc = new Percolation(n); //create object of Percolation. Takes in value of n
            int sitesOpened = 0; //initialize sitesOpened to 0
            
            // While we don't have percolation, open sites uniformly at random; 
            // if a site is open, increase sitesOpened by 1.
            while(!perc.percolates()){
                int i = StdRandom.uniform(n);    //assigns a random number to i.
                int j = StdRandom.uniform(n); //assigns a random number to j.
                
                // If the site is not open at i and j, 
                // open it and then increase sitesOpened
                if(!perc.isOpen(i, j)){
                    perc.open(i, j);
                    sitesOpened++;
                }
            }
            // Assign the fraction of sites opened at percolation to array stats.
            stats[k]=((double) sitesOpened)/size;
        }
        return stats;
    }


	/**
	 * Returns the mean value.
	 * Mean: ((fraction of open sites) + (fraction of open sites) + 
	 * ...(fraction of open sites T times)) / T times
	 */
    public double mean() {
        return StdStats.mean(fracOpenSites);
    }
    
    /**
     * Returns the standard deviation value. 
     * Standard Deviation: ((fraction of open sites #1 - Mean)^2 + 
     * 						(fraction of open sites #2 - Mean)^2 + 
     * 					    (fraction of open sites T times - Mean)^2) / T times - 1
     */
    public double stddev() {
        return StdStats.stddev(fracOpenSites);
    }

    /**
     * Return the low end point of 95% confidence interval.
     * Low Confidence: [(mean) - ((1.96 * sqrt of stddev)/())]
     */
    public double confidenceLow() {
        return mean() - (1.96 * stddev()/(Math.sqrt((double)repeat)));
    }
    
    /**
     * Return the high endpoint of 95% confidence interval.
     * High Confidence: [(mean)  + ((1.96 * sqrt of stddev)/())]
     */
    public double confidenceHigh() {
        return mean() + (1.96 * stddev()/(Math.sqrt((double)repeat)));
    }
    
    /**
     * Logic execution && expected output.
     * @param args
     */
    public static void main(String[] args) {
        PercolationStats perc = new PercolationStats(200, 100);
        System.out.println(perc.mean());
        System.out.println(perc.stddev());
        System.out.println(perc.confidenceLow());
        System.out.println(perc.confidenceHigh());
    }
}
