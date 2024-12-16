




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


    private int[][] boardTiles;
    private int n;
    private int[][] finishedTiles;
    


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {


        n = tiles.length;

        boardTiles = tiles;
        finishedTiles = new int[n][n];
        int k = 1;
        for (int i = 0; i<n; i++) {
            for (int j = 0; j<n; j++) {
                finishedTiles[i][j] = k;
            }
        }
        finishedTiles[n-1][n-1] = 0;

    }
                                           
    // string representation of this board
    public String toString() {

    }

    // board dimension n
    public int dimension() {

    }

    // number of tiles out of place
    public int hamming() {

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        for (int i = 0; i<boardTiles.length; i++) {

            if (boardTiles[])


        }

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