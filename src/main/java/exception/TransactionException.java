package main.java.exception;

public class TransactionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransactionException(String message)
	{
		super("Exception occured at service layer because of"+message);
	}
}
