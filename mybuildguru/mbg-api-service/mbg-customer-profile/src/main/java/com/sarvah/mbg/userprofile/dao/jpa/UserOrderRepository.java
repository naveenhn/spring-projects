/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author shivu
 *
 */
public interface UserOrderRepository extends CrudRepository<Order, Integer> {

	Order findByMbgOrderIdAndUserInfo(String ordid, UserInfo userInfo);

	List<Order> findByUserInfo(UserInfo userInfo);

	List<Order> findAll();

	Long countByUserInfo(UserInfo userInfo);

	List<Order> findByUserInfoAndOrderStatus(UserInfo userInfo,
			OrderStatus orderStatus);

	Collection<? extends Order> findByUserInfoAndOrderStatus_Description(
			UserInfo userInfo, String string);

	Page<Order> findByUserInfo(UserInfo userInfo, Pageable pageable);

	Page<Order> findAll(Pageable pageable);

	Page<Order> findByMbgOrderIdLikeAllIgnoreCase(String orderId,
			Pageable pageable);

	Page<Order> findDistinctOrderByItems_dealerId(String uid, Pageable pageable);

	Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerId(String orderId,
			String uid, Pageable pageable);

	Page<Order> findByShippingTypeInAndOrderStatusIn(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Page<Order> findByShippingTypeIn(List<ShippingType> shippingTypeList,
			Pageable pageable);

	Page<Order> findByOrderStatusIn(List<OrderStatus> orderStatusList,
			Pageable pageable);

	Page<Order> findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Page<Order> findDistinctOrderByItems_dealerIdAndShippingTypeIn(String uid,
			List<ShippingType> shippingTypeList, Pageable pageable);

	Page<Order> findDistinctOrderByItems_dealerIdAndOrderStatusIn(String uid,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Page<Order> findByUserInfoAndOrderStatus_DescriptionNotIn(
			UserInfo userInfo, String string, Pageable pageable);

	Page<Order> findByUserInfoAndOrderStatus(UserInfo userInfo,
			OrderStatus orderStatus, Pageable pageable);

	Page<Order> findByUserInfoAndOrderStatus_Description(UserInfo userInfo,
			String string, Pageable pageable);

	Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String orderId, String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
			String orderId, String uid, List<ShippingType> shippingTypeList,
			Pageable pageable);

	Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
			String orderId, String uid, List<OrderStatus> orderStatusList,
			Pageable pageable);

	Page<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
			String orderId, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Page<Order> findByMbgOrderIdAndShippingTypeIn(String orderId,
			List<ShippingType> shippingTypeList, Pageable pageable);

	Page<Order> findByMbgOrderIdAndOrderStatusIn(String orderId,
			List<OrderStatus> orderStatusList, Pageable pageable);

	Order findByMbgOrderId(String orderId);

	Page<Order> findByOrderedDateBetween(Date date1, Date date2,
			Pageable pageable);

	Page<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable);

	Page<Order> findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable);

	Page<Order> findByOrderStatusInAndOrderedDateBetween(
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable);

	Page<Order> findByShippingTypeInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate,
			Pageable pageable);

	Page<Order> findBymbgOrderIdAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate,
			Pageable pageable);

	List<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate);

	List<Order> findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate);

	List<Order> findByOrderStatusInAndOrderedDateBetween(
			List<OrderStatus> orderStatusList, Date sdate, Date edate);

	List<Order> findByShippingTypeInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate);

	List<Order> findBymbgOrderIdAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate);

	List<Order> findByOrderedDateBetween(Date sdate, Date edate);

	List<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList);

	List<Order> findByMbgOrderIdAndShippingTypeIn(String searchValue,
			List<ShippingType> shippingTypeList);

	List<Order> findByShippingTypeInAndOrderStatusIn(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList);

	List<Order> findByMbgOrderIdAndOrderStatusIn(String searchValue,
			List<OrderStatus> orderStatusList);

	List<Order> findByShippingTypeIn(List<ShippingType> shippingTypeList);

	List<Order> findByOrderStatusIn(List<OrderStatus> orderStatusList);

	Page<Order> findByUserInfo_FirstnameLike(String customerName,
			Pageable pageable);

	Page<Order> findByMbgOrderIdIgnoreCaseContaining(String searchValue,
			Pageable pageable);

	Page<Order> findByUserInfo_FirstnameIgnoreCaseContaining(
			String customerName, Pageable pageable);

	Page<Order> findByUserInfo_LastnameIgnoreCaseContaining(
			String customerName, Pageable pageable);

	List<Order> findDistinctOrderByItems_dealerId(String uid);

	List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String searchValue, String uid,
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList);

	List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
			String searchValue, String uid, List<ShippingType> shippingTypeList);

	List<Order> findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList);

	List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
			String searchValue, String uid, List<OrderStatus> orderStatusList);

	List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerId(
			String searchValue, String uid);

	List<Order> findDistinctOrderByItems_dealerIdAndShippingTypeIn(String uid,
			List<ShippingType> shippingTypeList);

	List<Order> findDistinctOrderByItems_dealerIdAndOrderStatusIn(String uid,
			List<OrderStatus> orderStatusList);

	Page<Order> findByPaymentDone(boolean b, Pageable pageable);

	Order findByInvoiceNumber(String invoiceNum);

}
