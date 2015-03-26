package main;

public class BoardFormatException extends Exception {

    /**
    * This exception is to be thrown by Translator.java when trying to read in a badly
    * formatted board.
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
