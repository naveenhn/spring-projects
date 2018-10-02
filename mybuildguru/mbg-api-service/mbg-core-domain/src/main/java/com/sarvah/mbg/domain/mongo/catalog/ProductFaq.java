/**
 * 
 */
package com.sarvah.mbg.domain.mongo.catalog;

import java.util.UUID;

import javax.validation.constraints.NotNull;

/**
 * @author naveen
 *
 */
public class ProductFaq {

	@NotNull
	private String id;
	@NotNull
	private String question;
	@NotNull
	private String answer;

	protected ProductFaq() {

	}

	public ProductFaq(String question, String answer) {
		this.id = UUID.randomUUID().toString();
		this.answer = answer;
		this.question = question;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question
	 *            the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * @param answer
	 *            the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
