package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import model.Msg;
import resources.AppConstants;

/**
 * Servlet for retrieving all the message from DB for the user/admin (by session).
 */
@WebServlet("/GetAllMSGS")
public class GetAllMSGS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllMSGS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();
			Gson gson = new GsonBuilder().create();
			String user = (String) request.getSession(false).getAttribute("username");
			if(user.equals(AppConstants.ADMIN)) {
			stmt = conn.prepareStatement(AppConstants.SELECT_ALL_MSGS_ORDERED_SENTDATE);
			rs = stmt.executeQuery();
			}else {
				stmt = conn.prepareStatement(AppConstants.SELECT_MSGS_BY_USERNAME_ORDERED_SENTDATE);
				stmt.setString(1, user);
				rs = stmt.executeQuery();
			}
			ArrayList<Msg> msgs = new ArrayList<>();
			while (rs.next()){
				Msg msg = null;
				msg = new Msg(rs.getInt("ID"),rs.getString("Username"),rs.getString("Subject"),rs.getString("Content"),rs.getString("Reply"),rs.getTimestamp("SentDate"),rs.getInt("ReadStatus"),rs.getInt("ReadStatusADMIN"));
				msgs.add(msg);
			}
			if(msgs.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_OK);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(msgs);
			String customerJsonResult = gson.toJson(jsonElement);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(customerJsonResult);
        	writer.close();
        	response.setStatus(HttpServletResponse.SC_OK);
	}catch (Exception e) {
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
