package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentDetails;

/**
 * 
 * @author Raju
 *
 */

public interface UserPaymentDetailsRepository extends
		CrudRepository<PaymentDetails, Integer> {

	Set<PaymentDetails> findByOrder(Order order);

	Set<PaymentDetails> findByOrderAndPaymentType_PaymentTypeIgnoreCase(
			Order order, String paymentMethodName);

}
