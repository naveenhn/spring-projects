/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

import java.util.Set;

/**
 * @author shivu s
 *
 */
public class ItemStatusUpdateRequestParam {

	private String status;
	private String note;
	private Set<String> itemsId;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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

	/**
	 * @return the itemsId
	 */
	public Set<String> getItemsId() {
		return itemsId;
	}

	/**
	 * @param itemsId the itemsId to set
	 */
	public void setItemsId(Set<String> itemsId) {
		this.itemsId = itemsId;
	}
}
