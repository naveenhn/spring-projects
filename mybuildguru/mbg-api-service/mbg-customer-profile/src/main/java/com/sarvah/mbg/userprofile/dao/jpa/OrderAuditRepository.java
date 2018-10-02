/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.audit.OrderAudit;

/**
 * @author Shiva
 *
 */
public interface OrderAuditRepository extends
		CrudRepository<OrderAudit, Integer> {

	List<OrderAudit> findByOrderId(int id);

	List<OrderAudit> findByOrderIdAndItemId(int orderId, String itemDetailsId);

}
