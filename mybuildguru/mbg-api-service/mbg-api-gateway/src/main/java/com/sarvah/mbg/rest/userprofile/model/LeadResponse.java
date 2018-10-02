/**
 * 
 */
package com.sarvah.mbg.rest.userprofile.model;

import java.util.List;

import com.sarvah.mbg.rest.model.AbstractCollectionResponse;
import com.sarvah.mbg.userprofile.ordermgmt.model.LeadProjectResponse;

/**
 * @author Shivu
 *
 */
public class LeadResponse extends AbstractCollectionResponse {
	private List<LeadProjectResponse> leadProjects;
	private int endusers;
	private int civilEngineers;
	private int civilContractors;
	private int plumbers;
	private int others;

	/**
	 * @return the leadProjects
	 */
	public List<LeadProjectResponse> getLeadProjects() {
		return leadProjects;
	}

	/**
	 * @param leadProjects
	 *            the leadProjects to set
	 */
	public void setLeadProjects(List<LeadProjectResponse> leadProjects) {
		this.leadProjects = leadProjects;
	}

	/**
	 * @return the endusers
	 */
	public int getEndusers() {
		return endusers;
	}

	/**
	 * @param endusers
	 *            the endusers to set
	 */
	public void setEndusers(int endusers) {
		this.endusers = endusers;
	}

	/**
	 * @return the civilEngineers
	 */
	public int getCivilEngineers() {
		return civilEngineers;
	}

	/**
	 * @param civilEngineers
	 *            the civilEngineers to set
	 */
	public void setCivilEngineers(int civilEngineers) {
		this.civilEngineers = civilEngineers;
	}

	/**
	 * @return the civilContractors
	 */
	public int getCivilContractors() {
		return civilContractors;
	}

	/**
	 * @param civilContractors
	 *            the civilContractors to set
	 */
	public void setCivilContractors(int civilContractors) {
		this.civilContractors = civilContractors;
	}

	/**
	 * @return the plumbers
	 */
	public int getPlumbers() {
		return plumbers;
	}

	/**
	 * @param plumbers
	 *            the plumbers to set
	 */
	public void setPlumbers(int plumbers) {
		this.plumbers = plumbers;
	}

	/**
	 * @return the others
	 */
	public int getOthers() {
		return others;
	}

	/**
	 * @param others
	 *            the others to set
	 */
	public void setOthers(int others) {
		this.others = others;
	}
}
