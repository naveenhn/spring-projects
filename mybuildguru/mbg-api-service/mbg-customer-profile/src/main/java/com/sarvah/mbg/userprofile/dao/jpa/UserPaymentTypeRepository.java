package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.payment.PaymentType;

/**
 * 
 * @author Raju
 *
 */
public interface UserPaymentTypeRepository extends
		CrudRepository<PaymentType, Integer> {

	PaymentType findByPaymentType(String paymentType);

}
