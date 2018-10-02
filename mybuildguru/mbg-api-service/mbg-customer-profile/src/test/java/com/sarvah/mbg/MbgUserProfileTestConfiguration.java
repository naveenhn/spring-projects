///**
// * 
// */
//package com.sarvah.mbg;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.sarvah.mbg.banners.service.BannerService;
//import com.sarvah.mbg.banners.service.impl.BannerServiceImpl;
//import com.sarvah.mbg.common.communication.UserCommunicationService;
//import com.sarvah.mbg.common.communication.UserCommunicationServiceImpl;
//import com.sarvah.mbg.commons.email.MailSenderService;
//import com.sarvah.mbg.commons.sms.SmsSenderService;
//import com.sarvah.mbg.email.DummyAzureQueue;
//import com.sarvah.mbg.email.DummyMailSenderImpl;
//import com.sarvah.mbg.email.DummySmsSenderServiceImpl;
//import com.sarvah.mbg.privilege.dao.mongo.PrivilegePromotionDAO;
//import com.sarvah.mbg.privilege.service.PrivilegeService;
//import com.sarvah.mbg.privilege.service.impl.PrivilegeServiceImpl;
//import com.sarvah.mbg.recentlyviewed.dao.mongo.DummyRecentlyViewedDAO;
//import com.sarvah.mbg.recentlyviewed.dao.mongo.RecentlyViewedDAO;
//import com.sarvah.mbg.store.dao.mongo.StoreDAO;
//import com.sarvah.mbg.userprofile.dao.jpa.UserOrderAddressRepository;
//import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;
//import com.sarvah.mbg.userprofile.dao.jpa.UserOrderStatusRepository;
//import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
//import com.sarvah.mbg.userprofile.dao.jpa.UserShippingTypeRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyBannerDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyPrivilegeDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyPrivilegePromotionDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyProductPromotionDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyRoleDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyStoreDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserBidDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserOrderAddressRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserOrderRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserOrderStatusRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserPackageDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserProjectDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyUserShippingTypeRepository;
//import com.sarvah.mbg.userprofile.dao.mongo.DummyWishListDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.PrivilegeDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.ProductPromotionDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.ProductStoreDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.PromotionBannerDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.RoleDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.UserBidDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
//import com.sarvah.mbg.userprofile.dao.mongo.WishListDAO;
//import com.sarvah.mbg.userprofile.dao.role.mongo.UserPackageDAO;
//import com.sarvah.mbg.userprofile.service.UserService;
//import com.sarvah.mbg.userprofile.service.impl.UserServiceImpl;
//import com.sarvah.mbg.userprofile.service.impl.UserValidation;
//
///**
// * @author shivu s
// *
// */
//@Configuration
//public class MbgUserProfileTestConfiguration {
//
//	@Bean
//	public MailSenderService mailSenderService() {
//		return new DummyMailSenderImpl();
//	}
//
//	@Bean
//	public BannerService bannerService() {
//		return new BannerServiceImpl();
//	}
//
//	@Bean
//	public SmsSenderService smsSenderService() {
//		return new DummySmsSenderServiceImpl();
//	}
//
//	@Bean
//	public PrivilegeService privilegeService() {
//		return new PrivilegeServiceImpl();
//	}
//
//	@Bean
//	public PrivilegePromotionDAO privilegePromotionDAO() {
//		return new DummyPrivilegePromotionDAO();
//	}
//
//	@Bean
//	public UserShippingTypeRepository userShippingTypeRepository() {
//		return new DummyUserShippingTypeRepository();
//	}
//
//	@Bean
//	public UserRepository userRepository() {
//		return new DummyUserRepository();
//	}
//
//	@Bean
//	public UserOrderStatusRepository userOrderStatusRepository() {
//		return new DummyUserOrderStatusRepository();
//	}
//
//	@Bean
//	public UserOrderAddressRepository userOrderAddressRepository() {
//		return new DummyUserOrderAddressRepository();
//	}
//
//	@Bean
//	public UserOrderRepository userOrderRepository() {
//		return new DummyUserOrderRepository();
//	}
//
//	@Bean
//	public UserCommunicationService userCommunicationService() {
//		return new UserCommunicationServiceImpl();
//	}
//
//	@Bean
//	public DummyAzureQueue queue() {
//		return new DummyAzureQueue();
//	}
//
//	@Bean
//	public UserValidation userValidation() {
//		return new UserValidation();
//	}
//
//	@Bean
//	public UserDAO userDAO() {
//		return new DummyUserDAO();
//	}
//
//	@Bean
//	public UserService userService() {
//		return new UserServiceImpl();
//	}
//
//	@Bean
//	public RecentlyViewedDAO recentlyViewedDAO() {
//		return new DummyRecentlyViewedDAO();
//	}
//
//	@Bean
//	public RoleDAO roleDAO() {
//		return new DummyRoleDAO();
//	}
//
//	@Bean
//	public UserProjectDAO userProjectDAO() {
//		return new DummyUserProjectDAO();
//	}
//
//	@Bean
//	public UserPackageDAO userPackageDAO() {
//		return new DummyUserPackageDAO();
//	}
//
//	@Bean
//	public WishListDAO wishListDAO() {
//		return new DummyWishListDAO();
//	}
//
//	@Bean
//	public UserBidDAO userBidDAO() {
//		return new DummyUserBidDAO();
//	}
//
//	@Bean
//	public PrivilegeDAO privilegeDAO() {
//		return new DummyPrivilegeDAO();
//	}
//
//	@Bean
//	public ProductStoreDAO productStoreDAO() {
//		return new DummyStoreDAO();
//	}
//
//	@Bean
//	public PromotionBannerDAO promotionBannerDAO() {
//		return new DummyBannerDAO();
//	}
//
//	@Bean
//	public ProductPromotionDAO productPromotionDAO() {
//		return new DummyProductPromotionDAO();
//	}
//
//	@Bean
//	public StoreDAO storeDAO() {
//		return new DummyStoreDAO();
//	}
//
//}
