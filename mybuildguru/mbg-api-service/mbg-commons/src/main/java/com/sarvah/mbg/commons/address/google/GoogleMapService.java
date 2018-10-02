/**
 * 
 */
package com.sarvah.mbg.commons.address.google;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import static com.google.maps.GeocodingApi.ComponentFilter.*;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.sarvah.mbg.domain.mongo.common.contact.Address;


/**
 * The GoogleMapService. A facade like class to make calls to google maps api
 *
 * @author naveen
 */
@Service
public class GoogleMapService {
	
	/** The api key. */
	@Value("${google.maps.apikey}")
	private String apiKey;
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(GoogleMapService.class);
	
	//AIzaSyDyqFGXBF_21ROhIa7MU0xiRp5b23nBHB8
	
	
	/**
	 * Gets the geocoded address.
	 *
	 * @param addressString the address string
	 * @return the geocoded address
	 */
	public Point getGeoCode(String addressString) {
		
		if(addressString != null) {
		logger.info("Trying to get lat lng using Google api service for a given address");
		// Replace the API key below with a valid API key.
		//GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDyqFGXBF_21ROhIa7MU0xiRp5b23nBHB8");
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		
		GeocodingResult[] results =  GeocodingApi.newRequest(context).address(addressString).components(country("IN")).awaitIgnoreError();
			   
			if(results != null && results.length >0) {
				double lat = results[0].geometry.location.lat;
				double lng = results[0].geometry.location.lng;
				Point point = new Point(lat, lng);
				return point;
				
				
			}
			
		}
		
		return null;

	}
	
	
	
	public Point getGeoCode(Integer pincode) {
		
		if(pincode != null) {
		logger.info("Trying to get lat lng using Google api service for a given pincode");
		// Replace the API key below with a valid API key.
		//GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDyqFGXBF_21ROhIa7MU0xiRp5b23nBHB8");
		GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		
		GeocodingResult[] results =  GeocodingApi.newRequest(context).components(postalCode(pincode.toString())).components(country("IN")).awaitIgnoreError();
			   
			if(results != null && results.length >0) {
				double lat = results[0].geometry.location.lat;
				double lng = results[0].geometry.location.lng;
				Point point = new Point(lat, lng);
				return point;
				
				
			}
		}
		
		return null;
		
	}
	
	
	
	/**
	 * Gets the reverse geo code.
	 *
	 * @param lat the lat
	 * @param lng the lng
	 * @return the reverse geo code
	 */
	public Address getReverseGeoCode(double lat, double lng) {
		logger.info("Trying to get approx address using Google api service");
		// Replace the API key below with a valid API key.
			//GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyDyqFGXBF_21ROhIa7MU0xiRp5b23nBHB8");
			GeoApiContext context = new GeoApiContext().setApiKey(apiKey);
		
				GeocodingResult[] results =  GeocodingApi.newRequest(context).latlng(new LatLng(lat, lng)).region("in").awaitIgnoreError();//GeocodingApi.reverseGeocode(context, new LatLng(lat, lng)).awaitIgnoreError();
				if(results != null && results.length > 0) {
					Address address = getAddressFromComponents(results[0].addressComponents);
					return address;
				}
			
			return null;
		
	}
	
	
	/**
	 * Gets the address from components.
	 *
	 * @param addressComponents the address components
	 * @return the address from components
	 */
	private Address getAddressFromComponents(AddressComponent[] addressComponents) {
		
		if(addressComponents != null && addressComponents.length > 0) {
			Address address = new Address();
			for (AddressComponent addressComponent : addressComponents) {
				
				switch (addressComponent.types[0]) {
				case POSTAL_CODE:
					//pincode
					address.setZipcode(Integer.parseInt(addressComponent.longName));
					break;
				case COUNTRY:
					//country
					address.setCountry(addressComponent.longName);
					break;
				case ADMINISTRATIVE_AREA_LEVEL_1:
					//state
					address.setState(addressComponent.longName);
					break;
					case ADMINISTRATIVE_AREA_LEVEL_2:
						//district
						address.setDistrict(addressComponent.longName);
					break;
					case LOCALITY:
						//city 
						address.setCity(addressComponent.longName);
						break;
					case SUBLOCALITY_LEVEL_1:
						address.setLocationName(addressComponent.longName);
						break;
					case ROUTE:
						address.setAddressLine2(addressComponent.longName);
						break;
					case STREET_ADDRESS:
						address.setAddressLine1(addressComponent.longName);
						break;
				default:
					break;
				}
				
			}
			
			return address;
		}
		
		return null;
	}
	
	
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		GoogleMapService googleMapService = new GoogleMapService();
		//googleMapService.getGeoCode("Udayaravi Road, Kuvempunagar Mysore-570023");
		
		Address address = googleMapService.getReverseGeoCode(12.3420233, 76.6527003);
		System.out.println(address);
	}

}
