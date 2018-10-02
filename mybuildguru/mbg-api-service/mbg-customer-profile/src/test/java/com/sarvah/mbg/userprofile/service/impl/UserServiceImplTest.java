//package com.sarvah.mbg.userprofile.service.impl;
//
//import static org.junit.Assert.*;
//
//import java.text.ParseException;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.data.geo.Point;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.sarvah.mbg.MbgUserProfileTestConfiguration;
//import com.sarvah.mbg.banners.service.BannerService;
//import com.sarvah.mbg.domain.mongo.aceproject.Bid;
//import com.sarvah.mbg.domain.mongo.aceproject.Project;
//import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
//import com.sarvah.mbg.domain.mongo.common.contact.Address;
//import com.sarvah.mbg.domain.mongo.marketing.Banner;
//import com.sarvah.mbg.domain.mongo.marketing.Promotion;
//import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;
//import com.sarvah.mbg.domain.mongo.userprofile.User;
//import com.sarvah.mbg.domain.mongo.userprofile.WishList;
//import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
//import com.sarvah.mbg.domain.mongo.userprofile.role.UserPackage;
//import com.sarvah.mbg.domain.ordermgmt.Order;
//import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
//import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
//import com.sarvah.mbg.userprofile.service.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MbgUserProfileTestConfiguration.class)
//public class UserServiceImplTest {
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private BannerService bannerService;
//
//	@Autowired
//	private UserProjectDAO userProjectDAO;
//
//	public void testSearchUser() throws Exception {
//		List<User> user = userService.searchUser("shivus@gmail.com", null,
//				null, null, null, null, null, null, 0, 0, null);
//		assertTrue(user.size() >= 1);
//	}
//
//	@Test
//	public void testCountUser() throws Exception {
//		// (userName, firstName, lastName, email,phoneNumber, fullName);
//		long count = userService.countUser("shivus@gmail.com", null, null,
//				null, null, null);
//		assertEquals(100l, count);
//
//		long count1 = userService.countUser(null, null, null, null, null, null);
//		assertEquals(200l, count1);
//
//		long count2 = userService.countUser(null, "shivu", "s", null, null,
//				null);
//		assertEquals(40l, count2);
//	}
//
//	@Test
//	public void testCreateUser() throws Exception {
//		Point point = new Point(12.827124, 77.682919);
//		User user = userService.createUser("abc@gmail.com", "abc", "d", "abcd",
//				"femail", "ACTIVE", "22", "annur", "hunsur", "mysore",
//				"karnataka", "india", "571114", point, "abc@gmail.com",
//				"MOBILE", "+919611652281", "true", "Provider");
//		assertEquals("abc@gmail.com", user.getUsername());
//	}
//
//	@Test
//	public void testSearchUserById() throws Exception {
//		User user = userService.searchUserById("1");
//		assertEquals(1, Integer.parseInt(user.getId()));
//	}
//
//	@Test
//	public void testUpdateUser() throws Exception {
//		User user = userService.updateUser("1", "abc@gmail.com", "abc", "d",
//				"abcd", "male");
//		assertEquals("abc@gmail.com", user.getUsername());
//	}
//
//	@Test
//	public void testDeleteUser() throws Exception {
//		String uid = userService.deleteUser("12345");
//		assertEquals(12345, Integer.parseInt(uid));
//	}
//
//	@Test
//	public void testUpdateUserAddress() throws Exception {
//		User user = userService
//				.updateUserAddress("123", "1", "#24,12th main", null, "mysore",
//						"karnataka", "india", "571114", "abc@gmail.com");
//		assertTrue(user.getAddresses().size() == 1);
//	}
//
//	@Test
//	public void testSearchUserAddress() throws Exception {
//		Set<Address> users = userService.searchUserAddress("123", null, null,
//				null, null, null, null, null);
//		assertTrue(users.size() == 1);
//	}
//
//	@Test
//	public void testDeleteUserAddresses() throws Exception {
//		String uid = userService.deleteUser("123");
//		assertEquals("123", uid);
//	}
//
//	@Test
//	public void testUpdateUserRole() throws Exception {
//		User user = userService.updateUserRole("123", "12", "Provider",
//				"Provider info");
//		assertTrue(user.getRoles().size() == 1);
//	}
//
//	@Test
//	@Ignore
//	public void testSearchUserRole() throws Exception {
//		List<User> users = userService.searchUserRole("123", null);
//		assertEquals(users.get(0).getId(), "123");
//		assertTrue(users.size() == 1);
//	}
//
//	@Test
//	public void testUpdateUserPhone() throws Exception {
//		User user = userService.updateUserPhone("123", "OFFICE", "9900888597",
//				"office number info", "true");
//		assertEquals(user.getId(), "123");
//	}
//
//	@Test
//	public void testSearchUserPhone() throws Exception {
//		List<User> users = userService.searchUserPhone("1");
//		assertTrue(users.size() == 1);
//		assertEquals(users.get(0).getId(), "1");
//	}
//
//	@Test
//	public void testUpdateUserStatus() throws Exception {
//		User user = userService.updateUserStatus("123", "ACTIVE");
//		assertEquals(user.getId(), "123");
//	}
//
//	@Test
//	public void testGetUserStatus() throws Exception {
//		User user = userService.getUserStatus("123");
//		assertEquals(user.getId(), "123");
//	}
//
//	@Test
//	public void testGetUserPackage() throws Exception {
//		UserPackage userPackage = userService.getUserPackage("12345", "4");
//		assertEquals(userPackage.getId(), "123");
//	}
//
//	@Test
//	public void testUpdateUserPackage() throws Exception {
//		UserPackage userPackage = userService.updateUserPackage("12345", "4",
//				"GOLD", "gold info");
//		assertEquals(userPackage.getId(), "123");
//	}
//
//	// Recently Viewed
//	@Test
//	public void testGetRecentlyViewedProduct() throws Exception {
//		RecentlyViewed rv = userService.getRecentlyViewedProduct("123");
//		assertTrue(rv.getProductIds().size() > 1);
//	}
//
//	@Test
//	public void testCreateRecentlyViewedProduct() throws Exception {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		RecentlyViewed rv = userService.createRecentlyViewedProduct("123",
//				productIds);
//		assertTrue(rv.getProductIds().size() > 1);
//	}
//
//	@Test
//	public void testUpdateRecentlyViewedProduct() throws Exception {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		RecentlyViewed rv = userService.updateRecentlyViewedProduct("123",
//				productIds);
//		assertTrue(rv.getProductIds().size() > 1);
//	}
//
//	@Test
//	public void testDeleteRecentlyViewedProduct() throws Exception {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		String uid = userService.deleteRecentlyViewedProduct("123", productIds);
//		assertEquals("123", uid);
//	}
//
//	// Roles
//	@Test
//	public void testGetUserRole() {
//		List<Role> roleList = userService.getUserRole();
//		assertTrue(roleList.size() == 1);
//	}
//
//	@Test
//	public void testCountRoles() {
//		long count = userService.countRoles();
//		assertEquals(10, count);
//	}
//
//	@Test
//	public void testCreateRole() throws Exception {
//		Role role = userService.createRole("Provider", "provider info",
//				"Silver");
//		assertEquals("Provider", role.getName());
//	}
//
//	@Test
//	public void testUpdateUserRoleById() throws Exception {
//		String roleId = userService.updateUserRoleById("123", "Provider",
//				"Provider info", "Silver");
//		assertEquals("123", roleId);
//	}
//
//	@Test
//	public void testDeleteUserRoleById() throws Exception {
//		String roleId = userService.deleteUserRoleById("123");
//		assertEquals("123", roleId);
//	}
//
//	// User Package
//	@Test
//	public void testGetUserRolePackage() {
//		List<UserPackage> userPackage = userService.getUserRolePackage();
//		assertTrue(userPackage.size() == 1);
//	}
//
//	@Test
//	public void testCreatePackage() {
//		UserPackage userPackage = userService.createPackage("Silver",
//				"Silver info", "gold_privilege");
//		assertEquals("Silver", userPackage.getName());
//	}
//
//	@Test
//	public void testUpdateUserRolePackage() throws Exception {
//		String pkgId = userService.updateUserRolePackage("123", "Gold",
//				"gold info", "gold_privilege");
//		assertEquals("123", pkgId);
//	}
//
//	@Test
//	public void testDeletePackageById() throws Exception {
//		String pkgId = userService.deletePackageById("123");
//		assertEquals("123", pkgId);
//	}
//
//	// WishList
//	@Test
//	public void testGetUserWishList() throws Exception {
//		WishList wishList = userService.getUserWishList("123");
//		assertEquals("123", wishList.getId());
//	}
//
//	@Test
//	public void testCreateWishList() throws Exception {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		WishList wishList = userService.createWishList("123", productIds);
//		assertEquals("123", wishList.getId());
//	}
//
//	@Test
//	public void testUpdateWishList() throws Exception {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		WishList wishList = userService.updateWishList("123", "22", productIds);
//		assertEquals("123", wishList.getId());
//	}
//
//	@Test
//	public void testDeleteWishList() {
//		Set<String> productIds = new HashSet<>();
//		productIds.add("1");
//		productIds.add("2");
//		String wlId = userService
//				.deleteWishListProduct("123", "11", productIds);
//		assertEquals("11", wlId);
//	}
//
//	// *****************************
//
//	@Test
//	public void testSearchUserRole1() {
//
//		assertTrue(userService.getUserRole().size() == 1);
//		Role role = userService.getUserRole().get(0);
//
//		assertEquals(role.getName(), "Provider");
//
//		assertEquals(role.getUserPackage().getName(), "Silver");
//
//		assertEquals(role.getDesc().getLang(), "eng");
//		assertEquals(role.getDesc().getVal(), "eng info");
//
//	}
//
//	// 25/08/15
//
//	/**
//	 * Method to test get User Project
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testGetUserProject() throws Exception {
//
//		List<Project> project = userService.getUserProjects("123",
//				"MyBuildGuru", null, null, null, null, null, null);
//
//		assertTrue(project.size() == 1);
//
//		List<Project> project2 = userService.getUserProjects("123",
//				"MyBuildGuru", "RESIDENTIAL", null, null, null, null, null);
//
//		assertTrue(project2.size() == 1);
//
//		List<Project> project3 = userService.getUserProjects("123",
//				"MyBuildGuru", "RESIDENTIAL", "ramakrishnanagar", null, null,
//				null, null);
//
//		assertTrue(project3.size() == 1);
//
//		List<Project> project4 = userService.getUserProjects("123",
//				"MyBuildGuru", "RESIDENTIAL", "ramakrishnanagar", "mysore",
//				null, null, null);
//
//		assertTrue(project4.size() == 1);
//
//		List<Project> project5 = userService.getUserProjects("123",
//				"MyBuildGuru", "RESIDENTIAL", "ramakrishnanagar", "mysore",
//				"mysore", null, null);
//
//		assertTrue(project5.size() == 1);
//
//		List<Project> project6 = userService.getUserProjects("123",
//				"MyBuildGuru", "RESIDENTIAL", "ramakrishnanagar", "mysore",
//				"mysore", "570022", null);
//
//		assertTrue(project6.size() == 1);
//
//	}
//
//	@Test
//	public void testCountProjects() throws Exception {
//
//		long i = userService.countProjects("1", null, null, null, null, null);
//		assertEquals(60l, i);
//
//		long i1 = userService.countProjects("1", "MyBuildGuru", null, null,
//				null, null);
//		assertEquals(10l, i1);
//
//		long i2 = userService.countProjects("1", null, null, null, null,
//				"mysore");
//		assertEquals(20l, i2);
//
//		long i3 = userService.countProjects("1", null, null,
//				"#24, 12th main, jayanagar", null, null);
//		assertEquals(30l, i3);
//
//		long i4 = userService.countProjects("1", null, null, null,
//				"#24, 12th main, jayanagar", null);
//		assertEquals(40l, i4);
//
//		long i5 = userService.countProjects("1", null, ProjectType.COMMERCIAL,
//				null, null, null);
//		assertEquals(50l, i5);
//
//	}
//
//	@Test
//	public void testCreateProject() throws Exception {
//		Point point = new Point(27.154, 135.45);
//		Project project1 = userService.createProject("11", "mbg123",
//				"mbg info", "40000", "RESIDENTIAL", "12", "#25, 12 main",
//				"#43, 15 main", "mysore", "karnataka", "india", "571114",
//				point, "shivu@gmail.com", null, null, "ACTIVE");
//		assertEquals(project1.getName(), "mbg123");
//		assertEquals(project1.getType(), ProjectType.RESIDENTIAL);
//		Double budget = Double.parseDouble("40000.0");
//		assertEquals(project1.getBudget(), budget);
//		assertEquals(project1.getStatus(), "ACTIVE");
//		Integer addressId = Integer.parseInt("12");
//		assertEquals(project1.getAddress().getAddressId(), addressId);
//		assertEquals(project1.getAddress().getAddressLine1(), "#25, 12 main");
//		assertEquals(project1.getAddress().getAddressLine2(), "#43, 15 main");
//		assertEquals(project1.getAddress().getCity(), "mysore");
//		assertEquals(project1.getAddress().getState(), "karnataka");
//		assertEquals(project1.getAddress().getCountry(), "india");
//		Integer zipcode = Integer.parseInt("571114");
//		assertEquals(project1.getAddress().getZipcode(), zipcode);
//		assertEquals(project1.getAddress().getEmail(), "shivu@gmail.com");
//
//	}
//
//	/**
//	 * Method to test Update user project
//	 * 
//	 * @throws Exception
//	 */
//	@Test
//	public void testUpdateUserProject() throws Exception {
//		Project project = userService.updateUserProject("1", "22",
//				"housing project", "housing project", "250000", "COMMERCIAL",
//				"ramakrishnanagar", null, null, null, null, null, null,
//				"ACTIVE");
//
//		assertEquals(project.getName(), "housing project");
//	}
//
//	@Test
//	public void testDeleteUserProject() throws Exception {
//		String projid = userService.deleteUserProject("1", "11");
//
//		assertEquals(projid, "11");
//	}
//
//	@Test
//	public void testGetBid() throws Exception {
//		List<Bid> bidList = userService.getBid("1");
//		assertTrue(bidList.size() == 1);
//	}
//
//	@Test
//	public void testCountUserBids() throws Exception {
//
//		long i = userService.countUserBids("1");
//		assertEquals(10l, i);
//
//	}
//
//	@Test
//	public void testUpdateBid() throws Exception {
//		Bid bid = userService.updateBid("1", "22", "project bid updated",
//				"250000");
//		Integer quoteAmount = new Integer("250000");
//		assertEquals(bid.getQuoteAmount(), quoteAmount);
//	}
//
//	@Test
//	public void testDeleteUserBids() throws Exception {
//
//		String bid = userService.deleteUserBids("1", "22");
//
//		assertEquals("22", bid);
//	}
//
//	// Banner
//	@Test
//	public void testGetUserBanners() throws Exception {
//
//		List<Banner> bannerList = bannerService.getUserBanners("11");
//
//		assertTrue(bannerList.size() == 1);
//	}
//
//	@Test
//	public void testGetUserBannerCount() throws Exception {
//
//		long count = bannerService.getUserBannerCount("1");
//
//		assertEquals(10l, count);
//	}
//
//	@SuppressWarnings("deprecation")
//	@Test
//	public void testCreateBanner() throws Exception {
//		Banner banner = bannerService.createBanner("123",
//				"Create Banner discription", "01/09/2015", "01/10/2015");
//		assertEquals(banner.getStartDate().getYear(), 115);
//
//	}
//
//	@Test
//	public void testUpdateBanner() throws Exception {
//		Banner banner = bannerService.updateBanner("123", "1",
//				"Create Banner discription", "01/09/2015", "01/10/2015");
//		assertEquals("1", banner.getId());
//	}
//
//	@Test
//	public void testDeleteUserBanner() throws Exception {
//
//		String banner = bannerService.deleteUserBanner("11", "22");
//
//		assertEquals("22", banner);
//
//	}
//
//	// store
//
//	@Test
//	public void testGetPromotionForStore() throws Exception {
//		List<Promotion> promotions = userService.getPromotionForStore("145",
//				"17");
//		System.out.println(promotions.size());
//		assertTrue(promotions.size() == 2);
//		assertEquals(promotions.get(0).getName(), "Deepavali Promotion");
//		assertEquals(promotions.get(1).getName(), "Sunday Promotion");
//	}
//
//	@Test
//	public void testgetPromotionCountForUser() throws Exception {
//
//		long count = userService.getPromotionCountForUser("11", "22");
//
//		assertEquals(10l, count);
//	}
//
//	@Test
//	public void testCreatePromotionForUserStore() throws Exception {
//		Promotion promotion = userService.createPromotionForUserStore("450",
//				"287", "Deepavali Promotion",
//				"Deepavali Promotion Description", "DISCOUNT", null, "50",
//				"10/09/2015", "30/09/2015");
//
//		assertEquals("Deepavali Promotion", promotion.getName());
//	}
//
//	@Test
//	public void testUpdateStorePromotion() throws ParseException, Exception {
//		Promotion promotion = userService.updateStorePromotion("12", "23",
//				"34", "New year offer", null, null, null, null, null, null);
//		assertEquals("New year offer", promotion.getName());
//	}
//
//	@Test
//	public void testDeletePromotionForUserStore() throws Exception {
//		String promId = userService.deletePromotionForUserStore("123", "4",
//				"15");
//		assertEquals("15", promId);
//	}
//
//	// OrderManagement
//
////	@Test
////	public void testCreateOrder() throws ParseException {
////		Order order = userService.createOrder("123", null, null, null, null,
////				null, null, "shivu", "shivu", null, null);
////		assertEquals(order.getOrderId(), 123);
////		assertNotEquals(order.getOrderId(), 111);
////		assertEquals(order.getCreateBy(), "shivu");
////		assertNotEquals(order.getCreateBy(), "ramesh");
////		assertEquals(order.getLastModifiedBy(), "shivu");
////		assertNotEquals(order.getLastModifiedBy(), "ramesh");
////	}
//
//	@Test
//	public void testGetOrderCount() {
//		long orderCount = userService.getOrderCount("123");
//		assertEquals(orderCount, 10l);
//		assertNotEquals(orderCount, 20l);
//	}
//
////	@Test
////	public void testUpdateOrderStatus() {
////		Order order = userService.updateOrderStatus("123", "627", "2");
////		assertEquals(order.getOrderId(), 123);
////		assertNotEquals(order.getOrderId(), 111);
////		assertEquals(order.getOrderStatus().getOrderStatusId(), 2);
////		assertNotEquals(order.getOrderStatus().getOrderStatusId(), 1);
////	}
//
//	@Test
//	public void testGetAddress() {
//		List<OrderAddress> orderAddress = userService.getAddress("123", "1");
//		assertTrue(orderAddress.size()==2);
//	}
//	
//	@Test
//	public void updateAddress(){
//		OrderAddress orderAddress=userService.updateAddress("123", "15", "1", "#24 mysore", "near to bus stop", "mysore", "karnataka", "india", "571114", "9611652283");
//		assertEquals(orderAddress.getContactNo(),"9611652283");
//		assertNotEquals(orderAddress.getContactNo(),"9611652285");
//	}
//
//}
