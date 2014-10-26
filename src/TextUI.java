import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class TextUI{

	//Instance variables
	private String log = "";
	private GameEngine gameE;
	private String[] commands = {"help","exit","saveBoard (sb)","saveLog (sl)","view (v)","move (m)","checkLegal (cl)","new (n)",
								"loadBoard (lb)","undo (u)"};
	private boolean exit;
	private boolean boardSaved;
	private boolean logSaved;

	//Constructor
	public TextUI(GameEngine g){
		gameE = g;
	}

	//Main method for running the UI.
	public void init(){
		
		exit = false;
		boardSaved = true;
		logSaved = true;

		String command;
		Arrays.sort(commands); //Lazily arranges commands
		Scanner sc = new Scanner(System.in);
		System.out.println("--Go Game TextUI - v0.987654321--");
		System.out.println("> Type \"help\" for commands.");

		//Main while-loop
		while(!exit){
			System.out.print("<< ");
			command = sc.nextLine();
			String[] splitC = command.split(" ");
			String primaryCommand;

			//Verifies that there is an input.
			if(splitC.length > 0){
				primaryCommand = splitC[0];
				switch(primaryCommand){
					case "help": {help(splitC); break;}
					case "exit": {exit(); break;}
					case "new": 
					case "n": {newGame(splitC);break;}
					case "saveLog":
					case "sl":{saveLog(splitC); break;}
					case "saveBoard":
					case "sb":{saveBoard(splitC); break;}
					case "loadBoard":
					case "lb":{loadBoard(splitC);break;}
					case "move":
					case "m":{move(splitC);break;}
					case "checkLegal":
					case "cl":{break;}
					case "view":
					case "v":{break;}
					case "undo":
					case "u":{break;}
					default: {System.out.println("> Command not found. Type \"help\" for commands");break;}
				}
			}
		}	
	}

	//Prints help info
	private void help(String[] cmd){
		if(cmd.length == 1){
			System.out.println("> Available commands are: ");
			for(String c : commands)
				System.out.println(c);
			System.out.println("> To find out more about a command, type: \"help <full command>\"");
		}
		else if(cmd.length == 2)
			System.out.println("> Help file: "+FileIO.readHelp(FileIO.RELATIVEPATH+"\\info\\"+cmd[1]));
		else
			System.out.println("> Inappropriate number of args. Usage: \"help <full command>\" ");
	}

	//Exits UI
	private void exit(){
		exit = true;
		System.out.println("> Exiting UI...");
	}

	//Creates new game
	private void newGame(String[] cmd){
		String text;
		if(cmd.length == 1){
			text = "> Creating new 9x9 Board...";
			gameE.newGame();
		}
		else if(cmd.length == 3){
			int w; int h;
			if(FileIO.isNumber(cmd[1]) && FileIO.isNumber(cmd[2]) && (w = Integer.parseInt(cmd[1])) > 0 && (h = Integer.parseInt(cmd[2])) > 0){
				text = "> Creating new "+w+"x"+h+" Board...";
				gameE.newGame(new Board(w,h));
			}
			else{
				System.out.println("> Inappropriate dimensions in args. Dimensions need to be positive numbers.");
				return;
			}	
		}
		else{
			System.out.println("> Inappropriate number of args. Usage: new (n) [[arg width] [arg height]]");
			return;
		}
		addToLog(text);
		System.out.println(text);
		printGameBoard(true,false);
	}

	//Makes a new move
	private void move(String[] cmd){

	}

	//Saves current board to a file
	private void saveBoard(String[] cmd){
		Board b = gameE.getCurrentBoard();
		if(b == null){
			System.out.println("> Board has not been created or loaded yet.");
		}
		else{
			switch(cmd.length){
			case 1: {FileIO.writeBoard(b); break;}
			case 2: {FileIO.writeBoard(b, FileIO.RELATIVEPATH+FileIO.DEFOUTPUT+cmd[1]);break;}
			default:{System.out.println("> Inappropriate number of args. Usage: saveBoard (sb) [arg name]"); return;}
		}
		boardSaved = true;
		}
	}

	//Loads a given board file
	private void loadBoard(String[] cmd){
		Board b;
		switch(cmd.length){
			case 1:{b = FileIO.readBoard(); break;}
			case 2:{b = FileIO.readBoard(FileIO.RELATIVEPATH+FileIO.DEFINPUT+cmd[1]); break;}
			default:{System.out.println("> Inappropriate number of args. Usage: loadBoard (lb) [arg name]");return;}
		}
		gameE.newGame(b);

		String text = "> Loaded "+b.getWidth()+"x"+b.getHeight();
		addToLog(text);
		System.out.println(text);
		printGameBoard(true,false);
	}

	//Saves log to a file.
	private void saveLog(String[] cmd){
		if(log.equals("")){
			System.out.println("> The log is empty. Stop wasting files.");
		}
		else{
			switch(cmd.length){
			case 1: {FileIO.writeLog(log); break;}
			case 2: {FileIO.writeLog(log, FileIO.RELATIVEPATH+FileIO.DEFOUTPUT+cmd[1]);break;}
			default:{System.out.println("> Inappropriate number of args. Usage: saveLog (sl) [arg name]"); return;}
		}
		logSaved = true;
		}
	}

	//Updates log
	private void addToLog(String data){
		log += ('\n'+(new Timestamp((new GregorianCalendar()).getTimeInMillis())).toString()+'\n');
		log += data+'\n';
		logSaved = false;
	}

	//Prints game board.
	private void printGameBoard(boolean saveToLog, boolean detailed){

		int[][] board = gameE.getCurrentBoard().getRaw();
		ArrayList<String> lines = new ArrayList<>();

		try{
			for(int i = 0; i < board[0].length; i++){
				lines.add("");
				int p = lines.size()-1;
				for(int j = 0; j < board.length; j++)
					lines.set(p, lines.get(p)+FileIO.translateToChar(board[j][i]));
			}
			printBoard(lines, saveToLog, detailed);
		}
		catch(BoardFormatException b){System.err.println(b.getMsg()+"\n> The board could not be printed");}

	}

	//Prints general boards.
	private void printBoard(ArrayList<String> lines, boolean saveToLog, boolean detailed){

		System.out.println();
		String tempLog = "";
		if(detailed)
			lines = addBoardDetails(lines);
		for(String s : lines){
			if(saveToLog)
				tempLog += s+'\n';
			System.out.println(s);
			}

		System.out.println();

		if(saveToLog)
			addToLog(tempLog);

	}
	//Adds details to a board view. Currently just board indexing.
	private ArrayList <String> addBoardDetails(ArrayList <String> board){

		//indices
		int width = board.get(0).length();
		int height = board.size();
		int wLength = String.valueOf(width).length(); //Digit length for buffering.
		int hLength = String.valueOf(height).length();

		ArrayList <String> detailedBoard = new ArrayList<>();
		
		//Row indices
		for(int i = 0; i < height; i++){

			String spacing = "";
			int buffer = String.valueOf(i).length();

			while(buffer++ < hLength)
				spacing += ' ';

			spacing += Integer.toString(i) + ' ';
			detailedBoard.add(spacing + board.get(i));
		}

		detailedBoard.add("");

		//Column indices
		for(int i = 0; i < wLength; i++){

			String line = "";
			for(int j = 0; j < width; j++){
				if(String.valueOf(j).length() > i)
					line += Integer.toString(j).charAt(i);
				else
					line += ' ';
			}
			for(int j = 0; j < hLength; j++)
				line = ' '+line;
			detailedBoard.add(' '+line);
		}


		return detailedBoard;
	}

}