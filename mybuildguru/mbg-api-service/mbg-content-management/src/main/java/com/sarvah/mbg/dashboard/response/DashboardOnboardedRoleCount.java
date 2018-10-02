package com.sarvah.mbg.dashboard.response;

public class DashboardOnboardedRoleCount {

	private long totalProducts;
	private long provider;
	private long dealer;
	private long mbg;
	
	public long getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}
	public long getProvider() {
		return provider;
	}
	public void setProvider(long provider) {
		this.provider = provider;
	}
	public long getDealer() {
		return dealer;
	}
	public void setDealer(long dealer) {
		this.dealer = dealer;
	}
	public long getMbg() {
		return mbg;
	}
	public void setMbg(long mbg) {
		this.mbg = mbg;
	}
	
}
