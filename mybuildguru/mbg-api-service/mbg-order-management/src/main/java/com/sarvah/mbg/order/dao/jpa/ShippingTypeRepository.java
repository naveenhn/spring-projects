/**
 * 
 */
package com.sarvah.mbg.order.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;

/**
 * @author shivu
 *
 */
public interface ShippingTypeRepository extends CrudRepository<ShippingType,String> {
	

}
