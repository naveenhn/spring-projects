package com.sarvah.mbg.dashboard.service.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.dashboard.dao.DashboardRepository;
import com.sarvah.mbg.dashboard.response.DashBoardProjectResponsesCount;
import com.sarvah.mbg.dashboard.response.DashBoardStatusCount;
import com.sarvah.mbg.dashboard.response.DashboardBidProjectResponse;
import com.sarvah.mbg.dashboard.response.DashboardConstructionsCount;
import com.sarvah.mbg.dashboard.response.DashboardOnboardedRoleCount;
import com.sarvah.mbg.dashboard.response.DashboardOrdersCount;
import com.sarvah.mbg.dashboard.response.DashboardPostResponsesCount;
import com.sarvah.mbg.dashboard.response.DashboardProductBrandResponse;
import com.sarvah.mbg.dashboard.response.DashboardProductDetailResponse;
import com.sarvah.mbg.dashboard.response.DashboardProductResponse;
import com.sarvah.mbg.dashboard.response.DashboardProjectResponses;
import com.sarvah.mbg.dashboard.response.DashboardTeleUpdatedLeadsResponse;
import com.sarvah.mbg.dashboard.response.DashboardTotalSavings;
import com.sarvah.mbg.dashboard.response.UserCountOnPackage;
import com.sarvah.mbg.dashboard.service.DashboardService;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.dashboard.Dashboard;
import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.aceproject.TeleCallHistory;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.catalog.ProductBrand;
import com.sarvah.mbg.domain.mongo.catalog.ProductStatus;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;
import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.domain.mongo.marketing.Promotion;
import com.sarvah.mbg.domain.mongo.store.Store;
import com.sarvah.mbg.domain.mongo.userprofile.RecentlyViewed;
import com.sarvah.mbg.domain.mongo.userprofile.ServiceProviders;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.WishList;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.ordermgmt.ItemStatus;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderDetails;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.promotion.dao.DashBoardProductNotOnBoardedNameStoreDAO;
import com.sarvah.mbg.promotion.dao.DashBoardRoleDAO;
import com.sarvah.mbg.promotion.dao.DashBoardVideoDAO;
import com.sarvah.mbg.promotion.dao.DashBoardWishListDAO;
import com.sarvah.mbg.promotion.dao.DashboardBannerDAO;
import com.sarvah.mbg.promotion.dao.DashboardBidDAO;
import com.sarvah.mbg.promotion.dao.DashboardBrandDAO;
import com.sarvah.mbg.promotion.dao.DashboardLeadProjectDAO;
import com.sarvah.mbg.promotion.dao.DashboardOrderDAO;
import com.sarvah.mbg.promotion.dao.DashboardOrderDetailsDAO;
import com.sarvah.mbg.promotion.dao.DashboardProductDAO;
import com.sarvah.mbg.promotion.dao.DashboardProjectDAO;
import com.sarvah.mbg.promotion.dao.DashboardPromotionDAO;
import com.sarvah.mbg.promotion.dao.DashboardStoreDAO;
import com.sarvah.mbg.promotion.dao.DashboardTeleCallHistoryDAO;
import com.sarvah.mbg.promotion.dao.DashboardUserDAO;
import com.sarvah.mbg.promotion.dao.DashboardUserInfoDAO;
import com.sarvah.mbg.promotion.dao.DashboardUserOrderRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	private static final Logger logger = LoggerFactory
			.getLogger(DashboardServiceImpl.class);

	@Autowired
	private DashboardPromotionDAO dashboardPromotionDAO;

	@Autowired
	private DashboardProductDAO dashboardProductDAO;

	@Autowired
	private DashboardRepository dashboardDAO;

	@Autowired
	private DashBoardVideoDAO dashBoardVideoDAO;

	@Autowired
	private DashboardUserDAO dashboardUserDAO;

	@Autowired
	private DashboardOrderDAO dashboarOrderDAO;

	@Autowired
	private DashboardUserInfoDAO dashboardUserInfoDAO;

	@Autowired
	private DashboardProjectDAO dashboardProjectDAO;

	@Autowired
	private DashboardBidDAO dashboardBidDAO;

	@Autowired
	private DashboardStoreDAO dashboardStoreDAO;

	@Autowired
	private DashBoardWishListDAO dashBoardWishListDAO;

	@Autowired
	private DashboardBrandDAO dashboardBrandDAO;

	@Autowired
	private DashboardBannerDAO dashboardBannerDAO;

	@Autowired
	private DashboardOrderDetailsDAO dashboardOrderDetailsDAO;

	@Autowired
	private DashBoardRoleDAO dashboardRoleDAO;

	@Autowired
	private MongoOperations operations;

	@Autowired
	private DashboardUserOrderRepository dashoardUserOrderRespository;

	@Autowired
	private DashBoardProductNotOnBoardedNameStoreDAO productNotOnBoardedNameStoreDAO;

	@Autowired
	private DashboardLeadProjectDAO dashboardLeadProjectDAO;

	@Autowired
	private DashboardTeleCallHistoryDAO dashboardTeleCallHistoryDAO;

	/**
	 * method for cuurent offers
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Product> ongoingpromotions(int page, int size, Sort sort)
			throws SQLException {
		List<Product> productList = new ArrayList<>();
		List<Promotion> promos = new ArrayList<>();
		List<Promotion> promosList = dashboardPromotionDAO.findAll();
		// sorting
		Collections.sort(promosList, new DashboardPromotionComparator());
		for (Promotion promotion : promosList) {

			Date endDate = promotion.getEndDate();
			Date todayDate = new Date();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = formatter.format(endDate);
			String format2 = formatter.format(todayDate);
			if (format2.compareTo(format1) <= 0) {
				// Not expired
				logger.info("Date not exppired");
				Set<String> productIds = promotion.getProductIds();
				if (productIds != null) {
					for (String pid : productIds) {
						Product product = dashboardProductDAO.findOne(pid);
						if (product != null) {
							productList.add(product);
						} else {
							logger.info("Product doesn't exists");
						}
					}
				}
				promos.add(promotion);

			} else {
				// Expired date
				logger.info("Date Expired");
			}
		}
		return productList;
	}

	/**
	 * method for display product count based on status.
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashBoardStatusCount getDashBoardProductStatus(String uid,
			String inMbg, String rejected, String waitingApproval)
			throws Exception {
		long MBG = 0;
		long Rejected = 0;
		long WaitingApproval = 0;
		DashBoardStatusCount dashBoardStatusCount = new DashBoardStatusCount();
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				ProductStatus.values();
				List<Product> productList = dashboardProductDAO
						.findByCreatedBy(uid);

				for (Product product : productList) {
					String skuid = product.getSkuId();

					if ((StringUtils.isNotBlank(inMbg))) {
						long mbgCount = dashboardProductDAO
								.countBySkuIdAndStatus(skuid,
										ProductStatus.IN_BUILDONN.toString());
						MBG = MBG + mbgCount;
					}
					if ((StringUtils.isNotBlank(waitingApproval))) {
						long waitingCount = dashboardProductDAO
								.countBySkuIdAndStatus(skuid,
										ProductStatus.APPROVAL_PENDING
												.toString());

						WaitingApproval = WaitingApproval + waitingCount;
					}
					if ((StringUtils.isNotBlank(rejected))) {
						long rejectedCount = dashboardProductDAO
								.countBySkuIdAndStatus(skuid,
										ProductStatus.REJECTED.toString());
						Rejected = Rejected + rejectedCount;
					}
				}
			} else {
				logger.info("Invalid User");
				throw new Exception("Invalid user");
			}
		}
		dashBoardStatusCount.setInMbg(MBG);
		dashBoardStatusCount.setRejected(Rejected);
		dashBoardStatusCount.setWaitingApproval(WaitingApproval);
		return dashBoardStatusCount;
	}

	/**
	 * method for get user order count based on status.
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @param view
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashboardOrdersCount getMyOrders(String uid) throws Exception {
		DashboardOrdersCount myordersCount = new DashboardOrdersCount();
		int placed = 0;
		int confirmed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;
		int cancelled = 0;
		int unConfirmed = 0;
		int returned = 0;
		int count = 0;
		int itemsCount = 0;
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				UserInfo userInfo = dashboardUserInfoDAO.findByMongoUserId(user
						.getId());
				if (userInfo != null) {
					Set<Order> orderList = new HashSet<>();
					orderList = dashboarOrderDAO.findByUserInfo(userInfo);
					for (Order order : orderList) {
						count++;
						Set<OrderDetails> itemsList = dashboardOrderDetailsDAO
								.findByOrder(order);
						for (OrderDetails orderDetails : itemsList) {
							ItemStatus itemStatus = orderDetails
									.getItemStatus();
							if (itemStatus != null) {
								String status = itemStatus.getDescription();
								if (status.equalsIgnoreCase("Placed")) {
									placed++;
								} else if (status.equalsIgnoreCase("Confirmed")) {
									confirmed++;
								} else if (status.equalsIgnoreCase("Packed")) {
									packed++;
								} else if (status
										.equalsIgnoreCase("Dispatched")) {
									dispatched++;
								} else if (status.equalsIgnoreCase("Delivered")) {
									delivered++;
								} else if (status.equalsIgnoreCase("Cancelled")) {
									cancelled++;
								} else if (status
										.equalsIgnoreCase("UnConfirmed")) {
									unConfirmed++;
								} else if (status.equalsIgnoreCase("Returned")) {
									returned++;
								}
							}
						}
					}
				}
			} else {
				logger.info("user doesn't exists");
				throw new Exception("user doesn't exists");
			}
			itemsCount = placed + confirmed + packed + dispatched + delivered
					+ cancelled + unConfirmed + returned;
			myordersCount.setPlaced(placed);
			myordersCount.setConfirmed(confirmed);
			myordersCount.setPacked(packed);
			myordersCount.setDispatched(dispatched);
			myordersCount.setDelivered(delivered);
			myordersCount.setCancelled(cancelled);
			myordersCount.setUnConfirmed(unConfirmed);
			myordersCount.setReturned(returned);
			myordersCount.setCount(count);
			myordersCount.setItemsCount(itemsCount);
		} else {
			logger.info("UserId null");
			throw new Exception("user Id null");
		}
		return myordersCount;
	}

	/**
	 * method for get All orders count based on status.
	 */
	@Override
	public DashboardOrdersCount getAllOrdersSummary(String uid)
			throws Exception {
		DashboardOrdersCount dealersOrders = new DashboardOrdersCount();

		int placed = 0;
		int confirmed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;

		int cancelled = 0;
		int unConfirmed = 0;
		int returned = 0;
		int count = 0;

		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				Iterable<Order> orderList = dashboarOrderDAO.findAll();

				for (Order order : orderList) {
					OrderStatus orderStatus = order.getOrderStatus();
					if (orderStatus != null) {
						String status = orderStatus.getDescription();
						if (status.equalsIgnoreCase("Placed")) {
							placed++;
						} else if (status.equalsIgnoreCase("Confirmed")) {
							confirmed++;
						} else if (status.equalsIgnoreCase("Packed")) {
							packed++;
						} else if (status.equalsIgnoreCase("Dispatched")) {
							dispatched++;
						} else if (status.equalsIgnoreCase("Delivered")) {
							delivered++;
						} else if (status.equals("Cancelled")) {
							cancelled++;
						} else if (status.equalsIgnoreCase("Unconfirmed")) {
							unConfirmed++;
						} else if (status.equalsIgnoreCase("Returned")) {
							returned++;
						}
					}
				}

			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("User id null");
			throw new Exception("User Id null");
		}

		count = placed + confirmed + packed + dispatched + delivered
				+ cancelled + unConfirmed + returned;
		dealersOrders.setPlaced(placed);
		dealersOrders.setConfirmed(confirmed);
		dealersOrders.setPacked(packed);
		dealersOrders.setDispatched(dispatched);
		dealersOrders.setDelivered(delivered);
		dealersOrders.setCancelled(cancelled);
		dealersOrders.setUnConfirmed(unConfirmed);
		dealersOrders.setReturned(returned);
		dealersOrders.setCount(count);

		return dealersOrders;

	}

	/**
	 * method for get dealer orders count based on status.
	 */
	@Override
	public DashboardOrdersCount getDealerOrders(String uid) throws Exception {
		DashboardOrdersCount dealersOrders = new DashboardOrdersCount();
		int placed = 0;
		int confirmed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;

		int cancelled = 0;
		int unConfirmed = 0;
		int returned = 0;
		int count = 0;
		int itemsCount = 0;

		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					if (role.getName().equalsIgnoreCase("DEALER")) {
						Set<Order> orderList = new HashSet<>();
						Set<OrderDetails> orderDetailsList = dashboardOrderDetailsDAO
								.findByDealerId(uid);
						for (OrderDetails orderDetails : orderDetailsList) {
							Order order = orderDetails.getOrder();
							orderList.add(order);
							ItemStatus itemStatus = orderDetails
									.getItemStatus();
							if (itemStatus != null) {
								String status = itemStatus.getDescription();
								if (status.equalsIgnoreCase("Placed")) {
									placed++;
								} else if (status.equalsIgnoreCase("Confirmed")) {
									confirmed++;
								} else if (status.equalsIgnoreCase("Packed")) {
									packed++;
								} else if (status
										.equalsIgnoreCase("Dispatched")) {
									dispatched++;
								} else if (status.equalsIgnoreCase("Delivered")) {
									delivered++;
								} else if (status.equalsIgnoreCase("Cancelled")) {
									cancelled++;
								} else if (status
										.equalsIgnoreCase("Unconfirmed")) {
									unConfirmed++;
								} else if (status.equalsIgnoreCase("Returned")) {
									returned++;
								}
							}
						}
						count = orderList.size();
					}
				}
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("User id null");
			throw new Exception("User Id null");
		}
		itemsCount = placed + confirmed + packed + dispatched + delivered
				+ cancelled + unConfirmed + returned;
		dealersOrders.setPlaced(placed);
		dealersOrders.setConfirmed(confirmed);
		dealersOrders.setPacked(packed);
		dealersOrders.setDispatched(dispatched);
		dealersOrders.setDelivered(delivered);
		dealersOrders.setCancelled(cancelled);
		dealersOrders.setUnConfirmed(unConfirmed);
		dealersOrders.setReturned(returned);
		dealersOrders.setCount(count);
		dealersOrders.setItemsCount(itemsCount);
		return dealersOrders;
	}

	/**
	 * method for get admin orders count based on status.
	 */
	@Override
	public DashboardOrdersCount getAllOrders(String uid) throws Exception {
		DashboardOrdersCount dealersOrders = new DashboardOrdersCount();
		int placed = 0;
		int confirmed = 0;
		int packed = 0;
		int dispatched = 0;
		int delivered = 0;

		int cancelled = 0;
		int unConfirmed = 0;
		int returned = 0;
		int count = 0;
		int itemsCount = 0;

		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				Set<Order> orderList = new HashSet<>();
				Iterable<OrderDetails> orderDetailsList = dashboardOrderDetailsDAO
						.findAll();

				for (OrderDetails orderDetails : orderDetailsList) {
					Order order = orderDetails.getOrder();
					orderList.add(order);
					ItemStatus itemStatus = orderDetails.getItemStatus();
					if (itemStatus != null) {
						String status = itemStatus.getDescription();
						if (status.equalsIgnoreCase("Placed")) {
							placed++;
						} else if (status.equalsIgnoreCase("Confirmed")) {
							confirmed++;
						} else if (status.equalsIgnoreCase("Packed")) {
							packed++;
						} else if (status.equalsIgnoreCase("Dispatched")) {
							dispatched++;
						} else if (status.equalsIgnoreCase("Delivered")) {
							delivered++;
						} else if (status.equalsIgnoreCase("Cancelled")) {
							cancelled++;
						} else if (status.equalsIgnoreCase("Unconfirmed")) {
							unConfirmed++;
						} else if (status.equalsIgnoreCase("Returned")) {
							returned++;
						}
					}
				}
				count = orderList.size();

			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("User id null");
			throw new Exception("User Id null");
		}
		itemsCount = placed + confirmed + packed + dispatched + delivered
				+ cancelled + unConfirmed + returned;
		dealersOrders.setPlaced(placed);
		dealersOrders.setConfirmed(confirmed);
		dealersOrders.setPacked(packed);
		dealersOrders.setDispatched(dispatched);
		dealersOrders.setDelivered(delivered);
		dealersOrders.setCancelled(cancelled);
		dealersOrders.setUnConfirmed(unConfirmed);
		dealersOrders.setReturned(returned);
		dealersOrders.setCount(count);
		dealersOrders.setItemsCount(itemsCount);
		return dealersOrders;
	}

	@Override
	public List<DashboardProductBrandResponse> getNewArrivedBrands(int page,
			int size, Sort sort) {

		List<ProductBrand> brandList = new ArrayList<ProductBrand>();
		List<DashboardProductBrandResponse> brandList1 = new ArrayList<DashboardProductBrandResponse>();

		Page<ProductBrand> pageable = dashboardBrandDAO
				.findAll(new PageRequest(page, size, sort));

		brandList.addAll(pageable.getContent());
		for (ProductBrand brand : brandList) {
			DashboardProductBrandResponse productBrandResponse = new DashboardProductBrandResponse();

			long productsCount = dashboardProductDAO.countByBrandAndStatus(
					brand, ProductStatus.IN_BUILDONN.toString());

			if (productsCount != 0) {
				productBrandResponse.setBrand(brand);
				Image img = brand.getProvider().getProfilePicture();
				if (img != null) {
					productBrandResponse.setBrandImage(img);
				}
				brandList1.add(productBrandResponse);
			}
		}
		return brandList1;

	}

	/**
	 * method for new arrivals
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardProductDetailResponse> getNewArrivals(int page,
			int size, Sort sort) {
		List<Product> productList = new ArrayList<>();
		List<DashboardProductDetailResponse> productList1 = new ArrayList<>();

		Page<Product> pageable = dashboardProductDAO.findByStatus(
				ProductStatus.IN_BUILDONN, new PageRequest(page, size, sort));

		productList.addAll(pageable.getContent());
		if (productList != null) {
			for (Product product : productList) {

				DashboardProductDetailResponse dashboardProductDetailResponse = new DashboardProductDetailResponse();
				dashboardProductDetailResponse.setProductId(product.getId());
				dashboardProductDetailResponse
						.setProductName(product.getName());
				if (product.getModel() != null) {
					dashboardProductDetailResponse.setModel(product.getModel());
				}
				if (product.getSkuId() != null) {
					dashboardProductDetailResponse.setSkuId(product.getSkuId());
				}
				if (product.getBrand() != null) {
					dashboardProductDetailResponse.setBrand(product.getBrand()
							.getName());
				}
				if (product.getSubcategories() != null) {
					dashboardProductDetailResponse.setSubcategories(product
							.getSubcategories());
				}
				if (product.getFeatures() != null) {
					dashboardProductDetailResponse.setFeatures(product
							.getFeatures());
				}
				if (product.getAttributes() != null) {
					dashboardProductDetailResponse.setAttributes(product
							.getAttributes());
				}
				if (product.getFaqs() != null) {
					dashboardProductDetailResponse.setFaqs(product.getFaqs());
				}
				if (product.getPrice() != null) {
					dashboardProductDetailResponse.setPrice(String
							.valueOf(product.getPrice().getMrp()));
				}
				if (product.getProductTypes() != null) {
					dashboardProductDetailResponse.setProductTypes(product
							.getProductTypes());
				}
				if (product.isAccessory()) {
					dashboardProductDetailResponse.setAccessory(String
							.valueOf(product.isAccessory()));
				}
				if (product.getRelatedProducts() != null) {
					dashboardProductDetailResponse.setRelatedProducts(product
							.getRelatedProducts());
				}
				if (product.getAccessories() != null) {
					dashboardProductDetailResponse.setAccessories(product
							.getAccessories());
				}
				if (product.getAssets() != null) {
					dashboardProductDetailResponse.setAssets(product
							.getAssets());
				}
				if (product.getTcDoc() != null) {
					dashboardProductDetailResponse.setTcDoc(product.getTcDoc());
				}
				if (product.getConsolidatedRating() != null) {
					dashboardProductDetailResponse.setRating(product
							.getConsolidatedRating());
				}
				if (product.getQuantityType() != null) {
					dashboardProductDetailResponse
							.setProductQuantityType(product.getQuantityType());
				}
				productList1.add(dashboardProductDetailResponse);
			}
		}
		return productList1;
	}

	/**
	 * method to get responses for my post
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashboardPostResponsesCount getResponseForMyPost(String uid)
			throws Exception {
		long architects = 0;
		long interiorDesigners = 0;
		long buildGuru = 0;
		long dealers = 0;
		List<Bid> architectBids = new ArrayList<>();
		List<Bid> interiorDesignerBids = new ArrayList<>();
		List<Bid> buildGuruBids = new ArrayList<>();
		List<Bid> dealerBids = new ArrayList<>();
		DashboardPostResponsesCount myPostResponsesCountParent = new DashboardPostResponsesCount();
		List<Project> projectList = new ArrayList<>();
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				projectList = dashboardProjectDAO.findByUser_Id(uid);
				for (Project project : projectList) {
					List<Bid> bidList = new ArrayList<>();
					bidList = dashboardBidDAO.findByProject(project);
					for (Bid bids : bidList) {
						// getting bid user roles
						Set<Role> roleList = bids.getUser().getRoles();
						for (Role role : roleList) {
							String roleName = role.getName();
							if (roleName.equalsIgnoreCase("Architect")) {
								architects++;
								architectBids.add(bids);
							}
							if (roleName.equalsIgnoreCase("InteriorDesigner")) {
								interiorDesigners++;
								interiorDesignerBids.add(bids);
							}
							if (roleName.equalsIgnoreCase("Dealer")) {
								dealers++;
								dealerBids.add(bids);
							}
							if (roleName.equalsIgnoreCase("SUPERADMIN")) {
								buildGuru++;
								buildGuruBids.add(bids);
							}
						}
					}
				}
			} else {
				logger.info("Invalid User");
				throw new Exception("Invalid user");
			}
			myPostResponsesCountParent.setArchitects(architects);
			myPostResponsesCountParent.setArchitectBids(architectBids);
			myPostResponsesCountParent.setInteriorDesigners(interiorDesigners);
			myPostResponsesCountParent
					.setInteriorDesignerBids(interiorDesignerBids);
			myPostResponsesCountParent.setDealers(dealers);
			myPostResponsesCountParent.setDealerBids(dealerBids);
			myPostResponsesCountParent.setBuildGuru(buildGuru);
			myPostResponsesCountParent.setBuildGuruBids(buildGuruBids);
			long total = architects + interiorDesigners + dealers + buildGuru;
			myPostResponsesCountParent.setTotalCount(total);
		} else {
			logger.info("User Id Null");
			throw new Exception("user Id null");
		}
		return myPostResponsesCountParent;
	}

	/**
	 * method for display user bids
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashBoardProjectResponsesCount getResponsesForMyBids(int page,
			int size, String uid) throws Exception {
		DashBoardProjectResponsesCount dashBoardProjectResponses = new DashBoardProjectResponsesCount();
		long totalResponses = 0;
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				List<Project> projectList = dashboardProjectDAO
						.findByUser_Id(uid);
				for (Project project : projectList) {
					List<Bid> bidList = null;
					bidList = dashboardBidDAO.findByProject(project);
					if (bidList != null) {
						for (Bid bid : bidList) {
							totalResponses++;
							logger.info(bid.getId());
						}
					}
				}
			} else {
				logger.info("user does not exist");
				throw new Exception("user does not exist");
			}
		} else {
			logger.info("user Id null");
			throw new Exception("user Id null");
		}
		dashBoardProjectResponses.setTotalResponses(totalResponses);
		return dashBoardProjectResponses;
	}

	/**
	 * method for display user project responses.
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardProjectResponses> getAllProjectResponses(int page,
			int size, String uid) throws Exception {
		List<DashboardProjectResponses> dashboardProjectResponsesList = new ArrayList<>();
		DashboardProjectResponses dashboardProjectResponses = new DashboardProjectResponses();
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				List<Project> projectList = dashboardProjectDAO
						.findByUser_Id(uid);
				for (Project project : projectList) {
					List<Bid> bidList = dashboardBidDAO.findByProject(project);
					if (bidList != null) {
						dashboardProjectResponses.setBids(bidList);
					}
					dashboardProjectResponsesList
							.add(dashboardProjectResponses);
				}
			} else {
				logger.info("user does not exist");
				throw new Exception("user does not exist");
			}
		} else {
			logger.info("user Id null");
			throw new Exception("user Id null");
		}
		return dashboardProjectResponsesList;
	}

	/**
	 * method for display all active project bids.
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Project> getAllProjectBids(int page, int size) {
		List<Project> projectList = null;
		List<Project> AllProjectList = new ArrayList<>();
		projectList = dashboardProjectDAO.findAll();
		// sorting
		Collections.sort(projectList, new DashboardProjectComparator());
		for (Project project : projectList) {
			String status = project.getStatus();
			if (status.equalsIgnoreCase("Active")) {
				AllProjectList.add(project);
			} else
				logger.info("Project Status InActive");
		}
		return AllProjectList;
	}

	/**
	 * method for display the projects constructed around me.
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @param distanceval
	 * @param type
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardConstructionsCount> getAllTypeOfConstructionsAroundMe(
			int page, int size, String uid, Distance distance, ProjectType type)
			throws Exception {
		List<DashboardConstructionsCount> dashboardConstructionsCountList = new ArrayList<>();
		DashboardConstructionsCount dashboardConstructionsCount = new DashboardConstructionsCount();
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				Point point = null;
				Set<Address> addressList = user.getAddresses();
				if (addressList != null && addressList.size() >= 1)
					point = ((Address) addressList.toArray()[0]).getLocation();
				List<Project> projectList = null;
				if (type != null) {
					projectList = dashboardProjectDAO
							.findByTypeAndStatusAndAddressLocationNear(type,
									"ACTIVE", point, distance);

					if (projectList != null) {
						dashboardConstructionsCount
								.setCount(projectList.size());
						dashboardConstructionsCount.setProject(projectList);
						dashboardConstructionsCount.setType(String
								.valueOf(type));
					}
					dashboardConstructionsCountList
							.add(dashboardConstructionsCount);
				} else {
					ProjectType.values();
					for (int i = 0; i < ProjectType.values().length; i++) {
						ProjectType type1 = ProjectType.values()[i];
						projectList = dashboardProjectDAO
								.findByTypeAndStatusAndAddressLocationNear(
										type1, "ACTIVE", point, distance);

						if (projectList != null) {
							DashboardConstructionsCount dashboardConstructionsCount1 = new DashboardConstructionsCount();
							dashboardConstructionsCount1.setCount(projectList
									.size());
							dashboardConstructionsCount1
									.setProject(projectList);
							dashboardConstructionsCount1.setType(String
									.valueOf(type1));
							dashboardConstructionsCountList
									.add(dashboardConstructionsCount1);
						}
					}
				}
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("Uid null");
			throw new Exception("user id null");
		}
		return dashboardConstructionsCountList;
	}

	/**
	 * method for display top five product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardProductResponse> getTop5Products() {
		List<DashboardProductResponse> dashboardProductResponseList = new ArrayList<>();
		AggregationResults<Product> results = operations.aggregate(
				newAggregation(WishList.class, unwind("productIds"),
						group("productIds").count().as("count"),
						sort(Direction.DESC, "count")), Product.class);
		List<Product> products = results.getMappedResults();
		for (int i = 0; i < products.size(); i++) {
			DashboardProductResponse dashboardProductResponse = new DashboardProductResponse();
			String productId = products.get(i).getId();
			long count = dashBoardWishListDAO.countByProductIds(productId);
			Product product = dashboardProductDAO.findOne(productId);
			dashboardProductResponse.setProduct(product);
			dashboardProductResponse.setCount(count);
			dashboardProductResponseList.add(dashboardProductResponse);
		}
		return dashboardProductResponseList;
	}

	/**
	 * method for get particular dealers promotion
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @param view
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Product> getMyCurrentPromotions(int page, int size, String uid)
			throws Exception {
		List<Product> productList = new ArrayList<>();
		List<Promotion> promotionList = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				List<Store> storeList = dashboardStoreDAO.findByUser(user);
				if (storeList != null) {
					promotionList = dashboardPromotionDAO.findByDealer(user);
					// sorting
					Collections.sort(promotionList,
							new DashboardPromotionComparator());

					for (Promotion promotion : promotionList) {

						Date endDate = promotion.getEndDate();
						Date todayDate = new Date();

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd");
						String format1 = formatter.format(endDate);
						String format2 = formatter.format(todayDate);
						if (format2.compareTo(format1) <= 0) {
							// Not expired
							logger.info("Date not exppired");
							Set<String> productIds = promotion.getProductIds();
							for (String pid : productIds) {
								Product product = dashboardProductDAO
										.findOne(pid);
								if (product != null) {
									productList.add(product);
								} else {
									logger.info("Product doesn't exists");
								}
							}
						} else {
							// Expired date
							logger.info("Date Expired");
						}
					}
				}
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		} else {
			logger.info("Uid null");
			throw new Exception("user id null");
		}
		return productList;
	}

	/**
	 * method for how to bid the project.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardBidProjectResponse> getHowToBidProjectResponse() {
		List<DashboardBidProjectResponse> howToBidProjectResponseList = new ArrayList<>();
		List<Dashboard> dashBoardList = new ArrayList<>();
		dashBoardList = dashboardDAO
				.findByDashBoardType_DescriptionLike("howtobidproject");
		if (dashBoardList != null) {
			for (Dashboard dashboard : dashBoardList) {
				DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
				String url = dashboard.getData();
				if (url != null) {
					howToBidProjectResponse.setVideoUrl(url);
					howToBidProjectResponseList.add(howToBidProjectResponse);
				}
			}
		}
		return howToBidProjectResponseList;
	}

	/**
	 * method for how to onboard the product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashboardBidProjectResponse getHowToOnBoardProduct() {
		DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
		List<Dashboard> dashBoardList = new ArrayList<>();
		dashBoardList = dashboardDAO
				.findByDashBoardType_DescriptionLike("howtoonboardproduct");
		if (dashBoardList != null) {
			Dashboard dashboard = dashBoardList.get(0);
			String url = dashboard.getData();
			if (url != null) {
				howToBidProjectResponse.setVideoUrl(url);
			}
		}
		return howToBidProjectResponse;
	}

	/**
	 * method for how to bid video.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashboardBidProjectResponse getHowToBidVideo() {
		DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
		List<Dashboard> dashBoardList = new ArrayList<>();
		dashBoardList = dashboardDAO
				.findByDashBoardType_DescriptionLike("howtovideo");
		if (dashBoardList != null) {
			Dashboard dashboard = dashBoardList.get(0);
			String url = dashboard.getData();
			if (url != null) {
				howToBidProjectResponse.setVideoUrl(url);
			}
		}
		return howToBidProjectResponse;
	}

	/**
	 * method for get onboarded products count by role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	@Override
	public DashboardOnboardedRoleCount getOnboardedByRole() {

		DashboardOnboardedRoleCount dashboardOnboardedRoleCount = new DashboardOnboardedRoleCount();
		long totalProduct = dashboardProductDAO.count();
		long provider = 0;
		long dealer = 0;
		long mbg = 0;
		List<User> userList = new ArrayList<User>();
		// = dashboardUserDAO.findAll();
		userList.addAll(dashboardUserDAO.findByRoles_Name("PROVIDER"));
		userList.addAll(dashboardUserDAO.findByRoles_Name("DEALER"));
		userList.addAll(dashboardUserDAO.findByRoles_Name("SUPERADMIN"));

		for (User user : userList) {
			Set<Role> roles = user.getRoles();
			for (Role role : roles) {
				String roleName = role.getName();
				if (StringUtils.isNotBlank(roleName)
						&& (roleName.equalsIgnoreCase("PROVIDER")
								|| roleName.equalsIgnoreCase("DEALER") || roleName
									.equalsIgnoreCase("SUPERADMIN"))) {
					String uid = user.getId();
					List<Product> productList = dashboardProductDAO
							.findByCreatedBy(uid);
					if (productList.size() != 0) {

						if (StringUtils.isNotBlank(roleName)
								&& roleName.equalsIgnoreCase("PROVIDER")) {

							long count = productList.size();
							provider = provider + count;

						} else if (StringUtils.isNotBlank(roleName)
								&& roleName.equalsIgnoreCase("DEALER")) {

							long count = productList.size();
							dealer = dealer + count;

						} else if (StringUtils.isNotBlank(roleName)
								&& roleName.equalsIgnoreCase("SUPERADMIN")) {

							long count = productList.size();
							mbg = mbg + count;

						}
					} else {
						logger.info("role doesn't onboard any products");
					}
				} else {
					logger.info("role doesn't have access to onboard products");
				}
			}
		}
		dashboardOnboardedRoleCount.setTotalProducts(totalProduct);
		dashboardOnboardedRoleCount.setProvider(provider);
		dashboardOnboardedRoleCount.setDealer(dealer);
		dashboardOnboardedRoleCount.setMbg(mbg);
		return dashboardOnboardedRoleCount;
	}

	/**
	 * method for display projects bided user count.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashboardPostResponsesCount getAllProjectUpdates() {
		DashboardPostResponsesCount dashboardPostResponsesCount = new DashboardPostResponsesCount();
		long totalCount = dashboardProjectDAO.count();

		long architects = 0;
		long interiorDesigners = 0;
		long buildGuru = 0;

		long biddingDone = 0;
		List<Project> projectList = dashboardProjectDAO.findAll();
		for (Project project : projectList) {
			List<Bid> bidList = new ArrayList<>();
			bidList = dashboardBidDAO.findByProject(project);
			for (Bid bids : bidList) {
				// getting bid user roles
				if (bids.getUser() != null) {
					Set<Role> roleList = bids.getUser().getRoles();
					for (Role role : roleList) {
						String roleName = role.getName();
						if (roleName
								.equalsIgnoreCase(ServiceProviders.ARCHITECT
										.toString())) {
							architects++;
						}
						if (roleName
								.equalsIgnoreCase(ServiceProviders.INTERIORDESIGNER
										.toString())) {
							interiorDesigners++;
						}
						if (roleName.equalsIgnoreCase(UserType.SUPERADMIN
								.toString())) {
							buildGuru++;
						}
					}
				}
			}

		}
		biddingDone = architects + interiorDesigners + buildGuru;
		dashboardPostResponsesCount.setArchitects(architects);
		dashboardPostResponsesCount.setInteriorDesigners(interiorDesigners);
		dashboardPostResponsesCount.setBuildGuru(buildGuru);
		dashboardPostResponsesCount.setTotalCount(totalCount);
		dashboardPostResponsesCount.setBiddingDone(biddingDone);

		return dashboardPostResponsesCount;
	}

	/**
	 * method for display products count based on role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public DashBoardStatusCount getAllProductsStatus(String inMbg,
			String rejected, String waitingApproval, String saved,
			String deactivated) {
		long MBG = 0;
		long Rejected = 0;
		long WaitingApproval = 0;
		long savedcount = 0;
		long deActivated = 0;

		DashBoardStatusCount dashBoardStatusCount = new DashBoardStatusCount();

		MBG = dashboardProductDAO.countByStatus(ProductStatus.IN_BUILDONN
				.toString());
		Rejected = dashboardProductDAO.countByStatus(ProductStatus.REJECTED
				.toString());
		WaitingApproval = dashboardProductDAO
				.countByStatus(ProductStatus.APPROVAL_PENDING.toString());
		savedcount = dashboardProductDAO.countByStatus(ProductStatus.SAVED
				.toString());
		deActivated = dashboardProductDAO
				.countByStatus(ProductStatus.DEACTIVATED.toString());

		dashBoardStatusCount.setDeactivated(deActivated);
		dashBoardStatusCount.setSaved(savedcount);
		dashBoardStatusCount.setInMbg(MBG);
		dashBoardStatusCount.setRejected(Rejected);
		dashBoardStatusCount.setWaitingApproval(WaitingApproval);
		return dashBoardStatusCount;
	}

	/**
	 * method for displaying all construction projects.
	 * 
	 * @param page
	 * @param size
	 * @param distanceval
	 * @param type
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardConstructionsCount> getAllConstructions(int page,
			int size, Distance distance, ProjectType type, String roleName) {
		List<DashboardConstructionsCount> dashboardConstructionsCountList = new ArrayList<>();
		DashboardConstructionsCount dashboardConstructionsCount = new DashboardConstructionsCount();

		if (type != null) {
			List<Project> projectList = new ArrayList<Project>();
			List<Project> projectList1 = null;
			if (roleName.equalsIgnoreCase("SUPERADMIN")) {
				projectList1 = dashboardProjectDAO.findByType(type);
			} else {
				projectList1 = dashboardProjectDAO.findByTypeAndStatus(type,
						"ACTIVE");
			}

			for (Project project : projectList1) {
				if (project.getUserType().equalsIgnoreCase(roleName)
						|| project.getUserType().equalsIgnoreCase(
								"BOTHARCHITECTANDINTERIORDESIGNER")
						|| roleName.equalsIgnoreCase("SUPERADMIN")) {
					projectList.add(project);
				}
			}
			if (projectList != null) {
				dashboardConstructionsCount.setCount(projectList.size());
				dashboardConstructionsCount.setProject(projectList);
				dashboardConstructionsCount.setType(String.valueOf(type));
			}
			dashboardConstructionsCountList.add(dashboardConstructionsCount);
		} else {
			for (int i = 0; i < ProjectType.values().length; i++) {
				ProjectType type1 = ProjectType.values()[i];
				List<Project> projectList2 = new ArrayList<Project>();
				List<Project> projectList3 = new ArrayList<Project>();
				if (roleName.equalsIgnoreCase("SUPERADMIN")) {
					projectList2 = dashboardProjectDAO.findByType(type1);
				} else {
					projectList2 = dashboardProjectDAO.findByTypeAndStatus(
							type1, "ACTIVE");
				}
				for (Project project : projectList2) {
					if (project.getUserType().equalsIgnoreCase(roleName)
							|| project.getUserType().equalsIgnoreCase(
									"BOTHARCHITECTANDINTERIORDESIGNER")
							|| roleName.equalsIgnoreCase("SUPERADMIN")) {
						projectList3.add(project);
					}
				}
				if (projectList3 != null) {
					DashboardConstructionsCount dashboardConstructionsCount1 = new DashboardConstructionsCount();
					dashboardConstructionsCount1.setCount(projectList3.size());
					dashboardConstructionsCount1.setProject(projectList3);
					dashboardConstructionsCount1.setType(String.valueOf(type1));
					dashboardConstructionsCountList
							.add(dashboardConstructionsCount1);
				}
			}
		}
		return dashboardConstructionsCountList;
	}

	/**
	 * method for display launch website url
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardBidProjectResponse> launchWebSite() {
		List<DashboardBidProjectResponse> howToBidProjectResponseList = new ArrayList<>();
		List<Dashboard> dashBoardList = new ArrayList<>();
		dashBoardList = dashboardDAO
				.findByDashBoardType_DescriptionLike("launchawebsite");
		if (dashBoardList != null) {
			for (Dashboard dashboard : dashBoardList) {
				DashboardBidProjectResponse howToBidProjectResponse = new DashboardBidProjectResponse();
				String url = dashboard.getData();
				if (url != null) {
					howToBidProjectResponse.setVideoUrl(url);
					howToBidProjectResponseList.add(howToBidProjectResponse);
				}
			}
		}
		return howToBidProjectResponseList;

	}

	/**
	 * method for get currentBanners
	 * 
	 * @param uid
	 * @return List<Banner>
	 * @throws Exception
	 */
	@Override
	public List<Banner> getMyCurrentBanners(String uid) throws Exception {
		List<Banner> banners = new ArrayList<>();
		if (StringUtils.isNotBlank(uid)) {

			User user = dashboardUserDAO.findOne(uid);
			if (user != null) {
				List<Banner> bannerList = dashboardBannerDAO.findByUser(user);
				for (Banner banner : bannerList) {
					Date endDate = banner.getEndDate();
					Date todayDate = new Date();

					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd");
					String format1 = formatter.format(endDate);
					String format2 = formatter.format(todayDate);
					if (format2.compareTo(format1) <= 0) {
						// Not expired
						logger.info("Date not exppired");
						banners.add(banner);
					} else {
						// Expired date
						logger.info("Date Expired");
					}
				}
			} else {
				logger.info("User doesn't exists");
				throw new Exception("USer doesn't exists");
			}
		} else {
			logger.info("User id null");
			throw new Exception("user id null");
		}
		return banners;
	}

	/**
	 * method to display mostviewed products
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<DashboardProductDetailResponse> mostViewed() {
		List<DashboardProductDetailResponse> dashboardProductResponseList = new ArrayList<>();
		AggregationResults<Product> results = operations.aggregate(
				newAggregation(RecentlyViewed.class, unwind("productIds"),
						group("productIds").count().as("count"),
						sort(Direction.DESC, "count")), Product.class);
		List<Product> products = results.getMappedResults();
		for (int i = 0; i < products.size(); i++) {
			if (i < 20) {
				String productId = products.get(i).getId();
				Product product = dashboardProductDAO.findOne(productId);
				if (product != null) {

					DashboardProductDetailResponse dashboardProductDetailResponse = new DashboardProductDetailResponse();
					dashboardProductDetailResponse
							.setProductId(product.getId());
					dashboardProductDetailResponse.setProductName(product
							.getName());
					if (product.getModel() != null) {
						dashboardProductDetailResponse.setModel(product
								.getModel());
					}
					if (product.getSkuId() != null) {
						dashboardProductDetailResponse.setSkuId(product
								.getSkuId());
					}
					if (product.getBrand() != null) {
						dashboardProductDetailResponse.setBrand(product
								.getBrand().getName());
					}
					if (product.getSubcategories() != null) {
						dashboardProductDetailResponse.setSubcategories(product
								.getSubcategories());
					}
					if (product.getFeatures() != null) {
						dashboardProductDetailResponse.setFeatures(product
								.getFeatures());
					}
					if (product.getAttributes() != null) {
						dashboardProductDetailResponse.setAttributes(product
								.getAttributes());
					}
					if (product.getFaqs() != null) {
						dashboardProductDetailResponse.setFaqs(product
								.getFaqs());
					}
					if (product.getPrice() != null) {
						dashboardProductDetailResponse.setPrice(String
								.valueOf(product.getPrice().getMrp()));
					}
					if (product.getProductTypes() != null) {
						dashboardProductDetailResponse.setProductTypes(product
								.getProductTypes());
					}
					if (product.isAccessory()) {
						dashboardProductDetailResponse.setAccessory(String
								.valueOf(product.isAccessory()));
					}
					if (product.getRelatedProducts() != null) {
						dashboardProductDetailResponse
								.setRelatedProducts(product
										.getRelatedProducts());
					}
					if (product.getAccessories() != null) {
						dashboardProductDetailResponse.setAccessories(product
								.getAccessories());
					}
					if (product.getAssets() != null) {
						dashboardProductDetailResponse.setAssets(product
								.getAssets());
					}
					if (product.getTcDoc() != null) {
						dashboardProductDetailResponse.setTcDoc(product
								.getTcDoc());
					}
					if (product.getConsolidatedRating() != null) {
						dashboardProductDetailResponse.setRating(product
								.getConsolidatedRating());
					}
					if (product.getQuantityType() != null) {
						dashboardProductDetailResponse
								.setProductQuantityType(product
										.getQuantityType());
					}

					dashboardProductResponseList
							.add(dashboardProductDetailResponse);
				}
			} else {
				break;
			}
		}
		return dashboardProductResponseList;
	}

	/**
	 * method to display most viewed produts by brands
	 * 
	 * @param brand
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public List<Product> mostViewedByBrand(String brand) {
		List<Product> dashboardProductResponseList = new ArrayList<>();
		AggregationResults<Product> results = operations.aggregate(
				newAggregation(RecentlyViewed.class, unwind("productIds"),
						group("productIds").count().as("count"),
						sort(Direction.DESC, "count")), Product.class);
		List<Product> products = results.getMappedResults();
		ProductBrand brand1 = dashboardBrandDAO.findByName(brand);
		for (int i = 0; i < products.size(); i++) {
			String productId = products.get(i).getId();
			Product product = dashboardProductDAO.findByIdAndBrand(productId,
					brand1);
			if (product != null) {
				dashboardProductResponseList.add(product);
			}
		}
		return dashboardProductResponseList;
	}

	/**
	 * method to display user count based on role.
	 * 
	 * @param roleName
	 * @return
	 */
	@Override
	public UserCountOnPackage getUserCountOnPackage(String roleName)
			throws Exception {
		UserCountOnPackage userCountOnPackage = new UserCountOnPackage();
		List<User> users = dashboardUserDAO.findByRoles_Name(roleName);

		Integer silverProviderCount = 0;
		Integer silverDealerCount = 0;
		Integer silverArchitectCount = 0;
		Integer silverInteriorDesignerCount = 0;

		Integer goldProviderCount = 0;
		Integer goldDealerCount = 0;
		Integer goldArchitectCount = 0;
		Integer goldInteriorDesignerCount = 0;

		Integer platinumProviderCount = 0;
		Integer platinumDealerCount = 0;
		Integer platinumArchitectCount = 0;
		Integer platinumInteriorDesignerCount = 0;

		Integer noOfRegisterdUsers = 0;
		Integer noOfActiveUsers = 0;
		Integer noOfInactiveUsers = 0;

		Role role = dashboardRoleDAO.findByName(roleName.toUpperCase());

		if (role != null) {
			if (role.getName().equalsIgnoreCase("Provider")
					|| role.getName().equalsIgnoreCase("Dealer")
					|| role.getName().equalsIgnoreCase("Architect")
					|| role.getName().equalsIgnoreCase("INTERIORDESIGNER")) {

				for (User user : users) {

					for (Role userRole : user.getRoles()) {
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("PROVIDER")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase("silver provider")) {
								silverProviderCount++;
								userCountOnPackage
										.setSilver(silverProviderCount);
							}
						}

						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("PROVIDER")
									&& userRole.getUserPackage().getName()
											.equalsIgnoreCase("gold provider")) {
								goldProviderCount++;
								userCountOnPackage.setGold(goldProviderCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("PROVIDER")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"platinum provider")) {
								platinumProviderCount++;
								userCountOnPackage
										.setPlatinum(platinumProviderCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("DEALER")
									&& userRole.getUserPackage().getName()
											.equalsIgnoreCase("silver dealer")) {
								silverDealerCount++;
								userCountOnPackage.setSilver(silverDealerCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("DEALER")
									&& userRole.getUserPackage().getName()
											.equalsIgnoreCase("gold dealer")) {
								goldDealerCount++;
								userCountOnPackage.setGold(goldDealerCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase("DEALER")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase("platinum dealer")) {
								platinumDealerCount++;
								userCountOnPackage
										.setPlatinum(platinumDealerCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName()
									.equalsIgnoreCase("architect")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"silver architect")) {
								silverArchitectCount++;
								userCountOnPackage
										.setSilver(silverArchitectCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName()
									.equalsIgnoreCase("architect")
									&& userRole.getUserPackage().getName()
											.equalsIgnoreCase("gold architect")) {
								goldArchitectCount++;
								userCountOnPackage.setGold(goldArchitectCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName()
									.equalsIgnoreCase("architect")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"platinum architect")) {
								platinumArchitectCount++;
								userCountOnPackage
										.setPlatinum(platinumArchitectCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase(
									"interiordesigner")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"silver INTERIORDESIGNER")) {
								silverInteriorDesignerCount++;
								userCountOnPackage
										.setSilver(silverInteriorDesignerCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase(
									"interiordesigner")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"gold INTERIORDESIGNER")) {
								goldInteriorDesignerCount++;
								userCountOnPackage
										.setGold(silverInteriorDesignerCount);
							}
						}
						if (userRole.getUserPackage() != null) {
							if (userRole.getName().equalsIgnoreCase(
									"interiordesigner")
									&& userRole
											.getUserPackage()
											.getName()
											.equalsIgnoreCase(
													"platinum INTERIORDESIGNER")) {
								platinumInteriorDesignerCount++;
								userCountOnPackage
										.setPlatinum(platinumInteriorDesignerCount);
							}
							userCountOnPackage.setRoleName(roleName);
						}
					}

				}
			} else {
				if (role.getName().equalsIgnoreCase("Engineer")
						|| role.getName().equalsIgnoreCase("VaastuConsultant")
						|| role.getName().equalsIgnoreCase("Plumber")
						|| role.getName().equalsIgnoreCase("Electrician")
						|| role.getName().equalsIgnoreCase("BIMConsultant")
						|| role.getName().equalsIgnoreCase("Contractor")
						|| role.getName()
								.equalsIgnoreCase("Business Associate")) {

					for (User user : users) {

						for (Role userRole : user.getRoles()) {

							if (userRole.getName().equalsIgnoreCase("Engineer")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase("Plumber")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase(
									"Contractor")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase(
									"VaastuConsultant")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase(
									"BIMConsultant")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase(
									"Electrician")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

							if (userRole.getName().equalsIgnoreCase(
									"BusinessAssociate")) {
								if (user.getStatus() == UserStatus.REGISTERED) {
									noOfRegisterdUsers++;
									userCountOnPackage
											.setNoOfRegisterdUsers(noOfRegisterdUsers);
								}
								if (user.getStatus() == UserStatus.ACTIVE) {
									noOfActiveUsers++;
									userCountOnPackage
											.setNoOfActiveUsers(noOfActiveUsers);
								}
								if (user.getStatus() == UserStatus.INACTIVE) {
									noOfInactiveUsers++;
									userCountOnPackage
											.setNoOfInactiveusers(noOfInactiveUsers);
								}
								userCountOnPackage
										.setUsersCount(noOfRegisterdUsers
												+ noOfActiveUsers
												+ noOfInactiveUsers);
								userCountOnPackage.setRoleName(roleName);
							}

						}

					}
				}
			}
		} else {
			throw new Exception("Role doesn't exist");
		}

		return userCountOnPackage;
	}

	/**
	 * Method to get count of registered/active/inactive user based on role
	 * 
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	@Override
	public UserCountOnPackage getRegisteredUserCountOnRoles(String roleName)
			throws Exception {
		UserCountOnPackage userCountOnPackage = null;
		if (StringUtils.isNotBlank(roleName)) {
			userCountOnPackage = new UserCountOnPackage();

			List<User> users = dashboardUserDAO.findByRoles_Name(roleName);

			Integer noOfRegisterdUsers = 0;
			Integer noOfActiveUsers = 0;
			Integer noOfInactiveUsers = 0;

			if (users != null && users.size() != 0) {

				for (User user : users) {
					if (user.getStatus() == UserStatus.REGISTERED) {
						noOfRegisterdUsers++;
					}
					if (user.getStatus() == UserStatus.ACTIVE) {
						noOfActiveUsers++;
					}
					if (user.getStatus() == UserStatus.INACTIVE) {
						noOfInactiveUsers++;
					}
				}
				// registered users
				userCountOnPackage.setNoOfRegisterdUsers(noOfRegisterdUsers);
				// active users
				userCountOnPackage.setNoOfActiveUsers(noOfActiveUsers);
				// inactive users
				userCountOnPackage.setNoOfInactiveusers(noOfInactiveUsers);
				// user count based on role
				userCountOnPackage.setUsersCount(noOfRegisterdUsers
						+ noOfActiveUsers + noOfInactiveUsers);
				// Role name
				userCountOnPackage.setRoleName(roleName);

			} else {
				logger.info("User doesn't exist for role name : {}", roleName);
			}
		} else {
			logger.info("Role name is blank or null");
			throw new Exception("Role name is blank or null");
		}
		return userCountOnPackage;
	}

	/**
	 * method to get total ServiceProviderCount and Active and InActive user
	 * count.
	 * 
	 * @return
	 */
	@Override
	public UserCountOnPackage getRegisteredServiceProvidersCountOnRoles() {
		UserCountOnPackage userCountOnPackage = new UserCountOnPackage();
		List<User> userList = dashboardUserDAO.findByRoles_type(UserType
				.valueOf("SERVICEPROVIDER"));

		Integer noOfRegisterdUsers = 0;
		Integer noOfActiveUsers = 0;
		Integer noOfInactiveUsers = 0;

		for (User user : userList) {
			if (user.getStatus() == UserStatus.REGISTERED) {
				noOfRegisterdUsers++;

			}
			if (user.getStatus() == UserStatus.ACTIVE) {
				noOfActiveUsers++;

			}
			if (user.getStatus() == UserStatus.INACTIVE) {
				noOfInactiveUsers++;

			}

		}
		userCountOnPackage.setNoOfRegisterdUsers(noOfRegisterdUsers);
		userCountOnPackage.setNoOfActiveUsers(noOfActiveUsers);
		userCountOnPackage.setNoOfInactiveusers(noOfInactiveUsers);
		long usersCount = noOfRegisterdUsers + noOfActiveUsers
				+ noOfInactiveUsers;
		userCountOnPackage.setUsersCount(usersCount);

		return userCountOnPackage;
	}

	/**
	 * Method to get total savings made by the user
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	@Override
	public DashboardTotalSavings getTotalSavings(String uid) throws Exception {

		DashboardTotalSavings totalSavings = new DashboardTotalSavings();
		double totalOrderSavings = 0;
		UserInfo userInfo = null;
		List<Order> userOrders = null;

		if (StringUtils.isNotBlank(uid)) {
			userInfo = dashboardUserInfoDAO.findByMongoUserId(uid);
			if (userInfo != null) {
				userOrders = dashoardUserOrderRespository
						.findByUserInfo(userInfo);

				for (Order order : userOrders) {
					logger.info("Getting total savings from all the orders!!");
					totalOrderSavings = totalOrderSavings
							+ order.getTotalSavings();
				}

			} else {
				logger.error("User doesn't exist!!");
				throw new Exception("User doesn't exist!!");
			}
		} else {
			logger.error("User id cannot be empty!!");
			throw new Exception("User id cannot be empty!!");
		}

		totalSavings.setTotalOrders(userOrders.size());
		totalSavings.setTotalSavings(totalOrderSavings);

		return totalSavings;
	}

	@Override
	public List<ProductNotOnBoardedNameStore> getMostOrderedProductButNotOnBoarded(
			int page, int size, Sort sort) {
		List<ProductNotOnBoardedNameStore> prodNames = new ArrayList<>();
		Page<ProductNotOnBoardedNameStore> pageable = null;
		pageable = productNotOnBoardedNameStoreDAO.findByOrderCountGreaterThan(
				2, new PageRequest(page, size, sort));
		if (pageable.getContent().size() != 0) {
			prodNames.addAll(pageable.getContent());
		}
		return prodNames;
	}

	@Override
	public List<DashboardTeleUpdatedLeadsResponse> getTeleTodayUpdatedLeads() {
		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();
		edate.setHours(edate.getHours() + 23);
		edate.setMinutes(edate.getMinutes() + 59);

		List<DashboardTeleUpdatedLeadsResponse> responseList = new ArrayList<DashboardTeleUpdatedLeadsResponse>();
		List<User> users = new ArrayList<>();
		users.addAll(dashboardUserDAO.findByRoles_NameAndStatus(
				"MBGTELEASSOCIATE", UserStatus.ACTIVE));
		users.addAll(dashboardUserDAO.findByRoles_NameAndStatus(
				"FIELDASSOCIATE", UserStatus.ACTIVE));
		for (User user : users) {

			DashboardTeleUpdatedLeadsResponse response = new DashboardTeleUpdatedLeadsResponse();
			if (user.getFirstName() != null && user.getLastName() != null) {
				response.setTeleAssociateName(user.getFirstName() + " "
						+ user.getLastName());
			} else if (user.getFirstName() != null) {
				response.setTeleAssociateName(user.getFirstName());
			}

			List<TeleCallHistory> teleCallHistoryList = dashboardTeleCallHistoryDAO
					.findByTeleIdAndCreatedDateBetween(user.getId(), sdate,
							edate);

			if (teleCallHistoryList != null && teleCallHistoryList.size() != 0) {
				TeleCallHistory teleCallHistory = teleCallHistoryList.get(0);
				response.setEndUsers(teleCallHistory.getEndUsers());
				response.setCivilEngineers(teleCallHistory.getCivilEngineers());
				response.setCivilContractors(teleCallHistory
						.getCivilContractors());
				response.setPlumbers(teleCallHistory.getPlumbers());
				response.setOthers(teleCallHistory.getOthers());
				// FollowUps
				response.setRequirementGiven(teleCallHistory
						.getRequirementGiven());
				response.setSwitchedOff(teleCallHistory.getSwitchedOff());
				response.setWrongNumber(teleCallHistory.getWrongNumber());
				response.setNotPicked(teleCallHistory.getNotPicked());
				response.setCallAfterOneToTwoDays(teleCallHistory
						.getCallAfterOneToTwoDays());
				response.setCallAfterOneToTwoWeeks(teleCallHistory
						.getCallAfterOneToTwoWeeks());
				response.setCallAfterFifteenToThirtyDays(teleCallHistory
						.getCallAfterFifteenToThirtyDays());
				response.setCallAfterThirtyToSixtyDays(teleCallHistory
						.getCallAfterThirtyToSixtyDays());
				response.setAnySpecific(teleCallHistory.getAnySpecific());
				// NewLeads
				response.setRequirementGivenNewLead(teleCallHistory
						.getRequirementGivenNewLead());
				response.setCallAfterOneToTwoDaysNewLead(teleCallHistory
						.getCallAfterOneToTwoDaysNewLead());
				response.setCallAfterOneToTwoWeeksNewLead(teleCallHistory
						.getCallAfterOneToTwoWeeksNewLead());
				response.setCallAfterFifteenToThirtyDaysNewLead(teleCallHistory
						.getCallAfterFifteenToThirtyDaysNewLead());
				response.setCallAfterThirtyToSixtyDaysNewLead(teleCallHistory
						.getCallAfterThirtyToSixtyDaysNewLead());
				response.setAnySpecific(teleCallHistory.getAnySpecificNewLead());
			}
			responseList.add(response);
		}
		return responseList;
	}

	@Override
	public List<DashboardTeleUpdatedLeadsResponse> getTeleWeeklyUpdatedLeads(
			Long startDate, Long endDate) {
		List<DashboardTeleUpdatedLeadsResponse> responseList = new ArrayList<DashboardTeleUpdatedLeadsResponse>();
		List<User> users = new ArrayList<>();
		users.addAll(dashboardUserDAO.findByRoles_NameAndStatus(
				"MBGTELEASSOCIATE", UserStatus.ACTIVE));
		users.addAll(dashboardUserDAO.findByRoles_NameAndStatus(
				"FIELDASSOCIATE", UserStatus.ACTIVE));
		for (User user : users) {
			int endusers = 0;
			int civilEngineers = 0;
			int civilContractors = 0;
			int plumbers = 0;
			int others = 0;

			int requirementGiven = 0;
			int switchedOff = 0;
			int wrongNumber = 0;
			int notPicked = 0;
			int callAfterOneToTwoDays = 0;
			int callAfterOneToTwoWeeks = 0;
			int callAfterFifteenToThirtyDays = 0;
			int callAfterThirtyToSixtyDays = 0;
			int anySpecific = 0;

			int requirementGivenNewLead = 0;
			int callAfterOneToTwoDaysNewLead = 0;
			int callAfterOneToTwoWeeksNewLead = 0;
			int callAfterFifteenToThirtyDaysNewLead = 0;
			int callAfterThirtyToSixtyDaysNewLead = 0;
			int anySpecificNewLead = 0;

			DashboardTeleUpdatedLeadsResponse response = new DashboardTeleUpdatedLeadsResponse();
			// TeleName
			if (user.getFirstName() != null && user.getLastName() != null) {
				response.setTeleAssociateName(user.getFirstName() + " "
						+ user.getLastName());
			} else if (user.getFirstName() != null) {
				response.setTeleAssociateName(user.getFirstName());
			}
			// Get TeleCallHistory By Date
			Date sdate = new Date(startDate);
			Date edate = new Date(endDate);
			edate.setHours(edate.getHours() + 23);
			edate.setMinutes(edate.getMinutes() + 59);

			List<TeleCallHistory> teleCallHistoryList = dashboardTeleCallHistoryDAO
					.findByTeleIdAndCreatedDateBetween(user.getId(), sdate,
							edate);
			if (teleCallHistoryList != null) {
				for (TeleCallHistory teleCallHistory : teleCallHistoryList) {
					// Users count
					endusers = endusers + teleCallHistory.getEndUsers();
					civilContractors = civilContractors
							+ teleCallHistory.getCivilContractors();
					civilEngineers = civilEngineers
							+ teleCallHistory.getCivilEngineers();
					plumbers = plumbers + teleCallHistory.getPlumbers();
					others = others + teleCallHistory.getOthers();
					// New leads created count
					requirementGivenNewLead = requirementGivenNewLead
							+ teleCallHistory.getRequirementGivenNewLead();
					callAfterOneToTwoDaysNewLead = callAfterOneToTwoDaysNewLead
							+ teleCallHistory.getCallAfterOneToTwoDaysNewLead();
					callAfterOneToTwoWeeksNewLead = callAfterOneToTwoWeeksNewLead
							+ teleCallHistory
									.getCallAfterOneToTwoWeeksNewLead();
					callAfterFifteenToThirtyDaysNewLead = callAfterFifteenToThirtyDaysNewLead
							+ teleCallHistory
									.getCallAfterFifteenToThirtyDaysNewLead();
					callAfterThirtyToSixtyDaysNewLead = callAfterThirtyToSixtyDaysNewLead
							+ teleCallHistory
									.getCallAfterThirtyToSixtyDaysNewLead();
					anySpecificNewLead = anySpecificNewLead
							+ teleCallHistory.getAnySpecificNewLead();
					// Leads updated count
					requirementGiven = requirementGiven
							+ teleCallHistory.getRequirementGiven();
					switchedOff = switchedOff
							+ teleCallHistory.getSwitchedOff();
					wrongNumber = wrongNumber
							+ teleCallHistory.getWrongNumber();
					notPicked = notPicked + teleCallHistory.getNotPicked();
					callAfterOneToTwoDays = callAfterOneToTwoDays
							+ teleCallHistory.getCallAfterOneToTwoDays();
					callAfterOneToTwoWeeks = callAfterOneToTwoWeeks
							+ teleCallHistory.getCallAfterOneToTwoWeeks();
					callAfterFifteenToThirtyDays = callAfterFifteenToThirtyDays
							+ teleCallHistory.getCallAfterFifteenToThirtyDays();
					callAfterThirtyToSixtyDays = callAfterThirtyToSixtyDays
							+ teleCallHistory.getCallAfterThirtyToSixtyDays();
					anySpecific = anySpecific
							+ teleCallHistory.getAnySpecific();
				}
			}

			response.setEndUsers(endusers);
			response.setCivilEngineers(civilEngineers);
			response.setCivilContractors(civilContractors);
			response.setPlumbers(plumbers);
			response.setOthers(others);
			// FollowUps
			response.setRequirementGiven(requirementGiven);
			response.setSwitchedOff(switchedOff);
			response.setWrongNumber(wrongNumber);
			response.setNotPicked(notPicked);
			response.setCallAfterOneToTwoDays(callAfterOneToTwoDays);
			response.setCallAfterOneToTwoWeeks(callAfterOneToTwoWeeks);
			response.setCallAfterFifteenToThirtyDays(callAfterFifteenToThirtyDays);
			response.setCallAfterThirtyToSixtyDays(callAfterThirtyToSixtyDays);
			response.setAnySpecific(anySpecific);
			// NewLeads
			response.setRequirementGivenNewLead(requirementGivenNewLead);
			response.setCallAfterOneToTwoDaysNewLead(callAfterOneToTwoDaysNewLead);
			response.setCallAfterOneToTwoWeeksNewLead(callAfterOneToTwoWeeksNewLead);
			response.setCallAfterFifteenToThirtyDaysNewLead(callAfterFifteenToThirtyDaysNewLead);
			response.setCallAfterThirtyToSixtyDaysNewLead(callAfterThirtyToSixtyDaysNewLead);
			response.setAnySpecific(anySpecificNewLead);

			responseList.add(response);
		}
		return responseList;
	}

}
