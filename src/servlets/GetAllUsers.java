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

import resources.AppConstants;

/**
 * Servlet for getting all the customers info from DB (doesn't return PASSWORDS).
 */
@WebServlet("/GetAllUsers")
public class GetAllUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllUsers() {
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
			stmt = conn.prepareStatement(AppConstants.SELECT_ALL_CUSTOMERS_STMT);
			rs = stmt.executeQuery();
			ArrayList<CustomerJSON> customers = new ArrayList<>();
			while (rs.next()){
				CustomerJSON customersResult = null;
				customersResult = new CustomerJSON(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(8), rs.getString(9), rs.getString(10));
				customers.add(customersResult);
			}
			if(customers.isEmpty()) {
				response.setStatus(HttpServletResponse.SC_OK);
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
				if(conn != null) {conn.close();}
				return;
			}
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}
			if(conn != null) {conn.close();}
			JsonElement jsonElement = gson.toJsonTree(customers);
			String customerJsonResult = gson.toJson(jsonElement);
        	response.addHeader("Content-Type", "application/json");
        	PrintWriter writer = response.getWriter();
        	writer.println(customerJsonResult);
        	writer.close();
        	response.setStatus(HttpServletResponse.SC_OK);
	}catch (Exception e) {
		// TODO: handle exception
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
			
			
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
