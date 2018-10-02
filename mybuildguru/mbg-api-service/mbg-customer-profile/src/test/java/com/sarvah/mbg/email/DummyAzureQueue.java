/**
 * 
 */
package com.sarvah.mbg.email;

import java.util.PriorityQueue;
import java.util.Queue;

import com.sarvah.mbg.domain.mail.MailContent;

/**
 * @author shivu s
 *
 */
public class DummyAzureQueue {
	
	private Queue<MailContent> queue = new PriorityQueue<MailContent>();

	/**
	 * @return the queue
	 */
	public Queue<MailContent> getQueue() {
		return queue;
	}

	
	
	

}
