/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

/**
 * @author shivu
 *
 */
public class CustBoughtResponse {
private List<ProductCustBought> productCustBoughtList;

/**
 * @return the productCustBoughtList
 */
public List<ProductCustBought> getProductCustBoughtList() {
	return productCustBoughtList;
}

/**
 * @param productCustBoughtList the productCustBoughtList to set
 */
public void setProductCustBoughtList(
		List<ProductCustBought> productCustBoughtList) {
	this.productCustBoughtList = productCustBoughtList;
}

}
