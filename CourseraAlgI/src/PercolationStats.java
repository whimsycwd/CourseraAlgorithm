/**
 * 
 * coordinate start fron left upper
 * @author whimsy
 *
 */

public class PercolationStats {

	// private int[] seq;
	private int N;
	private int T;
	private double[] avgArr;

	/**
	 * perform T independent computational experiments on an N-by-N grid
	 * 
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) {
		if (N < 1 || T < 2)
			throw new IllegalArgumentException();
		this.N = N;
		this.T = T;

		Percolation percolation;
		// generate shuffle sequence
		/*
		 * seq = new int[N * N]; for (int i = 0; i < N * N; ++i) { seq[i] = i; }
		 */

		avgArr = new double[T];

		for (int i = 0; i < T; ++i) {
			// StdRandom.shuffle(seq);
			percolation = new Percolation(N);

			for (int j = 0; j < N * N; ++j) {
				int x;
				int y;
				do {
					x = StdRandom.uniform(N) + 1;
					y = StdRandom.uniform(N) + 1;

				} while (percolation.isOpen(x, y));
				percolation.open(x, y);
				if (percolation.percolates()) {
					avgArr[i] = (double) (j + 1) / (N * N);
					break;
				}
			}

		}

	}

	/**
	 * sample mean of percolation threshold
	 * 
	 * @return
	 */
	public double mean() {
		double tot = 0;
		for (int i = 0; i < T; ++i) {
			tot += avgArr[i];
		}

		return tot / T;

	}

	/**
	 * sample standard deviation of percolation threshold
	 * 
	 * @return
	 */
	public double stddev() {
		double tot = 0;
		double avg = mean();
		for (int i = 0; i < T; ++i) {
			tot += square(avgArr[i] - avg);
		}
		return Math.sqrt(tot / (T - 1));
	}

	/**
	 * get d*d
	 * 
	 * @param d
	 * @return
	 */
	private double square(double d) {

		return d * d;
	}

	/**
	 * returns lower bound of the 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceLo() {
		double sigma = stddev();
		double avg = mean();
		return avg - 1.96 * sigma / Math.sqrt((double) T);

	}

	/**
	 * returns upper bound of the 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceHi() {
		double sigma = stddev();
		double avg = mean();
		return avg + 1.96 * sigma / Math.sqrt((double) T);
	}

	/**
	 * test client, described below
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();


		PercolationStats percolationStats = new PercolationStats(N, T);
		StdOut.println("mean                    = " + percolationStats.mean());
		StdOut.println("stddev                  = " + percolationStats.stddev());
		StdOut.println("95% confidence interval = "
				+ percolationStats.confidenceLo() + ", "
				+ percolationStats.confidenceHi());

	}
}