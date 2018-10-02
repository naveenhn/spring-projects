/**
 * 
 */
package com.sarvah.mbg.batch.dao.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sarvah.mbg.domain.mongo.aceproject.Project;

/**
 * @author Shiva
 *
 */
public interface ProjectDAO extends MongoRepository<Project, String> {

}
