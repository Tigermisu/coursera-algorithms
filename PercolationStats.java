import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int trials,
        percolationSize,
        totalSites;

    private double[] fractions;

    private double mean, stddev;


   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {
        if(n <= 0) throw new IllegalArgumentException("n must be greater than 0");   
        if(trials <= 0) throw new IllegalArgumentException("trials must be greater than 1");   

        this.trials = trials;
        this.percolationSize = n;
        this.totalSites = n*n;

        this.fractions = new double[trials];

        for(int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(percolationSize);
 
            do {
                int rdnRow = StdRandom.uniform(percolationSize) + 1,
                 rdnCol = StdRandom.uniform(percolationSize) + 1;
 
             percolation.open(rdnRow, rdnCol);
            } while(!percolation.percolates());
 
            fractions[i] = percolation.numberOfOpenSites() / (double)totalSites;
        }

        this.mean = StdStats.mean(fractions);
        this.stddev = StdStats.stddev(fractions);
   }  

   // sample mean of percolation threshold 
   public double mean() {
    return mean;
   }   

   public double stddev() {
    return stddev;
   }

   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
    return mean - 1.96d * stddev / Math.sqrt(trials);
   }

   // high endpoint of 95% confidence interval
   public double confidenceHi() {
    return mean + 1.96d * stddev / Math.sqrt(trials);
   }

   public static void main(String[] args) {
    int n = Integer.parseInt(args[0]),
        T = Integer.parseInt(args[1]);

    PercolationStats percolationStats = new PercolationStats(n, T);
 
    StdOut.print(String.format("mean\t\t\t = %f\nstddev\t\t\t = %f\n95%% confidence interval = [%f, %f]", 
        percolationStats.mean(), percolationStats.stddev(), 
        percolationStats.confidenceLo(), percolationStats.confidenceHi()));
   }
}