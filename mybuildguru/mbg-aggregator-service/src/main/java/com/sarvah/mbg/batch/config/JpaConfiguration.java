package com.sarvah.mbg.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sarvah.mbg.batch.dao.jpa.UserInfoRepository;
import com.sarvah.mbg.commons.email.dao.EmailAuditRepository;
import com.sarvah.mbg.commons.email.dao.EmailAuditTypeRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditTypeRepository;

/**
 * @author naveen
 * 
 * 
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { UserInfoRepository.class,
		EmailAuditTypeRepository.class, EmailAuditRepository.class,
		SmsAuditTypeRepository.class, SmsAuditRepository.class })
public class JpaConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
