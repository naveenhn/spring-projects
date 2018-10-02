/**
 * 
 */
package com.sarvah.mbg.domain.mongo.review;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author naveen
 *
 */
@Document
public class Comment extends AbstractDocument{
	
	
	
	
	private Rating rating;
	
	@NotNull (message = "Comment discussionId cannot be null")
	private String discussionId;//discussionId is unique
	private String slug;
	private CommentType commentType;
	
	@Size(max=250)
	private String text;
	
		
	@NotNull (message = "Comment author cannot be null")
	private Author author;
	
	/**
	 * @return the rating
	 */
	public Rating getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
	/**
	 * @param slug the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}
	/**
	 * @return the discussionId
	 */
	public String getDiscussionId() {
		return discussionId;
	}
	/**
	 * @param discussionId the discussionId to set
	 */
	public void setDiscussionId(String discussionId) {
		this.discussionId = discussionId;
	}

	/**
	 * @return the commentType
	 */
	public CommentType getCommentType() {
		return commentType;
	}

	/**
	 * @param commentType
	 *            the commentType to set
	 */
	public void setCommentType(CommentType commentType) {
		this.commentType = commentType;
	}
	
	
	
	
	
	
	
	
}
