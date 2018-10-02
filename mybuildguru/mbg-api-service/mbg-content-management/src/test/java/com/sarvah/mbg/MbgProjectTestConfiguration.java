package com.sarvah.mbg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.commons.storage.LocalFileStorage;
import com.sarvah.mbg.project.dao.mongo.BidsDAO;
import com.sarvah.mbg.project.dao.mongo.DummyBidsDAO;
import com.sarvah.mbg.project.dao.mongo.DummyProjectDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectDAO;
import com.sarvah.mbg.project.service.ProjectService;
import com.sarvah.mbg.project.service.impl.ProjectServiceImpl;

@Configuration
public class MbgProjectTestConfiguration {
	@Bean
	public ProjectDAO projectDAO() {
		return new DummyProjectDAO();
	}
	
	@Bean
	public FileStorage localFileStorage(){
		return new LocalFileStorage();
	}

	@Bean
	public ProjectService projectService() {
		return new ProjectServiceImpl();
	}

	@Bean
	public BidsDAO bidDAO() {
		return new DummyBidsDAO();
	}
	
	

}
