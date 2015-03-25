package ai;

public class AIException extends Exception {

    /**
	 * This exception is for AI instances and is to be thrown when an AI no longer makes a move.
	 */
	private static final long serialVersionUID = 508847670803750867L;
	private String message;

    public AIException() {
    }

    public AIException(String msg) {
        message = msg;
    }

    public String getMsg() {
        return message;
    }
}
