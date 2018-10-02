/**
 * 
 */
package com.sarvah.mbg.commons.address.postal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.common.contact.Pincodes;

// TODO: Auto-generated Javadoc
/**
 * The Class IndianPincodeService.
 *
 * @author naveen
 */
@Service
public class IndianPincodeService {
	
	/** The pincodes dao. */
	@Autowired
	private PincodesDAO pincodesDAO;
	
	
	/**
	 * Gets the geocoded address using pincode. Actually the first postal lat/log of the pincode. 
	 * This will server as backup to Google geocoding //just in case
	 *
	 * @param pincode the pincode
	 * @return the geocoded address using pincode
	 */
	public Point getGeocodedAddressUsingPincode(Integer pincode) {
		
		if(pincode != null) {
			List<Pincodes> pincodes = pincodesDAO.findByPostalcode(pincode);
			if(pincodes != null && pincodes.size() >0 ) {
				double lat = pincodes.get(0).getLatitude();
				double lng = pincodes.get(0).getLongitude();
				return new Point(lat, lng);
			}
				
		}
		
		return null;
	}
	
	
public Point getGeocodedAddressUsingAddress(String addressString) {
		
		if(addressString != null) {
			List<Pincodes> pincodes = pincodesDAO.findByPlacenameContainsOrAdminname1ContainsOrAdminname2ContainsOrAdminname3Contains(addressString);
			if(pincodes != null && pincodes.size() >0 ) {
				double lat = pincodes.get(0).getLatitude();
				double lng = pincodes.get(0).getLongitude();
				return new Point(lat, lng);
			}
				
		}
		
		return null;
	}
	
}
