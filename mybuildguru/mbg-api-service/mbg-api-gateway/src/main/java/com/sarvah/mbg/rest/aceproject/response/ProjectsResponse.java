/**
 * 
 */
package com.sarvah.mbg.rest.aceproject.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.rest.model.AbstractCollectionResponse;

/**
 * @author naveen
 *
 */

public class ProjectsResponse extends AbstractCollectionResponse {

	private List<Project> projects;

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
