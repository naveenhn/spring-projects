/**
 * 
 */
package com.sarvah.mbg.catalog.service.model;

import java.util.List;

/**
 * @author shivu
 *
 */
public class CustLikedResponse {
private List<ProductCustLiked> productCustLikedList;

/**
 * @return the productCustLikedList
 */
public List<ProductCustLiked> getProductCustLikedList() {
	return productCustLikedList;
}

/**
 * @param productCustLikedList the productCustLikedList to set
 */
public void setProductCustLikedList(List<ProductCustLiked> productCustLikedList) {
	this.productCustLikedList = productCustLikedList;
}


}
