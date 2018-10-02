package com.sarvah.mbg.batch;

/**
 * @author naveen
 * 
 */
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sarvah.mbg.batch.service.MBGCommandBase;


/**
 * The Class MbgAggregatorServiceApplication.
 */
@SpringBootApplication(scanBasePackages= {"com.sarvah.mbg.batch", "com.sarvah.mbg" })
public class MbgAggregatorServiceApplication implements CommandLineRunner {
	
	/** The logger. */
	private Logger logger = LoggerFactory
			.getLogger(MbgAggregatorServiceApplication.class);

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(MbgAggregatorServiceApplication.class, args);

	}

	/** The ctx. */
	@Autowired
	private ApplicationContext ctx;
	
		/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {

		logger.info("Let's inspect the beans registered to execute");
		if (args != null && args.length >= 1) {

			String beanName = StringUtils.trim(args[0]);
			if (ctx.containsBeanDefinition(beanName)) {
				logger.info("Found the matching bean to execute - {} ",
						beanName);
				MBGCommandBase command = (MBGCommandBase) ctx.getBean(beanName);				
				command.execute();
				
			} else {
				logger.info(
						"Unable to find service {} to execute, please check if the bean follows all the constructs as needed",
						args[0]);
			}
		}

	}
}
