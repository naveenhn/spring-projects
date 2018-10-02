/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class SellerQuoteProductPricing extends AbstractDocument {
	@DBRef
	@NotNull
	@JsonIgnore
	private User seller;
	private Double mrp;
	private Double sellingPrice;
	private Double buildOnnSellingPrice;
	private Double shippingCharge;
	private Double discount;
	private Double tax;
	private Double extraBenefits;
	private boolean shared;

	/**
	 * @return the seller
	 */
	public User getSeller() {
		return seller;
	}

	/**
	 * @param seller
	 *            the seller to set
	 */
	public void setSeller(User seller) {
		this.seller = seller;
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
