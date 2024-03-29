package com.product.servlet;

import java.io.IOException;

import com.user.auth.*;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.product.Product;
import com.product.ProductDAO;

import utils.CommonLogger;
import utils.JSON;

/**
 * Servlet implementation class GetAllProduct
 */

@WebServlet("/v1/GetAllProduct")
public class GetAllProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Logger logger = new CommonLogger(GetAllProductServlet.class).getLogger();
	
    private ProductDAO productDao;

    public void init() {
        productDao = new ProductDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      try {   		
        List<Product> products = productDao.getAllProducts();
        
        JSONArray jsonArray = new JSONArray();
        
         for (Product product : products) {
            JSONObject jsonProduct = new JSONObject();
            
            jsonProduct.put("ID", product.getProductId());
            jsonProduct.put("name", product.getProductName());
            jsonProduct.put("price", product.getProductPrice());
            jsonProduct.put("image", product.getProductImage());
            jsonProduct.put("offer", product.getProductOffer().getDiscountPercentage());
            jsonProduct.put("validityPeriod", product.getProductOffer().getValidityPeriod());
            jsonArray.put(jsonProduct);
         }
            JSONObject jsonProductobj = new JSONObject();
            jsonProductobj.put("statesCode", "200");
			jsonProductobj.put("data", jsonArray);
			logger.info("All Products fecth successfully");
		    response.getWriter().write(jsonProductobj.toString());
	  }
      catch (JSONException e) {
			logger.error("Something went wrong for convert json format to product details: " + e.getMessage());
		    JSONObject errorResponse = JSON.Create(400, e.getMessage());
			response.getWriter().write(errorResponse.toString());
	  }
      catch(Exception e) {
    	  logger.error("Something went wrong fecth product details: " + e.getMessage());
		  JSONObject errorResponse = JSON.Create(400, e.getMessage());
		  response.getWriter().write(errorResponse.toString());
      }  
      
    }
    
}
