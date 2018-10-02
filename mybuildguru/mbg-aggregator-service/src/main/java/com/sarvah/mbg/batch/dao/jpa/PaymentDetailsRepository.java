/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentDetails;

/**
 * @author Shiva
 *
 */
public interface PaymentDetailsRepository extends
		CrudRepository<PaymentDetails, Integer> {

	PaymentDetails findByOrder(Order order);
}
