import java.util.*;

public class Solver {
	private final MinPQ<SearchNode> pqOriginal;
	private final MinPQ<SearchNode> pqTwin;
	private final Stack<Board> shortestBoardSequence;
	private int totalMoves = 0;
	private int shortestNumOfMove = 0;
	private boolean isSolve = false;
	
	private final SET<SearchNode> setOriginal;
	private final SET<SearchNode> setTwin;
	
	
	public Solver(Board initial) // find a solution to the initial board (using
									// the A* algorithm)
	{
		shortestBoardSequence = new Stack<Board>();
		pqOriginal = new MinPQ<SearchNode>();
		pqTwin = new MinPQ<SearchNode>();
		pqOriginal.insert(new SearchNode(totalMoves, initial, null));
		pqTwin.insert(new SearchNode(totalMoves, initial.twin(), null));

		setOriginal = new SET<SearchNode>();
		setTwin = new SET<SearchNode>();
		
		Queue<Board> neighborBoards = new Queue<Board>();
		Board previousBoard = initial;
		SearchNode originalNode;
		SearchNode twinNode;

		outer: while (!pqOriginal.isEmpty() && !pqTwin.isEmpty()) {
			originalNode = pqOriginal.delMin();
			neighborBoards = (Queue<Board>) originalNode.board.neighbors();
			if (originalNode.previousNode != null) {
				previousBoard = originalNode.previousNode.board;
			}
			
			setOriginal.add(originalNode);
			
			for (Board neighborBoard : neighborBoards) {
				if (!previousBoard.equals(neighborBoard)) {
					totalMoves = originalNode.numOfMoves + 1;
					pqOriginal.insert(new SearchNode(totalMoves, neighborBoard,
							originalNode));
				}
			}

			if (originalNode.board.isGoal()) {
				isSolve = true;
				shortestQueue(originalNode);
				shortestBoardSequence.push(initial);
				break outer;
			}

			twinNode = pqTwin.delMin();
			neighborBoards = (Queue<Board>) twinNode.board.neighbors();
			if (twinNode.previousNode != null) {
				previousBoard = twinNode.previousNode.board;
			}
			
			setTwin.add(twinNode);
			for (Board neighborBoard : neighborBoards) {
				if (!previousBoard.equals((Board) neighborBoard)) {
					pqTwin.insert(new SearchNode(totalMoves, neighborBoard,
							twinNode));
				}
			}
			if (twinNode.board.isGoal()) {
				shortestNumOfMove = -1;
				isSolve = false;
				break outer;
			}

		}
	}

	private void shortestQueue(SearchNode original) {

		while (original.previousNode != null) {
			shortestBoardSequence.push(original.board);
			original = original.previousNode;
			shortestNumOfMove++;

		}
	}

	public boolean isSolvable() // is the initial board solvable?
	{
		return isSolve;
	}

	public int moves() // min number of moves to solve initial board; -1 if no
						// solution
	{
		return this.shortestNumOfMove;
	}

	public Iterable<Board> solution() // sequence of boards in a shortest
										// solution; null if no solution
	{
		if (shortestNumOfMove != -1)
			return shortestBoardSequence;
		else
			return null;
	}

	private class SearchNode implements Comparable<SearchNode> {
		private int numOfMoves;
		private Board board;
		private SearchNode previousNode;

		public SearchNode(int numOfMoves, Board board, SearchNode previousNode) {
			this.numOfMoves = numOfMoves;
			this.board = board;
			this.previousNode = previousNode;

		}

		public int compareTo(SearchNode that) {
			if (this.board.manhattan() + this.numOfMoves > that.board
					.manhattan() + that.numOfMoves)
				return 1;
			else if (this.board.manhattan() + this.numOfMoves < that.board
					.manhattan() + that.numOfMoves)
				return -1;
			else
				return 0;
		}

	}

	public static void main(String[] args) // solve a slider puzzle (given
											// below)
	{
		// long startTime = System.currentTimeMillis();

		// create initial board from file
		
		String str = "in.txt";
		In in = new In(str);
		int N = in.readInt();
	
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
		/*
		 * long endTime = System.currentTimeMillis(); double totalTime =
		 * (double)(endTime - startTime)/1000.0; System.out.println(totalTime);
		 */

	}
}