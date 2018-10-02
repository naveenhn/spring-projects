/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class RecentlyViewed extends AbstractDocument{

	@JsonIgnore
	@NotNull (message = "RecentlyViewed user cannot be null")
	@DBRef
	private User user;
	//productId Ref
	@NotNull (message = "RecentlyViewed productids cannot be null")
	private Set<String> productIds;
	
	
	/**
	 * @return the productIds
	 */
	public Set<String> getProductIds() {
		return productIds;
	}
	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(Set<String> productIds) {
		this.productIds = productIds;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
