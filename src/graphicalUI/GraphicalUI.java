package graphicalUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

import main.GameEngine;

public class GraphicalUI {

    // private static instance variables
    private static GameEngine gameEngine;

    // private instance variables for swing
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu fileMenu, subMenu;
    private JMenuItem menuItem;
    private JPanel boardPanel, labelPanel, buttonPanel, gridPanel;
    private JButton undoButton, resetButton, passButton;
    private JToggleButton boundsButton;
    private JLabel objectiveLabel, playerLabel, feedbackLabel;
    private static boolean bounds, competitive, creation; // for toggle buttons
    private static boolean mixedStones, deleteStones; // problem creation
    // options
    // public static instance variables for swing
    static JLabel player, feedback, objective;
    static BoardJPanel boardJP;
    static Container pane;
    static JToggleButton creationButton, competitiveButton;
    static String aiType;

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
        setGameEngine(gE);
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

        // set stones to alternate as default
        mixedStones = true;

        // default AI as minimax
        aiType = "MiniMax";

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
		// inner class to make sure all processes are terminated when the
        // program is closed
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent close) {
                Runtime.getRuntime().halt(0);
            }
        });

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
        subMenu.setMnemonic(KeyEvent.VK_N);

        // Menu item for new game
        menuItem = new JMenuItem("Default (9x9)", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Creates a New board with default size of 9x9");
        menuItem.addActionListener(new NewMenuListener());
        subMenu.add(menuItem);

        // Menu item for new game
        menuItem = new JMenuItem("Custom...", KeyEvent.VK_C);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Creates a New board with specified size");
        menuItem.addActionListener(new NewMenuListener());
        subMenu.add(menuItem);

        fileMenu.add(subMenu);

        // Menu items for saving the board
        menuItem = new JMenuItem("Load Problem", KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Loads a Go problem board");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        // Menu items for saving the board
        menuItem = new JMenuItem("Save Problem", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Saves current board");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        menuItem = new JMenuItem("Save Problem As...", KeyEvent.VK_V);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Saves current board to specified path");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        fileMenu.addSeparator();

        // menu item for exiting the program
        menuItem = new JMenuItem("Exit", KeyEvent.VK_E);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Exits the program");
        menuItem.addActionListener(new FileMenuListener());
        fileMenu.add(menuItem);

        // Build menu for competitive play
        fileMenu = new JMenu("Competitive Play");
        fileMenu.setMnemonic(KeyEvent.VK_1);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Menu with options during competitive play mode");
        menuBar.add(fileMenu);

        // Submenu for setting objective
        subMenu = new JMenu("Objective");
        subMenu.getAccessibleContext().setAccessibleDescription(
                "Allows user to set the objective.");
        subMenu.setMnemonic(KeyEvent.VK_O);

        // Menu item for setting objective
        menuItem = new JMenuItem("Set Objective", KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Allow user to specify board objective");
        menuItem.addActionListener(new ObjectiveMenuListener());
        subMenu.add(menuItem);

        // Menu item for removing objective
        menuItem = new JMenuItem("Remove Objective", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Removes objective from problem");
        menuItem.addActionListener(new ObjectiveMenuListener());
        subMenu.add(menuItem);

        fileMenu.add(subMenu);

        // Submenu for setting bounds
        subMenu = new JMenu("Bounds");
        subMenu.getAccessibleContext().setAccessibleDescription(
                "Allows user to set bounds.");
        subMenu.setMnemonic(KeyEvent.VK_B);

        // Menu item for setting bounds
        menuItem = new JMenuItem("Set Bounds", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                ActionEvent.SHIFT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Allow user to specify board bounds");
        menuItem.addActionListener(new BoundsMenuListener());
        subMenu.add(menuItem);

        // Menu item for removing bounds
        menuItem = new JMenuItem("Remove Bounds", KeyEvent.VK_R);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.SHIFT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Removes bounds from problem");
        menuItem.addActionListener(new BoundsMenuListener());
        subMenu.add(menuItem);

        fileMenu.add(subMenu);

        fileMenu.addSeparator();

        // Menu item for swapping player colour
        menuItem = new JMenuItem("Swap Player Colour", KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.SHIFT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Swap human and AI playing colours");
        menuItem.addActionListener(new CompetitiveMenuListener());
        fileMenu.add(menuItem);

        // Menu item for making AI move
        menuItem = new JMenuItem("Make AI Move", KeyEvent.VK_M);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                ActionEvent.SHIFT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Force AI to move first");
        menuItem.addActionListener(new CompetitiveMenuListener());
        fileMenu.add(menuItem);

        fileMenu.addSeparator();

        // Menu item to switch AI type
        menuItem = new JMenuItem("Select AI Type", KeyEvent.VK_A);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                ActionEvent.SHIFT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Choose algorithm that AI uses to move");
        menuItem.addActionListener(new CompetitiveMenuListener());
        fileMenu.add(menuItem);

        // Build menu for problem creation
        fileMenu = new JMenu("Problem Creation");
        fileMenu.setMnemonic(KeyEvent.VK_2);
        fileMenu.getAccessibleContext().setAccessibleDescription(
                "Menu with options during problem creation mode");
        menuBar.add(fileMenu);

        // menu item for using black stones
        menuItem = new JMenuItem("Use Black Stones", KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Place only black stones down");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        // menu item for using white stones
        menuItem = new JMenuItem("Use White Stones", KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Place only white stones down");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        // menu item for using white/black stones
        menuItem = new JMenuItem("Use Mixed Stones", KeyEvent.VK_M);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Places stones in black and white order");
        menuItem.addActionListener(new CreationMenuListener());
        fileMenu.add(menuItem);

        fileMenu.addSeparator();

        // menu item for removing stones
        menuItem = new JMenuItem("Delete Stones", KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
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
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
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
        menuItem = new JMenuItem("Shout for Help", KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
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
        boardPanel.setBackground(Color.lightGray);
        boardPanel.setBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED));
        boardPanel.setPreferredSize(new Dimension(height, width / 8 * 5));

        boardJP = new BoardJPanel(getGameEngine());
        boardPanel.add(boardJP);

        pane.add(boardPanel, BorderLayout.WEST);

		// END OF BOARD PANEL
        // START OF BUTTON PANEL //
        // right panel for buttons
        buttonPanel = new JPanel(new GridLayout(0, 1));
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
        objective = new JLabel();
        objective.setText("No set objective");

        // add objective labels to panel
        labelPanel.add(objectiveLabel);
        labelPanel.add(objective);

        // add padding to panel
        labelPanel.add(new JPanel());
        labelPanel.add(new JPanel());

        // labels to show whose turn it is
        playerLabel = new JLabel("      Player: ");
        player = new JLabel("Black to move");

        // add labels to panel
        labelPanel.add(playerLabel);
        labelPanel.add(player);

        // add padding to panel
        labelPanel.add(new JPanel());
        labelPanel.add(new JPanel());

        // labels to show invalid moves
        feedbackLabel = new JLabel("     User Message: ");
        feedback = new JLabel("Click to place stones");

        // add labels to panel
        labelPanel.add(feedbackLabel);
        labelPanel.add(feedback);

        // add label panel to top of button panel
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

    // game engine getter and setter for external action liFsteners
    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static void setGameEngine(GameEngine ge) {
        gameEngine = ge;
    }

    // Getters for GUI booleans
    public static boolean getBounds() {
        return bounds;
    }

    public static boolean getCompetitive() {
        return competitive;
    }

    public static boolean getCreation() {
        return creation;
    }

    public static boolean getMixedStones() {
        return mixedStones;
    }

    public static boolean getDeleteStones() {
        return deleteStones;
    }

    // Setters for GUI booleans
    public static void setBounds(boolean b) {
        bounds = b;
    }

    public static void setCompetitive(boolean b) {
        competitive = b;
    }

    public static void setCreation(boolean b) {
        creation = b;
    }

    public static void setMixedStones(boolean b) {
        mixedStones = b;
    }

    public static void setDeleteStones(boolean b) {
        deleteStones = b;
    }

    // method to reset the modes to default
    public static void resetModeButtons() {
        competitive = false;
        competitiveButton.setSelected(false);
        creation = false;
        creationButton.setSelected(false);
    }

}
