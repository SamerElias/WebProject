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

import resources.AppConstants;

/**
 * Servlet for getting all the genres of the books to display them as category.
 */
@WebServlet("/GetBooksGenre")
public class GetBooksGenre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBooksGenre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * 
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
			stmt = conn.prepareStatement(AppConstants.SELECT_BOOKS_ORDERD_GENRE_ASC);
			rs = stmt.executeQuery();
			ArrayList<String> genres = new ArrayList<>();
			while (rs.next()){
				if(!genres.contains(rs.getString("Genre"))) {
				genres.add(rs.getString("Genre"));
				}
			}
			if(genres.size() == 0) {
				response.sendError(500);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(genres);
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
		doGet(request, response);
	}

}
