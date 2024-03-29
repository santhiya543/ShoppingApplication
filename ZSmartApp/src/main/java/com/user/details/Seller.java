package com.user.details;

import java.util.List;

import com.product.Product;

public class Seller extends User{
	private String sellerName;
	private String sellerDescription;
	private String sellerAddress;
	private List<Product> products;
	
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerDescription() {
		return sellerDescription;
	}
	public void setSellerDescription(String sellerDescription) {
		this.sellerDescription = sellerDescription;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}
