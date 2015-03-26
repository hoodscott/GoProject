package main;

import graphicalUI.GraphicalUI;
/**
 * Runner for either the TextUI or GUI
 */
public class Main {

    public static void main(String[] args) {
        boolean gooey = true;
        GameEngine gE = new GameEngine();

        if (gooey) {
            GraphicalUI gUI = new GraphicalUI(gE);
            gUI.start(gE);
        } else {
            TextUI tUI = new TextUI();
            tUI.init();
        }
    }
}
