package com.product;

import java.sql.Date;

public class Offer {
	double discountPercentage;
    Date validityPeriod;
    
    
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public Date getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(Date validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
    
    
}
