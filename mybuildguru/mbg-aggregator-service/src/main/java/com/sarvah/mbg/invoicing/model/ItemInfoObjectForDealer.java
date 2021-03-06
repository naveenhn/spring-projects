/**
 * 
 */
package com.sarvah.mbg.invoicing.model;

/**
 * @author Shiva
 *
 */
public class ItemInfoObjectForDealer {

	private String name;
	private String qtyType;
	private int qty;
	private String rate;
	private String tax;
	private String amount;
	private String discount;
	private String deliveryCharge;
	private StringBuffer sellerName;

	private String dealerId;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the qtyType
	 */
	public String getQtyType() {
		return qtyType;
	}

	/**
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @return the tax
	 */
	public String getTax() {
		return tax;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @return the deliveryCharge
	 */
	public String getDeliveryCharge() {
		return deliveryCharge;
	}

	/**
	 * @return the dealerId
	 */
	public String getDealerId() {
		return dealerId;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param qtyType
	 *            the qtyType to set
	 */
	public void setQtyType(String qtyType) {
		this.qtyType = qtyType;
	}

	/**
	 * @param qty
	 *            the qty to set
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}

	/**
	 * @param tax
	 *            the tax to set
	 */
	public void setTax(String tax) {
		this.tax = tax;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * @param deliveryCharge
	 *            the deliveryCharge to set
	 */
	public void setDeliveryCharge(String deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	/**
	 * @param dealerId
	 *            the dealerId to set
	 */
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * @return the sellerName
	 */
	public StringBuffer getSellerName() {
		return sellerName;
	}

	/**
	 * @param sellerName
	 *            the sellerName to set
	 */
	public void setSellerName(StringBuffer sellerName) {
		this.sellerName = sellerName;
	}
}