package com.sarvah.mbg.email.receiver;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.email.MailAuditService;
import com.sarvah.mbg.domain.mail.MailContent;

/**
 * The Class EmailQueueSubscriber.
 */
@Service
public class EmailQueueSubscriber implements MessageListener {

	// Create a Log for logging updates
	private static final Logger logger = LoggerFactory
			.getLogger(EmailQueueSubscriber.class);

	private static boolean runReceiver = true;

	// Create a Connection object
	private Connection connection;

	private Session receiveSession;

	private MessageConsumer receiver;

	/** The mailer. */
	@Autowired
	private Mailer mailer;

	@Value("${azure.connection.factory.email}")
	private String azure_ConnectionFactory;

	@Value("${azure.queue.email}")
	private String azure_Queue;

	@Autowired
	private MailAuditService mailAuditService;

	/**
	 * Instantiates a new email receiver service.
	 *
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * EmailReceiver implementation for configuring JNDI environment,properties
	 * and queue
	 */
	public EmailQueueSubscriber() {

	}

	@PostConstruct
	public void init() {

		try {

			// Configure JNDI environment,properties and queue
			Properties prop = new Properties();
			prop.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.qpid.amqp_1_0.jms.jndi.PropertiesFileInitialContextFactory");
			prop.setProperty("connectionfactory.SBCF", azure_ConnectionFactory);
			prop.setProperty("queue.QUEUE", azure_Queue);

			// Initialize context,connectionFactory and queue
			Context context = new InitialContext(prop);
			ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
			Destination queue = (Destination) context.lookup("QUEUE");

			// Create Connection
			connection = cf.createConnection();

			// add exception listener
			connection.setExceptionListener(new AzureQueueExceptionListener());

			if (runReceiver) {
				// Create receiver-side Session, MessageConsumer,and
				// MessageListener
				receiveSession = connection.createSession(false,
						Session.CLIENT_ACKNOWLEDGE);
				receiver = receiveSession.createConsumer(queue);
				receiver.setMessageListener(this);
				connection.start();
				logger.info("EmailQueueSubscriber started successfully!");
			}
		} catch (NamingException | JMSException e) {
			logger.error("error occured initializing EmailQueueSubscriber", e);
			throw new RuntimeException(
					"Error occured while initializing EmailQueueSubscriber");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {

			logger.info(
					"EMail Send Request recieved from Azure queue successfully with JMSMessageID = {} ",
					message.getJMSMessageID());

			// Creating instance of ObjectMessage, MailContent class an
			ObjectMessage msg = (ObjectMessage) message;
			MailContent mail = (MailContent) msg.getObject();
			if (logger.isDebugEnabled()) {
				// print the whole mail content if possible
				logger.debug("MailContent to send : {} ", mail);
			}

			String[] msgIDArray = msg.getJMSMessageID().split(":");

			long messageId = Long.parseLong(msgIDArray[1]);

			if (mailAuditService.isMessageIdPresent(messageId)) {

				logger.info(
						"Message with same JMSSessionId already processed!!, so ignoring to process : {}",
						messageId);

			} else {

				// send mail
				mailer.sendMail(mail);

				// IMP, log the mail content with session id after sending the
				// mail
				mailAuditService.log(mail, messageId);

			}

		} catch (JMSException e) {
			// Updating the logger information on failure
			logger.error("Exception in onMessage()", e);
		} finally {
			// acknowledge the message so it can be removed from queue
			try {
				message.acknowledge();
				Thread.sleep(50);
			} catch (JMSException | InterruptedException e) {
				logger.error("Exception in onMessage()", e);
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
		if (connection != null && receiveSession != null && receiver != null) {
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
					"closing receiver,session and connection for the queue factory {}",
					azure_Queue);
		}

		try {
			receiver.close();
			receiveSession.close();
			// connection.stop();
			// connection.close();
			connection = null;
		} catch (Exception e) {
			logger.error(
					"Error trying to close connection, receiver and session", e);
			// ignore and continue to create the connection again
		}

	}
}