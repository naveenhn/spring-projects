/**
 * 
 */
package com.sarvah.mbg.rest.catalog.model;

import java.util.Set;

/**
 * @author shivu s
 *
 */
public class ProductLinkOrUnLinkResponse {
private Set<String> prodIds;

/**
 * @return the prodIds
 */
public Set<String> getProdIds() {
	return prodIds;
}

/**
 * @param prodIds the prodIds to set
 */
public void setProdIds(Set<String> prodIds) {
	this.prodIds = prodIds;
}

}
