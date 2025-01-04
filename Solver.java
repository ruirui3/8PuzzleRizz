import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private boolean solvable;
    private int moves;
    // for solution
    private SearchNode solutionLast;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        moves = 0;
        int twinMoves = 0;

        // 2 boards, 1 initial 1 twin.

        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        SearchNode main = new SearchNode(initial, moves, null);
        SearchNode twin = new SearchNode(initial.twin(), twinMoves, null);

        mainPQ.insert(main);
        twinPQ.insert(twin);

        // maybe change to isGoal
        boolean finished = main.copyBoard().isGoal();
        boolean twinFinished = twin.copyBoard().isGoal();

        // need this for end code
        SearchNode current = null;

        while (!finished && !twinFinished) {

            current = mainPQ.delMin();
            SearchNode currentTwin = twinPQ.delMin();
            SearchNode mainPrev = current.getPrev();
            SearchNode twinPrev = currentTwin.getPrev();
            Board tempBoard = current.copyBoard();
            Board twinTempBoard = currentTwin.copyBoard();

            // enqueue neighbors
            for (Board board : current.initialBoard.neighbors()) {
                // optimzation
                if (mainPrev == null || board.equals(mainPrev.initialBoard)) {
                    mainPQ.insert(new SearchNode(board, (current.getMoves() + 1), current));
                }
            }

            for (Board board : currentTwin.initialBoard.neighbors()) {
                // optimzation
                if (twinPrev == null || board.equals(twinPrev.initialBoard)) {
                    twinPQ.insert(new SearchNode(board, (currentTwin.getMoves() + 1), currentTwin));
                }
            }

            // note: from FAQ, prio of search node dequed from prio queue never decrease

        }

        // one will reach the end. if it isn't main, then main is not solvable. this is
        // because one will go on infinitely while one will eventually finish

        if (current.copyBoard().isGoal()) {
            solvable = true;
        } else {
            solvable = false;
        }
        moves = current.getMoves();
        solutionLast = current;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            SearchNode lastPrev = solutionLast.getPrev();
            while (lastPrev != null) {

            }

        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

    }

    @Deprecated
    private static void aStarAlg() {

    }

    /*
     * Critical optimization - used within solver to remove duplicate loops - don't
     * enqueue a neighbor if the board is the same as the board of the previous
     * search node in the game tree
     * 
     * 
     */
    // need something to compare for tracing back to delete duplicate nodes? make a
    // comparable?

    /**
     * A* search. Now, we describe a solution to the 8-puzzle problem that
     * illustrates a
     * general artificial intelligence methodology known as the A* search algorithm.
     * We define a search node of the game to be a board, the number of moves made
     * to reach the board,
     * and the previous search node. First, insert the initial search node (the
     * initial board, 0 moves,
     * and a null previous search node) into a priority queue. Then, delete from the
     * priority queue the
     * search node with the minimum priority, and insert onto the priority queue all
     * neighboring search
     * nodes (those that can be reached in one move from the dequeued search node).
     * Repeat this procedure
     * until the search node dequeued corresponds to the goal board.
     */

    // note: prio = man + move
    // node: record predecessor search node - FAQ

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode previous;
        private Board initialBoard;
        private int moves = 0;
        private int prio;

        public SearchNode(Board init, int mov, SearchNode prev) {
            previous = prev;
            initialBoard = init;
            moves = mov;
            prio = initialBoard.manhattan() + moves;
        }

        @Deprecated
        public void increaseMoves() {
            moves++;
        }

        public int getPrio() {
            return prio;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.getPrio() - that.getPrio();
        }

        public SearchNode getPrev() {
            return previous;
        }

        public Board copyBoard() {
            Board temp = initialBoard;
            return temp;
        }

        public int getMoves() {
            return moves;
        }

    }

}
