/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.audit.OrderAuditType;

/**
 * @author shivu s
 *
 */
public interface OrderAuditTypeRepository extends CrudRepository<OrderAuditType,Integer>  {

	OrderAuditType findByValue(String status);

}
