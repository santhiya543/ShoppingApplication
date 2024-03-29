package com.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;


import utils.CommonLogger;
import utils.DB;


public class CartDAO {
	
	static Logger logger = (new CommonLogger(CartDAO.class)).getLogger();
	Connection connection = DB.getConnection();

	private static CartDAO cartDAO = new CartDAO();

	public static CartDAO getObj() {
		return cartDAO;
	}
	
	public boolean addToCart(Cart cart){
		boolean flag = false;
		try {
			String query = "insert into cart(uid, pid, quantity) values(?,?,?)";
			PreparedStatement psmt = connection.prepareStatement(query);
			psmt.setInt(1, cart.getUserId());
			psmt.setInt(2, cart.getProductId());
			psmt.setInt(3, cart.getQuantity());

			psmt.executeUpdate();
			flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
