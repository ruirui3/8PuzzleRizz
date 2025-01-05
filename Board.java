import java.util.Arrays;
import java.util.Stack;

public class Board {

    private int[][] board;

    private int boardLength;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        board = cloning(tiles);

        boardLength = tiles.length;

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(boardLength + "\n");
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return boardLength;
    }

    // number of tiles out of place
    public int hamming() {

        int ham = 0;
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {

                // calculates the complete board equivilantcy

                /*
                 * 0 1 2
                 * 
                 * 0 1 2 3
                 * 1 4 5 6
                 * 2 7 8 9
                 * 
                 * do +1 becuase not 123/456/780 form
                 */

                if (board[i][j] != 0 && board[i][j] != ((i * boardLength + j + 1))) {
                    ham++;
                }

            }
        }
        return ham;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int man = 0;

        for (int row = 0; row < boardLength; row++) {
            for (int col = 0; col < boardLength; col++) {

                int tile = board[row][col];

                if (tile != 0) {
                    // -1 because not in form of 012/345/678
                    int correctRow = (tile - 1) / boardLength;
                    int correctCol = (tile - 1) % boardLength;

                    man += Math.abs(row - correctRow) + Math.abs(col - correctCol);
                }

                /*
                 * if (board[row][col] != 0) {
                 * int completeCol = 0;
                 * if (current % boardLength == 0) {
                 * completeCol = boardLength;
                 * } else {
                 * completeCol = current % boardLength;
                 * }
                 * 
                 * man += Math.abs((row + 1) - ((current / boardLength) + 1)) + Math.abs((col +
                 * 1) - completeCol); // check
                 * // if
                 * // right
                 * }
                 */

            }
        }
        return man;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    // does this board equal y?
    public boolean equals(Object y) {
        // look at Date.java
        if (this == y) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;

        return Arrays.deepEquals(this.board, that.board);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborsStack = new Stack<>();
        int indexBlank = findIndexBlank();
        int row = indexBlank / boardLength;
        int col = indexBlank % boardLength;
        // check if top exist, push swap betw blank and up
        if (row > 0) {
            neighborsStack.push(new Board(swapIndex(row, col, row - 1, col)));
        }

        // check if bot exist, push swap betw blank and up
        if (row < boardLength - 1) {
            neighborsStack.push(new Board(swapIndex(row, col, row + 1, col)));
        }

        // if there is something left, push swap betw blank and left
        if (col > 0) {
            neighborsStack.push(new Board(swapIndex(row, col, row, col - 1)));
        }

        // check if right exist, push swap betw blank and right
        if (col < boardLength - 1) {
            neighborsStack.push(new Board(swapIndex(row, col, row, col + 1)));
        }

        return neighborsStack;
    }

    private int[][] swapIndex(int row1, int col1, int row2, int col2) {

        int[][] copy = cloning(board);
        copy[row1][col1] = board[row2][col2];
        copy[row2][col2] = board[row1][col1];
        return copy;

    }

    private int[][] cloning(int[][] original) {
        int[][] copy = new int[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }

    private int findIndexBlank() {
        for (int i = 0; i < boardLength; i++) {
            for (int j = 0; j < boardLength; j++) {
                if (board[i][j] == 0) {
                    return j + i * boardLength;
                }
            }
        }
        return -1;
    }

    // a board that is obtained by exchanging any pair of tiles CHEESE: just swap
    // the origin with next element cuz 2x2 or higher

    // NOTE probably need to do it for any specified element not sure...
    public Board twin() {
        int[][] copy = cloning(board);

        if (copy[0][0] != 0 && copy[0][1] != 0) {
            return new Board(swapIndex(0, 0, 0, 1));
        } else {
            return new Board(swapIndex(1, 0, 1, 1));
        }

    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}