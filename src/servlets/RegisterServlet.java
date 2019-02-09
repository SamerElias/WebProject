package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import model.Customer;
import resources.AppConstants;

/**
 * Servlet for registering.
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Context context;
		StringBuffer jb = new StringBuffer();
		String line = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			context = new InitialContext();
			BasicDataSource ds = (BasicDataSource)context.lookup(
					getServletContext().getInitParameter(AppConstants.DB_DATASOURCE) + AppConstants.OPEN);
			conn = ds.getConnection();
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
			Gson gson = new GsonBuilder().create();
			JsonPostRequest recevied = gson.fromJson(jb.toString(), JsonPostRequest.class);
			stmt = conn.prepareStatement(AppConstants.SELECT_CUSTOMER_BY_USERNAME_STMT);
			stmt.setString(1, recevied.username.toLowerCase());
			rs = stmt.executeQuery();
			Customer customersResult2 = null;
			if (rs.next()){
				customersResult2 = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10));
			}
			if(customersResult2 != null || recevied.username.equals(AppConstants.ADMIN)) {
				response.sendError(400);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			CustomerJSON customersResult;
			stmt = conn.prepareStatement(AppConstants.INSERT_CUSTOMER_STMT);
			stmt.setString(1, recevied.username.toLowerCase());
			stmt.setString(2, recevied.email);
			stmt.setString(3, recevied.street);
			stmt.setString(4, recevied.city);
			stmt.setString(5, recevied.zipCode);
			stmt.setString(6, recevied.phone);
			stmt.setString(7, recevied.password);
			stmt.setString(8, recevied.nickname);
			stmt.setString(9, recevied.description);
			if(recevied.photo == null || recevied.photo.equals("")) {
				stmt.setString(10, "./Resources/Images/icons/User.png");
				recevied.photo = "./Resources/Images/icons/User.png";
			}else {
			stmt.setString(10, recevied.photo);
			}
			int res = stmt.executeUpdate();
			customersResult = new CustomerJSON(recevied.username.toLowerCase(), recevied.email,recevied.street,recevied.city,recevied.zipCode,recevied.phone,recevied.nickname,recevied.description,recevied.photo);
			request.getSession(true).setAttribute("username", recevied.username.toLowerCase());
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			String customerJsonResult = gson.toJson(customersResult);
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
	 class JsonPostRequest{
		public String username;
    	public String email;
    	public String password;
    	public String street;
    	public String city;
    	public String zipCode;
    	public String phone;
    	public String nickname;
    	public String description;
    	public String photo;
		public JsonPostRequest(String username, String email, String password, String street, String city,
				String zipCode, String phone, String nickname, String description, String photo) {
			super();
			this.username = username;
			this.email = email;
			this.password = password;
			this.street = street;
			this.city = city;
			this.zipCode = zipCode;
			this.phone = phone;
			this.nickname = nickname;
			this.description = description;
			this.photo = photo;
		}
		
    }
	 class CustomerJSON {
			public String username;
			public String email;
			public String street;
			public String city;
			public String zipCode;
			public String phone;
			public String nickname;
			public String description;
			public String photo;
			public CustomerJSON(String username, String email, String street, String city, String zipCode, String phone,
					String nickname, String description, String photo) {
				super();
				this.username = username;
				this.email = email;
				this.street = street;
				this.city = city;
				this.zipCode = zipCode;
				this.phone = phone;
				this.nickname = nickname;
				this.description = description;
				this.photo = photo;
			}
		 }
}

