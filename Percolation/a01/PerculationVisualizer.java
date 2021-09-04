package a01; 

import java.awt.Font; 

import edu.princeton.cs.algs4.In; 
import edu.princeton.cs.algs4.StdDraw; 

/** 
 * After each site is opened, PerculationVisualizer
 * will draw full sites in light blue, open sites 
 * (i.e., sites that aren't full) in white, and blocked sites in black,
 * with site (0, 0) in the upper left-hand corner.
 * 
 * @author //TODO
 */ 
public class PerculationVisualizer { 
    // delay in milliseconds (controls animation speed) 
    private final static int DELAY = 0; // 100; 
	private static final int DELAY_BETWEEN_FILES = 5000; 
    // draw N-by-N percolation system 
    public static void draw(Percolation perc, int N) { 
        StdDraw.clear(); 
        StdDraw.setPenColor(StdDraw.BLACK); 
        StdDraw.setXscale(-.05 * N, 1.05 * N); 
        // leave a border to write text 
        StdDraw.setYscale(-.05 * N, 1.05 * N);
        StdDraw.filledSquare(N/2.0, N/2.0, N/2.0); 
        // draw N-by-N grid 
        for (int row = 0; row < N; row++) { 
            for (int col = 0; col < N; col++) { 
                if (perc.isFull(row, col)) { 
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); 
                } 
                else if (perc.isOpen(row, col)) { 
                    StdDraw.setPenColor(StdDraw.WHITE); 
                } 
                else { 
                    StdDraw.setPenColor(StdDraw.BLACK); 
                } 
                StdDraw.filledSquare(col + 0.5, N - row - 0.5, 0.45); 
            } 
        } 
        // write status text 
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 12)); 
        StdDraw.setPenColor(StdDraw.BLACK); 
        StdDraw.text(.25 * N, -N * .025, perc.numberOfOpenSites() + " open sites"); 
        if (perc.percolates()) StdDraw.text(.75 * N, -N * .025, "percolates"); 
        else                   StdDraw.text(.75 * N, -N * .025, "does not percolate"); 
    } 
    
    private static void simulateFromFile(String filename) { 
        In in = new In(filename); 
        int N = in.readInt(); 
        Percolation perc = new Percolation(N); 
        // turn on animation mode 
        StdDraw.enableDoubleBuffering(); 
        // repeatedly read in sites to open and draw resulting system 
        draw(perc, N); 
        StdDraw.show(); 
        StdDraw.pause(DELAY); 
         
        while (!in.isEmpty()) { 
            int i = in.readInt(); 
            int j = in.readInt(); 
            perc.open(i, j); 
            draw(perc, N); 
            StdDraw.show(); 
            StdDraw.pause(DELAY); 
        } 
        StdDraw.pause(DELAY_BETWEEN_FILES); 
    } 
    
    public static void main(String[] args) throws InterruptedException { 
    	
        String[] fileNames = { 
        		"src/testFiles/eagle25.txt", "src/testFiles/greeting57.txt",
        		"src/testFiles/heart25.txt", "src/testFiles/input1.txt",
        		"src/testFiles/input10_no.txt", "src/testFiles/input10.txt",
        		"src/testFiles/input2_no.txt", "src/testFiles/input2.txt",
        		"src/testFiles/input20.txt", "src/testFiles/input3.txt",
        		"src/testFiles/input4.txt", "src/testFiles/input5.txt",
        		"src/testFiles/input50.txt", "src/testFiles/input8_no.txt",
        		"src/testFiles/input8.txt", "src/testFiles/java60.txt",
        		"src/testFiles/sedgewick60.txt", "src/testFiles/snake101.txt",
        		"src/testFiles/snake13.txt"
        };
         
        long startTime = System.nanoTime();
        for (String filename : fileNames) { 
        	simulateFromFile(filename); 
        	StdDraw.pause(1000); // pause for 2 seconds 
        } 
        long stopTime = System.nanoTime();
        
        System.out.println("Execution was successfully completed."); 
        System.out.println("Time elapsed: " + ((stopTime - startTime) / 1000000000) + " seconds.");
    } 
}
