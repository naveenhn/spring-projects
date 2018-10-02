/**
 * 
 */
package com.sarvah.mbg.userprofile.response;

import com.sarvah.mbg.domain.ordermgmt.ItemStatus;

/**
 * @author shivu s
 *
 */
public class ItemUpdateResponse {
	
	private ItemStatus itemStatus;

	public ItemStatus getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}
}
