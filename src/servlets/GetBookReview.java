package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

/**
 * Servlet for getting the reviews of the book.
 */
@WebServlet(
		urlPatterns = { "/GetBookReview" }, 
		initParams = { 
				@WebInitParam(name = "bookName", value = "")
		})
public class GetBookReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookReview() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Expects booName URI paramter.
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
			stmt = conn.prepareStatement(AppConstants.SELECT_ACCEPTED_REVIEWS_BY_BOOKNAME);
			stmt.setString(1, Bookname);
			rs = stmt.executeQuery();
			ArrayList<Reviewrespond> reviews = new ArrayList<>();
			while (rs.next()){
				customerstmt = conn.prepareStatement(AppConstants.SELECT_CUSTOMER_BY_USERNAME_STMT);
				customerstmt.setString(1, rs.getString("Username"));
				customerRs = customerstmt.executeQuery();
				Reviewrespond review = null;
				if(customerRs.next()) {
					review = new Reviewrespond(customerRs.getString("Photo"),customerRs.getString("Nickname"), rs.getString("BookName"),rs.getString("content"), rs.getInt("Accepted"),rs.getTimestamp("ReviewDate"));
				}else {
					review = new Reviewrespond(null,customerRs.getString("Nickname"), rs.getString("BookName"),rs.getString("content"), rs.getInt("Accepted"),rs.getTimestamp("ReviewDate"));
				}
				reviews.add(review);
			}
			if(reviews.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_OK);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(reviews);
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
				if(customerRs != null) {customerRs.close();}
				if(customerstmt != null) {customerstmt.close();}
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
					if(customerRs != null) {customerRs.close();}
					if(customerstmt != null) {customerstmt.close();}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			e.printStackTrace();
			response.sendError(500);
		}
	}
	class Reviewrespond{
		private String photo;
		private String nickname;
		private String bookName;
		private String content;
		private int accepted;
		private Timestamp reviewDate;
		public Reviewrespond(String photo, String nickname, String bookName, String content, int accepted,
				Timestamp reviewDate) {
			super();
			this.photo = photo;
			this.nickname = nickname;
			this.bookName = bookName;
			this.content = content;
			this.accepted = accepted;
			this.reviewDate = reviewDate;
		}
		
		
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
