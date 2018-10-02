package com.sarvah.mbg.email.receiver;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sarvah.mbg.domain.mail.MailContent;

/**
 * The Class Mailer.
 */
public class Mailer {

	private static final Logger log = LoggerFactory.getLogger(Mailer.class);

	/** The mail sender. */
	private JavaMailSender mailSender;

	/** The velocity engine. */
	private VelocityEngine velocityEngine;

	/**
	 * Instantiates a new mailer.
	 *
	 * @param mailSender
	 *            the mail sender
	 * @param velocityEngine
	 *            the velocity engine
	 */
	public Mailer(JavaMailSender mailSender, VelocityEngine velocityEngine) {
		this.mailSender = mailSender;
		this.velocityEngine = velocityEngine;
	}

	/**
	 * Send mail.
	 *
	 * @param mailcontent
	 *            the mailcontent
	 */
	public void sendMail(MailContent mailcontent) {
		// Creating an instance of MimeMessagePreparator
		MimeMessagePreparator preparator = getMimeMessagePreparator(mailcontent);
		this.mailSender.send(preparator);
	}

	/**
	 * Gets the mime message preparator.
	 *
	 * @param mailcontent
	 *            the mailcontent
	 * @return the mime message preparator
	 */
	private MimeMessagePreparator getMimeMessagePreparator(
			MailContent mailcontent) {
		return new MimeMessagePreparator() {
			/*
			 * Method for creating mail template with from,to and the subject
			 * for the mail to be sent
			 */
			public void prepare(MimeMessage mimeMessage) throws Exception {

				String vmTemplateName = mailcontent.getTemplateName();
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				// Later have to uncomment below line and set it to debug level
				// if (log.isDebugEnabled()) {
				log.info(
						"From {} - To {} - Subject {} - FullName {} - TemplateName {} - Bcc {} - Token {} - OTP {} - OrderId {} - ItemName {} - ItemCount {} - OrderAmount {} - ItemInfoForCommunication {}",
						mailcontent.getFrom(), mailcontent.getTo(),
						mailcontent.getSubjectInfo(),
						mailcontent.getFullName(),
						mailcontent.getTemplateName(), mailcontent.getBcc(),
						mailcontent.getToken(), mailcontent.getOtp(),
						mailcontent.getOrderId(), mailcontent.getItemName(),
						mailcontent.getItemCount(),
						mailcontent.getOrderAmount(),
						mailcontent.getItemInfoForCommunicationList());
				// }

				// Setting the "To" address
				message.setTo(mailcontent.getTo().toArray(
						new String[mailcontent.getTo().size()]));
				// Setting the "From" address
				message.setFrom(mailcontent.getFrom());
				message.setBcc(mailcontent.getBcc().toArray(
						new String[mailcontent.getBcc().size()]));
				// Setting the "Subject"
				message.setSubject(mailcontent.getSubjectInfo());
				Map<String, Object> model = new HashMap<>();
				model.put("mail", mailcontent);

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, vmTemplateName, "UTF-8", model);

				message.setText(text, true);

			}
		};
	}
}
