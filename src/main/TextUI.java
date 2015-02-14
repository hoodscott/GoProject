package main;

import ai.AIException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class TextUI {

    //Instance variables
    private String log = "";
    private GameEngine gameEngine;
    private String[] commands = {"help", "exit", "saveBoard (sb)", "saveLog (sl)", "view (v)", "move (m)", "pass (p)", "checkLegal (cl)", "startGame (sg)",
        "loadBoard (lb)", "undo (u)"};
    private boolean exit;
    private boolean boardSaved;
    private boolean logSaved;
    private boolean playerPassed;
    private boolean aiPassed;
    private int playerColour;

    //Constructor
    public TextUI() {
    }

    //Main method for running the UI.
    public void init() {

        exit = false;
        boardSaved = true;
        logSaved = true;

        String command;
        Arrays.sort(commands); //Lazily sorts commands
        Scanner sc = new Scanner(System.in);
        System.out.println("--Go Game TextUI - v0.987654321--");
        System.out.println("> Type \"help\" for commands.");

        //Main while-loop
        while (!exit) {
            System.out.print("<< ");
            command = sc.nextLine();
            String[] splitC = command.split(" ");
            String primaryCommand;

            //Verifies that there is an input.
            if (splitC.length > 0) {
                primaryCommand = splitC[0];
                switch (primaryCommand) {
                    case "help": {
                        help(splitC);
                        break;
                    }
                    case "exit": {
                        exit();
                        break;
                    }
                    case "saveLog":
                    case "sl": {
                        saveLog(splitC);
                        break;
                    }
                    case "saveBoard":
                    case "sb": {
                        saveBoard(splitC);
                        break;
                    }
                    case "loadBoard":
                    case "lb": {
                        loadBoard(splitC);
                        break;
                    }
                    case "move":
                    case "m": {
                        move(splitC);
                        break;
                    }
                    case "pass":
                    case "p": {
                        pass();
                        break;
                    }
                    case "startGame":
                    case "sg": {
                        startGame(splitC);
                        break;
                    }
                    case "checkLegal":
                    case "cl": {
                        checkLegal(splitC);
                        break;
                    }
                    case "view":
                    case "v": {
                        view();
                        break;
                    }
                    case "undo":
                    case "u": {
                        undo();
                        break;
                    }
                    default: {
                        System.out.println("> Command not found. Type \"help\" for commands");
                        break;
                    }
                }
            }
        }
    }

    //Prints help info
    public void help(String[] cmd) {
        try {
            if (cmd.length == 1) {
                System.out.println("> Available commands are: ");
                for (String c : commands) {
                    System.out.println(c);
                }
                System.out.println("> To find out more about a command, type: \"help <full command>\"");
            } else if (cmd.length == 2) {
                System.out.println("> Help file: " + FileIO.readHelp(FileIO.RELATIVEPATH + "\\info\\" + cmd[1]));
            } else {
                throw new BadInputException("> Inappropriate number of args. Usage: \"help <full command>\" ");
            }
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        }
    }

    //Exits UI
    public void exit() {
        Scanner temp = new Scanner(System.in);

        if (!boardSaved || !logSaved) {
            System.out.println("> You have not saved your board or log. Would you like to? (Y/N)");
            boolean answer = false;
            while (!answer) {
                System.out.print("<< ");
                String a = temp.nextLine();
                if (a.equalsIgnoreCase("Y")) {
                    System.out.println("> Then go save it.");
                    return;
                } else if (a.equalsIgnoreCase("N")) {
                    answer = true;
                }
            }
        }
        exit = true;
        System.out.println("> Exiting UI...");
    }

    //Starts game with an AI
    public void startGame(String[] cmd) {
        try {
            if (gameEngine == null || gameEngine.getObjective() == null) {
                throw new BoardFormatException("> No objective has been defined.");
            }
            if (cmd.length != 3) {
                throw new BoardFormatException("> Inappropriate number of args. Usage: startGame (sg) <black player> <white player>");
            }

            int humanColour = 0;
            int aiColour = 0;

            switch (cmd[1]) {
                case "h":
                case "human":
                    humanColour = Board.BLACK;
                    break;
                case "mini":
                case "minimax":
                    aiColour = Board.BLACK;
                    break;
                default:
                    throw new BoardFormatException("> The argument needs to be either a human (h) or minimax (m).");
            }

            if ((cmd[2].equals("h") || cmd[2].equals("human")) && humanColour == 0) {
                humanColour = Board.WHITE;
            } else if ((cmd[2].equals("mini") || cmd[2].equals("minimax")) && aiColour == 0) {
                aiColour = Board.WHITE;
            } else {
                throw new BoardFormatException("> Only one ai and one human are allowed as arguments.");
            }

            playerColour = humanColour;
            gameEngine.setMiniMax(aiColour);
            gameEngine.startGame();

            String message = "> Starting game with black as " + (playerColour == Board.BLACK ? "human" : "minimax")
                    + " and white as " + (playerColour == Board.WHITE ? "human" : "minimax") + ".";

            //message += "\n> "+Translator.translateToString(playerColour)+"\'s turn:";
            addToLog(message);
            System.out.println(message);

            if (gameEngine.getObjective().isStarting(aiColour)) {
                aiMove();
            }
        } catch (BoardFormatException bad) {
            System.out.println(bad.getMsg());
        }
    }

    //Makes a new move
    public void move(String[] cmd) {
        try {
            if (gameEngine == null || !gameEngine.isInGame()) {
                throw new BadInputException("> There currently is no board to make a move on.");
            }

            if (cmd.length != 3) {
                throw new BadInputException("> Inappropriate number of args. Usage: move (m) <arg x> <arg y>");
            }

            int x, y;
            int w = gameEngine.getCurrentBoard().getWidth();
            int h = gameEngine.getCurrentBoard().getHeight();

            if ((x = Integer.parseInt(cmd[1])) < 0 || x > w || (y = Integer.parseInt(cmd[2])) < 0 || y > h) {
                throw new BadInputException("> The x and y positions need to be non-negative numbers within the board.");
            }

            if (gameEngine.makeMove(new Coordinate(x, y), playerColour)) {
                String message = "> Placed " + Translator.translateToString(playerColour) + " at (" + cmd[1] + "," + cmd[2] + ")";
                addToLog(message);
                System.out.println(message);
                printGameBoard(true);
                boardSaved = false;

                aiMove();

                message = "> " + Translator.translateToString(playerColour) + "\'s turn:";
                addToLog(message);
                System.out.println(message);
            } else {
                throw new BadInputException("> This move is illegal.");
            }

        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        } catch (BoardFormatException bad) {
            System.err.println(bad.getMsg());
        } catch (NumberFormatException bad) {
            System.out.println("> The x and y positions need to be non-negative numbers within the board.");
        }
    }

    public void pass() {
        try {
            if (gameEngine == null || !gameEngine.isInGame()) {
                throw new BadInputException("> There is no board to pass on.");
            }

            String message = "> " + Translator.translateToString(playerColour) + "passes";
            addToLog(message);
            System.out.println(message);

            aiMove();
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        } catch (BoardFormatException bad) {
        }
    }

    public void aiMove() {
        try {
            String message = "> " + Translator.translateToString(gameEngine.getAI().getColour()) + "\'s turn:";
            message += "\n> " + Translator.translateToString(gameEngine.getAI().getColour()) + " " + gameEngine.aiMove();
            addToLog(message);
            System.out.println(message);
            printGameBoard(true);
            boardSaved = false;
        } catch (BoardFormatException | AIException a) {
            System.out.println("AI: " + a.getMessage());
        }
    }

    //Undoes the last move made.
    public void undo() {
        try {
            if (gameEngine == null) {
                throw new BadInputException("> There is no board with moves to undo.");
            }
            if (!gameEngine.undoLastMove()) {
                throw new BadInputException("> There are no moves to undo on this board.");
            }

            String message = " > Undoing last move...";
            addToLog(message);
            System.out.println(message);
            printGameBoard(true);
            boardSaved = false;
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        }
    }

    //Prints the current board
    public void view() {
        try {
            if (gameEngine == null) {
                throw new BadInputException("> There currently is no board to view.");
            }

            printGameBoard(false);
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        }
    }

    public void checkLegal(String[] cmd) {
        try {
            if (gameEngine == null) {
                throw new BadInputException("> There currently is no board to check.");
            }
            if (cmd.length != 2) {
                throw new BadInputException("> Inappropriate number of args. Usage: checkLegal (cl) <arg colour>");
            }

            if (cmd[1].equals("b") || cmd[1].equals("black") || cmd[1].equals("w") || cmd[1].equals("white")) {
                System.out.println("> Legal moves for " + cmd[1]);
                printLegalBoard(Translator.translateToInt(cmd[1].charAt(0)));
            } else {
                throw new BadInputException("> The colour argument needs to be either \"black\" (b) or \"white\" (w)");
            }
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        } catch (BoardFormatException bad) {
            System.err.println(bad.getMsg());
        }
    }

    //Saves current board to a file
    public void saveBoard(String[] cmd) {
        try {
            if (gameEngine == null) {
                throw new BadInputException("> Board has not been created or loaded yet.");
            }

            switch (cmd.length) {
                case 1: {
                    FileIO.writeBoard(gameEngine);
                    break;
                }
                case 2: {
                    FileIO.writeBoard(gameEngine, FileIO.RELATIVEPATH + FileIO.DEFOUTPUT + cmd[1]);
                    break;
                }
                default: {
                    throw new BadInputException("> Inappropriate number of args. Usage: saveBoard (sb) [arg name]");
                }
            }
            boardSaved = true;
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        } catch (BoardFormatException bad) {
            System.err.println(bad.getMsg());
        } catch (NumberFormatException bad) {
            System.err.println("ERROR: Non-integer values used to save board.");
        }
    }

    //Loads a given board file
    public void loadBoard(String[] cmd) {
        try {
            switch (cmd.length) {
                case 1: {
                    gameEngine = FileIO.readBoard();
                    break;
                }
                case 2: {
                    gameEngine = FileIO.readBoard(FileIO.RELATIVEPATH + FileIO.DEFINPUT + cmd[1]);
                    break;
                }
                default: {
                    throw new BadInputException("> Inappropriate number of args. Usage: loadBoard (lb) [arg name]");
                }
            }

            String text = "> Loaded " + gameEngine.getCurrentBoard().getWidth() + "x" + gameEngine.getCurrentBoard().getHeight();
            if (gameEngine.getObjective() != null) {
                text += "\n> Loaded Objective: " + Translator.translateToObjectiveInstruction(gameEngine.getObjective());
            }
            addToLog(text);
            System.out.println(text);
            printGameBoard(true);
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        } catch (BoardFormatException board) {
            System.err.println(board.getMsg());
        }
    }

    //Saves log to a file.
    public void saveLog(String[] cmd) {
        try {
            if (log.equals("")) {
                throw new BadInputException("> The log is empty. Stop wasting files.");
            }

            switch (cmd.length) {
                case 1: {
                    FileIO.writeLog(log);
                    break;
                }
                case 2: {
                    FileIO.writeLog(log, FileIO.RELATIVEPATH + FileIO.DEFLOGOUTPUT + cmd[1]);
                    break;
                }
                default: {
                    throw new BadInputException("> Inappropriate number of args. Usage: saveLog (sl) [arg name]");
                }
            }
            logSaved = true;
        } catch (BadInputException bad) {
            System.out.println(bad.getMsg());
        }
    }

    //Updates log
    public void addToLog(String data) {
        log += ('\n' + (new Timestamp((new GregorianCalendar()).getTimeInMillis())).toString() + '\n');
        log += data + '\n';
        logSaved = false;
    }

    //Prints game board.
    public void printGameBoard(boolean saveToLog) {

        int[][] board = gameEngine.getCurrentBoard().getRaw();
        ArrayList<String> lines = new ArrayList<>();

        try {
            for (int i = 0; i < board[0].length; i++) {
                lines.add("");
                int p = lines.size() - 1;
                for (int j = 0; j < board.length; j++) {
                    lines.set(p, lines.get(p) + Translator.translateToChar(board[i][j]));
                }
            }
            printBoard(lines, saveToLog);
        } catch (BoardFormatException b) {
            System.err.println(b.getMsg() + "\n> The board could not be printed");
        }
    }

    //Prints legal board
    public void printLegalBoard(int colour) {

        try {
            ArrayList<String> lines = new ArrayList<>();
            boolean[][] legalMoves = gameEngine.getLegalMoves(colour);
            int[][] board = gameEngine.getCurrentBoard().getRaw();

            for (int i = 0; i < legalMoves[0].length; i++) {
                String line = "";
                for (int j = 0; j < legalMoves.length; j++) {
                    if (board[j][i] != 0) {
                        line += Translator.translateToChar(board[j][i]);
                    } else {
                        line += (legalMoves[j][i] ? '+' : '-');
                    }
                }
                lines.add(line);
            }
            printBoard(lines, false);
        } catch (BoardFormatException b) {
            System.err.println(b.getMsg() + "\n> The board could not be printed");
        }
    }

    //Prints general boards.
    public void printBoard(ArrayList<String> lines, boolean saveToLog) {

        System.out.println();
        String tempLog = "";
        lines = addBoardDetails(lines);

        for (String s : lines) {
            if (saveToLog) {
                tempLog += s + '\n';
            }
            System.out.println(s);
        }

        System.out.println();

        if (saveToLog) {
            addToLog(tempLog);
        }

    }

    //Adds details to a board view. Currently just board indexing.
    public ArrayList<String> addBoardDetails(ArrayList<String> board) {

        //indices
        int width = board.get(0).length();
        int height = board.size();
        int wLength = String.valueOf(width).length(); //Digit length for buffering.
        int hLength = String.valueOf(height).length();

        ArrayList<String> detailedBoard = new ArrayList<>();

        //Row indices
        for (int i = 0; i < height; i++) {

            String spacing = "";
            int buffer = String.valueOf(i).length();

            while (buffer++ < hLength) {
                spacing += ' ';
            }

            spacing += Integer.toString(i) + ' ';
            detailedBoard.add(spacing + board.get(i));
        }

        detailedBoard.add("");

        //Column indices
        for (int i = 0; i < wLength; i++) {

            String line = "";
            for (int j = 0; j < width; j++) {
                if (String.valueOf(j).length() > i) {
                    line += Integer.toString(j).charAt(i);
                } else {
                    line += ' ';
                }
            }
            for (int j = 0; j < hLength; j++) {
                line = ' ' + line;
            }
            detailedBoard.add(' ' + line);
        }
        return detailedBoard;
    }
}
