/**
 * 
 */
package com.sarvah.mbg.config.populator;

import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.sarvah.mbg.catalog.dao.mongo.CategoryDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductQuantityTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.ProductTypeDAO;
import com.sarvah.mbg.catalog.dao.mongo.SearchKeywordStoreDAO;
import com.sarvah.mbg.catalog.dao.mongo.SubCategoryDAO;
import com.sarvah.mbg.notification.dao.mongo.NotificationChangeEventDAO;
import com.sarvah.mbg.notification.dao.mongo.NotificationDAO;
import com.sarvah.mbg.project.dao.mongo.BidsDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectDAO;
import com.sarvah.mbg.recentlyviewed.dao.mongo.RecentlyViewedDAO;
import com.sarvah.mbg.reviews.dao.mongo.CommentDAO;
import com.sarvah.mbg.store.dao.mongo.StoreDAO;
import com.sarvah.mbg.userprofile.dao.mongo.ActionDAO;
import com.sarvah.mbg.userprofile.dao.mongo.BannerUITemplateDAO;
import com.sarvah.mbg.userprofile.dao.mongo.BrandDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;
import com.sarvah.mbg.userprofile.dao.mongo.ProductPromotionDAO;
import com.sarvah.mbg.userprofile.dao.mongo.PromotionBannerDAO;
import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
import com.sarvah.mbg.userprofile.dao.mongo.StoreProductPricingDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserBidDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectProfileDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserRelatedDocumentsDAO;
import com.sarvah.mbg.userprofile.dao.mongo.WishListDAO;
import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;

/**
 * @author naveen
 *
 */
public class PopulatorBean {
	@Autowired
	UserDAO userDAO;

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	ProductDAO productDAO;

	@Autowired
	SubCategoryDAO subCategoryDAO;

	@Autowired
	ProductTypeDAO productTypeDAO;

	@Autowired
	RecentlyViewedDAO recentlyViewedDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	UserPackageDAO userPackageDAO;

	@Autowired
	PrivilegeDAO privilegeDAO;

	@Autowired
	ActionDAO actionDAO;

	@Autowired
	WishListDAO wishListDAO;

	@Autowired
	UserProjectDAO userProjectDAO;

	@Autowired
	UserBidDAO userBidDAO;

	@Autowired
	ProjectDAO projectDAO;

	@Autowired
	BidsDAO bidsDAO;

	@Autowired
	ProductPromotionDAO productPromotionDAO;

	@Autowired
	PromotionBannerDAO promotionBannerDAO;

	@Autowired
	StoreDAO productStoreDAO;

	@Autowired
	BannerUITemplateDAO uiTemplateDAO;

	@Autowired
	CommentDAO commentDAO;

	// @Autowired
	// EmailAuditTypeRepository emailAuditTypeRepository;

	@Autowired
	BrandDAO brandDAO;

	@Autowired
	UserProjectProfileDAO userProjectProfileDAO;

	@Autowired
	NotificationDAO notificationDAO;

	@Autowired
	ProductQuantityTypeDAO productQuantityTypeDAO;

	@Autowired
	UserRelatedDocumentsDAO userRelatedDocumentsDAO;

	@Autowired
	private SearchKeywordStoreDAO searchKeywordStoreDAO;

	@Autowired
	private NotificationChangeEventDAO notificationChangeEventDAO;

	@Autowired
	private StoreProductPricingDAO storeProductPricingDAO;

	@PostConstruct
	public void init() throws ParseException {
		// initPopulator();
	}

	public void initPopulator() throws ParseException {
		ActionPopulatorBean actionPopulatorBean = new ActionPopulatorBean(
				actionDAO);
		actionPopulatorBean.initAction();

		// EmailAuditPopulatorBean emailAuditPopulatorBean = new
		// EmailAuditPopulatorBean(
		// emailAuditTypeRepository);
		// emailAuditPopulatorBean.initEmailPopulatorBean();

		PrivilegePopulatorBean privilegePopulatorBean = new PrivilegePopulatorBean(
				privilegeDAO, actionDAO);
		privilegePopulatorBean.initPrivileges();

		UserPackagePopulatorBean userPackagePopulatorBean = new UserPackagePopulatorBean(
				userPackageDAO, privilegeDAO);
		userPackagePopulatorBean.initUserPackage();

		RolePopulatorBean rolePopulatorBean = new RolePopulatorBean(roleDAO,
				userPackageDAO);
		rolePopulatorBean.initRoles();

		UserPopulatorBean userPopulatorBean = new UserPopulatorBean(userDAO,
				roleDAO);
		userPopulatorBean.initUsers();

		CategoryPopulatorBean categoryPopulatorBean = new CategoryPopulatorBean(
				categoryDAO);
		categoryPopulatorBean.initCategories();

		// SubCategoryPopulatorBean subCategoryPopulatorBean = new
		// SubCategoryPopulatorBean(
		// subCategoryDAO, categoryDAO);
		// subCategoryPopulatorBean.initSubCategories();

		ProductTypePopulatorBean productTypePopulatorBean = new ProductTypePopulatorBean(
				productTypeDAO, subCategoryDAO);
		productTypePopulatorBean.initProdTypes();

		BrandPopulatorBean brandPopulatorBean = new BrandPopulatorBean(
				brandDAO, userDAO);
		brandPopulatorBean.initBrands();

		ProductQuantityTypePopulatorBean productQuantityTypePopulatorBean = new ProductQuantityTypePopulatorBean(
				productQuantityTypeDAO);
		productQuantityTypePopulatorBean.initProductQuantityType();

		ProductPopulatorBean productBean = new ProductPopulatorBean(productDAO,
				categoryDAO, subCategoryDAO, productTypeDAO, userDAO,
				recentlyViewedDAO, roleDAO, userPackageDAO, brandDAO,
				productQuantityTypeDAO);
		productBean.initProducts();

		RecentlyViewedPopulatorBean recentlyViewedPopulatorBean = new RecentlyViewedPopulatorBean(
				recentlyViewedDAO, userDAO, productDAO);
		recentlyViewedPopulatorBean.initRecentlyViewed();

		WishListPopulatorBean wishListPopulatorBean = new WishListPopulatorBean(
				wishListDAO, userDAO, productDAO);
		wishListPopulatorBean.initWistList();

		ProjectPopulatorBean projectPopulatorBean = new ProjectPopulatorBean(
				userProjectDAO, userDAO);
		projectPopulatorBean.initProject();

		BidsPopulatorBean bidPopulatorBean = new BidsPopulatorBean(userBidDAO,
				userDAO, userProjectDAO);
		bidPopulatorBean.initBid();

		PromotionPopulatorBean promotionPopulatorBean = new PromotionPopulatorBean(
				userDAO, productDAO, productPromotionDAO, subCategoryDAO);
		promotionPopulatorBean.initPromotion();

		UITemplatePopulatorBean uitemplatePopulatorBean = new UITemplatePopulatorBean(
				uiTemplateDAO);
		uitemplatePopulatorBean.initUITemplate();

		StorePopulatorBean storePopulatorBean = new StorePopulatorBean(
				productStoreDAO, userDAO, uiTemplateDAO, productDAO);
		storePopulatorBean.initStore();

		StoreProductPricingPopulatorBean StoreProductPricingPopulatorBean = new StoreProductPricingPopulatorBean(
				storeProductPricingDAO, productDAO, productStoreDAO);
		StoreProductPricingPopulatorBean.initStoreProductPricing();

		BannerPopulatorBean bannerPopulatorBean = new BannerPopulatorBean(
				promotionBannerDAO, userDAO, productStoreDAO);
		bannerPopulatorBean.initBanner();

		CommentPopulatorBean commentPopulatorBean = new CommentPopulatorBean(
				userDAO, productDAO, commentDAO);
		commentPopulatorBean.initComment();

		UserProjectProfilePopulatorBean userProjectProfilePopulatorBean = new UserProjectProfilePopulatorBean(
				userDAO, userProjectProfileDAO);
		userProjectProfilePopulatorBean.initUserProjectProfile();

		NotificationPopulatorBean notificationPopulatorBean = new NotificationPopulatorBean(
				notificationDAO, userDAO);
		notificationPopulatorBean.initNotification();

		UserRelatedDocumentsPopulatorBean userRelatedDocumentsPopulatorBean = new UserRelatedDocumentsPopulatorBean(
				userRelatedDocumentsDAO);
		userRelatedDocumentsPopulatorBean.initUserRelatedDocuments();

		SearchKeywordStorePopulatorBean searchKeywordStorePopulatorBean = new SearchKeywordStorePopulatorBean(
				searchKeywordStoreDAO);
		searchKeywordStorePopulatorBean.initSearchValues();

		NotificationChangeEventPopulatorBean notificationChangeEventPopulatorBean = new NotificationChangeEventPopulatorBean(
				notificationChangeEventDAO);
		notificationChangeEventPopulatorBean.initNotificationChangeEvent();
	}
}