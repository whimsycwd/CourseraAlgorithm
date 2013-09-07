/**
 * 
 * @author whimsy
 *
 */
public class Percolation {

	private boolean[] grid;
	private int N;
	private WeightedQuickUnionUF set;
	private WeightedQuickUnionUF stopBackWash;
	
	private boolean isPercolates = false;
	
	/**
	 * create N-by-N grid, with all sites blocked
	 * 
	 * @param N
	 */
	public Percolation(int N) {
		grid = new boolean[N * N];
		set = new WeightedQuickUnionUF(N * N + 2);
		stopBackWash = new WeightedQuickUnionUF(N * N + 1);
		/*
		for (int i = 0; i < N; ++i) {
			set.union(N * N, i);
			set.union(N*N+1, N*N-i-1);
		}
		
		
		for (int i = 0;i < N; ++i){
			stopBackWash.union(N*N, i);
		}
		*/
		
		for (int i = 0; i < N * N; ++i)
			grid[i] = false;
		this.N = N;
	}

	/**
	 * open site (row i, column j) if it is not already
	 * 
	 * @param i
	 * @param j
	 */

	public void open(int i, int j) {
		if (isIlegalRange(i, j))
			throw new IndexOutOfBoundsException();
		grid[decode(i, j)] = true;
		int cur = decode(i, j);
		if (i == 1){
			set.union(cur, N*N);
			stopBackWash.union(cur, N*N);
		}
		if (i == N){
			set.union(cur, N*N+1);
		}
		if (i != 1 && grid[cur] && grid[cur - N]) {
			set.union(cur, cur - N);
			stopBackWash.union(cur, cur-N);
		}
		if (i != N && grid[cur] && grid[cur + N]) {
			set.union(cur, cur + N);
			stopBackWash.union(cur, cur+N);
		}
		if (j != 1 && grid[cur] && grid[cur - 1]) {
			set.union(cur, cur - 1);
			stopBackWash.union(cur, cur-1);
		}
		if (j != N && grid[cur] && grid[cur + 1]) {
			set.union(cur, cur + 1);
			stopBackWash.union(cur, cur+1);
		}
	
	}

	private boolean isIlegalRange(int i, int j) {
		if (i < 1 || i > N || j < 1 || j > N)
			return true;
		return false;
	}

	/**
	 * is site (row i, column j) open
	 * 
	 * @param i
	 * @param j
	 * @return
	 */

	public boolean isOpen(int i, int j) {
		if (isIlegalRange(i, j))
			throw new IndexOutOfBoundsException();
		return grid[decode(i, j)];
	}

	/**
	 * translate (i,j) to (i-1)*N + j-1;
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private int decode(int i, int j) {
		return (i - 1) * N + j - 1;
	}

	/**
	 * is site (row i, column j) full?
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		if (isIlegalRange(i, j))
			throw new IndexOutOfBoundsException();
		int value = decode(i,j);
		return grid[value] && stopBackWash.connected(value, N*N);
	}

	/**
	 * // does the system percolate?
	 * 
	 * @return
	 */
	public boolean percolates() {
	
		return set.connected(N*N, N*N+1);
		
	}
}