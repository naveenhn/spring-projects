/**
 * 
 */
package com.sarvah.mbg.domain.mongo.aceproject;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;

/**
 * @author naveen
 *
 */
@Document
public class Bid extends AbstractDocument {
	
	private Description desc;	
	private Integer quoteAmount;
	//gridfs ids
	private List<String> assetIds;
	
	@DBRef
	@NotNull (message = "Bid needs to be associated with Project, it cannot be null")
	private Project project;
	
	@DBRef
	@NotNull(message = "User cannot be null in Bid")
	private User user;
	
	/**
	 * @return the desc
	 */
	public Description getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(Description desc) {
		this.desc = desc;
	}
	/**
	 * @return the quoteAmount
	 */
	public Integer getQuoteAmount() {
		return quoteAmount;
	}
	/**
	 * @param quoteAmount the quoteAmount to set
	 */
	public void setQuoteAmount(Integer quoteAmount) {
		this.quoteAmount = quoteAmount;
	}
	
	/**
	 * @return the assetIds
	 */
	public List<String> getAssetIds() {
		return assetIds;
	}
	/**
	 * @param assetIds the assetIds to set
	 */
	public void setAssetIds(List<String> assetIds) {
		this.assetIds = assetIds;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
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
