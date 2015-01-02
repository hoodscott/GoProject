package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

public class GraphicalUI {

    // private instance variables
    private GameEngine gameEngine;

    // private instance variables for swing
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu, subMenu;
    private JMenuItem menuItem;
    private Container pane;
    private JPanel boardPanel, labelPanel, buttonPanel, gridPanel;
    private JButton undoButton, resetButton, passButton;
    private JToggleButton boundsButton, competitiveButton, creationButton;
    private JLabel objectiveLabel, objective, playerLabel, invMoveLabel;
    private BoardJPanel boardJP;
    private static boolean bounds, competitive, creation; // for toggle buttons
    private static boolean mixedStones, deleteStones; // problem creation options

    static JLabel player, invMove;

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

        // set stones to white then black as default
        mixedStones = true;

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

        // Menu items for new boards
        subMenu = new JMenu("New Problem");
        subMenu.getAccessibleContext().setAccessibleDescription(
                "Creates a new problem.");
        subMenu.setMnemonic(KeyEvent.VK_1);
        subMenu.setMnemonic(KeyEvent.VK_N);

        // Menu item for new game
        menuItem = new JMenuItem("Default (9x9)", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Creates a New board with default size of 9x9");
        menuItem.addActionListener(new SubMenuListener());
        subMenu.add(menuItem);

        // Menu item for new game
        menuItem = new JMenuItem("Custom...", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Creates a New board with specified size");
        menuItem.addActionListener(new SubMenuListener());
        subMenu.add(menuItem);

        fileMenu.add(subMenu);

        // Menu items for saving the board
        menuItem = new JMenuItem("Load Problem", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Loads a Go problem board");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        // Menu items for saving the board
        menuItem = new JMenuItem("Save Problem", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Saves current board");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        menuItem = new JMenuItem("Save Problem As...", KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Saves current board to specified path");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        fileMenu.addSeparator();

        // Submenu for setting bounds
        subMenu = new JMenu("Bounds");
        subMenu.getAccessibleContext().setAccessibleDescription(
                "Allows user to set bounds.");
        subMenu.setMnemonic(KeyEvent.VK_B);

        // Menu item for setting bounds
        menuItem = new JMenuItem("Set Bounds", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Allow user to specify board bounds");
        menuItem.addActionListener(new BoundsMenuListener());
        subMenu.add(menuItem);

        // Menu item for removing bounds
        menuItem = new JMenuItem("Remove Bounds", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Removes bounds from problem");
        menuItem.addActionListener(new BoundsMenuListener());
        subMenu.add(menuItem);

        fileMenu.add(subMenu);

        fileMenu.addSeparator();

        // menu item for exiting the program
        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Exits the program");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        // Build help menu for debugging commands
        fileMenu = new JMenu("Problem Creation");
        fileMenu.setMnemonic(KeyEvent.VK_P);
        fileMenu.getAccessibleContext()
                .setAccessibleDescription(
                        "Menu with options during problem creation mode");
        menuBar.add(fileMenu);

        // menu item for using black stones
        menuItem = new JMenuItem("Use Black Stones", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Place only black stones down");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        // menu item for using white stones
        menuItem = new JMenuItem("Use White Stones", KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Place only white stones down");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        // menu item for using white/black stones
        menuItem = new JMenuItem("Use Both Stones", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Places stones in black and white order");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        fileMenu.addSeparator();

        // menu item for removing stones
        menuItem = new JMenuItem("Delete Stones", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Allow user to delete stones");
        menuItem.addActionListener(new CreationMenuListener());
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

        // labels to show invalid moves
        invMoveLabel = new JLabel("     User Message: ");
        invMove = new JLabel(BoardJPanel.getInvMove(1));

        // add labels to panel
        labelPanel.add(invMoveLabel);
        labelPanel.add(invMove);

        // add chooser panel to top of button panel
        buttonPanel.add(labelPanel, BorderLayout.NORTH);

		// END OF LABEL PANEL //
        // START OF LABEL PANEL //
        // grid panel for some buttons
        gridPanel = new JPanel(new GridLayout(0, 2));

        // add padding to panel
        gridPanel.add(new JPanel());
        gridPanel.add(new JPanel());

        // button to show bounds of problem
        competitiveButton = new JToggleButton("Competitive Play Mode");
        competitiveButton.setMnemonic(KeyEvent.VK_P);
        gridPanel.add(competitiveButton);

        // add action listener for this button
        competitiveButton.addActionListener(new GridToggleListener());

        // button to show bounds of problem
        creationButton = new JToggleButton("Problem Creation Mode");
        creationButton.setMnemonic(KeyEvent.VK_C);
        gridPanel.add(creationButton);

        // add action listener for this button
        creationButton.addActionListener(new GridToggleListener());

        // button to undo last move
        undoButton = new JButton("Undo");
        undoButton.setMnemonic(KeyEvent.VK_U);
        gridPanel.add(undoButton);

        // add action listener for this button
        undoButton.addActionListener(new GridListener());

        // button to reset problem
        resetButton = new JButton("Reset");
        resetButton.setMnemonic(KeyEvent.VK_R);
        gridPanel.add(resetButton);

        // add action listener for this button
        resetButton.addActionListener(new GridListener());

        // button to allow players to pass
        passButton = new JButton("Pass");
        passButton.setMnemonic(KeyEvent.VK_A);
        gridPanel.add(passButton);

        // add action listener for this button
        passButton.addActionListener(new GridListener());

        // button to show bounds of problem
        boundsButton = new JToggleButton("Show Bounds");
        boundsButton.setMnemonic(KeyEvent.VK_B);
        gridPanel.add(boundsButton);

        // add action listener for this button
        boundsButton.addActionListener(new GridToggleListener());

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
            // load specified board
            if (e.getActionCommand().equals("Load Problem")) {
                JFileChooser loadBoard = new JFileChooser();
                int command = loadBoard.showOpenDialog(pane);
                if (command == JFileChooser.APPROVE_OPTION) {
                    try {
                        gameEngine = FileIO.readBoard(loadBoard
                                .getSelectedFile().getAbsolutePath());
                        boardJP.loadBoard(gameEngine);
                    	invMove.setText("Board Loaded");
                    } catch (BoardFormatException bfe) {
                    	invMove.setText(bfe.getMsg());
                    }
                } else {
                    invMove.setText("User Cancelled Load Board Selection"); 
                }

                // save current board to default location
            } else if (e.getActionCommand().equals("Save Problem")) {
                try {
                    FileIO.writeBoard(gameEngine);
                	invMove.setText("Problem Saved.");
                } catch (BoardFormatException bfe) {
                    invMove.setText(bfe.getMsg());
                }

                // save current board to specified location
            } else if (e.getActionCommand().equals("Save Problem As...")) {
                JFileChooser saveBoard = new JFileChooser();
                int command = saveBoard.showSaveDialog(pane);
                if (command == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileIO.writeBoard(gameEngine, saveBoard
                                .getSelectedFile().getAbsolutePath());
                    	invMove.setText("Problem Saved");
                    } catch (BoardFormatException bfe) {
                        invMove.setText(bfe.getMsg());
                    }
                } else {
                    invMove.setText("User Cancelled Save Board Selection");
                }
            } // exit program
            else {
                System.exit(0);
            }
        }
    }

    private class SubMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // load default board
            if (e.getActionCommand().equals("Default (9x9)")) {
                // default 9x9
                gameEngine = new GameEngine(new Board());
                BoardJPanel.setPlayer("black");
                boardJP.loadBoard(gameEngine);
            	invMove.setText("Board Loaded");
            } else {
                // load specified board
                Object[] sizes = {"9", "14", "19"};
                String s = (String) JOptionPane.showInputDialog(frame,
                        "Select board size...", "New Problem",
                        JOptionPane.PLAIN_MESSAGE, null, sizes, "9");
                if (s != null) {
                    // set new board to specified size
                    int length = Integer.parseInt(s);
                    gameEngine = new GameEngine(new Board(length, length));
                    // set player to back then draw the new board
                    BoardJPanel.setPlayer("black");
                    boardJP.loadBoard(gameEngine);
                	invMove.setText("Board Loaded");
                }

            }
        }
    }

    private class BoundsMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // allow user to set bounds
            if (e.getActionCommand().equals("Set Bounds")) {
                String bounds = (String) JOptionPane.showInputDialog(frame,
                        "Specify Bounds Seperated By Spaces:", "Set Bounds",
                        JOptionPane.PLAIN_MESSAGE, null, null, "");
                System.out.println(bounds);
                System.out.println(bounds.length());
                if (bounds != null && bounds.length() == 7) {
                	// create array of specified bounds
                	int[] selectBounds = {0,0,0,0}; 
                	int j = 0;
                	for (int i = 0; i<bounds.length(); i++) {
                		if (Character.isDigit(bounds.charAt(i))) {
                			selectBounds[j] = Integer.parseInt(String.valueOf(bounds.charAt(i)));
                			j++;
                		}
                	}
                	BoardJPanel.setBounds(selectBounds);
                	invMove.setText("Bounds Updated");
                } else {
                	invMove.setText("Invalid Bounds Supplied");
                }
            } else {
                // remove bounds - (make bounds size of board)
                int lines = BoardJPanel.getLines();
                int[] noBounds = {0, 0, lines, lines};
                BoardJPanel.setBounds(noBounds);
            	invMove.setText("Bounds Removed.");
            }
        }

    }

    private class CreationMenuListener implements ActionListener {

		// TODO implement debugging actions
        @Override
        public void actionPerformed(ActionEvent e) {
            if ((e.getActionCommand().equals("Use Black Stones")) && creation) {
                // set down only black stones
                BoardJPanel.setPlayer("black");
                mixedStones = false;
                deleteStones = false;
            } else if ((e.getActionCommand().equals("Use White Stones")) && creation) {
                // set down only white stones
                BoardJPanel.setPlayer("white");
                mixedStones = false;
                deleteStones = false;
            } else if ((e.getActionCommand().equals("Use Both Stones")) && creation) {
                // use a mixture of stones
                mixedStones = true;
                deleteStones = false;
            } else if ((e.getActionCommand().equals("Delete Stones")) && creation) {
                // remove stones user clicks on
                deleteStones = true;
                mixedStones = false;
            }
        }
    }

    private class DebugMenuListener implements ActionListener {

		// TODO implement debugging actions
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Do A Debug")) {
                // debug game
            }
        }
    }

    private class HelpMenuListener implements ActionListener {

		// TODO implement helper actions
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Shout for Help")) {
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
                } else {
                    invMove.setText("No more moves to undo");
                }
            }
            if (button.getText().equals("Pass")) {
                // TODO create pass function in gameEngine
                if (true) {
                    boardJP.changePlayer();
                    player.setText(BoardJPanel.getPlayer());
                    boardJP.loadBoard(gameEngine);
                } else {
                    // TODO write message to user if passing fails
                }
            }
            if (button.getText().equals("Reset")) {
                if (gameEngine.restartBoard()) {
                    BoardJPanel.setPlayer("black");
                    player.setText(BoardJPanel.getPlayer());
                    boardJP.loadBoard(gameEngine);
                }
            }
        }
    }
     private class GridToggleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton button = (JToggleButton) e.getSource();
			if (button.getText().equals("Show Bounds")) {
				if (!bounds) {
					bounds = true;
					boardJP.loadBoard(gameEngine);
				} else {
					bounds = false;
					boardJP.loadBoard(gameEngine);
				}
			}
			if (button.getText().equals("Competitive Play Mode")) {
				creationButton.setSelected(false);
				creation = false;
				if (!competitive) {
					competitive = true;
				} else {
					competitive = false;
				}
			}
			if (button.getText().equals("Problem Creation Mode")) {
				competitiveButton.setSelected(false);
				competitive = false;
				if (!creation) {
					creation = true;
				} else {
					creation = false;
				}
			}
		}
		
	}
	
	// Getter for GUI toggle button booleans
	public static boolean getBounds() {
		return bounds;
	}
	
	public static boolean getCompetitive() {
		return competitive;
	}
	
	public static boolean getCreation() {
		return creation;
	}
	
	// Getters for problem creation mode booleans
	public static boolean getMixedStones() {
		return mixedStones;
	}
	
	public static boolean getDeleteStones() {
		return deleteStones;
	}
}
