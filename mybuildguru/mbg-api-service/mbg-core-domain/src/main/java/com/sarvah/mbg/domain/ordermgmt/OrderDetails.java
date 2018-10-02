/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "order_details", schema = "mbgdb")
public class OrderDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_details_id")
	private int orderDetailsId;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "itemid")
	private String itemId;

	@Column(name = "itemname")
	private String itemName;

	@Column(nullable = false)
	private int quantity;

	@Column(nullable = false)
	private double mrp;

	@Column(nullable = false)
	private double tax;

	@Column(name = "sellingprice")
	private double sellingPrice;

	@Column(name = "shippingchrge")
	private double shippingCharge;

	@Column(name = "totalprice")
	private double totalPrice;

	@Column(name = "dealerid")
	private String dealerId;

	@Column(name = "dispatchedby")
	// For logistics purpose
	private String dispatchedBy; // For logistics purpose

	@Column(name = "dispatchid")
	// For logistics purpose
	private String dispatchId; // For logistics purpose

	@ManyToOne
	@JoinColumn(name = "itemstatuscode", nullable = false)
	private ItemStatus itemStatus;

	@Column(name = "createddate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "lastmodifieddate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Column(name="mindeliverydays", nullable = false)
	private int minDeliveryTimeInDays;
	
	@Column(name = "maxdeliverydays", nullable = false)
	private int maxDeliveryTimeInDays;

	@Column(nullable = false)
	private String createdby;

	@Column(nullable = false)
	private String lastmodifiedby;

	/**
	 * @return the orderDetailsId
	 */
	public int getOrderDetailsId() {
		return orderDetailsId;
	}

	/**
	 * @param orderDetailsId
	 *            the orderDetailsId to set
	 */
	public void setOrderDetailsId(int orderDetailsId) {
		this.orderDetailsId = orderDetailsId;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

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
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
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
	 * @return the dispatchedBy
	 */
	public String getDispatchedBy() {
		return dispatchedBy;
	}

	/**
	 * @param dispatchedBy
	 *            the dispatchedBy to set
	 */
	public void setDispatchedBy(String dispatchedBy) {
		this.dispatchedBy = dispatchedBy;
	}

	/**
	 * @return the dispatchId
	 */
	public String getDispatchId() {
		return dispatchId;
	}

	/**
	 * @param dispatchId
	 *            the dispatchId to set
	 */
	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}

	/**
	 * @return the itemStatus
	 */
	public ItemStatus getItemStatus() {
		return itemStatus;
	}

	/**
	 * @param itemStatus
	 *            the itemStatus to set
	 */
	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby
	 *            the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the lastmodifiedby
	 */
	public String getLastmodifiedby() {
		return lastmodifiedby;
	}

	/**
	 * @param lastmodifiedby
	 *            the lastmodifiedby to set
	 */
	public void setLastmodifiedby(String lastmodifiedby) {
		this.lastmodifiedby = lastmodifiedby;
	}

	/**
	 * @return the minDeliveryTimeInDays
	 */
	public int getMinDeliveryTimeInDays() {
		return minDeliveryTimeInDays;
	}

	/**
	 * @param minDeliveryTimeInDays the minDeliveryTimeInDays to set
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
	 * @param maxDeliveryTimeInDays the maxDeliveryTimeInDays to set
	 */
	public void setMaxDeliveryTimeInDays(int maxDeliveryTimeInDays) {
		this.maxDeliveryTimeInDays = maxDeliveryTimeInDays;
	}

}
