/**
 * 
 */
package com.sarvah.mbg.commons.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.address.google.GoogleMapService;
import com.sarvah.mbg.commons.address.postal.IndianPincodeService;
import com.sarvah.mbg.domain.mongo.common.contact.Address;

/**
 * @author naveen
 *
 */
@Service
public class AddressLookupServiceImpl implements AddressLookupService {
	
	@Autowired
	private GoogleMapService googleMapService;
	
	@Autowired
	private IndianPincodeService pincodeService;

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.address.AddressLookupService#getGeoCodedAddress(java.lang.String)
	 */
	@Override
	public Point getGeoCodedAddress(String addressString) {

		if (addressString != null) {
			return googleMapService.getGeoCode(addressString);
		}
		return null;

	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.address.AddressLookupService#getGeoCodedAddress(java.lang.Integer)
	 */
	@Override
	public Point getGeoCodedAddress(Integer pincode) {
		
		if(pincode != null) {
		
		Point p = googleMapService.getGeoCode(""+pincode);
		if(p == null) {
			//try the fall back option
			p = pincodeService.getGeocodedAddressUsingPincode(pincode);
		}
		return p;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sarvah.mbg.common.address.AddressLookupService#getReverseGeoCodedAddress(long, long)
	 */
	@Override
	public Address getReverseGeoCodedAddress(double lat, double lng) {
		
		if(lat != 0 && lng != 0) {
			return googleMapService.getReverseGeoCode(lat, lng);
		}
		return null;
	}

}
