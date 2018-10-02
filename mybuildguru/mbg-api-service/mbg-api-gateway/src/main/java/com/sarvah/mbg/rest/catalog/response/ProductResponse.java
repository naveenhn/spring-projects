/**
 * 
 */
package com.sarvah.mbg.rest.catalog.response;

import java.util.List;

import com.sarvah.mbg.catalog.service.model.ProductCatalogDetails;
import com.sarvah.mbg.catalog.service.model.SearchFilters;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author naveen
 *
 */
public class ProductResponse extends AbstractCollectionResponse {
	private List<ProductCatalogDetails> products;
	private SearchFilters filters;
	private String searchKeyword;

	/**
	 * @return the products
	 */
	public List<ProductCatalogDetails> getProducts() {
		return products;
	}

	/**
	 * @param products
	 *            the products to set
	 */
	public void setProducts(List<ProductCatalogDetails> products) {
		this.products = products;
	}

	/**
	 * @return the filters
	 */
	public SearchFilters getFilters() {
		return filters;
	}

	/**
	 * @param filters
	 *            the filters to set
	 */
	public void setFilters(SearchFilters filters) {
		this.filters = filters;
	}

	/**
	 * @return the searchKeyword
	 */
	public String getSearchKeyword() {
		return searchKeyword;
	}

	/**
	 * @param searchKeyword
	 *            the searchKeyword to set
	 */
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
}
