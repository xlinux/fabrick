package it.fabrick.demo.controller.bean;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TransazioniRequestBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7930067993308100313L;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate fromAccountingDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate toAccountingDate;

	public LocalDate getFromAccountingDate() {
		return fromAccountingDate;
	}

	public void setFromAccountingDate(LocalDate fromAccountingDate) {
		this.fromAccountingDate = fromAccountingDate;
	}

	public LocalDate getToAccountingDate() {
		return toAccountingDate;
	}

	public void setToAccountingDate(LocalDate toAccountingDate) {
		this.toAccountingDate = toAccountingDate;
	}

}