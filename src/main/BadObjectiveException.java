package main;

//This exception is for AI instances and is to be thrown when an AI no longer makes a move.
public class BadObjectiveException extends Exception{
    
    private String message;

    public BadObjectiveException() {
    }

    public BadObjectiveException(String msg) {
        message = msg;
    }

    public String getMsg() {
        return message;
    }
}
