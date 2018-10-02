/**
 * 
 */
package com.sarvah.mbg.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.invoicing.DealerInvoiceGenerator;
import com.sarvah.mbg.invoicing.UserInvoiceGenerator;

/**
 * @author Shiva
 *
 */
@Configuration
public class MbgAggregatorServiceConfiguration {

	@Bean
	public UserInvoiceGenerator userInvoiceGenerator() {
		return new UserInvoiceGenerator();
	}

	@Bean
	public DealerInvoiceGenerator dealerInvoiceGenerator() {
		return new DealerInvoiceGenerator();
	}

}
