package com.sarvah.mbg.commons.sms;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.sms.SmsContent;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
	private static final Logger logger = LoggerFactory
			.getLogger(SmsSenderServiceImpl.class);

	private Connection connection;
	private Session sendSession;

	private MessageProducer sender;
	private static Random randomGenerator = new Random();

	@Value("${azure.connection.factory.sms}")
	private String queuefactoryURL;

	@Value("${azure.queue.sms}")
	private String queueName;

	/**
	 * Constructor Create session and connection from connection factory using
	 * the properties
	 * 
	 * @throws Exception
	 */
	public SmsSenderServiceImpl() {

	}

	@PostConstruct
	public void init() {

		try {

			// creating JNDI context
			// set properties for connection factory and queue
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
			prop.setProperty("connectionfactory.SBCF", queuefactoryURL);
			prop.setProperty("queue.QUEUE", queueName);

			// initialize JNDI context
			Context context;
			context = new InitialContext(prop);

			// lookup connection factory and queue information from context
			ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
			Destination queue = (Destination) context.lookup("QUEUE");

			// Create a Connection
			connection = cf.createConnection();

			// add exception listener
			connection.setExceptionListener(new AzureQueueExceptionListener());

			// Create sender-side Session and MessageProducer
			sendSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			sender = sendSession.createProducer(queue);

		} catch (JMSException | NamingException e) {
			logger.error("error occured in SMSService constructor", e);
			throw new RuntimeException("Error occured creating a SMSService");
		}

	}

	/**
	 * 
	 * @throws JMSException
	 */
	@Override
	public void send(SmsContent smscontent) throws JMSException {
		// creating and setting object message
		logger.info("From  {} - To {}", smscontent.getFrom(),
				smscontent.getTo());

		try {

			composeAndSend(smscontent);

		} catch (JMSException e) {

			// quick fix to reconnect and try again
			try {

				// sleep for 100 ms
				Thread.sleep(100);

				// re-establish the connection
				reconnect();

				// send one more time
				composeAndSend(smscontent);

			} catch (InterruptedException | JMSException e1) {
				logger.error(
						"Unable to recover from JMS exception when sending smscontent to the queue, log it for reference",
						e1);
			}

		}

	}

	/**
	 * @param smscontent
	 * @throws JMSException
	 */
	private void composeAndSend(SmsContent smscontent) throws JMSException {
		ObjectMessage message = sendSession.createObjectMessage();
		message.setObject(smscontent);

		// generating random number
		long randomMessageID = randomGenerator.nextLong() >>> 1;
		randomMessageID += new Date().getTime();
		message.setJMSMessageID("ID:" + randomMessageID);

		// sending message
		sender.send(message);

		logger.info("Sent sms with JMSMessageID = " + message.getJMSMessageID());
	}

	/**
	 * Exception Listener for JMS exception
	 * 
	 * @author naveen
	 *
	 */
	private class AzureQueueExceptionListener implements ExceptionListener {
		@Override
		public void onException(JMSException exception) {
			try {
				//
				logger.error("JMS Exception has occured, trying to reconnect ",
						exception);
				// try to reconnect
				reconnect();
			} catch (JMSException e) {
				logger.error("Unable to reconnect connection to sms queue", e);

			}
		}
	}

	/**
	 * Try to reconnect costly affair
	 * 
	 * @throws JMSException
	 */
	private synchronized void reconnect() throws JMSException {
		if (connection != null && sendSession != null && sender != null) {
			close();
			init();
		}

	}

	/**
	 * Close connection and other jms objects
	 * 
	 * @throws JMSException
	 */
	private void close() throws JMSException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"closing sender,session and connection for the queue factory {}",
					queueName);
		}
		try {
			sender.close();
			sendSession.close();
			// connection.stop();
			// connection.close();
			connection=null;
		} catch (Exception e) {
			logger.error(
					"Error trying to close connection, Sender and session", e);
			// ignore and continue to create the connection again
		}

	}
}
