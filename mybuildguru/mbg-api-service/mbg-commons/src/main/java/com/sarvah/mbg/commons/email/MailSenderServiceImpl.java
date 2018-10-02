package com.sarvah.mbg.commons.email;

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

import com.sarvah.mbg.domain.mail.MailContent;

@Service
public class MailSenderServiceImpl implements MailSenderService {

	private static final Logger logger = LoggerFactory
			.getLogger(MailSenderServiceImpl.class);

	private Connection connection;
	private Session sendSession;

	private MessageProducer sender;
	private static Random randomGenerator = new Random();

	@Value("${azure.connection.factory.email}")
	private String queuefactoryURL;

	@Value("${azure.queue.email}")
	private String queueName;

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public MailSenderServiceImpl() {

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

			if (logger.isDebugEnabled()) {
				logger.debug(
						"Setting email queue factory url : {} and email queue : {}",
						queuefactoryURL, queueName);
			}

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
			logger.error("error occured in MailSenderImpl constructor", e);
			throw new RuntimeException(
					"Error occured while initializing MailSenderService");

		}
	}

	/**
	 * Send method to send Information to Queue
	 * 
	 * @throws JMSException
	 */
	@Override
	public void send(MailContent mailcontent) {
		// creating and setting object message
		logger.info(
				"From  {} - To {} - Subject {} - FirstName {} - LastName {} - TemplateName {} - BCC {} - Token {}",
				mailcontent.getFrom(), mailcontent.getTo(),
				mailcontent.getSubjectInfo(), mailcontent.getFirstName(),
				mailcontent.getLastName(), mailcontent.getTemplateName(),
				mailcontent.getBcc(), mailcontent.getToken());

		try {

			ObjectMessage message = sendSession.createObjectMessage();
			message.setObject(mailcontent);

			// generating random number
			long randomMessageID = randomGenerator.nextLong() >>> 1;
			randomMessageID += new Date().getTime();

			message.setJMSMessageID("ID:" + randomMessageID);

			// sending message with message id
			sender.send(message);

			logger.info("Sent message with JMSMessageID = "
					+ message.getJMSMessageID());

		} catch (JMSException e) {
			// retry after 200ms - second try and then silent exit
			try {
				// 2ooms
				Thread.sleep(200);
				// reconnect
				reconnect();

				ObjectMessage message = sendSession.createObjectMessage();

				message.setObject(mailcontent);

				// generating random number
				long randomMessageID = randomGenerator.nextLong() >>> 1;
				message.setJMSMessageID("ID:" + randomMessageID);

				// sending message with message id
				sender.send(message);

				logger.info("Sent message with JMSMessageID = "
						+ message.getJMSMessageID());

			} catch (InterruptedException | JMSException e1) {
				logger.error(
						"Unable to recover from JMS exception when sending mailcontent to the queue",
						e);
			}

		}
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
				logger.error("Unable to reconnect connection to email queue", e);

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
			connection = null;
		} catch (Exception e) {
			logger.error(
					"Error trying to close connection, sender and session", e);
			// ignore and continue to create the connection again
		}

	}

}
