package main;

public class Main{
	//Use this class to test your stuff. Just comment out or delete your code before you commit it.
	public static void main(String[] args)
	{
            GameEngine gE = new GameEngine();
            TextUI tUI = new TextUI(gE);
            tUI.init();
	}
}