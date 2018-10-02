/**
 * 
 */
package com.sarvah.mbg.commons.address;

import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.common.contact.Address;

// TODO: Auto-generated Javadoc
/**
 * The Interface AddressLookupService.
 *
 * @author naveen
 */
public interface AddressLookupService {
	
	/**
	 * Gets the geo coded address.
	 *
	 * @param address the address
	 * @return the geo coded address
	 */
	Point getGeoCodedAddress(String address);
	
	/**
	 * Gets the geo coded address.
	 *
	 * @param zipcode the zipcode
	 * @return the geo coded address
	 */
	Point getGeoCodedAddress(Integer zipcode);
	
	/**
	 * Gets the reverse geo coded address.
	 *
	 * @param lat the lat
	 * @param lng the lng
	 * @return the reverse geo coded address
	 */
	Address getReverseGeoCodedAddress(double lat, double lng);
	

}
