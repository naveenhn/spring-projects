package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.List;

import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;

public class ManageDealerView {
	private String userId;
	private String name;
	private List<String> shopname;

	private Date createdDate;
	private int estimatedDeliveryDate;
	private String emailId;
	private String packageName;
	private int totalShops;
	private long totalProducts;
	private String vatNumber;
	private String panNumber;
	private UserStatus status;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public List<String> getShopname() {
		return shopname;
	}

	public void setShopname(List<String> shopname) {
		this.shopname = shopname;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public int getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(int estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public int getTotalShops() {
		return totalShops;
	}

	public void setTotalShops(int totalShops) {
		this.totalShops = totalShops;
	}

	public long getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

}
