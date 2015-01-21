package main;

//Holds objective for a player and assesses whether it is met or not.
public class Objective {

	private int eyes;

	public enum Action {
		KILL, DEFEND
	}

	Action black;
	Action white;
	private Action action;
	private final int startingColour;

	private final Coordinate position;

	// Objective Constructor, the text should adhere the appropriate format,
	// containing the colour this objective is for.
	public Objective(String action, int colour, Coordinate position) {
		startingColour = colour;
		this.action = translateToAction(action);
		if (startingColour == Board.BLACK) {
			black = this.action;
			white = getOpposingAction(black);
		} else {
			white = this.action;
			black = getOpposingAction(white);
		}
		this.position = position;
	}

	public String getOriginalAction() {
		return translateToString(action);
	}

	public String getAction(int colour) {
		switch (colour) {
		case Board.BLACK:
			return translateToString(black);
		case Board.WHITE:
			return translateToString(white);
		}
		return null;
	}

	public int getColour() {
		return startingColour;
	}

	public Coordinate getPosition() {
		return position;
	}

	private String translateToString(Action action) {
		switch (action) {
		case KILL:
			return "kill";
		case DEFEND:
			return "defend";
		}
		return null;
	}

	private Action translateToAction(String action) {
		if ("kill".equals(action)) {
			return Action.KILL;
		}
		return Action.DEFEND;
	}

	private Action getOpposingAction(Action action) {
		switch (action) {
		case KILL:
			return Action.DEFEND;
		case DEFEND:
			return Action.KILL;
		}
		return null;
	}

	// Checks if the objective succeeded for the given player.
	// !!!!!!!!!!!! add colour parameter and remove the second objective from
	// minimax
	public boolean checkSucceeded(Board board, int colour) {
		if (colour == Board.BLACK) {
			if (black.equals(Action.KILL)) {
				return board.get(position.x, position.y) != getOtherColour(colour);
			} else {
				return board.get(position.x, position.y) == colour;
			}
		} else {
			if (white.equals(Action.KILL)) {
				return board.get(position.x, position.y) != getOtherColour(colour);
			} else {
				return board.get(position.x, position.y) == colour;
			}
		}
	}

	// Returns whether the player plays first or not.
	public boolean isStarting(int colour) {
		return colour == startingColour;
	}

	public int getStartingColour() {
		return startingColour;
	}

	public int getOtherColour(int colour) {
		if (colour == Board.BLACK) {
			return Board.WHITE;
		} else {
			return Board.BLACK;
		}
	}

	// helper method for toString to get string of colour
	private String toColour(int c) {
		if (c == Board.BLACK) {
			return "Black";
		} else
			return "White";
	}

	// private method for toString to translate coords to string
	private String coordString(Coordinate c) {
		return null;
	}

	public String toString() {
		return toColour(this.startingColour) + " to "
				+ translateToString(this.action) + " "
				+ this.position.toString();
	}

	// functions to count the eyes of the defending group
	/*
	 * public void countEyes(Board board, boolean b[][], Coordinate p){ // if
	 * checked already or opponent return if (b[p.x][p.y] == true ||
	 * board.get(p.x, p.y) == getOtherColour()) return; // else mark as checked
	 * b[p.x][p.y]=true; // if position is empty -> check if eye if
	 * (board.get(p.x, p.y) == Board.EMPTY) { if (checkIfEye(board,p) == true) {
	 * eyes++; } return; }
	 * 
	 * //out of boundary check + recursion // if (p.x>0) { Coordinate np = new
	 * Coordinate(p.x-1,p.y); countEyes(board, b, np); } if
	 * (p.x<board.getWidth() - 1) { Coordinate np = new Coordinate(p.x+1,p.y);
	 * countEyes(board, b, np); } if (p.y>0) { Coordinate np = new
	 * Coordinate(p.x,p.y-1); countEyes(board, b, np); } if
	 * (p.y<board.getHeight() - 1){ Coordinate np = new Coordinate(p.x,p.y+1);
	 * countEyes(board, b, np); } }
	 * 
	 * public boolean checkIfEye(Board board, Coordinate p){ // check all eight
	 * positions (whichever exist) around for (int i = p.x-1 ; i <= p.x+1; i++){
	 * loop2: for(int j = p.y-1; j <= p.y+1; j++){ // do not check the potential
	 * eye itself if (i==p.x && j == p.y ) continue loop2; if(i >= 0 && j >= 0
	 * && i<=board.getWidth() - 1 && j<=board.getHeight() - 1){ if
	 * (board.get(i,j) != colour) return false; } } } return true; }
	 */
}
