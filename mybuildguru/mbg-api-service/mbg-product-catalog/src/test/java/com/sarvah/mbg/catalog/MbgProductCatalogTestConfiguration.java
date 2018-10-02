/**
 * 
 */
package com.sarvah.mbg.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductCommentDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.PromotionDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.catalog.service.ProductCatalogService;
import com.sarvah.mbg.catalog.service.ProductCategoryService;
import com.sarvah.mbg.catalog.service.impl.ProductCatalogServiceImpl;
import com.sarvah.mbg.catalog.service.impl.ProductCategoryServiceImpl;
import com.sarvah.mbg.commons.storage.LocalFileStorage;
import com.sarvah.mbg.promotion.service.PromotionService;
import com.sarvah.mbg.promotion.service.impl.PromotionServiceImpl;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;

/**
 * @author shivu s
 *
 */
@Configuration
public class MbgProductCatalogTestConfiguration {

	@Bean
	public ProductDAO productDAO() {
		return new DummyProductDAO();
	}

	@Bean
	public ProductCatalogService productCatalogService() {
		return new ProductCatalogServiceImpl();
	}

	@Bean
	public PromotionService promotionService() {
		return new PromotionServiceImpl();
	}

	@Bean
	public ProductCategoryService productCategoryService() {
		return new ProductCategoryServiceImpl();
	}

	@Bean
	public PromotionDAO promotionDAO() {
		return new DummyPromotionDAO();
	}

	@Bean
	public UserDAO userDAO() {
		return new DummyProductUserDAO();
	}

	@Bean
	public SubCategoryDAO subCategoryDAO() {
		return new DummySubCategoryDAO();
	}

	@Bean
	public CategoryDAO categoryDAO() {
		return new DummyCategoryDAO();
	}

	@Bean
	public ProductTypeDAO productTypeDAO() {
		return new DummyProductTypeDAO();
	}

	@Bean
	public StoreDAO storeDAO() {
		return new DummyStoreDAO();
	}

	@Bean
	public ProductCommentDAO productCommentDAO() {
		return new DummyProductCommentsDAO();
	}
	
	@Bean
	public LocalFileStorage localFileStorage(){
		return new LocalFileStorage();
	}

}
