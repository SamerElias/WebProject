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
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import resources.AppConstants;
import servlets.GetBookReview.Reviewrespond;

/**
 * Servlet getting the likers of the book.
 */
@WebServlet(
		urlPatterns = { "/GetBookLikes" }, 
		initParams = { 
				@WebInitParam(name = "bookName", value = "")
		})
public class GetBookLikes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookLikes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Expects bookName as URI paramter.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		String Bookname = request.getParameter("bookName");
		if(Bookname == null || Bookname.equals("")) {
			response.sendError(500);
			return;
		}
		Connection conn = null;
		PreparedStatement stmt = null,customerstmt = null;
		ResultSet rs = null,customerRs = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();
			Gson gson = new GsonBuilder().create();
			stmt = conn.prepareStatement(AppConstants.SELECT_BOOKLIKES_BY_BOOKNAME);
			stmt.setString(1, Bookname);
			rs = stmt.executeQuery();
			ArrayList<BookLikeJSON> bookLikes = new ArrayList<>();
			while (rs.next()){
				customerstmt = conn.prepareStatement(AppConstants.SELECT_CUSTOMER_BY_USERNAME_STMT);
				customerstmt.setString(1, rs.getString("Username"));
				customerRs = customerstmt.executeQuery();
				BookLikeJSON b = null;
				if(customerRs.next()) {
					b = new BookLikeJSON(rs.getString("Username"),customerRs.getString("Nickname"),rs.getString("BookName"));
				}else {
					b = new BookLikeJSON(rs.getString("Username"),rs.getString("Username"),rs.getString("BookName"));
				}
				Reviewrespond review = null;
				
				bookLikes.add(b);
			}
			if(bookLikes.isEmpty()) {
				response.sendError(200);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(bookLikes);
			String customerJsonResult = gson.toJson(jsonElement);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(customerJsonResult);
        	writer.close();
        	response.setStatus(HttpServletResponse.SC_OK);
		}catch (NamingException e) {
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
		doGet(request, response);
	}
	class BookLikeJSON{
		private String username;
		private String nickname;
		private String bookName;
		public BookLikeJSON(String username, String nickname, String bookName) {
			super();
			this.username = username;
			this.nickname = nickname;
			this.bookName = bookName;
		}

		
		
		
	}
}
