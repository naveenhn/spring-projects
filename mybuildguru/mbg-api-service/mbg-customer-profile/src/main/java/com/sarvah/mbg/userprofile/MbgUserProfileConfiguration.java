/**
 * 
 */
package com.sarvah.mbg.userprofile;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.userprofile.service.impl.UserValidation;

/**
 * @author naveen
 *
 */
@Configuration
public class MbgUserProfileConfiguration {
	
	@Bean
	public UserValidation userValidation() {
		return new UserValidation();
	}
	

}
