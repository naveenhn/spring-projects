/**
 * 
 */
package com.sarvah.mbg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sarvah.mbg.commons.email.dao.EmailAuditRepository;
import com.sarvah.mbg.commons.email.dao.EmailAuditTypeRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditRepository;
import com.sarvah.mbg.commons.sms.dao.SmsAuditTypeRepository;
import com.sarvah.mbg.dashboard.dao.DashboardRepository;
import com.sarvah.mbg.order.dao.jpa.OrderRepository;
import com.sarvah.mbg.order.dao.jpa.UserInfoRepository;
import com.sarvah.mbg.promotion.dao.DashboardOrderDAO;
import com.sarvah.mbg.promotion.dao.DashboardUserInfoDAO;
import com.sarvah.mbg.reviews.dao.jpa.ReviewUserOrderDetailRepository;
import com.sarvah.mbg.reviews.dao.jpa.ReviewUserOrderRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserInvoiceRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserPaymentDetailsRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserPaymentTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserShippingTypeRepository;

/**
 * @author naveen
 *
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = { UserRepository.class,
		UserInfoRepository.class, OrderRepository.class,
		UserShippingTypeRepository.class, DashboardRepository.class,
		DashboardOrderDAO.class, DashboardUserInfoDAO.class,
		ReviewUserOrderRepository.class, ReviewUserOrderDetailRepository.class,
		UserPaymentTypeRepository.class, UserPaymentDetailsRepository.class,
		EmailAuditTypeRepository.class, EmailAuditRepository.class,
		SmsAuditTypeRepository.class, SmsAuditRepository.class,
		UserInvoiceRepository.class })
public class JpaConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
