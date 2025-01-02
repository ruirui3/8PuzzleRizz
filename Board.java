
//maybe since each number is 1 greater than index, can make a 
//conversion method. AKA CHANGE int[][] finishedTiles at some point

import java.util.Arrays;

/**
 * Hammering calculation: number of elements in their INCORRECT position
 * Manhatton calculation: the sum of distance (moves one element per shift, no
 * diagonal) to the correct position
 * priority calculation: the sum of hammering and manhatton. the lower prio, the
 * better
 * 
 * 
 * Board prio works like
 */

public class Board {

    private Node[] board;
    private int bLength;
    private int boardSize;
    private Node[] complete;

    private static class Node {
        private int row;
        private int col;
        private int value;

    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    // 1 2 3
    // 4 5 6
    // 7 8 9
    // = 123456789
    public Board(int[][] tiles) {

        bLength = tiles.length;
        board = new Node[bLength * bLength];
        boardSize = bLength * bLength;
        int cur = 0;
        for (int i = 0; i < bLength; i++) {
            for (int j = 0; j < bLength; j++) {

                board[cur].value = tiles[i][j];
                board[cur].row = i + 1;
                board[cur].col = j + 1;

                cur++;
            }
        }

        // makes copy of complete board
        for (int i = 1; i < boardSize; i++) {
            complete[i].value = i;
            complete[i].row = (i / bLength) + 1;
            if (i % bLength == 0) {
                complete[i].col = bLength;
            } else {
                complete[i].col = i % bLength;
            }

        }
        complete[boardSize - 1].value = 0;
        complete[boardSize - 1].row = bLength;
        complete[boardSize - 1].col = bLength;

    }

    // string representation of this board
    public String toString() { // convert to legible form
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return bLength * 2;
    }

    // number of tiles out of place
    // FINISHED
    public int hamming() {
        int rightIndex = 1;
        int ham = 0;
        for (int i = 0; i < dimension() - 1; i++) {
            if (board[i].value != rightIndex) {
                ham++;
            }
            rightIndex++;
        }

        return ham;

        // use this if hammer counts last item
        /*
         * if (board[dimension()-1].value == 0) {
         * return ham;
         * }
         * return ham+1;
         */
    }

    // sum of Manhattan distances between tiles and goal
    // use Math.abs()
    public int manhattan() {
        int man = 0;

        for (int i = 0; i < boardSize; i++) {
            man += Math.abs(board[i].row - complete[i].row) + Math.abs(board[i].col - complete[i].col);
        }
        return man;

    }

    /**
     * 1 2 3
     * 4 5 6
     * 7 8 0
     * 
     * @param row
     * @param col
     * @return
     */

    // returns index in Node[] board at given row and col with bound consideration
    // like (1,1) as origin (refer to onenote)
    private int getIndex(int row, int col) {
        return (row - 1) * bLength + col - 1;
    }

    // is this board the goal board?
    public boolean isGoal() {

        int correctVal = 1;
        for (int i = 0; i < (bLength * bLength) - 1; i++) {
            if (board[i].value != correctVal) {
                return false;
            }
            correctVal++;
        }
        if (board[(bLength * bLength) - 1].value != 0) {
            return false;
        }
        return true;
    }

    @Override
    // does this board equal y? implement comparison equal method
    public boolean equals(Object y) {
        // look at Date.java
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return Arrays.deepEquals(this.board, that.board); // this might not work? FAQ says if a and b are type int[]
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}