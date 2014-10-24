//This exception is to be thrown by FileIO.java when a board is badly formatted.
public class BoardFormatException extends Exception{
	public BoardFormatException(){}

	public BoardFormatException(String msg){
		super(msg);
	}

}