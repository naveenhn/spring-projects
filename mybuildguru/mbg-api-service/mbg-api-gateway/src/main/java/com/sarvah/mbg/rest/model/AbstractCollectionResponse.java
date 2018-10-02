/**
 * 
 */
package com.sarvah.mbg.rest.model;


/**
 * @author naveen
 *
 */
public abstract class AbstractCollectionResponse {
	
	private Page page;

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Page page) {
		this.page = page;
	}
	

}
