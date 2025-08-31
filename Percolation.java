/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    private int n;

    private int top;

    private int bottom;

    private int openSites = 0;


    // create a n-by-n grid with all sites blocked
    public Percolation(int n) {
        // This is like the constructor
        // A closed space will be a 0
        this.n = n;
        this.top = n * n;
        this.bottom = n * n + 1;
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        uf = new WeightedQuickUnionUF(n * n + 2);
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // Set the value to 0
                grid[i][j] = 0;
                // If we are on the top row, connect to the virtual top
            }
        }
    }

    // Helper to get the uf index:
    private int get1Dvalue(int row, int col) {
        return row * n + col;
    }

    // open the site
    public void open(int row, int col) {
        // check if it's open

        if (row > n || row < 1 || col < 1 || col > n) {
            throw new IllegalArgumentException("Values are not found on the grid");
        }
        row = row - 1;
        col = col - 1;

        if (grid[row][col] == 1) {
            return;
        }
        else {
            grid[row][col] = 1;
            if (row == 0) {
                uf.union(get1Dvalue(row, col), top);
            }
            // If we are on the bottom row, connnect to the 'virtual' bottom
            if (row == n - 1) {
                uf.union(get1Dvalue(row, col), bottom);
            }
            this.openSites++;
            // Go through the neighbouring cells perform union
            int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            for (int[] dir : dirs) {
                int nr = row + dir[0];
                int nc = col + dir[1];
                if (nr >= 0 && nr < n && nc >= 0 && nc < n && isOpen(nr + 1, nc + 1)) {
                    uf.union(get1Dvalue(row, col), get1Dvalue(nr, nc));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row > n || row < 1 || col < 1 || col > n) {
            throw new IllegalArgumentException("Values are not found on the grid");
        }
        return grid[row - 1][col - 1] == 1;
    }

    // check if it's full
    public boolean isFull(int row, int col) {
        if (row > n || row < 1 || col < 1 || col > n) {
            throw new IllegalArgumentException("Values are not found on the grid");
        }
        if (!isOpen(row, col)) return false;
        int index = get1Dvalue(row - 1, col - 1);
        return uf.find(index) == uf.find(top);

    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client
    public static void main(String[] args) {
    }
}
