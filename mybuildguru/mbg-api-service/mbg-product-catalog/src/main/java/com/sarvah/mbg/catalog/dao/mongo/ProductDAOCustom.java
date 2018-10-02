/**
 * 
 */
package com.sarvah.mbg.catalog.dao.mongo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sarvah.mbg.catalog.service.model.ProductCustomSearch;
import com.sarvah.mbg.domain.mongo.catalog.Product;

/**
 * The Interface ProductDAOCustom.
 *
 * @author naveen
 */
public interface ProductDAOCustom {

	/**
	 * Search products.
	 *
	 * @param productCustomSearch
	 *            the product custom search
	 * @return the list
	 */
	List<Product> searchProducts(ProductCustomSearch productCustomSearch);

	/**
	 * Search products.
	 *
	 * @param productCustomSearch
	 *            the product custom search
	 * @param pageable
	 *            the pageable
	 * @return the page
	 */
	Page<Product> searchProducts(ProductCustomSearch productCustomSearch,
			Pageable pageable);

	/**
	 * Count.
	 *
	 * @param productCustomSearch
	 *            the product custom search
	 * @return the long
	 */
	long count(ProductCustomSearch productCustomSearch);

	List<Product> filterProducts(List<Product> products);

}
