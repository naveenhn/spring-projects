/**
 * 
 */
package com.sarvah.mbg.userprofile.dao.jpa;

import org.springframework.data.repository.CrudRepository;

import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;

/**
 * @author shivu
 *
 */
public interface UserShippingTypeRepository extends CrudRepository<ShippingType,Integer>{
	ShippingType findByShippingType(String shippingType);
}
