package model;

public class BookLike {
private String username;
private String bookName;
public BookLike(String username, String bookName) {
	super();
	this.username = username;
	this.bookName = bookName;
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

}
