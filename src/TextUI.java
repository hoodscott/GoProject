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
		String command;
		Arrays.sort(commands); //Lazily arranges commands
		Scanner sc = new Scanner(System.in);

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
					case "saveBoard":
					case "sb": {}
					default: break;
				}
		}	}
	}

	private void help(String[]){
		if(splitC == 1){
			System.out.println("> Available commands are: ");
			for(String c : commands)
				System.out.println(c);
			System.out.println("> To find out more about a command, type \"help <command>\"");
		}
		else if(splitC == 2){
			
		}
	}

	private void exit(){
		exit = true;
		System.out.println("> Exiting UI...");
	}

	private void saveBoard(){

	}
}