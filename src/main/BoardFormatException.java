package main;

public class BoardFormatException extends Exception {

    /**
	 * This exception is to be thrown by FileIO.java when a board is badly formatted.
	 */
	private static final long serialVersionUID = 6708681695639503403L;
	private String message;

    public BoardFormatException() {
    }

    public BoardFormatException(String msg) {
        message = msg;
    }

    public String getMsg() {
        return message;
    }

}
