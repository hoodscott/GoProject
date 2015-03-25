package main;

public class BadInputException extends Exception {

    /**
	 * This exception is to be thrown by TextUI.java when incorrect inputs are made.
	 */
	private static final long serialVersionUID = 1171492964935101610L;
	private String message;

    public BadInputException() {
    }

    public BadInputException(String msg) {
        message = msg;
    }

    public String getMsg() {
        return message;
    }

}
