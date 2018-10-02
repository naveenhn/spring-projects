/**
 * 
 */
package com.sarvah.mbg.batch.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;

/**
 * @author Shiva
 *
 */
public interface InvoiceRepository extends
		CrudRepository<InvoiceDetails, Integer> {

	List<InvoiceDetails> findAll();

	List<InvoiceDetails> findByOrder(Order order);

	long countByOrder(Order order);

	InvoiceDetails findByOrderAndUserId(Order order, String id);

}
