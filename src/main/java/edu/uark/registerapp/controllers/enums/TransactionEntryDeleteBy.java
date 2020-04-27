package edu.uark.registerapp.controllers.enums;


public enum TransactionEntryDeleteBy {
	ID("id"),
	TRANSACTION_ID("transactionId");

	public String getValue() {
		return value;
	}
	
	private String value;

	private TransactionEntryDeleteBy(final String value) {
		this.value = value;
	}
}
