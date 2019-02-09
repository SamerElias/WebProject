package listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Book;
import resources.AppConstants;

/**
 * Application Lifecycle Listener implementation class ManageBooksDBFromJsonFile
 *
 */
@WebListener
public class ManageBooksDBFromJsonFile implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ManageBooksDBFromJsonFile() {
        // TODO Auto-generated constructor stub
    }
    
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	 
         //shut down database
    	 try {
     		//obtain CustomerDB data source from Tomcat's context and shutdown
     		Context context = new InitialContext();
     		BasicDataSource ds = (BasicDataSource)context.lookup(
     				cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.SHUTDOWN);
     		ds.getConnection();
     		ds = null;
		} catch (SQLException | NamingException e) {
			cntx.log("Error shutting down database",e);
		}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	ServletContext cntx = event.getServletContext();
    	
    	try{
    		
    		//obtain BooksDB data source from Tomcat's context
    		Context context = new InitialContext();
    		BasicDataSource ds = (BasicDataSource)context.lookup(
    				cntx.getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
    		Connection conn = ds.getConnection();
    		
    		boolean created = false;
    		try{
    			//create Books table
    			Statement stmt = conn.createStatement();
    			stmt.executeUpdate(AppConstants.CREATE_BOOKS_TABLE);
    			//commit update
        		conn.commit();
        		stmt.close();
    		}catch (SQLException e){
    			//check if exception thrown since table was already created (so we created the database already 
    			//in the past
    			created = tableAlreadyExists(e);
    			if (!created){
    				throw e;//re-throw the exception so it will be caught in the
    				//external try..catch and recorded as error in the log
    			}
    		}
    		
    		//if no database exist in the past - further populate its records in the table
    		if (!created){
    			//populate Books table with Book data from json file
    			Collection<Book> Books = loadBooks(cntx.getResourceAsStream(File.separator +
    														   AppConstants.BOOKS_FILE));
    			PreparedStatement pstmt = conn.prepareStatement(AppConstants.INSERT_BOOK_STMT);
    			for (Book book : Books){
    				pstmt.setString(1,book.getName());
    				pstmt.setDouble(2,book.getPrice());
    				pstmt.setString(3,book.getAuthor());
    				pstmt.setString(4,book.getDescription());
    				pstmt.setString(5, book.getUrl());
    				pstmt.setString(6, book.getCover());
    				pstmt.setString(7, book.getGenre());
    				pstmt.setInt(8, book.getFeatured());
    				pstmt.executeUpdate();
    			}

    			//commit update
    			conn.commit();
    			//close statements
    			pstmt.close();
    		}
    		

    		//close connection
    		conn.close();

    	} catch (SQLException | NamingException | IOException e) {
    		//log error 
    		cntx.log("Error during database initialization",e);
    	}
    }
private Collection<Book> loadBooks(InputStream is) throws IOException{
		
		//wrap input stream with a buffered reader to allow reading the file line by line
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}

		Gson gson = new Gson();
		//this is a require type definition by the Gson utility so Gson will 
		//understand what kind of object representation should the json file match
		Type type = new TypeToken<Collection<Book>>(){}.getType();
		Collection<Book> Books = gson.fromJson(jsonFileContent.toString(), type);
		//close
		br.close();	
		return Books;

	}
}
