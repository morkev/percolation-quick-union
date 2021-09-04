package a01;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Estimates the percolation threshold. Calculates the mean, standard deviation, and low and high confidence intervals.
 * 
 */
public class PercolationStats {
    private int n;
    private int size;    //total number of sites
    private int sitesOpened;    //counts number of sites opened
    private double[] fracOpenSites;//Stores the fraction of the  # of opened sites/N*N at percolation in an array.
    private int repeat; //number of times we repeat simulation, T
    
    /**
     * Monte Carol simulation
     * Perform T independent experiments on an NxN grid.
     * While we don't have percolation, choose a site (i and j) uniformly at random among all blocked sites.
     * 
     * @param N - Number of sites of 1 side of the grid
     * @param T - Number of times we repeat the simulation
     */
    public PercolationStats(int N, int T) {
        
        /*Throw exception if N or T is less than 0*/
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException ("N and T must be greater than 0.");
        }
        fracOpenSites = new double[T];
        this.repeat= T; //Needed for constructor
        size = N * N; //Number of sites
        fracOpenSites = getStats(N,T);
    }
    
    private double[] getStats(int n, int t) {
        double[] repeat = new double[t];
        
        for(int k =0; k<t; k++){
            Percolation perc = new Percolation(n);
            int sitesOpened = 0;
            
            while(!perc.percolates()){
                int i = StdRandom.uniform(n);
                int j = StdRandom.uniform(n);
                if(!perc.isOpen(i, j)){
                    perc.open(i, j);
                    sitesOpened++;
                }
            }
            repeat[k]=((double) sitesOpened)/size;
        }
        return repeat;
    }


/**
 * Returns the mean value
 * Mean: ((fraction of open sites) + (fraction of open sites)+...(fraction of open sites T times)) / T times
 * 
 * @return
 */
    public double mean() {
        return StdStats.mean(fracOpenSites);
    }
    
    /**
     * Returns the standard deviation value. 
     *Standard Deviation: ((fraction of open sites #1 - Mean)^2 +(fraction of open sites #2 - Mean)^2+...(fraction of open sites T times - Mean)^2)/ T times - 1
     * 
     * @return
     */
    public double stddev() {
        
        return StdStats.stddev(fracOpenSites);
    }

    /**
     * Return the low endpoint of 95% confidence interval 
     * Low Confidence: [(mean) - ((1.96 * sqrt of stddev)/())]
     * @return
     */
    public double confidenceLow() {
        return mean()-(1.96*stddev()/(Math.sqrt((double)repeat)));
    }
    
    /**
     * Return the high endpoint of 95% confidence interval 
     * High Confidence: [(mean)  + ((1.96 * sqrt of stddev)/())]
     * @return
     */
    public double confidenceHigh() {
        return mean()+(1.96*stddev()/(Math.sqrt((double)repeat)));
    }
    
    public static void main(String[] args) {
        PercolationStats perc = new PercolationStats(200, 100);
        System.out.println(perc.mean());
        System.out.println(perc.stddev());
        System.out.println(perc.confidenceLow());
        System.out.println(perc.confidenceHigh());
        
    }
    
}
