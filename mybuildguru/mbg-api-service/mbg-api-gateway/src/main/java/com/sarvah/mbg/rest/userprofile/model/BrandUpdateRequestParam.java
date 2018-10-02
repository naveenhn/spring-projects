/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import java.util.Set;

/**
 * @author shivu
 *
 */
public class BrandUpdateRequestParam {
	private String name;
	private String desc;
	private Set<String> subCatIds;

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
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the subCatIds
	 */
	public Set<String> getSubCatIds() {
		return subCatIds;
	}

	/**
	 * @param subCatIds
	 *            the subCatIds to set
	 */
	public void setSubCatIds(Set<String> subCatIds) {
		this.subCatIds = subCatIds;
	}
}
