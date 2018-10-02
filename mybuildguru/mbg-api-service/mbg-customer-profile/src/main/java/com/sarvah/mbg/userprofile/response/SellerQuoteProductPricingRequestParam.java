/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

/**
 * @author Shivu
 *
 */
public class SellerQuoteProductPricingRequestParam {
	private String sellerQuoteProductPricingId;
	private String sellerId;
	private Double mrp;
	private Double sellingPrice;
	private Double buildOnnSellingPrice;
	private Double shippingCharge;
	private Double discount;
	private Double tax;
	private Double extraBenefits;

	/**
	 * @return the sellerQuoteProductPricingId
	 */
	public String getSellerQuoteProductPricingId() {
		return sellerQuoteProductPricingId;
	}

	/**
	 * @param sellerQuoteProductPricingId
	 *            the sellerQuoteProductPricingId to set
	 */
	public void setSellerQuoteProductPricingId(
			String sellerQuoteProductPricingId) {
		this.sellerQuoteProductPricingId = sellerQuoteProductPricingId;
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

}
