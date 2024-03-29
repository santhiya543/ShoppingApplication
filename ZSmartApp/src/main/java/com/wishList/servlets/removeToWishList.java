package com.wishList.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.wishList.wishListDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class removeToWishList
 */
@WebServlet("/removeToWishList")
public class removeToWishList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = new CommonLogger(removeToWishList.class).getLogger();
	
    public removeToWishList() {
        super();
        // TODO Auto-generated constructor stub
    }

    wishListDAO wishlistDao=new wishListDAO();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productID"));
		String mail = request.getParameter("mail");
	try {
		boolean out=wishlistDao.deleteWishlist(mail,productId);
		if(out) {
			JSONObject respJson = new JSONObject();
    		respJson.put("statuscode", 200);
    		respJson.put("message", "remove the product from the the wishlist Successfully");
			logger.info("remove the product from the the wishlist Successfully");
			response.getWriter().write(respJson.toString());
		}
		else {
			logger.error("remove the product from the the wishlist failed");
			throw new Exception("remove the product from the the wishlist failed");
		}
	}
	catch(Exception e) {
		logger.error("Something went wrong for remove the product from the wishlist: " + e.getMessage());
	    JSONObject errorResponse = JSON.Create(400, e.getMessage());
		response.getWriter().write(errorResponse.toString());
	}
  }

}
