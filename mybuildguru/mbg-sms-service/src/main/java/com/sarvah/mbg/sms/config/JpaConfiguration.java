/**
 * 
 */
package com.sarvah.mbg.sms.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sarvah.mbg.commons.sms.dao.SmsAuditRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditTypeRepository;

/**
 * @author Shivu
 *
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { SmsAuditTypeRepository.class,
		SmsAuditRepository.class })
@EntityScan(basePackages = "com.sarvah.mbg.domain.sms.audit")
public class JpaConfiguration {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
