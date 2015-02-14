package ai;

//This exception is for AI instances and is to be thrown when an AI no longer makes a move.
public class AIException extends Exception {

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
