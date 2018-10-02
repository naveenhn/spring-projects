/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.ItemStatus;

/**
 * @author shivu
 *
 */
public interface ItemStatusRepository extends CrudRepository<ItemStatus,Integer> {

	ItemStatus findByDescription(String status);

}
