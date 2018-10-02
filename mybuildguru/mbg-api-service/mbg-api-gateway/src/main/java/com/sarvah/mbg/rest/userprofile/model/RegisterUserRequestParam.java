/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.sarvah.mbg.userprofile.auth.model.RegisterUserBean;

/**
 * @author naveen
 *
 */
public class RegisterUserRequestParam extends RegisterUserBean {

	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
	public boolean validate() {
		
		if (!EmailValidator.getInstance().isValid(this.getUsername())) {
			throw new ValidationException("username is not a valid email address");
		}
		
		if(!StringUtils.isNumeric(this.getContactNumber())) {
			throw new ValidationException("Contact number should be numeric");
		}

		if (!(String.valueOf(this.getZipcode()).matches("^\\d{6}$"))) {
			throw new ValidationException("Zipcode is not in the right format");
		}

		return true;
	}


}
