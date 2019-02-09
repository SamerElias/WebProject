package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import model.Owned;
import resources.AppConstants;

/**
 * Servlet for getting a book to read.
 */
@WebServlet(urlPatterns = {"/GetBook"}, initParams = { 
		@WebInitParam(name = "bookName", value = "")
})
public class GetBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Expects bookName as URI paramter.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		String bookname = request.getParameter("bookName");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();;
			String user = (String) request.getSession(false).getAttribute("username");
			stmt = conn.prepareStatement(AppConstants.SELECT_OWNED_BY_USERNAME_BOOKNAME);
			stmt.setString(1, user);
			stmt.setString(2, bookname);
			rs = stmt.executeQuery();
			Owned owned = null;
			if (rs.next()){
				owned = new Owned(rs.getString(1), rs.getString(2), rs.getDouble(3),rs.getTimestamp(4),rs.getDouble(5));                                                                                                                  
			}
			if(owned == null) {
				response.sendError(401);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			RequestDispatcher view = request.getRequestDispatcher("BooksHTML/" + bookname + ".htm");
			view.forward(request, response);
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
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
