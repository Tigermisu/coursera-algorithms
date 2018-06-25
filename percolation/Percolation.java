import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF sites;
    private final WeightedQuickUnionUF topSites;
    private boolean[] openSites;
    private int numberOfOpenSitesCount;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {  
        if(n <= 0) throw new IllegalArgumentException("n must be greater than 0");              

        int sqN = n*n;
        this.n = n;
        numberOfOpenSitesCount = 0;
        openSites = new boolean[sqN];
        sites = new WeightedQuickUnionUF(sqN + 2);
        topSites = new WeightedQuickUnionUF(sqN + 1);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {    
        if(isOpen(row, col)) return;

        int flatIndex = getFlatIndex(row, col);

        openSites[flatIndex] = true;

        numberOfOpenSitesCount++;

        connectToAdjacentOpenSites(row, col, flatIndex);
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {  
        validateIndexBetweenRange(row);
        validateIndexBetweenRange(col);

        return openSites[getFlatIndex(row, col)];       
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndexBetweenRange(row);
        validateIndexBetweenRange(col);
        
        int sqN = n*n;

        return topSites.connected(getFlatIndex(row, col), sqN);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int sqN = n*n;

        return sites.connected(sqN, sqN + 1);
    }

    private void validateIndexBetweenRange(int index) {
        if (index <= 0 || index > n) throw new IllegalArgumentException("Index is out of bounds");
    }

    private int getFlatIndex(int row, int col) {
        return  n * --row + --col;
    }

    private void connectToAdjacentOpenSites(int row, int col, int baseIndex) {
        if(col-1 > 0 && isOpen(row, col-1)) {
            sites.union(baseIndex, getFlatIndex(row, col-1));
            topSites.union(baseIndex, getFlatIndex(row, col-1));
        }

        if(col+1 <= n && isOpen(row, col+1)) {
            sites.union(baseIndex, getFlatIndex(row, col+1));
            topSites.union(baseIndex, getFlatIndex(row, col+1));
        }

        if(row-1 > 0) {
            if(isOpen(row-1, col)) {
                sites.union(baseIndex, getFlatIndex(row-1, col));
                topSites.union(baseIndex, getFlatIndex(row-1, col));
            }
        } else {
            sites.union(baseIndex, n*n);
            topSites.union(baseIndex, n*n);
        }

        if(row+1 <= n) {
            if(isOpen(row+1, col)) {
                sites.union(baseIndex, getFlatIndex(row+1, col));
                topSites.union(baseIndex, getFlatIndex(row+1, col));
            }
        } else {
            sites.union(baseIndex, n*n+1);
        }
    }
}