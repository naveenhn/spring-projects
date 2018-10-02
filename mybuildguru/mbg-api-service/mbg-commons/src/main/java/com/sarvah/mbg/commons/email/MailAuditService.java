/**
 * 
 */
package com.sarvah.mbg.commons.email;

import com.sarvah.mbg.domain.mail.MailContent;

/**
 * @author Shivu
 *
 */
public interface MailAuditService {
	void log(MailContent mailcontent, long messageId);
	boolean isMessageIdPresent(long messageId);
}
