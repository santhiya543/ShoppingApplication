package com.product;

import java.util.List;

import com.models.Feedback;
import com.user.details.Seller;

public class Product {
	private int productId;
    private String productName;
    private double productPrice;
    private String productDescription;
    private SubCategory subCategory;
    private Seller seller;
    private Brand brand;
    private Offer offer;
    private List<Feedback> feedback;
    private String productImage;

	public Product() {
	}

    public Product(String productName, double productPrice, String productDescription,
			SubCategory subCategory, Seller seller, Brand brand, Offer offer,
			String productImages) {
		this.productName = productName;
		this.productPrice = productPrice;
		this.productDescription = productDescription;
		this.subCategory = subCategory;
		this.seller = seller;
		this.brand = brand;
		this.offer = offer;
		this.productImage = productImages;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Offer getProductOffer() {
		return offer;
	}

	public void setProductOffer(Offer offer) {
		this.offer = offer;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImages) {
		this.productImage = productImages;
	}
    
}