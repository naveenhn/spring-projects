/**
 * 
 */
package com.sarvah.mbg.commons.sms;

import com.sarvah.mbg.domain.sms.SmsContent;

/**
 * @author Shivu
 *
 */
public interface SmsAuditService {
	void log(SmsContent mailcontent, long messageId);

	boolean isMessageIdPresent(long messageId);
}
