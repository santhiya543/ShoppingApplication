package com.wishList.servlets;

import java.io.IOException;
import utils.JSON;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.product.ProductDAO;
import com.wishList.wishListDAO;

import utils.CommonLogger;

/**
 * Servlet implementation class addToWishListServlet
 */
@WebServlet("/addToWishList")
public class addToWishListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger logger = (new CommonLogger(addToWishListServlet.class)).getLogger();
   
    public addToWishListServlet() {
        super();
    }

    wishListDAO wishlistDao=new wishListDAO();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int productId = Integer.parseInt(request.getParameter("productID"));
		String mail = request.getParameter("mail");
	try {
		boolean out=wishlistDao.addProductToWishlist(mail,productId);
		
		if(out) {
			JSONObject respJson = new JSONObject();
    		respJson.put("statuscode", 200);
    		respJson.put("message", "product added to the wishList");
			logger.info("product added to the wishList");
			response.getWriter().write(respJson.toString());
		}
		else {
			logger.error("product didn't added to the wishList");
			throw new Exception("product didn't added to the wishList");
		}
	}
	catch(Exception e) {
		logger.error("Something went wrong foradd the product to wishList: " + e.getMessage());
	      JSONObject errorResponse = JSON.Create(400, e.getMessage());
		 response.getWriter().write(errorResponse.toString());
	}
  }

}
