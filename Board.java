




//maybe since each number is 1 greater than index, can make a 
//conversion method. AKA CHANGE int[][] finishedTiles at some point
/**
 * Hammering calculation: number of elements in their INCORRECT position
 * Manhatton calculation: the sum of distance (moves one element per shift, no diagonal) to the correct position
 * priority calculation: the sum of hammering and manhatton. the lower prio, the better
 * 
 * 
 * Board prio works like 
 */



public class Board {

    private Node[] board;
    private int bLength;

    private static class Node {
        private int row;
        private int col;
        private int value;

    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        bLength = tiles.length;
        board = new Node[bLength*2];
        int cur = 0;
        for (int i = 0; i<bLength; i++) {
            for (int j = 0; j<bLength; j++) {

                board[cur].value = tiles[i][j];
                board[cur].row = i+1;
                board[cur].col = j+1;

                cur++;
            }
        }

    }
                                           
    // string representation of this board
    public String toString() { //convert to legible form
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
        return bLength*2;
    }

    // number of tiles out of place
    public int hamming() {
        int right = 1;
        int ham = 0;
        for (int i = 0; i<dimension()-1; i++) {
            if (board[i].value != right) {
                ham++;
            }
        }
        if (board[dimension()-1].value == 0) {
            return ham;
        }
            return ham+1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        

    }

    /**
     *    1 2 3
     *    4 5 6 
     *    7 8 0
     * 
     * @param row
     * @param col
     * @return
     */


    private int getIndex(int row, int col) {
        return (row-1) * bLength + col; 
    } 



    // is this board the goal board?
    public boolean isGoal() {

    }

    // does this board equal y? implement comparison equal method
    public boolean equals(Object y) {

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }

    // unit testing (not graded)
    public static void main(String[] args)

}