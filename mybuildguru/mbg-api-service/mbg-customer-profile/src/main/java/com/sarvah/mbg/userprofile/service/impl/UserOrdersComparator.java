/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.util.Comparator;

import com.sarvah.mbg.domain.ordermgmt.Order;

/**
 * @author Shiva
 *
 */
public class UserOrdersComparator implements Comparator<Order> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */

	@Override
	public int compare(Order o1, Order o2) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Integer orderId1 = 0;
		Integer orderId2 = 0;

		orderId1 = o1.getOrderId();
		orderId2 = o2.getOrderId();

		if (orderId1 == orderId2)
			return 0;
		else if (orderId1 < orderId2)
			return 1;
		else
			return -1;
	}

}
