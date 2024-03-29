package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;



public class DB {

	private static Connection conn = null;
	static Logger logger = new CommonLogger(DB.class).getLogger();
	
	public static Connection getConnection() {
		
		
		if (conn == null) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				logger.error("SQL driver not found!");
			}
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Shopping", "santhi-zstk347" , "1234");
				logger.info("DB connection created!");
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(conn+"Error on sql: "+e);
			}
	      	
		}
		
		return conn;
    } 
	
	
	public static boolean checkValueisExist(String value, String table_name, String column) {
		Connection conn = DB.getConnection();

		PreparedStatement statement = null;
		try {
			System.out.println(conn);
			statement = conn.prepareStatement("SELECT "+column+" FROM "+table_name+" WHERE "+column+" like ?");
            statement.setString(1, value);
            
            logger.info("value:"+value+" - "+statement.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            
        } catch (SQLException e) {    	
			logger.error("SQL querry exception: "+e+" Querry: "+ statement);
		}
		
		return false;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(getConnection());
	}


	public static boolean validateSession(String sessionID, String mailID) throws SQLException {
		PreparedStatement pstmt = DB.getConnection().prepareStatement("Select * from Session where sessionID=? and mailID=?");
		pstmt.setString(1,sessionID);
		pstmt.setString(2,mailID);
		ResultSet rs = pstmt.executeQuery();

		return rs.next();
	}
	
}
