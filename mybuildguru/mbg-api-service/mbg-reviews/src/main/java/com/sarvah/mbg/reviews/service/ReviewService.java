/**
 * 
 */
package com.sarvah.mbg.reviews.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.sarvah.mbg.domain.mongo.review.Comment;

/**
 * @author sumanth
 *
 */
public interface ReviewService {

	/**
	 * Method to get Comments
	 * 
	 * @param discussion_id
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	List<Comment> search(String discussion_id, int page, int size, Sort sort)
			throws Exception;

	/**
	 * Method to get count of Comments
	 * 
	 * @param discussion_id
	 * @return
	 * @throws Exception
	 */
	int count(String discussion_id) throws Exception;

	/**
	 * Method to create new comment
	 * 
	 * @param ratingval
	 * @param desc
	 * @param slug
	 * @param text
	 * @param expert
	 * @param discussionId
	 * @return
	 * @throws Exception
	 */
	Comment createComment(String userId, String ratingval, String desc,
			String slug, String text, String expert, String discussionId)
			throws Exception;

	/**
	 * Method to update Comment information
	 * 
	 * @param ratingval
	 * @param desc
	 * @param slug
	 * @param text
	 * @param expert
	 * @param discussionId
	 * @param commentId
	 * @return
	 * @throws Exception
	 */
	Comment updateComment(String ratingval, String desc, String slug,
			String text, String expert, String discussionId, String commentId)
			throws Exception;

	/**
	 * Method to delete Comment
	 * 
	 * @param commentId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	String remove(String commentId, String productId) throws Exception;

}
