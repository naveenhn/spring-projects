/**
 * 
 */
package com.sarvah.mbg.commons.address.postal;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.common.contact.Pincodes;
import java.lang.Integer;
import java.util.List;
import java.lang.String;

/**
 * @author naveen
 *
 */
public interface PincodesDAO extends MongoRepository<Pincodes, String> {

			List<Pincodes> findByPostalcode(Integer postalcode);
			
			List<Pincodes> findByPlacenameContainsOrAdminname1ContainsOrAdminname2ContainsOrAdminname3Contains (String placename);
	
	
}
