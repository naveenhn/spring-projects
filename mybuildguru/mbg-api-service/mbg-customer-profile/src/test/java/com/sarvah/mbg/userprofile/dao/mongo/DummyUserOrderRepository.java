/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.OrderStatus;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;

/**
 * @author shivu
 *
 */
public class DummyUserOrderRepository implements UserOrderRepository {

	@Override
	public <S extends Order> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Order> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOne(Integer id) {
		Order order = new Order();
		order.setOrderId(123);
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setOrderStatusId(1);
		order.setOrderStatus(orderStatus);
		OrderAddress billingAddress = new OrderAddress();
		billingAddress.setAddressId(1);
		billingAddress.setAddressLine1("#24 mysore");
		billingAddress.setAddressLine2("near to bus stop");
		billingAddress.setCity("mysore");
		billingAddress.setState("karnataka");
		billingAddress.setCountry("india");
		billingAddress.setContactNo("9645785421");
		billingAddress.setPincode(571114);
		order.setBillingAddress(billingAddress);

		OrderAddress shippingAddress = new OrderAddress();
		shippingAddress.setAddressId(2);
		shippingAddress.setAddressLine1("#24 mysore");
		shippingAddress.setAddressLine2("near to bus stop");
		shippingAddress.setCity("mysore");
		shippingAddress.setState("karnataka");
		shippingAddress.setCountry("india");
		shippingAddress.setContactNo("9645785421");
		shippingAddress.setPincode(571114);
		order.setShippingAddress(shippingAddress);
		return order;
	}

	@Override
	public boolean exists(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Order> findAll(Iterable<Integer> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 10l;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Order entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Iterable<? extends Order> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Order> findByUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countByUserInfo(UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByUserInfoAndOrderStatus(UserInfo userInfo,
			OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Order> findByUserInfoAndOrderStatus_Description(
			UserInfo userInfo, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository#findByUserInfo
	 * (com.sarvah.mbg.domain.user.UserInfo,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Order> findByUserInfo(UserInfo userInfo, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository#findAll(org.
	 * springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Order> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository#
	 * findByShippingTypeInAndOrderStatusIn(java.util.List, java.util.List,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Order> findByShippingTypeInAndOrderStatusIn(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository#findByShippingTypeIn
	 * (java.util.List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Order> findByShippingTypeIn(
			List<ShippingType> shippingTypeList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository#findByOrderStatusIn
	 * (java.util.List, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Order> findByOrderStatusIn(List<OrderStatus> orderStatusList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfoAndOrderStatus_DescriptionNotIn(
			UserInfo userInfo, String string, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfoAndOrderStatus(UserInfo userInfo,
			OrderStatus orderStatus, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfoAndOrderStatus_Description(
			UserInfo userInfo, String string, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByItems_dealerId(String uid,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByItems_dealerIdAndShippingTypeIn(
			String uid, List<ShippingType> shippingTypeList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByItems_dealerIdAndOrderStatusIn(
			String uid, List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findByMbgOrderIdAndUserInfo(String ordid, UserInfo userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findByMbgOrderId(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerId(
			String orderId, String uid, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String orderId, String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
			String orderId, String uid, List<ShippingType> shippingTypeList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
			String orderId, String uid, List<OrderStatus> orderStatusList,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
			String orderId, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdAndShippingTypeIn(String orderId,
			List<ShippingType> shippingTypeList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdAndOrderStatusIn(String orderId,
			List<OrderStatus> orderStatusList, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByOrderedDateBetween(Date date1, Date date2,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByOrderStatusInAndOrderedDateBetween(
			List<OrderStatus> orderStatusList, Date sdate, Date edate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByShippingTypeInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findBymbgOrderIdAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByShippingTypeInAndOrderStatusInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList, Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByOrderStatusInAndOrderedDateBetween(
			List<OrderStatus> orderStatusList, Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByShippingTypeInAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findBymbgOrderIdAndOrderedDateBetween(
			List<ShippingType> shippingTypeList, Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByOrderedDateBetween(Date sdate, Date edate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByMbgOrderIdAndShippingTypeInAndOrderStatusIn(
			String searchValue, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByMbgOrderIdAndShippingTypeIn(String searchValue,
			List<ShippingType> shippingTypeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByShippingTypeInAndOrderStatusIn(
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByMbgOrderIdAndOrderStatusIn(String searchValue,
			List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByShippingTypeIn(List<ShippingType> shippingTypeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByOrderStatusIn(List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdLikeAllIgnoreCase(String orderId,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfo_FirstnameLike(String customerName,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByMbgOrderIdIgnoreCaseContaining(String searchValue,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfo_FirstnameIgnoreCaseContaining(
			String customerName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByUserInfo_LastnameIgnoreCaseContaining(
			String customerName, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByItems_dealerId(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String searchValue, String uid,
			List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndShippingTypeIn(
			String searchValue, String uid, List<ShippingType> shippingTypeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByItems_dealerIdAndShippingTypeInAndOrderStatusIn(
			String uid, List<ShippingType> shippingTypeList,
			List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerIdAndOrderStatusIn(
			String searchValue, String uid, List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByMbgOrderIdAndItems_dealerId(
			String searchValue, String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByItems_dealerIdAndShippingTypeIn(
			String uid, List<ShippingType> shippingTypeList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findDistinctOrderByItems_dealerIdAndOrderStatusIn(
			String uid, List<OrderStatus> orderStatusList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Order> findByPaymentDone(boolean b, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findByInvoiceNumber(String invoiceNum) {
		// TODO Auto-generated method stub
		return null;
	}
}
