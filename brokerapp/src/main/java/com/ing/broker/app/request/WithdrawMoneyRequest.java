package com.ing.broker.app.request;

public class WithdrawMoneyRequest extends MoneyRequest {
	
	private String iban;

	//Getters & Setters
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
