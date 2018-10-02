/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

/**
 * @author shivu
 *
 */
public class ItemCreateRequestParam {
	private String itemId;
	private String itemName;
	private int quantity;
	private String quantityType;
	private double mrp;
	private double tax;
	private double sellingPrice;
	private double shippingCharge;
	private String dealerId;
	private int minDeliveryTimeInDays;
	private int maxDeliveryTimeInDays;

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the mrp
	 */
	public double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the sellingPrice
	 */
	public double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the shippingCharge
	 */
	public double getShippingCharge() {
		return shippingCharge;
	}

	/**
	 * @param shippingCharge
	 *            the shippingCharge to set
	 */
	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}

	/**
	 * @param dealerId
	 *            the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the minDeliveryTimeInDays
	 */
	public int getMinDeliveryTimeInDays() {
		return minDeliveryTimeInDays;
	}

	/**
	 * @param minDeliveryTimeInDays
	 *            the minDeliveryTimeInDays to set
	 */
	public void setMinDeliveryTimeInDays(int minDeliveryTimeInDays) {
		this.minDeliveryTimeInDays = minDeliveryTimeInDays;
	}

	/**
	 * @return the maxDeliveryTimeInDays
	 */
	public int getMaxDeliveryTimeInDays() {
		return maxDeliveryTimeInDays;
	}

	/**
	 * @param maxDeliveryTimeInDays
	 *            the maxDeliveryTimeInDays to set
	 */
	public void setMaxDeliveryTimeInDays(int maxDeliveryTimeInDays) {
		this.maxDeliveryTimeInDays = maxDeliveryTimeInDays;
	}

	/**
	 * @return the quantityType
	 */
	public String getQuantityType() {
		return quantityType;
	}

	/**
	 * @param quantityType
	 *            the quantityType to set
	 */
	public void setQuantityType(String quantityType) {
		this.quantityType = quantityType;
	}
}
