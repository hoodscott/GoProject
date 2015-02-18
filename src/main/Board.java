package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final byte[][] boardRep;
    private static final int DEF_DIMENSION = 9; //Default dimension for empty boards
    public static final byte EMPTY = 0;
    public static final byte BLACK = 1;
    public static final byte WHITE = 2;
    public static final byte EMPTY_AI = 3;
    public static final char C_EMPTY = '.';
    public static final char C_EMPTY_AI = '-';
    public static final char C_BLACK = 'b';
    public static final char C_WHITE = 'w';
    public static final String S_EMPTY = "empty";
    public static final String S_BLACK = "black";
    public static final String S_WHITE = "white";

    // Create empty board
    public Board() {
        boardRep = new byte[DEF_DIMENSION][DEF_DIMENSION];
    }

    // Create new board from given int array
    public Board(byte[][] board) {
        boardRep = board;
    }

    //Create an empty board that takes in a pair of dimensions
    public Board(int width, int height) {
        boardRep = new byte[width][height];
    }

    // Get x and y dimensions of board
    public int getWidth() {
        return boardRep.length;
    }

    public int getHeight() {
        return boardRep[0].length;
    }

    //Get raw representation of the board
    public byte[][] getRaw() {
        return boardRep;
    }

    // Get int representation of board position
    public byte get(int x, int y) {
        return boardRep[x][y];
    }

    // Set int representation of board position
    public void set(int x, int y, byte value) {
        boardRep[x][y] = value;
    }

    //Tests for equality among Boards. Use with care.
    public boolean equals(Board b) {
        if (b == null) {
            return false;
        }
        byte[][] otherBoardRep = b.getRaw();
        for (int i = 0; i < otherBoardRep.length; i++) {
            for (int j = 0; j < otherBoardRep[0].length; j++) {
                int p1 = otherBoardRep[i][j];
                int p2 = boardRep[i][j];
                if (p1 != p2) {
                    if (!((p1 == EMPTY_AI || p1 == EMPTY) && (p2 == EMPTY_AI || p2 == EMPTY))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // Make deep copy of original Board
    @Override
    public Board clone() {
        Board clone = new Board(boardRep.length, boardRep[0].length);
        for (int i = 0; i < boardRep.length; i++) {
            for (int j = 0; j < boardRep[0].length; j++) {
                clone.set(i, j, boardRep[i][j]);
            }
        }

        return clone;
    }
}
