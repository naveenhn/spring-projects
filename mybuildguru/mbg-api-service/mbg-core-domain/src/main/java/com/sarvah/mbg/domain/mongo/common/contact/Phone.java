/**
 * 
 */
package com.sarvah.mbg.domain.mongo.common.contact;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.sarvah.mbg.domain.mongo.common.Description;

/**
 * @author naveen
 *
 */
public class Phone {
	
	@NotNull (message = "Phone type cannot be null")
	private PhoneType type;
	
	@NotNull (message = "Phone number cannot be null")
	@Pattern(regexp="^\\+{0,1}[0-9]{0,2}\\-{0,1}[0-9]{10}$", message="phone number is either blank or not in the right format")
	private String number;
	private Description desc;
	private boolean primary = false;
	/**
	 * @return the type
	 */
	public PhoneType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(PhoneType type) {
		this.type = type;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
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
	 * @return the primary
	 */
	public boolean isPrimary() {
		return primary;
	}
	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}
	
	
	
	

}
