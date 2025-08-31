/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] thresholds;
    private int trialnum;

    public PercolationStats(int n, int trials) {

        this.trialnum = trials;

        thresholds = new double[trials];

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }

        for (int t = 0; t < trials; t++) {
            Percolation p = new Percolation(n); // new grid for each trial

            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }

            thresholds[t] = (double) p.numberOfOpenSites() / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        double total = 0;
        for (int i = 0; i < thresholds.length; i++) {
            total += thresholds[i];
        }
        return total / thresholds.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trialnum));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trialnum));
    }


    // test client (see below)
    public static void main(String[] args) {
        PercolationStats P = new PercolationStats(Integer.parseInt(args[0]),
                                                  Integer.parseInt(args[1]));
        System.out.printf("%-23s = %.16f\n", "mean", P.mean());
        System.out.printf("%-23s = %.16f\n", "stddev", P.stddev());
        System.out.printf("%-23s = [%.16f, %.16f]\n",
                          "95% confidence interval", P.confidenceLo(), P.confidenceHi());
    }


}
