package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.review.Comment;

public interface UserCommentDAO extends MongoRepository<Comment,String> {
	List<Comment> findByDiscussionId(String id);
	long countByDiscussionId(String id);

}
