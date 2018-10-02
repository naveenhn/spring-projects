/**
 * 
 */
package com.sarvah.mbg.domain.mongo.common;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.TextIndexed;

/**
 * @author naveen
 *
 */
public class Description {
	
	@NotNull (message = "Description lang cannot be null")
	private String lang;
	
	@NotNull (message = "Description val cannot be null")
	@TextIndexed
	private String val;
	
	
	public Description() {
		
	}
	
	public Description(String lang, String val) {
		this.lang = lang;
		this.val = val;
	}
	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}
	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
	/**
	 * @return the val
	 */
	public String getVal() {
		return val;
	}
	/**
	 * @param val the val to set
	 */
	public void setVal(String val) {
		this.val = val;
	}
}