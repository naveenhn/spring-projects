/**
 * 
 */
package com.sarvah.mbg.domain.mongo.store;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.SubCategory;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.UITemplate;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Store extends AbstractDocument {

	private String storename;
	private Description desc;

	@DBRef
	@NotNull
	@JsonIgnore
	private User user;

	@DBRef
	private UITemplate template;

	@JsonIgnore
	@DBRef
	@NotNull
	private List<Product> products;

	private Set<SubCategory> subcategories;

	private Set<ProductBrand> brands;

	/**
	 * @return the storename
	 */
	public String getStorename() {
		return storename;
	}

	/**
	 * @param storename
	 *            the storename to set
	 */
	public void setStorename(String storename) {
		this.storename = storename;
	}

	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the template
	 */
	public UITemplate getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(UITemplate template) {
		this.template = template;
	}

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * @return the subcategories
	 */
	public Set<SubCategory> getSubcategories() {
		return subcategories;
	}

	/**
	 * @param subcategories
	 *            the subcategories to set
	 */
	public void setSubcategories(Set<SubCategory> subcategories) {
		this.subcategories = subcategories;
	}

	/**
	 * @return the brands
	 */
	public Set<ProductBrand> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(Set<ProductBrand> brands) {
		this.brands = brands;
	}

}
