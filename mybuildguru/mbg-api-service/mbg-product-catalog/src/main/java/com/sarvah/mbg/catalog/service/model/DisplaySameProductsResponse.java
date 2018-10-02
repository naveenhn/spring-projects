/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * @author Shivu
 *
 */
public class DisplaySameProductsResponse {
	private Set<Product> products;
	private String varyingAttribute;
	private Set<SimilarProductVariant> variants;

	/**
	 * @return the products
	 */
	public Set<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	/**
	 * @return the varyingAttribute
	 */
	public String getVaryingAttribute() {
		return varyingAttribute;
	}

	/**
	 * @param varyingAttribute
	 *            the varyingAttribute to set
	 */
	public void setVaryingAttribute(String varyingAttribute) {
		this.varyingAttribute = varyingAttribute;
	}

	/**
	 * @return the variants
	 */
	public Set<SimilarProductVariant> getVariants() {
		return variants;
	}

	/**
	 * @param variants
	 *            the variants to set
	 */
	public void setVariants(Set<SimilarProductVariant> variants) {
		this.variants = variants;
	}

}
