package com.sarvah.mbg.dashboard.response;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Project;

public class DashboardConstructionsCount {
	
	private String type;
	private long count;
	private List<Project> project;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<Project> getProject() {
		return project;
	}

	public void setProject(List<Project> project) {
		this.project = project;
	}

}
