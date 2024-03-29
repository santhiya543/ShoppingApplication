package com.models;

import java.time.LocalDate;

import com.product.SubCategory;

public class main {
	public static void main(String args[]) {
		SubCategory sub1=SubCategory.getSubCategoryName("dress");
		SubCategory sub2=SubCategory.getSubCategoryName("watch");
		SubCategory sub3=SubCategory.getSubCategoryName("dress");
		System.out.println("sub1"+sub1);
		System.out.println("sub2"+sub2);
		System.out.println("sub3"+sub3);
		
		LocalDate currentDate = LocalDate.now();
        System.out.println("Current Date in Java: " + currentDate);
	}
}
