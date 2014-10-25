import java.util.Scanner;
import java.util.Arrays;
public class TextUI{

	//Instance variables
	private String UIlog;
	private String InputLog;
	private String OutputLog;
	private GameEngine gameE;
	private String[] commands = {"help","exit","saveBoard (sb)","saveLog (sl)","view (v)","move (m)","checkLegal (c)","new (n)",
								"loadBoard (l)"};
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
		System.out.println("--Go Game TextUI - v0.99999999999--");
		System.out.println("> Type \"help\" for commands.");

		//Main while-loop
		while(!exit){
			System.out.print("< ");
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
					case "n": {newGame(splitC)break;}
					default: break;
				}
		}	}
	}

	//Returns help info
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

	private void exit(){
		exit = true;
		System.out.println("> Exiting UI...");
	}

	private void newGame(String[] cmd){
		if(cmd.length == 1){
			String text = "> Creating new 9x9 Board...";
			printBoard()

		}
	}

	private void addToLog(){}

	private void printBoard(boolean ){}
}