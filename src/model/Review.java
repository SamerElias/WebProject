package model;

import java.sql.Timestamp;

public class Review {
private String username;
private String bookName;
private String content;
private int accepted;
private Timestamp reviewDate;
public Review(String username, String bookName, String content, int accepted, Timestamp reviewDate) {
	super();
	this.username = username;
	this.bookName = bookName;
	this.content = content;
	this.accepted = accepted;
	this.reviewDate = reviewDate;
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
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public int getAccepted() {
	return accepted;
}
public void setAccepted(int accepted) {
	this.accepted = accepted;
}
public Timestamp getReviewDate() {
	return reviewDate;
}
public void setReviewDate(Timestamp reviewDate) {
	this.reviewDate = reviewDate;
}

}
