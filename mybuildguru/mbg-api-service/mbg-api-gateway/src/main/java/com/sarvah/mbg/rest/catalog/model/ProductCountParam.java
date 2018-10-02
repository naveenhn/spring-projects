package com.sarvah.mbg.rest.catalog.model;

import javax.ws.rs.QueryParam;

import com.sarvah.mbg.rest.model.AbstractRequestParam;

public class ProductCountParam extends AbstractRequestParam {
	@QueryParam("skuid")
	private String skuId;

	@QueryParam("productName")
	private String productName;

	@QueryParam("pricing")
	private String price;

	public String getProductAssets() {
		return productAssets;
	}

	public void setProductAssets(String productAssets) {
		this.productAssets = productAssets;
	}

	public String getProductAttributes() {
		return productAttributes;
	}

	public void setProductAttributes(String productAttributes) {
		this.productAttributes = productAttributes;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@QueryParam("category")
	private String category;

	@QueryParam("productsType")
	private String productsType;

	@QueryParam("productAssets")
	private String productAssets;

	@QueryParam("productAttributes")
	private String productAttributes;

	@QueryParam("providerName")
	private String providerName;

	@QueryParam("rating")
	private String rating;

	@QueryParam("latestproducts")
	private String latestproducts;

	@QueryParam("accessory")
	private String accessory;

	@QueryParam("status")
	private String status;

	@QueryParam("page")
	private int page;

	@QueryParam("size")
	private int size;

	@QueryParam("sort")
	private String sort;

	@QueryParam("lat")
	private String lat;

	@QueryParam("lon")
	private String lon;

	@QueryParam("brand")
	private String brand;

	/**
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * @param skuId
	 *            the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the productsType
	 */
	public String getProductsType() {
		return productsType;
	}

	/**
	 * @param productsType
	 *            the productsType to set
	 */
	public void setProductsType(String productsType) {
		this.productsType = productsType;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rating
	 *            the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @return the latestproducts
	 */
	public String getLatestproducts() {
		return latestproducts;
	}

	/**
	 * @param latestproducts
	 *            the latestproducts to set
	 */
	public void setLatestproducts(String latestproducts) {
		this.latestproducts = latestproducts;
	}

	/**
	 * @return the accessory
	 */
	public String getAccessory() {
		return accessory;
	}

	/**
	 * @param accessory
	 *            the accessory to set
	 */
	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * @param sort
	 *            the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the lat
	 */
	public String getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}

	/**
	 * @return the lon
	 */
	public String getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            the lon to set
	 */
	public void setLon(String lon) {
		this.lon = lon;
	}

}
