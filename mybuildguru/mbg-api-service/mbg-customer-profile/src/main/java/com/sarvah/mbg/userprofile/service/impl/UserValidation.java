package com.sarvah.mbg.userprofile.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.exception.UserInputValidationException;

public class UserValidation {
	private static final String PHONE_PATTERN = "^\\+{0,1}[0-9]{0,2}\\-{0,1}[0-9]{10}$";
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws UserInputValidationException
	 */
	public boolean userValidation(User user) throws UserInputValidationException {
		boolean isValid = false;

		String fname = user.getFirstName();
		String lname = user.getLastName();
		String username = user.getUsername();
		String sex = user.getSex();

		if (StringUtils.isNotBlank(fname) && StringUtils.isNotBlank(lname)
				&& StringUtils.isNotBlank(sex)
				&& StringUtils.isNotBlank(username)) {
			if (validateEmail(username)) {
				isValid = true;
			}
		} else {
			throw new UserInputValidationException("User firstname or lastname or sex or username is empty");
		}

		if (user.getAddresses() != null) {
			for (Address addr : user.getAddresses()) {

				if (validateUserAddress(addr)) {
					isValid = true;
				}
			}
		} 
		return isValid;
	}
	/**
	 * 
	 * @param addr
	 * @return
	 * @throws UserInputValidationException
	 */
	private boolean validateUserAddress(Address addr) throws UserInputValidationException {
		boolean isValid = false;
		String address1 = addr.getAddressLine1();
		String address2 = addr.getAddressLine2();
		String email = addr.getEmail();
		if (StringUtils.isNotBlank(address1)
				&& StringUtils.isNotBlank(address2)
				&& StringUtils.isNotBlank(email)) {
			if (validateEmail(email)) {
				isValid = true;
			}
		} else {
			throw new UserInputValidationException("User address field should not be empty");
		}
		if (addr.getPhoneNumbers() != null) {

			for (Phone phone : addr.getPhoneNumbers()) {
				if (validateUserPhone(phone)) {
					isValid = true;
				}
			}

		} else {
			throw new UserInputValidationException("error!user phoneNumber should not be empty");
		}

		return isValid;
	}
	/**
	 * 
	 * @param phone
	 * @return
	 * @throws UserInputValidationException
	 */
	private boolean validateUserPhone(Phone phone) throws UserInputValidationException {
		boolean isValid = false;
		String phoneType = phone.getNumber();
		String phoneNumber = phone.getNumber();
		if (StringUtils.isNotBlank(phoneType)
				&& StringUtils.isNotBlank(phoneNumber)) {
			if (validatePhoneNumber(phoneNumber)) {
				isValid = true;
			}
		} else {
			throw new UserInputValidationException("error!phoneNumber field should not be blank");
		}

		return isValid;
	}

	/**
	 * 
	 * @param phoneNumber
	 * @return {@link Boolean}
	 * @throws UserInputValidationException
	 */
	public boolean validatePhoneNumber(String phoneNumber) throws UserInputValidationException {
		boolean isValid = false;
		Pattern pattern = Pattern.compile(PHONE_PATTERN);
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			isValid = true;
		} else {
			throw new UserInputValidationException("error!inValid phoneNumber format");
		}
		return isValid;
	}

	/**
	 * 
	 * @param email
	 * @return
	 * @throws UserInputValidationException
	 */
	public boolean validateEmail(String email) throws UserInputValidationException {
		boolean isValid = false;
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			isValid = true;
		} else {
			throw new UserInputValidationException("error!invalid email format");
		}
		return isValid;
	}

}
