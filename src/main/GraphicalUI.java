package main;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GraphicalUI {
	
	// private instance variables
	private GameEngine gameE;
	
	// private instance variables for swing
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu fileMenu, logSubmenu;
	private JMenuItem menuItem;
	private Container pane;
	private JPanel boardPanel, chooserPanel, buttonPanel;
	
	// static variables for gridbad layout manager
	final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;
	
	/**
	 * Start the gui.
	 */
	public void start(final GameEngine gE) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphicalUI window = new GraphicalUI(gE);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @param gE 
	 */
	public GraphicalUI(GameEngine gE) {
		gameE = gE;
		initialise();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {
		
		int xInit, xBound, yInit, yBound;
		xInit = 100; xBound = 1100;
		yInit = 100; yBound = 700;
		
		//frame to hold all elements
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the menu bar.
		menuBar = new JMenuBar();

		//Build the file menu.
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(fileMenu);

		// Menu item for new game
		menuItem = new JMenuItem("New",
		                         KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "Creates a New board");
		fileMenu.add(menuItem);
		
		// Menu items for saving the board
		menuItem = new JMenuItem("Load Problem",
                KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		"Loads a Go problem board");
		fileMenu.add(menuItem);
		
		// Menu items for saving the board
		menuItem = new JMenuItem("Save Problem",
                KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Saves current board");
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		fileMenu.addSeparator();
		
		// log sub menu
		logSubmenu = new JMenu("Log");
		logSubmenu.getAccessibleContext().setAccessibleDescription(
				"Saves or loads the log of the program.");
		logSubmenu.setMnemonic(KeyEvent.VK_4);
		logSubmenu.setMnemonic(KeyEvent.VK_G);

		// menu item for saving the log
		menuItem = new JMenuItem("Save Log");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_5, ActionEvent.ALT_MASK));
		logSubmenu.add(menuItem);
		
		// menu item for loading the log
		menuItem = new JMenuItem("Load Log");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_6, ActionEvent.ALT_MASK));
		logSubmenu.add(menuItem);
		
		// adds log sub menu to log menu
		fileMenu.add(logSubmenu);
		
		fileMenu.addSeparator();
		fileMenu.addSeparator();
		
		// menu item for exiting the program
		menuItem = new JMenuItem("Exit",
                KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_7, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Exits the program");
		fileMenu.add(menuItem);

		//Build help menu for debugging commands
		fileMenu = new JMenu("Debug");
		fileMenu.setMnemonic(KeyEvent.VK_D);
		fileMenu.getAccessibleContext().setAccessibleDescription(
		        "This menu does nothing but could be used for debugging the program");
		menuBar.add(fileMenu);
		
		// menu item for a debug command
		menuItem = new JMenuItem("Do A Debug",
                KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"A magic trick; Debugs the entire program!");
		fileMenu.add(menuItem);
		
		//Build help menu in the menu bar.
		fileMenu = new JMenu("Help");
		fileMenu.setMnemonic(KeyEvent.VK_H);
		fileMenu.getAccessibleContext().setAccessibleDescription(
		        "There should be helpful things here.");
		menuBar.add(fileMenu);
		
		// menu item for getting help
		menuItem = new JMenuItem("Shout for Help",
                KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Shouts for help");
		fileMenu.add(menuItem);
		
		// add entire menu bar to frame
		frame.setJMenuBar(menuBar);
		
		// border layout for frame
		pane = frame.getContentPane();
		pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gbCons = new GridBagConstraints();
		gbCons.weightx = 0.5;
	    gbCons.weighty = 0.5;
	    gbCons.fill = GridBagConstraints.BOTH;
		
		// panel for choosing player and move
		chooserPanel = new JPanel();
		chooserPanel.setBackground(Color.yellow);;
// TODO put swing elements here to control players of the game
		gbCons.gridx = 0;
		gbCons.gridy = 0;
		pane.add(chooserPanel, gbCons);
		
		// panel for choosing player and move
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.red);;
// TODO put swing elements here to control players of the game
		gbCons.gridx = 0;
		gbCons.gridy = 1;
		pane.add(buttonPanel, gbCons);
		
		// panel for the board
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.blue);
// TODO put actual board here
		gbCons.gridx = 1;
		gbCons.gridy = 0;
		gbCons.gridheight = 2;
		pane.add(boardPanel, gbCons);
	
	}

}
