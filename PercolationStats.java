import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] proportionOfOpenSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n " + n + " is not > 0");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("trials " + trials + " is not > 0");
        }
        this.trials = trials;
        proportionOfOpenSites = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int rndRow = StdRandom.uniformInt(n) + 1;
                int rndCol = StdRandom.uniformInt(n) + 1;

                if (!percolation.isOpen(rndRow, rndCol)) {
                    percolation.open(rndRow, rndCol);
                }
            }
            proportionOfOpenSites[i] = percolation.numberOfOpenSites()/Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(proportionOfOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(proportionOfOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();

        return mean - ((CONFIDENCE_95* stddev)/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();

        return mean + ((CONFIDENCE_95* stddev)/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
