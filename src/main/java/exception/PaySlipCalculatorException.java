package main.java.exception;

public class PaySlipCalculatorException extends RuntimeException{
	private String message;
	
	public PaySlipCalculatorException(String message){
		super(message);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
