/**
 * 
 *//*
package com.sarvah.mbg.domain.mongo.config;

import javax.validation.Validator;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

*//**
 * @author naveen
 *
 *//*
@Configuration
@ConditionalOnMissingBean({ValidatingMongoEventListener.class})
@AutoConfigureAfter(MongoAutoConfiguration.class)
@EnableMongoAuditing
public class MongoValidationConfiguration {
	
	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {		
		return new ValidatingMongoEventListener(validator());
		
	}
	
}
*/