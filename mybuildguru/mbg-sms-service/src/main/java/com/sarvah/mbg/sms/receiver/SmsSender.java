package com.sarvah.mbg.sms.receiver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sarvah.mbg.domain.sms.SmsContent;
import com.sarvah.mbg.domain.sms.SmsContent.SmsType;

public class SmsSender {
	private static final Logger log = LoggerFactory.getLogger(SmsSender.class);
	@Autowired
	private HttpClient httpClient;

	@Autowired
	private VelocityEngine velocityEngine;

	@Value("${sms.service.url}")
	private String serviceURL;

	@Value("${sms.service.promotional.userid}")
	private String promoUserId;

	@Value("${sms.service.promotional.password}")
	private String promoPasswd;

	@Value("${sms.service.transactional.userid}")
	private String transUserId;

	@Value("${sms.service.transactional.password}")
	private String transPasswd;

	/**
	 * Constructor
	 */
	public SmsSender() {

	}

	/**
	 * Send mail.
	 *
	 * @param mailcontent
	 *            the mailcontent
	 * @throws Exception
	 */
	public void sendSms(SmsContent smscontent) throws Exception {

		if (smscontent != null) {

			try {
				URIBuilder uriBuilder = new URIBuilder(serviceURL);

				if (smscontent.getType().compareTo(SmsType.TRANSACTIONAL) == 0) {
					uriBuilder.addParameter("userid",
							URLEncoder.encode(transUserId, "UTF-8"))
							.addParameter("password",
									URLEncoder.encode(transPasswd, "UTF-8"));
				} else {

					uriBuilder.addParameter("userid",
							URLEncoder.encode(promoUserId, "UTF-8"))
							.addParameter("password",
									URLEncoder.encode(promoPasswd, "UTF-8"));
				}

				// now add text after checking the template name and replacing
				// values
				String vmTemplateName = smscontent.getTemplateName();
				if (StringUtils.isNotBlank(vmTemplateName)) {
					Map<String, Object> model = new HashMap<>();
					model.put("sms", smscontent);

					String smsText = VelocityEngineUtils
							.mergeTemplateIntoString(velocityEngine,
									vmTemplateName, "UTF-8", model);

					// msg to be sent
					uriBuilder.addParameter("msg", smsText);
				} else {
					// This is promotional sms
					uriBuilder.addParameter("msg", smscontent.getPromoInfo());
				}

				if (smscontent.getTo() != null && smscontent.getTo().size() > 0) {
					// TODO : we may have to implement batching for more senders
					uriBuilder
							.addParameter("send_to", URLEncoder.encode(
									StringUtils.join(smscontent.getTo(), ","),
									"UTF-8")); // phone number
				}

				// Sending sms to one/more user
				Set<String> toSet = smscontent.getTo();
				String to1 = null;
				int count = 0;
				for (String to : toSet) {
					if (count == 0) {
						to1 = to + ",";
						count++;
					} else {
						to1 = to1 + to + ",";
					}
				}
				uriBuilder.addParameter("send_to", to1);

				// build the URI
				URI uri = uriBuilder.build();

				boolean smsNotSent = true;
				int retryCount = 0;

				// the logic below will retry 3 times before giving up
				while (smsNotSent) {

					HttpGet httpget = new HttpGet(uri);
					log.info("Sending message to : {}", httpget.getURI());

					// Create a custom response handler
					ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

						@Override
						public String handleResponse(final HttpResponse response)
								throws ClientProtocolException, IOException {
							int status = response.getStatusLine()
									.getStatusCode();
							if (status >= 200 && status < 300) {
								HttpEntity entity = response.getEntity();
								return entity != null ? EntityUtils
										.toString(entity) : null;
							} else {
								throw new ClientProtocolException(
										"Unexpected response status: " + status);
							}
						}

					};

					RequestConfig.Builder requestConfigBuilder = RequestConfig
							.custom();
					requestConfigBuilder.setSocketTimeout(5000);
					requestConfigBuilder.setConnectTimeout(5000);
					requestConfigBuilder.setConnectionRequestTimeout(5000);
					httpget.setConfig(requestConfigBuilder.build());

					try {

						String responseBody = httpClient.execute(httpget,
								responseHandler);

						// if success, set the flag
						if (responseBody.contains("success")
								|| responseBody.contains("SUCCESS")) {
							log.info(
									"--------Sms sent response info-----------: {}",
									responseBody);
							smsNotSent = false;
						}

					} catch (IOException e) {
						// for any timeoutexceptions or clientprotocolexceptions
						// retry
						log.error(
								"Error occured trying to send sms, retry count {}",
								retryCount++);
						if (retryCount >= 3)
							throw e;
					}

				}

			} catch (URISyntaxException | UnsupportedEncodingException e) {
				log.error(
						"Error occured while sending the sms, URI parameters has some errors",
						e);
				throw e;
			}
		}
	}
}
