/**
 * 
 */
package com.sarvah.mbg.domain.mongo.store;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sarvah.mbg.domain.mongo.catalog.ProductQuantityType;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

// TODO: Auto-generated Javadoc
/**
 * The Class StoreProductPricing.
 *
 * @author naveen
 */
@Document
public class StoreProductPricing extends AbstractDocument{

	/** The store product id. */
	/* id of this document should be set in combination with productId,skuid and storeid
	 * "mrp": "2400", "selling_price": "2300", "listing_status": "INACTIVE",
	 * "fulfilled_by": "seller", "national_shipping_charge": "20",
	 * "zonal_shipping_charge": "20", "local_shipping_charge": "20",
	 * "procurement_sla": "3", "stock_count": "23"
	 */
	@Indexed
	private String storeProductId;

	/** The product id. */
	private String productId;
	
	/** The sku id. */
	private String skuId;
	
	/** The store id. */
	private String storeId;
	
//	private ProductQuantityType ProductQuantityType;

	/** The max retail price. */
	@JsonProperty("mrp")
	private Double maxRetailPrice;

	/** The selling price. */
	@JsonProperty("selling_price")
	private Double sellingPrice;

	/** The listing status. */
	@JsonProperty("listing_status")
	private String listingStatus;

	/** The fulfilled by. */
	@JsonProperty("fullfilled_by")
	private String fulfilledBy;


	/** The stock count. */
	@JsonProperty("stock_count")
	private Integer stockCount;

	/** The min quantity buy. */
	@JsonProperty("min_quantity_buy")
	private Integer minQuantityBuy;

	/** The tax. */
	private Double tax; // inclusive tax I presume	

	/** The mbg share. */
	private Double mbgShare;

	/** The discount. */
	private Double discount;

	/** The store delivery. */
	private boolean storeDelivery ;
	
	/** The sale. */
	private Sale sale;
	
	/** The shipping. */
	private Shipping shipping;
	
	
	/**
	 * Instantiates a new store product pricing.
	 *
	 * @param storeId the store id
	 * @param productId the product id
	 * @param skuId the sku id
	 */
	public StoreProductPricing(String storeId, String productId, String skuId) {
		setProductId(productId);
		setSkuId(skuId);
		setStoreId(storeId);
		String combinedId = storeId + productId + skuId;
		setStoreProductId(combinedId);		
	}
	

	/**
	 * Gets the product id.
	 *
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId            the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * Gets the discount.
	 *
	 * @return the discount
	 */
	public Double getDiscount() {
		return discount;
	}

	/**
	 * Sets the discount.
	 *
	 * @param discount the new discount
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	

	/**
	 * Gets the selling price.
	 *
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * Sets the selling price.
	 *
	 * @param sellingPrice            the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * Gets the listing status.
	 *
	 * @return the listingStatus
	 */
	public String getListingStatus() {
		return listingStatus;
	}

	/**
	 * Sets the listing status.
	 *
	 * @param listingStatus            the listingStatus to set
	 */
	public void setListingStatus(String listingStatus) {
		this.listingStatus = listingStatus;
	}

	/**
	 * Gets the fulfilled by.
	 *
	 * @return the fulfilledBy
	 */
	public String getFulfilledBy() {
		return fulfilledBy;
	}

	/**
	 * Sets the fulfilled by.
	 *
	 * @param fulfilledBy            the fulfilledBy to set
	 */
	public void setFulfilledBy(String fulfilledBy) {
		this.fulfilledBy = fulfilledBy;
	}

	
	/**
	 * Gets the stock count.
	 *
	 * @return the stockCount
	 */
	public Integer getStockCount() {
		return stockCount;
	}

	/**
	 * Sets the stock count.
	 *
	 * @param stockCount            the stockCount to set
	 */
	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	/**
	 * Gets the max retail price.
	 *
	 * @return the maxRetailPrice
	 */
	public Double getMaxRetailPrice() {
		return maxRetailPrice;
	}

	/**
	 * Sets the max retail price.
	 *
	 * @param maxRetailPrice            the maxRetailPrice to set
	 */
	public void setMaxRetailPrice(Double maxRetailPrice) {
		this.maxRetailPrice = maxRetailPrice;
	}

	/**
	 * Gets the tax.
	 *
	 * @return the tax
	 */
	public Double getTax() {
		return tax;
	}

	/**
	 * Sets the tax.
	 *
	 * @param tax            the tax to set
	 */
	public void setTax(Double tax) {
		this.tax = tax;
	}

	/**
	 * Gets the min quantity buy.
	 *
	 * @return the minQuantityBuy
	 */
	public Integer getMinQuantityBuy() {
		return minQuantityBuy;
	}

	/**
	 * Sets the min quantity buy.
	 *
	 * @param minQuantityBuy            the minQuantityBuy to set
	 */
	public void setMinQuantityBuy(Integer minQuantityBuy) {
		this.minQuantityBuy = minQuantityBuy;
	}

		/**
		 * Gets the mbg share.
		 *
		 * @return the mbgShare
		 */
	public Double getMbgShare() {
		return mbgShare;
	}

	/**
	 * Sets the mbg share.
	 *
	 * @param mbgShare            the mbgShare to set
	 */
	public void setMbgShare(Double mbgShare) {
		this.mbgShare = mbgShare;
	}


	/**
	 * Gets the sku id.
	 *
	 * @return the skuId
	 */
	public String getSkuId() {
		return skuId;
	}

	/**
	 * Sets the sku id.
	 *
	 * @param skuId the skuId to set
	 */
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	/**
	 * Gets the store id.
	 *
	 * @return the storeId
	 */
	public String getStoreId() {
		return storeId;
	}

	/**
	 * Sets the store id.
	 *
	 * @param storeId the storeId to set
	 */
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	/**
	 * Checks if is store delivery.
	 *
	 * @return the storeDelivery
	 */
	public boolean isStoreDelivery() {
		return storeDelivery;
	}

	/**
	 * Sets the store delivery.
	 *
	 * @param storeDelivery the storeDelivery to set
	 */
	public void setStoreDelivery(boolean storeDelivery) {
		this.storeDelivery = storeDelivery;
	}


	/**
	 * Gets the store product id.
	 *
	 * @return the storeProductId
	 */
	public String getStoreProductId() {
		return storeProductId;
	}


	/**
	 * Sets the store product id.
	 *
	 * @param storeProductId the storeProductId to set
	 */
	public void setStoreProductId(String storeProductId) {
		this.storeProductId = storeProductId;
	}


	/**
	 * Gets the sale.
	 *
	 * @return the sale
	 */
	public Sale getSale() {
		return sale;
	}


	/**
	 * Sets the sale.
	 *
	 * @param sale the sale to set
	 */
	public void setSale(Sale sale) {
		this.sale = sale;
	}


	/**
	 * Gets the shipping.
	 *
	 * @return the shipping
	 */
	public Shipping getShipping() {
		return shipping;
	}


	/**
	 * Sets the shipping.
	 *
	 * @param shipping the shipping to set
	 */
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}


//	/**
//	 * @return the productQuantityType
//	 */
//	public ProductQuantityType getProductQuantityType() {
//		return ProductQuantityType;
//	}
//
//
//	/**
//	 * @param productQuantityType the productQuantityType to set
//	 */
//	public void setProductQuantityType(ProductQuantityType productQuantityType) {
//		ProductQuantityType = productQuantityType;
//	}
}