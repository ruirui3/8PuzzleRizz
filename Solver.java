/**
 * @author Rui Zhao attests that this code is their original work and was written in compliance with the class Academic Integrity and Collaboration Policy found in the syllabus. 
 */

/*
 * Personally I believe the Solver was much harder than Board.java. The hardest part of the start up on how to approach the solver. 
 * Despite knowing how to do it, execution is hard. I found myself reading the specs over and over again. Below you can see my notes
 * detailing everything regarding the Solver.java and how I came to realize how the code works. I have remembered Node class from back in the 
 * Stacks and Deque whatever unit I think? I employed that logic here to simplify the process along with aligning it with the specifications and FAQ. 
 * For the solver method, I thought it was very hard but all you needed to do is look at the Game Tree from specs and check for cases for optimization and detecting dupes. 
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private boolean solvable;
    private int moves;
    // for solution
    private SearchNode solutionLast;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException("womp womp");
        }

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

        // need this for end code
        SearchNode current = mainPQ.delMin();
        SearchNode currentTwin = twinPQ.delMin();

        // end only after 1 of the isFinished is finished
        while (!current.initialBoard.isGoal() && !currentTwin.initialBoard.isGoal()) {

            SearchNode mainPrev = current.getPrev();
            SearchNode twinPrev = currentTwin.getPrev();

            // enqueue neighbors
            for (Board board : current.initialBoard.neighbors()) {
                // optimzation
                if (mainPrev == null || !board.equals(mainPrev.initialBoard)) {
                    mainPQ.insert(new SearchNode(board, (current.getMoves() + 1), current));
                }
            }

            for (Board board : currentTwin.initialBoard.neighbors()) {
                // optimzation
                if (twinPrev == null || !board.equals(twinPrev.initialBoard)) {
                    twinPQ.insert(new SearchNode(board, (currentTwin.getMoves() + 1), currentTwin));
                }
            }

            current = mainPQ.delMin();
            currentTwin = twinPQ.delMin();

            // note: from FAQ, prio of search node dequed from prio queue never decrease

        }

        // one will reach the end. if it isn't main, then main is not solvable. this is
        // because one will go on infinitely while one will eventually finish

        if (current.initialBoard.isGoal()) {
            solvable = true;
            moves = current.getMoves();
            solutionLast = current;
        } else {
            solvable = false;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {

        if (solvable) {
            return moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {

        SearchNode last = solutionLast;

        Stack<Board> stack = new Stack<Board>();
        if (isSolvable()) {

            while (last.getPrev() != null) {
                stack.push(last.initialBoard);
                last = last.getPrev();
            }
            stack.push(last.initialBoard);
            return stack;
        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
        private int man;

        public SearchNode(Board init, int mov, SearchNode prev) {
            previous = prev;
            initialBoard = init;
            moves = mov;
            man = init.manhattan();
        }

        @Deprecated
        public void increaseMoves() {
            moves++;
        }

        public int getPrio() {
            return man + moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.getPrio() - that.getPrio();
        }

        public SearchNode getPrev() {
            return previous;
        }

        // Not useful since no mutation in boards or SearchNode
        public Board copyBoard() {
            Board temp = initialBoard;
            return temp;
        }

        public int getMoves() {
            return moves;
        }

    }

}
