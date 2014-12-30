package main;

public class Main{
	//Use this class to test your stuff. Just comment out or delete your code before you commit it.
	public static void main(String[] args)
	{		
        boolean gooey = true;
        GameEngine gE = new GameEngine();

        if (gooey) {
            GraphicalUI gUI = new GraphicalUI(gE);
            gUI.start(gE);
        }
        else {
                TextUI tUI = new TextUI();
                tUI.init();
        }
	}
}