package it.fabrick.demo.controller.bean;

import java.io.Serializable;

public class TransazioniBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7930067993308100313L;

	public String transactionId;
	public String operationId;
	public String accountingDate;
	public String valueDate;
	public String typeE;
	public String typeValue;
	public String amount;
	public String currency;
	public String description;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getAccountingDate() {
		return accountingDate;
	}

	public void setAccountingDate(String accountingDate) {
		this.accountingDate = accountingDate;
	}

	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public String getTypeE() {
		return typeE;
	}

	public void setTypeE(String typeE) {
		this.typeE = typeE;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}