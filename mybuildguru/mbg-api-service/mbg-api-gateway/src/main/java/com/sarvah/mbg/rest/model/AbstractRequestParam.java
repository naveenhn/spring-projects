/**
 * 
 */
package com.sarvah.mbg.rest.model;

import javax.ws.rs.QueryParam;

/**
 * @author shivu s
 *
 */
public class AbstractRequestParam {
	
	@QueryParam("page")
	private int page;
	
	@QueryParam("size")
	private int size;
	
	@QueryParam("sort")
	private String sort;
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	

}
