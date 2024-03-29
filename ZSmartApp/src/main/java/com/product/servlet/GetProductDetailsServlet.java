package com.product.servlet;

import java.io.IOException;
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

import com.user.auth.*;

/**
 * Servlet implementation class GetProductDetailsServlet
 */

@WebServlet("/v1/GetProductDetails")
public class GetProductDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Logger logger = new CommonLogger(GetAllProductServlet.class).getLogger();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetProductDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private ProductDAO productDao;

    public void init() {
        productDao = new ProductDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	int productID=Integer.parseInt(request.getParameter("productID"));
    	System.out.print(productID);
        JSONArray jsonArray = new JSONArray();
        
        try {
           List<Product> products = productDao.getProductDetails(productID);
        	System.out.println(products);
        	
         for (Product product : products) {
            JSONObject jsonProduct = new JSONObject();
            
            jsonProduct.put("name", product.getProductName());
            jsonProduct.put("price", product.getProductPrice());
            jsonProduct.put("description", product.getProductDescription());
            jsonProduct.put("image", product.getProductImage());
            jsonProduct.put("subCat", product.getSubCategory().getSubCategoryName());
            jsonProduct.put("category", product.getSubCategory().getCategory().getCategory());
            jsonProduct.put("seller", product.getSeller().getSellerName());
            jsonProduct.put("brand", product.getBrand().getBrandName());
            jsonProduct.put("offer", product.getProductOffer().getDiscountPercentage());
            jsonProduct.put("validityPeriod", product.getProductOffer().getValidityPeriod());
            jsonArray.put(jsonProduct);
         }
            JSONObject jsonProductobj = new JSONObject();
            jsonProductobj.put("statesCode", "200");
			jsonProductobj.put("data", jsonArray);
			 logger.info("Product details fecth successfully");
		     response.getWriter().write(jsonProductobj.toString());
		} catch (JSONException e) {
			logger.error("Something went wrong fecth product details: " + e.getMessage());
		    JSONObject errorResponse = JSON.Create(400, e.getMessage());
			 response.getWriter().write(errorResponse.toString());
		}
        catch (Exception e) {
			logger.error("Something went wrong fecth product details: " + e.getMessage());
		    JSONObject errorResponse = JSON.Create(400, e.getMessage());
			 response.getWriter().write(errorResponse.toString());
		}
    }


}
