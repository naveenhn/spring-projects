/**
 * 
 */
package com.sarvah.mbg.config;

import javax.validation.Validator;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sarvah.mbg.config.populator.PopulatorBean;

/**
 * @author naveen
 *
 */
@Configuration
@ConditionalOnMissingBean({ ValidatingMongoEventListener.class })
@AutoConfigureAfter(MongoAutoConfiguration.class)
@EnableMongoAuditing
public class MongoConfiguration {

	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());

	}

//	@Bean
//	public PopulatorBean populateBean() {
//		return new PopulatorBean();
//	}
}
