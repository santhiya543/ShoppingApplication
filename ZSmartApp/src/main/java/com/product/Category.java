package com.product;

import java.util.HashMap;
import java.util.Map;

public class Category {
	String category;
	private static final Map<String, Category> categoryMap = new HashMap<>();
	public Category() {
		
	}
	public Category(String category) {
	    this.category = category;
	    
	    if (!categoryMap.containsKey(category)) {
	    	categoryMap.put(category, this);
	    }
	}
	
	public static Category getCategoryName(String category) {
		if (categoryMap.containsKey(category)) {
            return categoryMap.get(category);
        }
		  return new Category(category);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
