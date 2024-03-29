package com.user.details;

//import java.security.Timestamp;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import utils.CommonLogger;
import utils.DB;
import utils.Generator;
import utils.Query;

public class UserDAO {
	static Logger logger = (new CommonLogger(UserDAO.class)).getLogger();
	Connection connection = DB.getConnection();

	private static UserDAO userDAO = new UserDAO();

	public static UserDAO getObj() {
		return userDAO;
	}

	
	public boolean createAccount(String userName, String mailID, String passwd) throws Exception {
		// insert user in user table & insert passwd in Login table
		try {

			logger.info("New Account going to create for "+mailID+" useName:"+userName);

			PreparedStatement statement = connection.prepareStatement(Query.CreateAccount);
			statement.setString(1, mailID);
			statement.setString(2, userName);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				logger.error("can't create account: " + statement.toString());
				throw new Exception("Something went wrong!");
			} else {
//				sqlFile.append(statement.toString());
				
				// inserting login creds
				statement = connection.prepareStatement(Query.InsertCredentials);
				statement.setString(1, mailID);
				statement.setString(2, passwd);
				statement.execute();
				logger.info("new account created successfully.");
//				sqlFile.append(statement.toString());
				return true;
			}
		} catch (SQLException e) {
			logger.error("SQL error on creating account"+" mail:"+mailID+" userName"+userName+ e);
			throw new Exception("Oops! Something went wrong. Please contact admin.");
		}
	}
	
	
	public boolean LoginUser(String mailID, String passwd) throws Exception {
		System.out.println(mailID + " "+passwd);
		String hshPW = getPasswd(mailID);
		if (BCrypt.checkpw(passwd, hshPW)) {
			return true;
		} else {
			return false;
		}
	}

	
	private String getPasswd(String mailID) throws Exception {
		try {
			PreparedStatement pstmt = connection.prepareStatement(Query.getPasswordByMailID);
			pstmt.setString(1, mailID);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				logger.info("Fetch password by Mail ID:" + mailID);
				String pw = rs.getString("passwd");
				System.out.println(pw);
				logger.info("check password"+pw);
				return pw;
			} else {
				logger.error("Can't get password for this user:" + mailID);
				throw new Exception("Something went wrong! Please contact admin.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in getting passwd by mail:" + mailID + " error:" + e);
			throw new Exception("Oops! Something went wrong.");
		}
	}

	public static String createNewSession(String mailID) throws Exception {
		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement(Query.CreateNewSession);
			String sessionID = Generator.createUUID("Session", "sessionID");
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
			statement.setString(1, sessionID);
			statement.setString(2, mailID);
			statement.setTimestamp(3, currentTimestamp);

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected == 0) {
				logger.error("can't create new session: " + statement.toString());
				throw new Exception("Invalid session!");
			} else {
				logger.info("new session created successfully.");
				// sqlFile.append(statement.toString());
				return sessionID;
			}
		} catch (SQLException e) {
			logger.error("SQL error on creating session" + e);
			throw new Exception("Can't create session! Please contact admin.");
		}

	}

	public static void deleteSession(String sessionID) throws Exception {

		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement("");

			statement.setString(1, sessionID);

			if (statement.execute()) {
				// sqlFile.append(statement.toString());
				logger.info("session deleted successfully.");
			}
		} catch (SQLException e) {
			logger.error("SQL error on deleting session" + e);
			throw new Exception("session error! Please contact admin.");
		}

	}

	public static boolean validateSession(String sessionID, String mailID) throws Exception {

		Connection conn = DB.getConnection();

		try {
			PreparedStatement statement = conn.prepareStatement("");

			statement.setString(1, mailID);
			statement.setString(2, sessionID);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				logger.info("valid session!" + sessionID);
				return true;
			} else {
				logger.info("Session invalid!" + sessionID+" mailID:"+mailID);
				return false;
			}
		} catch (SQLException e) {
			logger.error("SQL error on deleting session" + e);
			throw new Exception("session error! Please contact admin.");
		}

	}
	
	public String getMailIDFromUserName(String userName) throws Exception{

		String mailID = null;

		try{
			PreparedStatement pstmt = connection.prepareStatement("");
			pstmt.setString(1, userName);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()){
				logger.info("MailId got successfully.");
				mailID = rs.getString("mailID");
			}
		}
		catch(Exception e){
			logger.error("Error on get mailID : " + e);
			throw new Exception("Failed to get mailID");
		}

		return mailID;
	}
}
