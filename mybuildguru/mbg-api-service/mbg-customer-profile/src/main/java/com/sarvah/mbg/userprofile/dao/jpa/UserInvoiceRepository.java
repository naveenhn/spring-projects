package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;

public interface UserInvoiceRepository extends
		CrudRepository<InvoiceDetails, Integer> {

	InvoiceDetails findByOrderAndUserId(Order order, String userid);
	List<InvoiceDetails> findByOrder(Order order);

}
