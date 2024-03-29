package com.user.details;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.enums.Role;

import utils.DB;

public class User {
	String name;
    String password;
    String email;
    String phone;
    String address;
    Role role;
    
    
    public User() {   	
    }
    
    public User(String name, String password, String email, String phone, String address) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = Role.CUSTOMER;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}




	Connection conn =  DB.getConnection();
	public String login(String username, String password) {
	    String queryResult = "0";
	    System.out.println(username);
	    System.out.println(password);
	    try {
	        PreparedStatement preparedStatement = conn.prepareStatement("SELECT user_name, password FROM Users WHERE user_name LIKE ?");
	        preparedStatement.setString(1, username);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            String dbUsername = resultSet.getString("user_name");
	            String dbPassword = resultSet.getString("password");

	            if (password.equals(dbPassword)) {
	                queryResult = "1"; 
	            } else {
	                queryResult = "2"; 
	            }
	        } 
	        else {
	            queryResult = "3";
	        }

	        resultSet.close();
	        preparedStatement.close();
	    } 
	    catch (SQLException ex) {
	        ex.printStackTrace();
	        System.out.println("Error: " + ex.getMessage());
	        queryResult = "4";
	    }
        System.out.println(queryResult);
	    return queryResult;
	}
    
    
    
	public String signUp() {
		 try {
	       	PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (username, password,email,phone,address) VALUES (?,?,?,?,?)");
	       	pstmt.setString(1, name);
	        pstmt.setString(2, password);
	        pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, address);
	         int rows = pstmt.executeUpdate();
	          if (rows > 0) {
		           	return "0";
	           } else {
	        	   return "1";
	           }
	        } catch (SQLException ex) {
		         ex.printStackTrace();
	             return "2";
	        }
	}
}
