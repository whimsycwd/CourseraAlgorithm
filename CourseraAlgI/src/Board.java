public class Board {

	private final int[][] blocks;

	// construct a board from an N-by-N array of
	// blocks
	// (where blocks[i][j] = block in row i,
	// column j)
	public Board(int[][] blocks) {
		this.blocks = blocks;
	}

	// board dimension N
	public int dimension() {
		return blocks.length;
	}

	// number of blocks out of place

	public int hamming() {
		int ret = 0;
		int n = dimension();
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (blocks[i][j] != 0) {
					if (i * n + j + 1 != blocks[i][j])
						++ret;

				}
			}
		}
		return ret;
	}

	// sum of Manhattan distances between blocks and goal

	public int manhattan() {
		int ret = 0;
		int n = dimension();
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j)
				if (blocks[i][j] != 0) {
					int ii = (blocks[i][j] - 1) / 3;
					int jj = (blocks[i][j] - 1) % 3;
					ret += abs(i - ii) + abs(j - jj);
				}
		}
		return ret;
	}

	// is this board the goal board?

	private int abs(int i) {
		if (i < 0)
			i = -i;
		return i;
	}

	public boolean isGoal() {
		return hamming() == 0;
	}

	// a board obtained by exchanging two adjacent blocks in
	// the same row
	public Board twin() {
		int n = dimension();
		int[][] a = new int [n][n];
		for (int i = 0;i<n;++i)
			for (int j =0;j<n;++j) a[i][j] = blocks[i][j];
		for (int i = 0;i<n;++i){
			if (a[i][0] != 0 && a[i][1] != 0){
				int tmp = a[i][0];
				a[i][0] = a[i][1];
				a[i][1] = tmp;
				return new Board(a);
			}
		}
		// this is not gonna happen!?
		assert (true);
		return null;
	}

	// does this board equal y?
	public boolean equals(Object y) {
		if (this == y) return true;
		if (y == null) return false;
		if (this.getClass() != y.getClass() ) return false;
		Board yBoard = (Board) y;
		if (dimension() != yBoard.dimension()) return false;
		
		int n = dimension();
		for (int i = 0;i<n;++i){
			for (int j = 0;j<n;++j){
				if (blocks[i][j] != yBoard.blocks[i][j]) return false;
			}
		}
		return true;
		
	}

	
	// all neighboring boards

	public Iterable<Board> neighbors() {
		Queue<Board> que = new Queue<Board>();
		int dx[] ={1,0,-1,0};
		int dy[] ={0,1,0,-1};
		
		
		int x = 0,y = 0;
		int n = dimension();
		for (int i = 0;i<n;++i){
			for (int j = 0;j<n;++j)
			if (blocks[i][j] == 0){
				x = i;
				y = j;
				break;
			}
		}
		
		for (int dir = 0;dir<4;++dir){
			int xx = x + dx[dir];
			int yy = y + dy[dir];
			if (xx < 0 || xx >= n || yy < 0 || yy >=n ) continue;
			int [][] tmp = copy(blocks);
			int t = tmp[x][y]; 
			tmp[x][y] = tmp[xx][yy];
			tmp[xx][yy] = t;
			que.enqueue(new Board(tmp));
		}
		
		return que;
	}

	// string representation of the board (in the
	// output format specified below)

	private int [][]  copy(int[][] blocks) {
		int [][] ret = new int [blocks.length][blocks.length];
		for (int i = 0;i<blocks.length;++i){
			for (int j = 0;j<blocks.length;++j){
				ret[i][j] = blocks[i][j];
			}
		}
		return ret;
	}

	public String toString() {
		int N = dimension();
		int [][] tiles = blocks;
	    StringBuilder s = new StringBuilder();
	    s.append(N + "\n");
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) {
	            s.append(String.format("%2d ", tiles[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
}