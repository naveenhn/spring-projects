package com.sarvah.mbg.rest.catalog.model;

import javax.ws.rs.QueryParam;

import com.sarvah.mbg.rest.model.AbstractRequestParam;

public class ProductSearchRequestParam extends AbstractRequestParam {
	@QueryParam("skuid")
	private String skuId;

	@QueryParam("s")
	private String searchValue;

	@QueryParam("model")
	private String model;

	@QueryParam("pricing")
	private String price;

	@QueryParam("providerName")
	private String providerName;

	@QueryParam("category")
	private String category;

	@QueryParam("subCategory")
	private String subCategory;

	@QueryParam("productsType")
	private String productsType;

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

	@QueryParam("zipcode")
	private String zipcode;

	@QueryParam("city")
	private String city;

	@QueryParam("brand")
	private String brand;

	@QueryParam("key")
	private String key;

	@QueryParam("value")
	private String value;

	@QueryParam("filter")
	private boolean filter;

	@QueryParam("isSubcategoryList")
	private boolean isSubcategoryList;

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
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue
	 *            the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * @return the subCategory
	 */
	public String getSubCategory() {
		return subCategory;
	}

	/**
	 * @param subCategory
	 *            the subCategory to set
	 */
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the filter
	 */
	public boolean isFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	/**
	 * @return the isSubcategoryList
	 */
	public boolean isSubcategoryList() {
		return isSubcategoryList;
	}

	/**
	 * @param isSubcategoryList
	 *            the isSubcategoryList to set
	 */
	public void setSubcategoryList(boolean isSubcategoryList) {
		this.isSubcategoryList = isSubcategoryList;
	}
}
