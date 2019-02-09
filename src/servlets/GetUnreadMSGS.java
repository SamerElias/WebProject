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
import com.google.gson.JsonObject;

import resources.AppConstants;
import servlets.GetAllReviews.Reviewrespond;

/**
 * Servlet for getting unread messages for customer
 */
@WebServlet("/GetUnreadMSGS")
public class GetUnreadMSGS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUnreadMSGS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		Connection conn = null;
		PreparedStatement stmt = null,customerstmt = null;
		ResultSet rs = null,customerRs = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();
			Gson gson = new GsonBuilder().create();
			String user = (String) request.getSession(false).getAttribute("username");
			if(user.equals(AppConstants.ADMIN)) {
				stmt = conn.prepareStatement(AppConstants.SELECT_COUNT_MSGS_BY_READSTATUSADMIN);
				stmt.setInt(1, 0);
			}else {
				stmt = conn.prepareStatement(AppConstants.SELECT_COUNT_MSGS_BY_READSTATUS);
				stmt.setInt(1, 0);
				stmt.setString(2, user);
			}
			rs = stmt.executeQuery();
			ArrayList<Reviewrespond> reviews = new ArrayList<>();
			int numberOfRows  = 0;
			if(rs.next())
			{
				 numberOfRows = rs.getInt(1);
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonObject json = new JsonObject();
			json.addProperty("counter", numberOfRows);
			String customerJsonResult = json.toString();
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
