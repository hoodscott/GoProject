//This exception is to be thrown by TextUI.java when incorrect inputs are made.
public class BadInputException extends Exception{

	private String message;
	public BadInputException(){}

	public BadInputException(String msg){
		message = msg;
	}

	public String getMsg(){return message;}

}