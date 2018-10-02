package com.sarvah.mbg.userprofile.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.userprofile.UserRelatedDocuments;

/**
 * 
 * @author RAJU
 *
 */

public interface UserRelatedDocumentsDAO extends
		MongoRepository<UserRelatedDocuments, String> {

	List<UserRelatedDocuments> findByFileName(String fileName);

	UserRelatedDocuments findByFileNameAndActiveIsTrue(String fileName,
			boolean documentStatus);

	UserRelatedDocuments findByFileNameAndActiveIsFalse(String fileName,
			boolean status);
}
