/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;

/**
 * @author shivu
 *
 */
public interface OrderAddressRepository extends CrudRepository<OrderAddress,Integer>{

}
