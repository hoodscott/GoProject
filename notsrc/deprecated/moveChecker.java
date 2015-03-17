package moveChecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class check {

    private static ArrayList<int[][]> PrevBoards = new ArrayList<int[][]>();
    private static int[][] board = new int[9][9];
    private static int libCounter = 0;

    public static void main(String[] args) {

        String currentline;
        //store the board from file into 2 dimensional array
        try (BufferedReader br = new BufferedReader(new FileReader("board.txt"))) {
            int lineNumber = 0;
            while ((currentline = br.readLine()) != null) {
                for (int c = 0; c < 9; c++) {
                    board[lineNumber][c] = Character.getNumericValue(currentline.charAt(c));
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrevBoards.add(board);
        print();

        // we may put a correct input check ..
        // choose black or white to play
        Scanner sc = new Scanner(System.in);
        System.out.println("Player (1 - black, 2 - white)");
        int player = sc.nextInt();
        boolean switchP = false;
        boolean flag = true;

        // we may put a correct coordinates input check
        while (flag) {
            if (switchP) {
                player = switchPlayer(player);
            }
            System.out.println("Choose position to play (x coordinate)");
            int xCoord = sc.nextInt();
            System.out.println("Choose position to play (y coordinate)");
            int yCoord = sc.nextInt();
            if (checkMove(xCoord, yCoord, player)) {
                updateBoard();
                switchP = true;
                System.out.println("Continue? [y/n]");
                String answer = sc.next();
                if (answer.compareTo("y") != 0) {
                    flag = false;
                }
            } else {
                switchP = false;
                System.out.println("Illegal move, try again!");
            }
        }
        sc.close();
    }

    // check if a move is legal
    public static boolean checkMove(int x, int y, int p) {
        //1. if there is a stone already - illegal
        if (board[x][y] != 0) {
            return false;
        }

        //2. put a stone down
        if (p == 1) {
            board[x][y] = 1;
        } else {
            board[x][y] = 2;
        }

        //3. check if any enemy stones have no liberty; if so remove them
        // 3.1. check if there are any adjacent enemy stones to the current one
        //if yes store them in a hash map
        Map<Integer, Coordinates> enemyCoordinates = new HashMap<Integer, Coordinates>();
        int coordFound = 0;

        if (x >= 1) {
            if (board[x - 1][y] == switchPlayer(p)) {
                enemyCoordinates.put(++coordFound, new Coordinates(x - 1, y));
            }
        }
        if (x <= 7) {
            if (board[x + 1][y] == switchPlayer(p)) {
                enemyCoordinates.put(++coordFound, new Coordinates(x + 1, y));
            }
        }
        if (y >= 1) {
            if (board[x][y - 1] == switchPlayer(p)) {
                enemyCoordinates.put(++coordFound, new Coordinates(x, y - 1));
            }
        }
        if (y <= 7) {
            if (board[x][y + 1] == switchPlayer(p)) {
                enemyCoordinates.put(++coordFound, new Coordinates(x, y + 1));
            }
        }

        // if yes check if there are enemy stones without liberty and remove them if
        if (coordFound != 0) {
            for (int i = 1; i <= coordFound; i++) {
                Coordinates c = enemyCoordinates.get(i);
                // call the recursive function (it uses floodfill alg.)
                checkLiberty(c.getX(), c.getY(), switchPlayer(p), 3);
                for (int o = 0; o < 9; o++) {
                    for (int q = 0; q < 9; q++) {
                        if (libCounter == 0) {
                            if (board[o][q] == 3) {
                                board[o][q] = 0;
                            }
                        } else {
                            if (board[o][q] == 3) {
                                board[o][q] = switchPlayer(p);
                            }
                        }
                    }
                }
                libCounter = 0;
            }
        }

        //4. does the new stone group has liberty; if no - illegal return false
        checkLiberty(x, y, p, 3);
        for (int o = 0; o < 9; o++) {
            for (int q = 0; q < 9; q++) {
                if (board[o][q] == 3) {
                    board[o][q] = p;
                }
            }
        }
        if (libCounter == 0) {
            board[x][y] = 0; // remove the stone
            return false; //illegal
        }
        libCounter = 0;

        //5. has the board position appeared before; if yes - illegal
        for (int a[][] : PrevBoards) {
            if (Arrays.equals(a, board)) {
                return false;
            }
        }

        //6. legal
        return true;

    }

    //recursive function to update the global liberty counter 
    public static void checkLiberty(int x, int y, int pl, int checked) {
        if (board[x][y] == checked || board[x][y] == switchPlayer(pl)) {
            return;
        }
        if (board[x][y] == 0) {
            libCounter++;
            return;
        }
        if (board[x][y] == pl) {
            board[x][y] = checked;

            //out of boundary check + recursion 
            if (x >= 1) {
                checkLiberty(x - 1, y, pl, checked);
            }
            if (x <= 7) {
                checkLiberty(x + 1, y, pl, checked);
            }
            if (y >= 1) {
                checkLiberty(x, y - 1, pl, checked);
            }
            if (y <= 7) {
                checkLiberty(x, y + 1, pl, checked);
            }
        }
    }

    public static int switchPlayer(int p) {
        if (p == 1) {
            return 2;
        } else {
            return 1;
        }

    }

    public static void updateBoard() {
        PrevBoards.add(board);
        print();
    }

    public static void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    System.out.print(".");
                } else if (board[i][j] == 1) {
                    System.out.print("X");
                } else if (board[i][j] == 2) {
                    System.out.print("O");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
