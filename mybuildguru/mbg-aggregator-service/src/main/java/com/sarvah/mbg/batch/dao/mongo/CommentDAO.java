/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.review.Comment;

/**
 * @author Shiva
 *
 */
public interface CommentDAO extends MongoRepository<Comment, String> {

	List<Comment> findByDiscussionId(String discussionId);

	int countByDiscussionId(String discussionId);

}
