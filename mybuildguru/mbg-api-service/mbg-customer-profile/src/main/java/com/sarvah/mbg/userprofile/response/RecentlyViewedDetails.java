/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.SubCategory;

/**
 * @author Shivu
 *
 */
public class RecentlyViewedDetails {
	private String id;
	private String name;
	private String image;
	private Set<SubCategory> subcategories;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
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
}
