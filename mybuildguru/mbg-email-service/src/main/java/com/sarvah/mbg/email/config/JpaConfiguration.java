/**
 * 
 */
package com.sarvah.mbg.email.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sarvah.mbg.commons.email.dao.EmailAuditRepository;
import com.sarvah.mbg.commons.email.dao.EmailAuditTypeRepository;

/**
 * @author naveen
 *
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { EmailAuditTypeRepository.class,
		EmailAuditRepository.class })
@EntityScan(basePackages="com.sarvah.mbg.domain.mail.audit")
public class JpaConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
