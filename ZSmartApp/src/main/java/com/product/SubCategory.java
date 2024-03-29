package com.product;

import java.util.HashMap;
import java.util.Map;

public class SubCategory{
	private static final Map<String, SubCategory> subCategoryMap = new HashMap<>();	
	String subCategoryName;
	Category category;
	
	public SubCategory(){}
	
	public SubCategory(String subCategoryName){
	    this.subCategoryName = subCategoryName;
	    
	    if (!subCategoryMap.containsKey(subCategoryName)){
	    	subCategoryMap.put(subCategoryName, this);
	    }
	}
	
	public static SubCategory getSubCategoryName(String subCategoryName) {
		if (subCategoryMap.containsKey(subCategoryName)) {
            return subCategoryMap.get(subCategoryName);
        }
		  return new SubCategory(subCategoryName);
	}
	
	public String getSubCategoryName() {
		return subCategoryName;
	}
	
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
