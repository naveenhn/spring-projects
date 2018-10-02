/**
 * 
 */
package com.sarvah.mbg.userprofile.service;

import java.util.Set;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;

/**
 * @author shivu
 *
 */
public interface UserBrandService {

	/**
	 * method for get Provider brands.
	 * 
	 * @param uid
	 * @return
	 */
	ProductBrand getProviderBrands(String uid) throws Exception;

	/**
	 * method for create brand.
	 * 
	 * @param uid
	 * @param brandCreateRequestParam
	 * @return
	 */
	ProductBrand createBrand(String uid, String name, String desc)
			throws Exception;

	/**
	 * method for update product brand.
	 * 
	 * @param uid
	 * @param brandid
	 * @param brandUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	ProductBrand updateBrand(String uid, String brandid, String name,
			String desc, Set<String> subCatIds) throws Exception;

	/**
	 * method for delete brand
	 * 
	 * @param uid
	 * @param brandid
	 * @return
	 * @throws Exception
	 */
	ProductBrand deleteBrand(String uid, String brandid) throws Exception;

}
