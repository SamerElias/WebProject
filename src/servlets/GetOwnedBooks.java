package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import model.Book;
import model.Owned;
import resources.AppConstants;

/**
 * Servlet for getting owned books by session (logged in customer).
 */
@WebServlet( "/GetOwnedBooks" )
public class GetOwnedBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOwnedBooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();
			Gson gson = new GsonBuilder().create();
			String user = (String) request.getSession(false).getAttribute("username");
			stmt = conn.prepareStatement(AppConstants.SELECT_OWNED_BY_USERNAME);
			stmt.setString(1, user);
			rs = stmt.executeQuery();
			ArrayList<Owned> owneds = new ArrayList<>();
			while (rs.next()){
				Owned owned = new Owned(rs.getString("Username"), rs.getString("BookName"), rs.getDouble("readingSession"),rs.getTimestamp("PurchaseDate"),rs.getDouble("Amount"));
				owneds.add(owned);
			}
			if(owneds.isEmpty()) {
				response.sendError(200);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			String query = AppConstants.SELECT_BOOK_BY_BOOKNAME_forloop;
			query = query.concat("\'" + owneds.get(0).getBookName() + "\'");
			owneds.remove(0);
			for(Owned owned : owneds) {
				query = query.concat(" OR Name=\'" + owned.getBookName() + "\'");
			}
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			ArrayList<Book> books = new ArrayList<>();
			while (rs.next()){
				Book book = new Book(rs.getString("Name"),rs.getDouble("Price"),rs.getString("Author"),rs.getString("Description"),rs.getString("Url"),rs.getString("Cover"),rs.getString("Genre"),rs.getInt("featured"));
				books.add(book);
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(books);
			String customerJsonResult = gson.toJson(jsonElement);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(customerJsonResult);
        	writer.close();
        	response.setStatus(HttpServletResponse.SC_OK);
		} catch (NamingException e) {
			try {
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			response.sendError(500);
		} catch (SQLException e) {
			try {
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			response.sendError(500);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
