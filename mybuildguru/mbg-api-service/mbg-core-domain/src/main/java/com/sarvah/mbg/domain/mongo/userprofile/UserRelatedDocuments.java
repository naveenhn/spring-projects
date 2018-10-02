/**
 * 
 */
package com.sarvah.mbg.domain.mongo.userprofile;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class UserRelatedDocuments extends AbstractDocument  {
	
	private File file;
	private boolean active;
	private Set<UserType> forUserTypes;
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return the forUserTypes
	 */
	public Set<UserType> getForUserTypes() {
		return forUserTypes;
	}
	/**
	 * @param forUserTypes the forUserTypes to set
	 */
	public void setForUserTypes(Set<UserType> forUserTypes) {
		this.forUserTypes = forUserTypes;
	}
	
	
	

}
