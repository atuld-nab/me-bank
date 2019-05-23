package main.java.model;

import java.time.LocalDateTime;

public class Transaction {
	public Transaction(String transactionId, String debitAccount, String creditAccount,Double amount, 
			LocalDateTime timeStamp,String transactionType,
			String reversalTransactionId) {
		super();
		this.transactionId = transactionId;
		this.debitAccount = debitAccount;
		this.creditAccount = creditAccount;
		this.transactionType = transactionType;
		this.reversalTransactionId = reversalTransactionId;
		this.amount = amount;
		this.timeStamp = timeStamp;
	}
	private String creditAccount;
	private String debitAccount;
	private String transactionType;
	private String transactionId;
	private String reversalTransactionId;
	private Double amount;
	private LocalDateTime timeStamp;
	
	
	public String getCreditAccount() {
		return creditAccount;
	}
	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	public String getDebitAccount() {
		return debitAccount;
	}
	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getReversalTransactionId() {
		return reversalTransactionId;
	}
	public void setReversalTransactionId(String reversalTransactionId) {
		this.reversalTransactionId = reversalTransactionId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	
}
