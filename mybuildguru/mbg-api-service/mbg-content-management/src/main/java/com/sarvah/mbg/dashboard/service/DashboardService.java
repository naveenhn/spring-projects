package com.sarvah.mbg.dashboard.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;

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
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.dashboard.ProductNotOnBoardedNameStore;
import com.sarvah.mbg.domain.mongo.marketing.Banner;

public interface DashboardService {

	/**
	 * method for cuurent offers
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	List<Product> ongoingpromotions(int page, int size, Sort sort)
			throws SQLException;

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
	DashBoardStatusCount getDashBoardProductStatus(String uid, String inMbg,
			String rejected, String waitingApproval) throws Exception;

	/**
	 * method for get dealer orders count based on status.
	 */
	DashboardOrdersCount getDealerOrders(String uid) throws Exception;

	/**
	 * method for get admin orders count based on status.
	 */
	DashboardOrdersCount getAllOrders(String uid) throws Exception;

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
	DashboardOrdersCount getMyOrders(String uid) throws Exception;

	DashboardOrdersCount getAllOrdersSummary(String uid) throws Exception;

	/**
	 * 
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	List<DashboardProductBrandResponse> getNewArrivedBrands(int page, int size,
			Sort sort);

	/**
	 * method for new arrivals
	 * 
	 * @param page
	 * @param size
	 * @param sortList
	 * @return
	 * @throws MBGAppException
	 */
	List<DashboardProductDetailResponse> getNewArrivals(int page, int size,
			Sort sort);

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
	DashboardPostResponsesCount getResponseForMyPost(String uid)
			throws Exception;

	/**
	 * method for display user bids
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */
	DashBoardProjectResponsesCount getResponsesForMyBids(int page, int size,
			String uid) throws Exception;

	/**
	 * method for display user project responses.
	 * 
	 * @param page
	 * @param size
	 * @param uid
	 * @return
	 * @throws MBGAppException
	 */

	List<DashboardProjectResponses> getAllProjectResponses(int page, int size,
			String uid) throws Exception;

	/**
	 * method for display all active project bids.
	 * 
	 * @param page
	 * @param size
	 * @return
	 * @throws MBGAppException
	 */

	List<Project> getAllProjectBids(int page, int size);

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
	List<DashboardConstructionsCount> getAllTypeOfConstructionsAroundMe(
			int page, int size, String uid, Distance distance, ProjectType type)
			throws Exception;

	/**
	 * method for display top five product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	List<DashboardProductResponse> getTop5Products();

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

	List<Product> getMyCurrentPromotions(int page, int size, String uid)
			throws Exception;

	/**
	 * method for how to bid the project.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	List<DashboardBidProjectResponse> getHowToBidProjectResponse();

	/**
	 * method for how to onboard the product.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	DashboardBidProjectResponse getHowToOnBoardProduct();

	/**
	 * method for how to bid video.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	DashboardBidProjectResponse getHowToBidVideo();

	/**
	 * method for get onboarded products count by role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	DashboardOnboardedRoleCount getOnboardedByRole();

	/**
	 * method for display projects bided user count.
	 * 
	 * @return
	 * @throws MBGAppException
	 */

	DashboardPostResponsesCount getAllProjectUpdates();

	/**
	 * method for display products count based on role.
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	DashBoardStatusCount getAllProductsStatus(String inMbg, String rejected,
			String waitingApproval, String saved, String deactivated);

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

	List<DashboardConstructionsCount> getAllConstructions(int page, int size,
			Distance distance, ProjectType type, String roleName);

	/**
	 * method for display launch website url
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	List<DashboardBidProjectResponse> launchWebSite();

	/**
	 * method for get currentBanners
	 * 
	 * @param uid
	 * @return List<Banner>
	 * @throws Exception
	 */

	List<Banner> getMyCurrentBanners(String uid) throws Exception;

	/**
	 * method to display mostviewed products
	 * 
	 * @return
	 * @throws MBGAppException
	 */
	List<DashboardProductDetailResponse> mostViewed();

	/**
	 * method to display most viewed produts by brands
	 * 
	 * @param brand
	 * @return
	 * @throws MBGAppException
	 */

	List<Product> mostViewedByBrand(String brand);

	/**
	 * method to display user count based on role.
	 * 
	 * @param roleName
	 * @return
	 */
	UserCountOnPackage getUserCountOnPackage(String roleName) throws Exception;

	/**
	 * method to get total ServiceProviderCount and Active and InActive user
	 * count.
	 * 
	 * @return
	 */
	UserCountOnPackage getRegisteredServiceProvidersCountOnRoles();

	/**
	 * Method to get count of registered/active/inactive user based on role
	 * 
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	UserCountOnPackage getRegisteredUserCountOnRoles(String roleName)
			throws Exception;

	/**
	 * Method to get total savings made by the user
	 * 
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	DashboardTotalSavings getTotalSavings(String uid) throws Exception;

	List<ProductNotOnBoardedNameStore> getMostOrderedProductButNotOnBoarded(
			int page, int size, Sort sort);

	List<DashboardTeleUpdatedLeadsResponse> getTeleTodayUpdatedLeads();

	List<DashboardTeleUpdatedLeadsResponse> getTeleWeeklyUpdatedLeads(
			Long sDate, Long eDate);
}
