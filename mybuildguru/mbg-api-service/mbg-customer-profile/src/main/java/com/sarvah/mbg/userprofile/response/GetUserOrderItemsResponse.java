/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Date;
import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductAsset;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.ordermgmt.ItemStatus;

/**
 * @author shivu
 *
 */
public class GetUserOrderItemsResponse {
	private int itemId;
	private String pid;
	private String itemName;
	private int quantity;
	private double mrp;
	private double tax;
	private double sellingPrice;
	private double shippingCharge;
	private double totalPrice;
	private double taxPrice;
	private String dealerId;
	private ItemStatus itemStatus;
	private String createBy;
	private String lastModifiedBy;
	private Date createdDate;
	private Date lastmodifiedDate;
	private ProductAsset productAsset;
	private int estimatedDeliveryDate;
	private String sellName;
	private String note;
	private Set<SubCategory> subCategories;

	// promotion
	private String promotionName;
	private String promoCode;
	private double discountedPrice;
	private double discount;

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
	 * @return the lastmodifiedDate
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * @param lastmodifiedDate
	 *            the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	public ProductAsset getProductAsset() {
		return productAsset;
	}

	public void setProductAsset(ProductAsset productAsset) {
		this.productAsset = productAsset;
	}

	/**
	 * @return the estimatedDeliveryDate
	 */
	public int getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	/**
	 * @param estimatedDeliveryDate
	 *            the estimatedDeliveryDate to set
	 */
	public void setEstimatedDeliveryDate(int estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	/**
	 * @return the sellName
	 */
	public String getSellName() {
		return sellName;
	}

	/**
	 * @param sellName
	 *            the sellName to set
	 */
	public void setSellName(String sellName) {
		this.sellName = sellName;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the promotionName
	 */
	public String getPromotionName() {
		return promotionName;
	}

	/**
	 * @param promotionName
	 *            the promotionName to set
	 */
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	/**
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode
	 *            the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	/**
	 * @return the discountedPrice
	 */
	public double getDiscountedPrice() {
		return discountedPrice;
	}

	/**
	 * @param discountedPrice
	 *            the discountedPrice to set
	 */
	public void setDiscountedPrice(double discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(double taxPrice) {
		this.taxPrice = taxPrice;
	}

	/**
	 * @return the subCategories
	 */
	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	/**
	 * @param subCategories
	 *            the subCategories to set
	 */
	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}
}
