package model;

import java.sql.Timestamp;

public class Owned {
	private String username;
	private String bookName;
	private Double readingSession;
	private Timestamp purchaseDate;
	private double amount;
	public Owned(String username, String bookName, Double readingSession, Timestamp purchaseDate, double amount) {
		super();
		this.username = username;
		this.bookName = bookName;
		this.readingSession = readingSession;
		this.purchaseDate = purchaseDate;
		this.amount = amount;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public Double getReadingSession() {
		return readingSession;
	}
	public void setReadingSession(Double readingSession) {
		this.readingSession = readingSession;
	}
	public Timestamp getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Timestamp purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
		
}
