/**
 * 
 */
package com.nav.spring.velocity;

import java.util.Map;

/**
 * @author naveen
 *
 */
public class Order {
	
	private String name;
	private String orderId;
	private Double amount;
	private int itemCount;
	private Map<String, String> itemsMap;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the itemCount
	 */
	public int getItemCount() {
		return itemCount;
	}
	/**
	 * @param itemCount the itemCount to set
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	/**
	 * @return the itemsMap
	 */
	public Map<String, String> getItemsMap() {
		return itemsMap;
	}
	/**
	 * @param itemsMap the itemsMap to set
	 */
	public void setItemsMap(Map<String, String> itemsMap) {
		this.itemsMap = itemsMap;
	}
	
	

}
