/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.service.UserBrandService;

/**
 * @author shivu
 *
 */
@Service
public class UserBrandServiceImpl implements UserBrandService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private BrandDAO brandDAO;

	/**
	 * method for get Provider brands.
	 * 
	 * @param uid
	 * @return
	 */
	@Override
	public ProductBrand getProviderBrands(String uid) throws Exception {
		ProductBrand brand = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				brand = brandDAO.findByProvider(user);
			} else {
				logger.info("user is null");
				throw new Exception("user is null");
			}
		} else {
			logger.info("user id should not empty");
			throw new Exception("user id should not empty");
		}
		return brand;
	}

	/**
	 * method for create brand.
	 * 
	 * @param uid
	 * @param brandCreateRequestParam
	 * @return
	 */
	@Override
	public ProductBrand createBrand(String uid, String name, String desc)
			throws Exception {
		ProductBrand productBrand = null;
		if (StringUtils.isNotBlank(uid)) {
			User provider = userDAO.findOne(uid);
			if (provider != null) {
				productBrand = new ProductBrand();
				productBrand.setName(name);
				Description desc1 = new Description();
				desc1.setLang("eng");
				desc1.setVal(desc);
				productBrand.setDesc(desc1);
				productBrand.setProvider(provider);
				brandDAO.insert(productBrand);
			} else {
				logger.info("provider is null");
				throw new Exception("provider is null");
			}
		} else {
			logger.info("user id should not be empty");
			throw new Exception("user id should not be empty");
		}
		return productBrand;
	}

	/**
	 * method for update product brand.
	 * 
	 * @param uid
	 * @param brandid
	 * @param brandUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductBrand updateBrand(String uid, String brandid, String name,
			String desc, Set<String> subCatIds) throws Exception {
		ProductBrand productBrand = null;
		if (StringUtils.isNotBlank(uid)) {
			User provider = userDAO.findOne(uid);
			if (provider != null) {
				productBrand = brandDAO.findOne(brandid);
				if (StringUtils.isNotBlank(name)) {
					productBrand.setName(name);
				}
				if (StringUtils.isNotBlank(desc)) {
					productBrand.getDesc().setVal(desc);
				}
				if (subCatIds != null && subCatIds.size() != 0) {
					productBrand.setSubCategoryIds(subCatIds);
				}
			} else {
				logger.info("provider is null");
				throw new Exception("provider is null");
			}
		} else {
			logger.info("user id should not be null");
			throw new Exception("user id should not be null");
		}

		brandDAO.save(productBrand);
		return productBrand;
	}

	/**
	 * method for delete brand
	 * 
	 * @param uid
	 * @param brandid
	 * @return
	 * @throws Exception
	 */
	@Override
	public ProductBrand deleteBrand(String uid, String brandid)
			throws Exception {
		ProductBrand productBrand = null;
		if (StringUtils.isNotBlank(uid)) {
			User provider = userDAO.findOne(uid);
			if (provider != null) {
				productBrand = brandDAO.findOne(brandid);
				if (productBrand != null) {
					brandDAO.delete(productBrand);
				} else {
					logger.info("product brand is null");
					throw new Exception("product brand is null");
				}
			} else {
				logger.info("provider is null");
				throw new Exception("provider brand is null");
			}
		} else {
			logger.info("user id should not be null");
			throw new Exception("user id should not be null");
		}
		return productBrand;
	}
}
