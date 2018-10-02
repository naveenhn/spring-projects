/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.ItemStatus;

/**
 * @author shivu
 *
 */
public interface UserItemStatusRepository extends CrudRepository<ItemStatus,Integer> {
   ItemStatus findByDescription(String itemStatus);
}
