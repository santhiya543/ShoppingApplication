package com.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.user.details.Seller;
import com.user.details.UserDAO;
import utils.CommonLogger;
import utils.DB;
import utils.Query;

public class ProductDAO {
	static Logger logger = (new CommonLogger(ProductDAO.class)).getLogger();
	Connection connection = DB.getConnection();

	private static ProductDAO productDAO = new ProductDAO();

	public static ProductDAO getObj() {
		return productDAO;
	}
	
	public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
             
        try { 
             PreparedStatement statement = connection.prepareStatement(Query.getAllProducts);
             ResultSet resultSet = statement.executeQuery(); 
             
            while (resultSet.next()) {
                Product product = new Product();
                
                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setProductPrice(resultSet.getDouble("price"));
                product.setProductImage(resultSet.getString("image"));
                
                Offer offer = new Offer();
                offer.setDiscountPercentage(resultSet.getDouble("discountPercentage"));
                offer.setValidityPeriod(resultSet.getDate("validityPeriod"));
                product.setProductOffer(offer);
                
                products.add(product);
            }
            return products;
        } catch (Exception e) {
        	logger.error("something went wrong for fetching all product details:"+ e);
			throw new Exception("Oops! something went wrong for fetching all product details. Please contact admin.");
		}
    }
	
	public List<Product> getProductDetails(int productID) throws Exception {
        List<Product> products = new ArrayList<>();
        
        try { 
             PreparedStatement statement = connection.prepareStatement(Query.getProductDetail);
             statement.setInt(1, productID);
             ResultSet resultSet = statement.executeQuery(); 
             
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductName(resultSet.getString("product_name"));
                product.setProductPrice(resultSet.getDouble("price"));
                product.setProductDescription(resultSet.getString("description"));
                product.setProductImage(resultSet.getString("image"));
                
                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryName(resultSet.getString("subCategory_name"));
                
                Category category = new Category();
                category.setCategory(resultSet.getString("category_name"));
                subCategory.setCategory(category);
                
                product.setSubCategory(subCategory);
                
                Seller seller = new Seller();
                seller.setSellerName(resultSet.getString("seller_name"));
                product.setSeller(seller);
                
                Brand brand = new Brand();
                brand.setBrandName(resultSet.getString("brand_name"));
                brand.setBrandDescription(resultSet.getString("brandDescription"));
                product.setBrand(brand);
                
                Offer offer = new Offer();
                offer.setDiscountPercentage(resultSet.getDouble("discountPercentage"));
                offer.setValidityPeriod(resultSet.getDate("validityPeriod"));
                product.setProductOffer(offer);
                
                products.add(product);
            }
            return products;
        } catch (Exception e) {
        	logger.error("something went wrong for fetching all product details:"+ e);
        	throw new Exception("Oops! something went wrong for fetching all product details. Please contact admin.");
		}
    }
}
