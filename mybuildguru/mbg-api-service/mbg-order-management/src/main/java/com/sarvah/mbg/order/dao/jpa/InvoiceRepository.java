/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;

/**
 * @author Shiva
 *
 */
public interface InvoiceRepository extends
		CrudRepository<InvoiceDetails, Integer> {

	InvoiceDetails findByOrder(Order order);

	InvoiceDetails findByOrderAndUserId(Order order, String userId);
}
