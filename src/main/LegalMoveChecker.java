package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class LegalMoveChecker implements Cloneable {

    private final ArrayList<Board> moveHistory; //= new ArrayList<int[][]>();
    private Board lastChecked;
    private int liberties;
    private static final int EMPTY = Board.EMPTY;
    private static final int BLACK = Board.BLACK;
    private static final int WHITE = Board.WHITE;

    //Constructor
    public LegalMoveChecker() {
        moveHistory = new ArrayList<>();
    }

    //Constructor to be used by clone method only.
    private LegalMoveChecker(ArrayList<Board> list) {
        moveHistory = list;
    }

    //Main MoveChecking method.
    public boolean checkMove(Board board, Coordinate coord, int colour) {

        int x = coord.x;
        int y = coord.y;
        Board bCopy = board.clone();
        int aggressor;
        int defender;
        boolean[][] visited;
        lastChecked = null;

        //1. if there is a stone already - illegal
        if (bCopy.get(x, y) != EMPTY) {
            return false;
        }

        //2. put a stone down
        if (colour == (BLACK)) {
            bCopy.set(x, y, BLACK);
            aggressor = BLACK;
            defender = WHITE;
        } else {
            bCopy.set(x, y, WHITE);
            aggressor = WHITE;
            defender = BLACK;
        }

            //3. check if any enemy stones have no liberty; if so remove them
        // 3.1. check if there are any adjacent enemy stones to the current one
        LinkedList<Coordinate> enemyCoordinates = new LinkedList<>();

        if (x > 0 && bCopy.get(x - 1, y) == defender) {
            enemyCoordinates.add(new Coordinate(x - 1, y));
        }

        if (x < bCopy.getWidth() - 1 && bCopy.get(x + 1, y) == defender) {
            enemyCoordinates.add(new Coordinate(x + 1, y));
        }

        if (y > 0 && bCopy.get(x, y - 1) == defender) {
            enemyCoordinates.add(new Coordinate(x, y - 1));
        }

        if (y < bCopy.getHeight() - 1 && bCopy.get(x, y + 1) == defender) {
            enemyCoordinates.add(new Coordinate(x, y + 1));
        }

            // if yes check if there are enemy stones without liberty and remove them if
        while (!enemyCoordinates.isEmpty()) {
            Coordinate c = enemyCoordinates.remove();
            liberties = 0;
            visited = new boolean[bCopy.getWidth()][bCopy.getHeight()];
            checkLiberty(bCopy, c, aggressor, visited);

            // restore defending checked stones to either black/white or remove if no liberty found
            if (liberties == 0) {
                for (int column = 0; column < bCopy.getWidth(); column++) {
                    for (int row = 0; row < bCopy.getHeight(); row++) {
                        if (visited[column][row]) {
                            bCopy.set(column, row, EMPTY);
                        }
                    }
                }
            }
        }

        //4. does the new stone group have a liberty; if no - illegal return false
        liberties = 0;
        visited = new boolean[bCopy.getWidth()][bCopy.getHeight()];

        checkLiberty(bCopy, new Coordinate(x, y), defender, visited);

        if (liberties == 0) {
            return false;
        }

        //5. Tests for SuperKo; if yes - illegal
        for (Board b : moveHistory) {
            if (b.equals(bCopy)) {
                return false;
            }
        }

        //6. legal
        lastChecked = bCopy;
        return true;
    }

    //Adds board to moveHistory
    public void addBoard(Board board) {
        moveHistory.add(board.clone());
    }

    //Removes the last board from the moveHistory
    public Board removeLast() {
        //System.out.println(moveHistory.toString());
        return moveHistory.remove(moveHistory.size() - 1);
    }

    //Gets last Board if it was legal. If not, returns null.
    public Board getLastLegal() {
        return lastChecked;
    }

    //Checks whether the moveHistory contains anything.
    public boolean isEmpty() {
        //System.out.println(moveHistory.toString());
        return moveHistory.isEmpty();
    }

	//Clones and returns LegalMoveChecker
    //Note, it does not clone the actual Boards, merely the list.
    @Override
    public LegalMoveChecker clone() {
        ArrayList<Board> history = new ArrayList<>();
        for (Board b : moveHistory) {
            history.add(b.clone());
        }
        return new LegalMoveChecker(history);
    }

    //recursive function to update the global liberty counter 

    private void checkLiberty(Board board, Coordinate c, int otherPlayer, boolean[][] visited) {
        if (visited[c.x][c.y] || board.get(c.x, c.y) == otherPlayer || liberties > 0) {
            return;
        }
        if (board.get(c.x, c.y) == EMPTY) {
            liberties++;
            return;
        }

        visited[c.x][c.y] = true;
        //out of boundary check + recursion 
        if (c.x > 0) {
            checkLiberty(board, new Coordinate(c.x - 1, c.y), otherPlayer, visited);
        }
        if (c.x < board.getWidth() - 1) {
            checkLiberty(board, new Coordinate(c.x + 1, c.y), otherPlayer, visited);
        }
        if (c.y > 0) {
            checkLiberty(board, new Coordinate(c.x, c.y - 1), otherPlayer, visited);
        }
        if (c.y < board.getHeight() - 1) {
            checkLiberty(board, new Coordinate(c.x, c.y + 1), otherPlayer, visited);
        }
    }
}
