/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

/**
 * @author Shivu
 *
 */
public class QuoteProductPricingResponse {
	private String quoteProductPricingId;
	private String sellerId;
	private Double mrp;
	private Double sellingPrice;
	private Double buildOnnSellingPrice;
	private Double shippingCharge;
	private Double discount;
	private Double tax;
	private Double totalPrice;
	private Double vatAmount;
	private Double extraBenefits;
	private boolean shared;

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
	 * @return the sellerId
	 */
	public String getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId
	 *            the sellerId to set
	 */
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the mrp
	 */
	public Double getMrp() {
		return mrp;
	}

	/**
	 * @param mrp
	 *            the mrp to set
	 */
	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	/**
	 * @return the sellingPrice
	 */
	public Double getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * @param sellingPrice
	 *            the sellingPrice to set
	 */
	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * @return the buildOnnSellingPrice
	 */
	public Double getBuildOnnSellingPrice() {
		return buildOnnSellingPrice;
	}

	/**
	 * @param buildOnnSellingPrice
	 *            the buildOnnSellingPrice to set
	 */
	public void setBuildOnnSellingPrice(Double buildOnnSellingPrice) {
		this.buildOnnSellingPrice = buildOnnSellingPrice;
	}

	/**
	 * @return the shippingCharge
	 */
	public Double getShippingCharge() {
		return shippingCharge;
	}

	/**
	 * @param shippingCharge
	 *            the shippingCharge to set
	 */
	public void setShippingCharge(Double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(Double vatAmount) {
		this.vatAmount = vatAmount;
	}

	/**
	 * @return the extraBenefits
	 */
	public Double getExtraBenefits() {
		return extraBenefits;
	}

	/**
	 * @param extraBenefits
	 *            the extraBenefits to set
	 */
	public void setExtraBenefits(Double extraBenefits) {
		this.extraBenefits = extraBenefits;
	}

	/**
	 * @return the shared
	 */
	public boolean isShared() {
		return shared;
	}

	/**
	 * @param shared
	 *            the shared to set
	 */
	public void setShared(boolean shared) {
		this.shared = shared;
	}
}
