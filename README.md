# ALGORITHMS PART I | Princeton University <br /> Programming Assignment: Percolation

## Packages

This program uses the classes: StdRandom, StdStats, and WeightedQuickUnionUF from algs4.jar provided by Princeton University from their Coursera Algorithms Part I course.

## Introduction

Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor?

Here a percolation system is modeled using an $n$ by $n$ grid of sites. Each site is either open or blocked. A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left, right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other words, a system percolates if we fill all open sites connected to the top row and that process fills some open site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting.

## Problem

In a famous scientific problem, researchers are interested in the following question: if sites are independently set to be open with probability $p$ (and therefore blocked with probability $1 − p$), what is the probability that the system percolates? When $p$ equals 0, the system does not percolate; when $p$ equals 1, the system percolates.
   
When $n$ is sufficiently large, there is a threshold value $p*$ such that when $p < p*$ a random n-by-n grid almost never percolates, and when $p > p*$, a random $n$ by $n$ grid almost always percolates. No mathematical solution for determining the percolation threshold $p*$ has yet been derived.

This computer program estimates $p*$ with the following API:

    public class Percolation {

        // creates n-by-n grid, with all sites initially blocked
        public Percolation(int n)

        // opens the site (row, col) if it is not open already
        public void open(int row, int col)

        // is the site (row, col) open?
        public boolean isOpen(int row, int col)

        // is the site (row, col) full?
        public boolean isFull(int row, int col)

        // returns the number of open sites
        public int numberOfOpenSites()

        // does the system percolate?
        public boolean percolates()

        // test client (optional)
        public static void main(String[] args)
    }

The Percolation data type is implemented using the quick find algorithm in QuickFindUF

## Corner cases

By convention, the row and column indices are integers between 1 and $n$, where (1, 1) is the upper-left site: An IllegalArgumentException is thrown if any argument to open(), isOpen(), or isFull() is outside its prescribed range.

## Performance

The constructor takes time proportional to $n$<sup>2</sup> and all instance methods take constant time plus a constant number of calls to union() and find().

## Monte Carlo simulation

To estimate the percolation threshold, the following computational experiment is considered:

* Initialize all sites to be blocked.
* Repeat the following until the system percolates:
* Choose a site uniformly at random among all blocked sites.
* Open the site.
* The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold. For example, if sites are opened in a 20-by-20 lattice according to the snapshots below, then our estimate of the percolation threshold is 204/400 = 0.51 because the system percolates when the 204<sup>th</sup> site is opened.

By repeating this computation experiment $T$ times and averaging the results, we obtain a more accurate estimate of the percolation threshold. Let $x$<sub>$t$</sub> be the fraction of open sites in computational experiment $t$. The sample mean $\bar{x}$ provides an estimate of the percolation threshold; the sample standard deviation $s$; measures the sharpness of the threshold.

To perform a series of computational experiments, a data type called PercolationStats has been created with the following API.

    public class PercolationStats {

        // perform independent trials on an n-by-n grid
        public PercolationStats(int n, int trials)

        // sample mean of percolation threshold
        public double mean()

        // sample standard deviation of percolation threshold
        public double stddev()

        // low endpoint of 95% confidence interval
        public double confidenceLo()

        // high endpoint of 95% confidence interval
        public double confidenceHi()

       // test client (see below)
       public static void main(String[] args)
    }

An IllegalArgumentException is thrown in the constructor if either $n$ ≤ 0 or trials ≤ 0.

A main() method is included that takes two command-line arguments $n$ and $T$, performs $T$ independent computational experiments (discussed above) on an $n$ by $n$  grid, and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold. StdRandom is used to generate random numbers; StdStats is used to compute the sample mean and sample standard deviation.
