/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author Shivu
 *
 */
@Document
public class ProductPricing extends AbstractDocument {
	@Indexed(unique = true)
	@NotNull
	@DBRef
	private Product product;

	@NotNull
	private Set<ProductPrice> productPrices;

	private String note;

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the productPrices
	 */
	public Set<ProductPrice> getProductPrices() {
		return productPrices;
	}

	/**
	 * @param productPrices
	 *            the productPrices to set
	 */
	public void setProductPrices(Set<ProductPrice> productPrices) {
		this.productPrices = productPrices;
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
}
