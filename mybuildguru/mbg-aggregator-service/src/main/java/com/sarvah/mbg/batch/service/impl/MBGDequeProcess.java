/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMode;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveQueueMessageResult;
import com.sarvah.mbg.batch.service.MBGCommandBase;

/**
 * @author naveen
 *
 */
@Component("MBGDequeProcess")
public class MBGDequeProcess implements MBGCommandBase  {
	


	@Value("${azure.queue.sms}")
	private String azure_sms_Queue;
	
	
	
	@Value("${azure.queue.email}")
	private String azure_email_Queue;
	
	

	@Override
	public void execute() throws Exception {
	
		  try
		  {
			  
			  
		Configuration config =
				        ServiceBusConfiguration.configureWithSASAuthentication(
				                "sarvah",
				                "mbgsmsqadm",
				                "k09tyn+QVWRxM0V2wQaWSa75R5gEWSr9Q19ldeagQAY=",
				                ".servicebus.windows.net"
				                ); 
				            	/*  	 Configuration config =
				        ServiceBusConfiguration.configureWithSASAuthentication(
				                "sarvah",
				                "mbgemailqadm",
				                "bcNzEXW4SzrX0JYw3q21VTYsrjKus+Rm3wSo7diZJwM=",
				                ".servicebus.windows.net"
				                );*/

				ServiceBusContract service = ServiceBusService.create(config);
			  
		      ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
		      opts.setReceiveMode(ReceiveMode.PEEK_LOCK);
		      int count = 0;

		      while(true)  {
		           ReceiveQueueMessageResult resultQM =
		                  service.receiveQueueMessage("emailqueue", opts);
		          BrokeredMessage message = resultQM.getValue();
		          if (message != null && message.getMessageId() != null)
		          {
		              System.out.println("MessageID: " + message.getMessageId());
		              // Display the queue message.
		              System.out.print("From queue: ");
		              count++;
		              byte[] b = new byte[300];
		              String s = null;
		              int numRead = message.getBody().read(b);
		              while (-1 != numRead)
		              {
		                  s = new String(b);
		                  s = s.trim();
		                  System.out.print(s);
		                  numRead = message.getBody().read(b);
		              }
		         
		              System.out.println("Deleting this message.");
		              service.deleteMessage(message);
		          }  
		          else  
		          {
		              System.out.println("Finishing up - no more messages.");
		              break;
		              // Added to handle no more messages.
		              // Could instead wait for more messages to be added.
		          }
		      }
		      
		      System.out.println(count);
		  }
		  catch (ServiceException e) {
		      System.out.print("ServiceException encountered: ");
		      System.out.println(e.getMessage());
		      System.exit(-1);
		  }
		  catch (Exception e) {
		      System.out.print("Generic exception encountered: ");
		      System.out.println(e.getMessage());
		      System.exit(-1);
		  }
		
	}

}
