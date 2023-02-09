import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int numberOfSites;
    private boolean[] open;
    private WeightedQuickUnionUF network;
    private int numberOfOpenSites;

    // creates n-by-n grid (with source and exit node), with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n " + n + " is not > 0");
        }
        this.n = n;

        numberOfSites = (int) (Math.pow(n, 2) + 2);

        open = new boolean[numberOfSites];
        for (int i = 1; i < numberOfSites - 1; i++) {
            open[i] = false;
        }
        open[0] = true;
        open[numberOfSites - 1] = true;

        numberOfOpenSites = 0;

        network = new WeightedQuickUnionUF(numberOfSites);
        for (int i = 1; i < n + 1; i++) {
            network.union(0, i);
            network.union(numberOfSites-1, numberOfSites-1-i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            int nodeIndex = nodeIndex(row, col);
            open[nodeIndex] = true;
            numberOfOpenSites++;

            updateNetwork(row, col);
        }
    }

    // update network after opening site (row, col), connecting site (row, col) with open neighbours
    private void updateNetwork(int row, int col) {
        int nodeIndex = nodeIndex(row, col);

        if (nodeIndex > n && open[nodeIndex - n]) {
            network.union(nodeIndex, nodeIndex - n);
        }
        if (nodeIndex % n != 0 && open[nodeIndex + 1]) {
            network.union(nodeIndex, nodeIndex + 1);
         }
         if (nodeIndex < (n*(n - 1) + 1) && open[nodeIndex + n]) {
            network.union(nodeIndex, nodeIndex + n);
         }
         if (nodeIndex % n != 1 && open[nodeIndex - 1]) {
            network.union(nodeIndex, nodeIndex - 1);
         }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int nodeIndex = nodeIndex(row, col);
        return open[nodeIndex];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int nodeIndex = nodeIndex(row, col);
        return network.find(nodeIndex) == network.find(0) && open[nodeIndex];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return network.find(numberOfSites - 1) == network.find(0);
    }

    // translate row and column to node index
    private int nodeIndex(int row, int col) {
        validate(row, col);
        return n*(row - 1) + col;
    }

    // validate row and col are within prescribed range
    private void validate(int row, int col) {
        if (row < 1 || n < row) {
            throw new IllegalArgumentException("row " + row + " is not between 1 and " + n);
        }
        if (col < 1 || n < col) {
            throw new IllegalArgumentException("col " + col + " is not between 1 and " + n);
        }
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
