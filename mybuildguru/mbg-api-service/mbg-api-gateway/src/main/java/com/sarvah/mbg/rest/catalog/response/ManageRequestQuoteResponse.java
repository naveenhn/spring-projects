/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.catalog.service.model.AdminManageRequestQuote;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author Shivu
 *
 */
public class ManageRequestQuoteResponse extends AbstractCollectionResponse {
	private List<AdminManageRequestQuote> requestQuotes;

	/**
	 * @return the requestQuotes
	 */
	public List<AdminManageRequestQuote> getRequestQuotes() {
		return requestQuotes;
	}

	/**
	 * @param requestQuotes
	 *            the requestQuotes to set
	 */
	public void setRequestQuotes(List<AdminManageRequestQuote> requestQuotes) {
		this.requestQuotes = requestQuotes;
	}
}
