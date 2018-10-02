/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

/**
 * @author shivu
 *
 */
public interface UserOrderAddressRepository extends CrudRepository<OrderAddress,Integer>{

}
