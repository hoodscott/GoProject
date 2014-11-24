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
	private GameEngine gameE;

	// private instance variables for swing
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu fileMenu, logSubmenu;
	private JMenuItem menuItem;
	private Container pane;
	private JPanel boardPanel, chooserPanel, buttonPanel, gridPanel;
	private JButton undoButton, resetButton;
	private JLabel playerLabel, whiteLabel, blackLabel;
	private JRadioButton whiteHumanRadio, whiteAIRadio, blackHumanRadio, blackAIRadio;
	private ButtonGroup whiteGroup, blackGroup;

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
		gameE = gE;
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
		fileMenu.add(menuItem);

		// Menu items for saving the board
		menuItem = new JMenuItem("Load Problem", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Loads a Go problem board");
		fileMenu.add(menuItem);

		// Menu items for saving the board
		menuItem = new JMenuItem("Save Problem", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				ActionEvent.ALT_MASK));
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
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.ALT_MASK));
		logSubmenu.add(menuItem);

		// menu item for loading the log
		menuItem = new JMenuItem("Load Log");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.ALT_MASK));
		logSubmenu.add(menuItem);

		// adds log sub menu to log menu
		fileMenu.add(logSubmenu);

		fileMenu.addSeparator();
		fileMenu.addSeparator();

		// menu item for exiting the program
		menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7,
				ActionEvent.ALT_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
				"Exits the program");
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
		fileMenu.add(menuItem);

		// add entire menu bar to frame
		frame.setJMenuBar(menuBar);

		// END OF MENUBAR //
		// START OF GRIDBAG LAYOUT //

		// border layout for frame
		pane = frame.getContentPane();
		pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gbCons = new GridBagConstraints();
		gbCons.weightx = 0.5;
		gbCons.weighty = 0.5;
		gbCons.fill = GridBagConstraints.BOTH;

		// START OF BUTTON PANEL //

		// right panel for buttons
		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));

		// START OF CHOOSER PANEL //

		// top right panel for choosing player and move
		chooserPanel = new JPanel(new GridLayout(0, 3));
		chooserPanel.setBackground(Color.lightGray);
		chooserPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));

		// label for player chooser
		playerLabel = new JLabel("  Choose Players:");
		chooserPanel.add(playerLabel);
		
		// add whitespace along top of chooser panel
		chooserPanel.add(new JLabel());
		chooserPanel.add(new JLabel());

		// label and radio buttons to select white player
		whiteLabel = new JLabel("        White Player: ");
		whiteHumanRadio = new JRadioButton("Human");
		whiteHumanRadio.setSelected(true);
		whiteAIRadio = new JRadioButton("AI");

		// add white radio buttons to group
		whiteGroup = new ButtonGroup();
		whiteGroup.add(whiteHumanRadio);
		whiteGroup.add(whiteAIRadio);

		// add action listener to radio items in white group
		whiteHumanRadio.addActionListener(new WhiteRadioListener());
		whiteAIRadio.addActionListener(new WhiteRadioListener());

		// add label and radio items to chooser panel
		chooserPanel.add(whiteLabel);
		chooserPanel.add(whiteHumanRadio);
		chooserPanel.add(whiteAIRadio);

		// label and radio buttons to select black player
		blackLabel = new JLabel("        Black Player:");
		blackHumanRadio = new JRadioButton("Human");
		blackHumanRadio.setSelected(true);
		blackAIRadio = new JRadioButton("AI");

		// add black radio buttons to group
		blackGroup = new ButtonGroup();
		blackGroup.add(blackHumanRadio);
		blackGroup.add(blackAIRadio);

		// add action listener to radio items in black group
		blackHumanRadio.addActionListener(new BlackRadioListener());
		blackAIRadio.addActionListener(new BlackRadioListener());

		// add label and radio items to chooser panel
		chooserPanel.add(blackLabel);
		chooserPanel.add(blackHumanRadio);
		chooserPanel.add(blackAIRadio);

		// add chooser panel to top of button panel
		buttonPanel.add(chooserPanel, BorderLayout.NORTH);

		// END OF CHOOSER PANEL //

		// grid panel for some buttons
		gridPanel = new JPanel(new GridLayout(0, 2));

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

		// add grid panel to button panel
		buttonPanel.add(gridPanel, BorderLayout.SOUTH);

		gbCons.gridx = 0;
		gbCons.gridy = 0;
		pane.add(buttonPanel, gbCons);

		// END OF BUTTON PANEL //
		// START OF BOARD PANEL //

		// left panel for the board
		boardPanel = new JPanel();
		boardPanel.setBackground(Color.gray);
		boardPanel.setBorder(BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED));

		// TODO put actual board here

		gbCons.ipadx = width / 2;
		gbCons.gridx = 1;
		gbCons.gridy = 0;
		gbCons.gridheight = 2;
		pane.add(boardPanel, gbCons);

		// END OF BOARD PANEL
		// END OF GRIDBAD LAYOUT //
		// END OF FRAME //

	}
	
	/**
	 * Listener classes for buttons.
	 */
	private class WhiteRadioListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			
			// TODO implement actions
			
			if (button.getText().equals("Human")) {
				// set human as white player
			} else {
				// set AI as white player
			}
		}
	}
	
	private class BlackRadioListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton button = (JRadioButton) e.getSource();
			
			// TODO implement actions
			
			if (button.getText().equals("Human")) {
				// set human as black player
			} else {
				// set AI as black player
			}
		}
	}
	
	private class GridListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			
			// TODO implements actions 
			
			if (button.getText().equals("Undo")) {
				// undo last move
			} else {
				// reset all moves
			}
		}
	}

}
