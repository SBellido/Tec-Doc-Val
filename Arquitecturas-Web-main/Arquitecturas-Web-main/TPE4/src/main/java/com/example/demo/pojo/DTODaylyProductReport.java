package com.example.demo.pojo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DTODaylyProductReport {
	private Date date;
	private int quantitySold;
	private int amountCollected;

	public DTODaylyProductReport() {
	}

	@SuppressWarnings({  "static-access" })
	public DTODaylyProductReport(String date, int quantitySold, int amountCollected) {
		super();
		if(date.length() < 10) {
			String []aux = date.split("-");
			date = aux[0];
			if(aux[1].length() < 2) {
				date += "-0"+aux[1];
			} else {
				date += "-"+aux[1];	
			}
			if(aux[2].length() < 2) {
				date += "-0"+aux[2];
			} else {
				date += "-"+aux[2];		
			}
		}
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate lDate = LocalDate.parse(date, format);
		this.date =  new Date().from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.quantitySold = quantitySold;
		this.amountCollected = amountCollected;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(int quantitySold) {
		this.quantitySold = quantitySold;
	}

	public int getAmountCollected() {
		return amountCollected;
	}

	public void setAmountCollected(int amountCollected) {
		this.amountCollected = amountCollected;
	}

}
