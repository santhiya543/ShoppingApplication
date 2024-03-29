package com.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brand {
	private String brandName;
	private String brandDescription;
	private List<Product> products;
	private static final Map<String, Brand> brandMap = new HashMap<>();
	public Brand() {
		
	}
	public Brand(String brandName) {
	    this.brandName = brandName;
	    
	    if (!brandMap.containsKey(brandName)) {
	    	brandMap.put(brandName, this);
	    }
	}
	
	public static Brand getBrandName(String brandName) {
		if (brandMap.containsKey(brandName)) {
            return brandMap.get(brandName);
        }
		  return new Brand(brandName);
	}
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandDescription() {
		return brandDescription;
	}
	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	
}
