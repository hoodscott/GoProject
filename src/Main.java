import java.util.Arrays;

public class Main{
	//Use this class to test your stuff. Just comment out or delete your code before you commit it.
	public static void main(String[] args)
	{
		//Test of GameEngine + LegalMoveChecker
		GameEngine gE = new GameEngine();
		int[][] b1 = {{0,0,0},{1,0,1},{1,1,1}};
		int[][] b2 = {{0,1,0},{1,0,1},{1,1,1}};
		int[][] b3 = {{1,1,1},{1,1,1},{1,1,1}};

		gE.newGame(new Board(b1));
		System.out.println(Arrays.deepToString(b1));
		System.out.println("Black: "+Arrays.deepToString(gE.getLegalMoves(1)));
		System.out.println("White: "+Arrays.deepToString(gE.getLegalMoves(2)));

		gE.newGame(new Board(b2));
		System.out.println(Arrays.deepToString(b2));
		System.out.println("Black: "+Arrays.deepToString(gE.getLegalMoves(1)));
		System.out.println("White: "+Arrays.deepToString(gE.getLegalMoves(2)));

		gE.newGame(new Board(b3));
		System.out.println(Arrays.deepToString(b3));
		System.out.println("Black: "+Arrays.deepToString(gE.getLegalMoves(1)));
		System.out.println("White: "+Arrays.deepToString(gE.getLegalMoves(2)));

	}
}


		/* Test of Board.clone()

		import java.util.Arrays;

		Board a = new Board(2,2);
		a.set(0,0,0);
		a.set(0,1,1);
		a.set(1,0,2);
		a.set(1,1,3);

		Board b = a.clone();
		b.set(0,0,3);
		b.set(0,1,2);
		b.set(1,0,1);
		b.set(1,1,0);

		System.out.println("A:");
		System.out.println(a.get(0,0));
		System.out.println(a.get(0,1));
		System.out.println(a.get(1,0));
		System.out.println(a.get(1,1));

		System.out.println("B:");
		System.out.println(b.get(0,0));
		System.out.println(b.get(0,1));
		System.out.println(b.get(1,0));
		System.out.println(b.get(1,1));
		*/

				/*
		// Test of FileIO boardWrite()

		Board a = new Board(2,2);
		a.set(0,0,0);
		a.set(0,1,1);
		a.set(1,0,2);
		a.set(1,1,1);

		Board b = new Board(2,2);
		b.set(0,0,1);
		b.set(0,1,2);
		b.set(1,0,1);
		b.set(1,1,0);

		FileIO.writeBoard(a);
		FileIO.writeBoard(b, "saveData/boards/specialBoard"); 

		Board c = FileIO.readBoard();
		FileIO.readBoard("saveData/boards/BoardExampleFormat");

		System.out.println("Board top left "+c.get(0,0));

		FileIO.writeToLog("This is a log text");
		FileIO.writeToLog("This is another log text");
		FileIO.writeToLog("This is a log text", "saveData/logs/specialLog");

		*/