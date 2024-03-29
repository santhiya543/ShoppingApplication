package com.wishList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.product.ProductDAO;

import utils.CommonLogger;
import utils.DB;
import utils.Query;

public class wishListDAO {
	
	static Logger logger = (new CommonLogger(wishListDAO.class)).getLogger();
	Connection connection = DB.getConnection();

	private static wishListDAO wishListDao = new wishListDAO();

	public static wishListDAO getObj() {
		return wishListDao;
	}
	
	 public boolean addProductToWishlist(String  mail, int productId) throws Exception {
	        try {
	        	System.out.println("userId"+mail+"productName"+productId);
	            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
	            
	            PreparedStatement wishlistPsmt = connection.prepareStatement(Query.addToWishList);
	            wishlistPsmt.setDate(1, currentDate);
	            wishlistPsmt.setInt(2, productId);
	            wishlistPsmt.setString(3, mail);
	            int rowsAffected = wishlistPsmt.executeUpdate();
	            if(rowsAffected > 0) {
	            	logger.info("add a product to wishList is successful");
	            	return true;
	            }else {
	            	logger.error("add a product to wishList not successful");
	            	return false;
	            }
	            
	        } catch (SQLException e) {
	        	logger.error("something went wrong for add a product to wishList:"+ e);
	        	throw new Exception("Oops! something went wrong for add a product to wishList. Please contact admin.");
	        }
	    }
	 
	 public boolean deleteWishlist(String mail, int productId) throws Exception {
		    try {
		        String query = "DELETE FROM WishLists WHERE customer_id like ? AND product_id like ?";
		        PreparedStatement psmt = this.connection.prepareStatement(query);
		        psmt.setString(1, mail);
		        psmt.setInt(2, productId);
		        
		        int rowsAffected = psmt.executeUpdate();
		        if(rowsAffected > 0) {
		        	logger.info("delete a product from wishList is successfully");
		        	return true;
		        }
		        else {
		        	logger.error("something went wrong for delete a product to wishList");
		        	return false;
		        }
		    }
		    catch (Exception e) {
		    	logger.error("something went wrong for delete a product to wishList:"+ e);
	        	throw new Exception("Oops! something went wrong for delete a product to wishList. Please contact admin.");
		    }
		}
}
