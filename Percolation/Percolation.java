import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int TOP = 0;
    private boolean[][] opened;
    private final int bottom;
    private final int size;
    private final WeightedQuickUnionUF qf;
    private final WeightedQuickUnionUF full;
    private int countOpened;

    /**
     * Creates N-by-N grid, with all sites blocked.
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        bottom = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        full = new WeightedQuickUnionUF(size * size + 1);
        opened = new boolean[size][size];
        countOpened = 0;
    }

    /**
     * Opens site (row i, column j) if it is not already.
     */
    public void open(int i, int j) {
        if (i <= 0 || i > size || j <= 0 || j > size) throw new IllegalArgumentException();
        if (!isOpen(i, j)) countOpened++;
        opened[i - 1][j - 1] = true;

        if (i == 1) {
            qf.union(getQFIndex(i, j), TOP);
            full.union(getQFIndex(i, j), TOP);
        }
        if (i == size) {
            qf.union(getQFIndex(i, j), bottom);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            qf.union(getQFIndex(i, j), getQFIndex(i, j - 1));
            full.union(getQFIndex(i, j), getQFIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            qf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
            full.union(getQFIndex(i, j), getQFIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            qf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
            full.union(getQFIndex(i, j), getQFIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            qf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
            full.union(getQFIndex(i, j), getQFIndex(i + 1, j));
        }
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > size || j <= 0 || j > size) throw new IllegalArgumentException();
        return opened[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > size || j <= 0 || j > size) throw new IllegalArgumentException();
        return full.connected(TOP, getQFIndex(i, j));
    }

    public int numberOfOpenSites() {
        return countOpened;
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return qf.connected(TOP, bottom);
    }

    private int getQFIndex(int i, int j) {
        return size * (i - 1) + j;
    }
}