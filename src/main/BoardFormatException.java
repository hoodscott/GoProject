package main;

//This exception is to be thrown by FileIO.java when a board is badly formatted.
public class BoardFormatException extends Exception{

	private String message;
	public BoardFormatException(){}

	public BoardFormatException(String msg){
		message = msg;
	}

	public String getMsg(){return message;}

}