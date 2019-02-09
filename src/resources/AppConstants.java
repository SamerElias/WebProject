package resources;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.reflect.TypeToken;

import model.Customer;

/**
 * A simple place to hold global application constants
 */
public interface AppConstants {

	public final String ADMIN = "admin";
	public final String ADMIN_PASSWORD = "Passw0rd";
	public final String CUSTOMERS = "customers";
	public final String CUSTOMERS_FILE = CUSTOMERS + ".json";
	public final String BOOKS = "Books";
	public final String BOOKS_FILE = BOOKS + ".json";
	public final String BOOKSLIKES = "BOOKSLIKES";
	public final String BOOKSLIKES_FILE = BOOKSLIKES + ".json";
	public final String REVIEWS = "Reviews";
	public final String REVIEWS_FILE = REVIEWS + ".json";
	public final String OWNED = "Owned";
	public final String OWNED_FILE = OWNED + ".json";
	public final String MSGS = "Msgs";
	public final String MSGS_FIlE = MSGS + ".json";
	public final String NAME = "name";
	public final Type CUSTOMER_COLLECTION = new TypeToken<Collection<Customer>>() {}.getType();
	//derby constants
	public final String DB_NAME = "DB_NAME";
	public final String DB_DATASOURCE = "DB_DATASOURCE";
	public final String PROTOCOL = "jdbc:derby:"; 
	public final String OPEN = "Open";
	public final String SHUTDOWN = "Shutdown";
	
	//sql statements
	public final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE CUSTOMER(Username varchar(100) NOT NULL,"
			+ "Email varchar(100),"
			+ "Street varchar(100),"
			+ "City varchar(100),"
			+ "ZipCode varchar(100),"
			+ "Phone varchar(100),"
			+ "Password varchar(100),"
			+ "Nickname varchar(100),"
			+ "Description varchar(500),"
			+ "Photo varchar(500),"
			+ "PRIMARY KEY (Username))";
	public final String CREATE_BOOKS_TABLE = "CREATE TABLE BOOKS(Name varchar(100) NOT NULL,"
			+ "Price REAL,"
			+ "Author varchar(100),"
			+ "Description varchar(2000),"
			+ "Url varchar(100),"
			+ "Cover varchar(100),"
			+ "Genre varchar(100),"
			+ "featured INTEGER DEFAULT 0,"
			+ "PRIMARY KEY (Name))";
	public final String CREATE_BOOKSLIKES_TABLE = "CREATE TABLE BOOKSLIKES(Username varchar(100),"
			+ "BookName varchar(100),"
			+ "FOREIGN KEY (Username) REFERENCES CUSTOMER(Username) ON DELETE CASCADE,"
			+ "FOREIGN KEY (BookName) REFERENCES BOOKS(Name) ON DELETE CASCADE)";
	public final String CREATE_REVIEWS_TABLE = "CREATE TABLE REVIEWS(Username varchar(100),"
			+ "BookName varchar(100),"
			+ "Content varchar(500),"
			+ "Accepted INTEGER DEFAULT 0,"
			+ "ReviewDate TIMESTAMP,"
			+ "FOREIGN KEY (Username) REFERENCES CUSTOMER(Username) ON DELETE CASCADE,"
			+ "FOREIGN KEY (BookName) REFERENCES BOOKS(Name) ON DELETE CASCADE)";
	public final String CREATE_OWNED_TABLE = "CREATE TABLE OWNED(Username varchar(100),"
			+ "BookName varchar(100),"
			+ "readingSession REAL,"
			+ "PurchaseDate TIMESTAMP,"
			+ "Amount REAL,"
			+ "FOREIGN KEY (Username) REFERENCES CUSTOMER(Username) ON DELETE CASCADE,"
			+ "FOREIGN KEY (BookName) REFERENCES BOOKS(Name) ON DELETE CASCADE)";
	public final String CREATE_MSGS_TABLE = "CREATE TABLE MSGS(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "Username varchar(100),"
			+ "Subject varchar(100),"
			+ "Content varchar(1000),"
			+ "Reply varchar(1000) DEFAULT NULL,"
			+ "SentDate TIMESTAMP,"
			+ "ReadStatus INTEGER DEFAULT 0,"
			+ "ReadStatusADMIN INTEGER DEFAULT 0,"
			+ "FOREIGN KEY (Username) REFERENCES CUSTOMER(Username) ON DELETE CASCADE)";
	public final String INSERT_REVIEW_STMT = "INSERT INTO REVIEWS (Username,BookName,Content,Accepted,ReviewDate) VALUES (?,?,?,?,?)";
	public final String INSERT_BOOKSLIKES_STMT = "INSERT INTO BOOKSLIKES (USERNAME,BOOKNAME) VALUES (?,?)";
	public final String INSERT_OWNED_STMT = "INSERT INTO OWNED (USERNAME,BOOKNAME,READINGSESSION,PURCHASEDATE, AMOUNT) VALUES (?,?,?,?,?)";
	public final String INSERT_MSGS_STMT = "INSERT INTO MSGS(USERNAME,SUBJECT,CONTENT,REPLY,SENTDATE,READSTATUS,READSTATUSADMIN) VALUES (?,?,?,?,?,?,?)";
	public final String UPDATE_OWNED_SESSION_BY_USERNAME_BOOKNAME = "UPDATE OWNED SET readingSession=? "
			+ "WHERE Username=? AND BookName=?";
	public final String UPDATE_REVIEW_BY_USERNAME_BOOKNAME = "UPDATE REVIEWS SET Accepted=? "
			+ "WHERE Username=? AND BookName=?";
	public final String INSERT_CUSTOMER_STMT = "INSERT INTO CUSTOMER (USERNAME, EMAIL, STREET, CITY, ZIPCODE, PHONE, PASSWORD, NICKNAME, DESCRIPTION, PHOTO) VALUES(?,?,?,?,?,?,?,?,?,?)";
	public final String INSERT_BOOK_STMT = "INSERT INTO BOOKS (Name, Price, Author, Description, Url, Cover, Genre, Featured) VALUES(?,?,?,?,?,?,?,?)";
	public final String SELECT_ALL_CUSTOMERS_STMT = "SELECT * FROM CUSTOMER";
	public final String SELECT_CUSTOMER_BY_USERNAME = "SELECT * FROM CUSTOMER "
			+ "WHERE Username=?";
	public final String SELECT_ALL_OWNED = "SELECT * FROM OWNED";
	public final String SELECT_OWNED_BY_USERNAME_BOOKNAME = "SELECT * FROM OWNED "
			+ "WHERE Username=? and Bookname=?";
	public final String SELECT_OWNED_BY_USERNAME = "SELECT * FROM OWNED "
			+ "WHERE Username=?";
	public final String SELECT_ACCEPTED_REVIEWS_BY_BOOKNAME = "SELECT * FROM REVIEWS "
			+ "WHERE BookName=? And Accepted=1";
	public final String SELECT_BOOKLIKES_BY_BOOKNAME = "SELECT * FROM BOOKSLIKES "
			+ "WHERE BookName=?";
	public final String SELECT_BOOKSLIKES_BY_USERNAME = "SELECT * FROM BOOKSLIKES "
			+ "WHERE Username=?";
	public final String SELECT_CUSTOMER_BY_USERNAME_STMT = "SELECT * FROM CUSTOMER "
			+ "WHERE Username=?";
	public final String SELECT_CUSTOMER_BY_USERNAME_PASSWORD_STMT = "SELECT * FROM CUSTOMER " 
			+ "WHERE Username=? " + "AND " + "PASSWORD=?";
	public final String SELECT_BOOKS_BY_FEATURED = "SELECT * FROM BOOKS "
			+ "WHERE Featured=? "
			+ "ORDER BY Name ASC";
	public final String SELECT_BOOKS_ORDERD_NAME_ASC = "SELECT * FROM BOOKS "
			+ "ORDER BY Name ASC";
	public final String SELECT_BOOKS_ORDERD_GENRE_ASC = "SELECT * FROM BOOKS "
			+ "ORDER BY Genre ASC";
	public final String SELECT_BOOK_BY_BOOKNAME_forloop = "SELECT * FROM BOOKS "
			+ "WHERE Name=";
	public final String SELECT_BOOK_BY_BOOKNAME = "SELECT * FROM BOOKS "
			+ "WHERE Name=?";
	public final String SELECT_BOOKS_BY_GENRE_ORDERD_NAME_ASC = "SELECT * FROM BOOKS "
			+ "WHERE Genre=? "
			+ "ORDER BY Name ASC";
	public final String DELETE_BOOKLIKE_BY_BOOKNAME = "DELETE FROM BOOKSLIKES "
			+ "WHERE Username=? and BookName=?";
	public final String SELECT_REVIEWS_NOT_ACCEPTED = "SELECT * FROM REVIEWS "
			+ "WHERE Accepted = 0 "
			+ "ORDER BY ReviewDate DESC";
	public final String DELETE_REVIEWS_BY_USERNAME_BOOKNAME = "DELETE FROM REVIEWS "
			+ "WHERE Username=? AND BookName=?";
	public final String DELETE_CUSTOMER_BY_USERNAME = "DELETE FROM CUSTOMER "
			+ "WHERE Username=?";
	public final String SELECT_MSGS_BY_USERNAME_ORDERED_SENTDATE = "SELECT * FROM MSGS "
			+ "WHERE Username=? "
			+ "ORDER BY SentDate DESC";
	public final String SELECT_ALL_MSGS_ORDERED_SENTDATE = "SELECT * FROM MSGS "
			+ "ORDER BY SentDate DESC";
	public final String UPDATE_MSGS_REPLY_BY_ID = "UPDATE MSGS SET Reply=?, ReadStatus=0 "
			+ "WHERE ID=?";
	public final String UPDATE_MSGS_READSTATUS_BY_ID = "UPDATE MSGS SET ReadStatus=? "
			+ "WHERE ID=?";
	public final String UPDATE_MSGS_READSTATUSADMIN_BY_ID = "UPDATE MSGS SET ReadStatusADMIN=? "
			+ "WHERE ID=?";
	public final String SELECT_COUNT_MSGS_BY_READSTATUSADMIN = "SELECT COUNT(*) FROM MSGS "
			+ "WHERE ReadStatusADMIN=?";
	public final String SELECT_COUNT_MSGS_BY_READSTATUS = "SELECT COUNT(*) FROM MSGS "
			+ "WHERE ReadStatus=? AND Username=?";
	
};
