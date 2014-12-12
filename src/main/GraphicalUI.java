package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

public class GraphicalUI {

	// private instance variables
	private GameEngine gameEngine;

	// private instance variables for swing
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu fileMenu, logSubmenu;
	private JMenuItem menuItem;
	private Container pane;
	private JPanel boardPanel, labelPanel, buttonPanel, gridPanel;
	private JButton undoButton, resetButton, passButton;
	private JLabel objectiveLabel, objective, playerLabel;
	private BoardJPanel boardJP;
	static JLabel player;

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
	 * 
	 * @param gE
	 */
	public GraphicalUI(GameEngine gE) {
		gameEngine = gE;
		initialise();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialise() {

		// define size of window in 16:9 ratio
		int xInit, yInit, width, height;
		width = 1200;
		height = (width / 16) * 9;
		xInit = 100;
		yInit = 100;

		// START OF FRAME //

		// frame to hold all elements
		frame = new JFrame();
		Insets ins = frame.getInsets();
		Dimension frameSize = new Dimension(width + ins.left + ins.right,
				height + ins.left + ins.right);
		frame.setBounds(xInit, yInit, width, height);
		frame.setSize(frameSize);
		frame.setPreferredSize(frameSize);
		frame.setMinimumSize(frameSize);

		// START OF MENUBAR //

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the file menu.
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"The only menu in this program that has menu items");
		menuBar.add(fileMenu);

		// Menu item for new game
		menuItem = new JMenuItem("New Problem", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Creates a New board");
		menuItem.addActionListener(new FileMenuListener());
		fileMenu.add(menuItem);

		// Menu items for saving the board
		menuItem = new JMenuItem("Load Problem", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Loads a Go problem board");
		menuItem.addActionListener(new FileMenuListener());
		fileMenu.add(menuItem);

		// Menu items for saving the board
		menuItem = new JMenuItem("Save Problem", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Saves current board");
		menuItem.addActionListener(new FileMenuListener());
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("Save Problem As...", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Saves current board to specified path");
		menuItem.addActionListener(new FileMenuListener());
		fileMenu.add(menuItem);

		fileMenu.addSeparator();
		fileMenu.addSeparator();

		// log sub menu
		logSubmenu = new JMenu("Log");
		logSubmenu.getAccessibleContext().setAccessibleDescription(
				"Saves or loads the log of the program.");
		logSubmenu.setMnemonic(KeyEvent.VK_4);
		logSubmenu.setMnemonic(KeyEvent.VK_G);
		
		// menu item for loading the log
		menuItem = new JMenuItem("Load Log");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new LogMenuListener());
		logSubmenu.add(menuItem);

		// menu items for saving the log
		menuItem = new JMenuItem("Save Log");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new LogMenuListener());
		logSubmenu.add(menuItem);
		
		menuItem = new JMenuItem("Save Log As...");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7,
				ActionEvent.ALT_MASK));
		menuItem.addActionListener(new LogMenuListener());
		logSubmenu.add(menuItem);

		// adds log sub menu to log menu
		fileMenu.add(logSubmenu);

		fileMenu.addSeparator();
		fileMenu.addSeparator();

		// menu item for exiting the program
		menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Exits the program");
		menuItem.addActionListener(new FileMenuListener());
		fileMenu.add(menuItem);

		// Build help menu for debugging commands
		fileMenu = new JMenu("Debug");
		fileMenu.setMnemonic(KeyEvent.VK_D);
		fileMenu.getAccessibleContext()
				.setAccessibleDescription(
						"This menu does nothing but could be used for debugging the program");
		menuBar.add(fileMenu);

		// menu item for a debug command
		menuItem = new JMenuItem("Do A Debug", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"A magic trick; Debugs the entire program!");
		menuItem.addActionListener(new DebugMenuListener());
		fileMenu.add(menuItem);

		// Build help menu in the menu bar.
		fileMenu = new JMenu("Help");
		fileMenu.setMnemonic(KeyEvent.VK_H);
		fileMenu.getAccessibleContext().setAccessibleDescription(
				"There should be helpful things here.");
		menuBar.add(fileMenu);

		// menu item for getting help
		menuItem = new JMenuItem("Shout for Help", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Shouts for help");
		menuItem.addActionListener(new HelpMenuListener());
		fileMenu.add(menuItem);

		// add entire menu bar to frame
		frame.setJMenuBar(menuBar);

		// END OF MENUBAR //
		// START OF PANE LAYOUT //

		// border layout for frame
		pane = frame.getContentPane();
		pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		pane.setLayout(new BorderLayout());

		// START OF BOARD PANEL //

		// left panel for the board
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.gray);
		boardPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));
		boardPanel.setPreferredSize(new Dimension(height, width / 8 * 5));

		boardJP = new BoardJPanel(gameEngine);
		boardPanel.add(boardJP);

		pane.add(boardPanel, BorderLayout.WEST);

		// END OF BOARD PANEL
		// START OF BUTTON PANEL //

		// right panel for buttons
		buttonPanel = new JPanel(new GridLayout(0, 1));
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));

		// START OF LABEL PANEL //

		// top right panel for choosing player and move
		labelPanel = new JPanel(new GridLayout(0, 2));
		labelPanel.setBackground(Color.lightGray);
		labelPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));

		// labels for player chooser
		objectiveLabel = new JLabel("      Objective:");
		// TODO add objective to this label
		objective = new JLabel();

		// add objective labels to panel
		labelPanel.add(objectiveLabel);
		labelPanel.add(objective);

		// add padding to panel
		labelPanel.add(new JPanel());
		labelPanel.add(new JPanel());

		// labels to show whose turn it is
		playerLabel = new JLabel("      Player: ");
		player = new JLabel(BoardJPanel.getPlayer());

		// add labels to panel
		labelPanel.add(playerLabel);
		labelPanel.add(player);

		// add padding to panel
		labelPanel.add(new JPanel());
		labelPanel.add(new JPanel());

		// add chooser panel to top of button panel
		buttonPanel.add(labelPanel, BorderLayout.NORTH);

		// END OF LABEL PANEL //
		// START OF LABEL PANEL //

		// grid panel for some buttons
		gridPanel = new JPanel(new GridLayout(0, 2));

		// add padding to panel
		gridPanel.add(new JPanel());
		gridPanel.add(new JPanel());

		// button to undo last move
		undoButton = new JButton("Undo");
		gridPanel.add(undoButton);

		// add action listener for this button
		undoButton.addActionListener(new GridListener());

		// button to reset problem
		resetButton = new JButton("Reset");
		gridPanel.add(resetButton);

		// add action listener for this button
		resetButton.addActionListener(new GridListener());
		
		//button to allow players to pass
		passButton = new JButton("Pass");
		gridPanel.add(passButton);

		// add action listener for this button
		passButton.addActionListener(new GridListener());
		// add grid panel to button panel
		buttonPanel.add(gridPanel, BorderLayout.SOUTH);

		// add right hand panel to pane
		pane.add(buttonPanel);

		// END OF BUTTON PANEL //
		// END OF GRIDBAD LAYOUT //
		// END OF FRAME //

	}

	/**
	 * Listener classes for menus.
	 */
	private class FileMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// load new board
			if (e.getActionCommand() == "New Problem") {
				// TODO add dialogue box here to choose size of board
				// default 9x9
				gameEngine = new GameEngine(new Board());
				boardJP.loadBoard(gameEngine);
			
			// load specified board
			} else if (e.getActionCommand() == "Load Problem") {
				JFileChooser loadBoard = new JFileChooser();
				int command = loadBoard.showOpenDialog(pane);
				if (command == JFileChooser.APPROVE_OPTION) {
					try {
						gameEngine = FileIO.readBoard();
					} catch (BoardFormatException bfe) {
						System.err.println(bfe.getMsg()); // TODO: Write to label?
					}
				} else {
					System.out.println("User cancelled load board selection."); // TODO: Write to label?
				}
			
			// save current board to default location
			} else if (e.getActionCommand() == "Save Problem") {
				try {
					FileIO.writeBoard(gameEngine);
				} catch (BoardFormatException bfe) {
					System.err.println(bfe.getMsg());
				}
					
			// save current board to specified location
			} else {
				JFileChooser saveBoard = new JFileChooser();
				int command = saveBoard.showSaveDialog(pane);
				if (command == JFileChooser.APPROVE_OPTION) {
					try {
						FileIO.writeBoard(gameEngine); // TODO: Write to specified path instead of default
					} catch (BoardFormatException bfe) {
						System.err.println(bfe.getMsg());
					}
				} else {
					System.out.println("User cancelled save board selection."); // TODO: Write to label?
				}
			}
		}
	}

	private class LogMenuListener implements ActionListener {
		// TODO implement log after every move and then implement these actions
		@Override
		public void actionPerformed(ActionEvent e) {
			// load specified log
			if (e.getActionCommand() == "Load Log") {
				JFileChooser loadLog = new JFileChooser();
				int command = loadLog.showOpenDialog(pane);
				if (command == JFileChooser.APPROVE_OPTION) {
					try {
						// TODO: Log loading function?
					} catch (Exception exc) {
						// Add appropriate exception
					}
				} else {
					System.out.println("User cancelled load log selection."); // TODO: Write to label?
				}
			
			// save log in default place
			} else if (e.getActionCommand() == "Save Log"){
				// TODO: try/catch command: writeLog(String log);
			} else {
				// TODO: try/catch command: writeLog(String log, String path);
			}
		}
	}

	private class DebugMenuListener implements ActionListener {

		// TODO implement actions

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Do A Debug") {
				// debug game
			}
		}
	}

	private class HelpMenuListener implements ActionListener {

		// TODO implement actions

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "Shout for Help") {
				// show user help
			}
		}
	}

	// action listener for buttons in grid on bottom left
	private class GridListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button.getText().equals("Undo")) {
				if (gameEngine.undoLastMove()) {
					boardJP.changePlayer();
					player.setText(BoardJPanel.getPlayer());
					boardJP.loadBoard(gameEngine);
				}
				else {
					//TODO report error here
				}
			} else {
				// TODO reset board here
			}
		}
	}

}