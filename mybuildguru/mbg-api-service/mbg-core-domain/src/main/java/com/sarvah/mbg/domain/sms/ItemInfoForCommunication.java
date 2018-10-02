/**
 * 
 */
package com.sarvah.mbg.domain.sms;

import java.io.Serializable;

/**
 * @author shivu
 *
 */
public class ItemInfoForCommunication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String itemId;
	private String dealerId;
	private String itemName;
	private String itemImage;
	private String itemQuantityType;
	private int itemQuantity;
	private String itemUnitPrice;
	private String itemTotalPrice;
	private String itemShippingChrg;
	private int minDeliveryTimeInDays;
	private int maxDeliveryTimeInDays;
	// ShareQuote with customer purpose
	private String brand;
	private double mrp;
	private double itemPrice;
	private String deliveryDate;
	private String quoteProductPricingId;
	private String shared;

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
	 * @return the itemImage
	 */
	public String getItemImage() {
		return itemImage;
	}

	/**
	 * @param itemImage
	 *            the itemImage to set
	 */
	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	/**
	 * @return the itemQuantityType
	 */
	public String getItemQuantityType() {
		return itemQuantityType;
	}

	/**
	 * @param itemQuantityType
	 *            the itemQuantityType to set
	 */
	public void setItemQuantityType(String itemQuantityType) {
		this.itemQuantityType = itemQuantityType;
	}

	/**
	 * @return the itemQuantity
	 */
	public int getItemQuantity() {
		return itemQuantity;
	}

	/**
	 * @param itemQuantity
	 *            the itemQuantity to set
	 */
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	/**
	 * @return the itemUnitPrice
	 */
	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	/**
	 * @param itemUnitPrice
	 *            the itemUnitPrice to set
	 */
	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	/**
	 * @return the itemTotalPrice
	 */
	public String getItemTotalPrice() {
		return itemTotalPrice;
	}

	/**
	 * @param itemTotalPrice
	 *            the itemTotalPrice to set
	 */
	public void setItemTotalPrice(String itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	/**
	 * @return the itemShippingChrg
	 */
	public String getItemShippingChrg() {
		return itemShippingChrg;
	}

	/**
	 * @param itemShippingChrg
	 *            the itemShippingChrg to set
	 */
	public void setItemShippingChrg(String itemShippingChrg) {
		this.itemShippingChrg = itemShippingChrg;
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
	 * @return the itemPrice
	 */
	public double getItemPrice() {
		return itemPrice;
	}

	/**
	 * @param itemPrice
	 *            the itemPrice to set
	 */
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	/**
	 * @return the deliveryDate
	 */
	public String getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * @param deliveryDate
	 *            the deliveryDate to set
	 */
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return the quoteProductPricingId
	 */
	public String getQuoteProductPricingId() {
		return quoteProductPricingId;
	}

	/**
	 * @param quoteProductPricingId
	 *            the quoteProductPricingId to set
	 */
	public void setQuoteProductPricingId(String quoteProductPricingId) {
		this.quoteProductPricingId = quoteProductPricingId;
	}

	/**
	 * @return the shared
	 */
	public String getShared() {
		return shared;
	}

	/**
	 * @param shared
	 *            the shared to set
	 */
	public void setShared(String shared) {
		this.shared = shared;
	}
}
