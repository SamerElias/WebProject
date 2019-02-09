package model;

import java.sql.Timestamp;

public class Msg {
	private int ID;
	private String username;
	private String subject;
	private String content;
	private String reply;
	private Timestamp sentDate;
	private int readStatus;
	private int readStatusADMIN;
	public Msg(int iD, String username, String subject, String content, String reply, Timestamp sentDate,
			int readStatus, int readStatusADMIN) {
		super();
		ID = iD;
		this.username = username;
		this.subject = subject;
		this.content = content;
		this.reply = reply;
		this.sentDate = sentDate;
		this.readStatus = readStatus;
		this.readStatusADMIN = readStatusADMIN;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public Timestamp getSentDate() {
		return sentDate;
	}
	public void setSentDate(Timestamp sentDate) {
		this.sentDate = sentDate;
	}
	public int getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}
	public int getReadStatusADMIN() {
		return readStatusADMIN;
	}
	public void setReadStatusADMIN(int readStatusADMIN) {
		this.readStatusADMIN = readStatusADMIN;
	}
	
	
	
}
