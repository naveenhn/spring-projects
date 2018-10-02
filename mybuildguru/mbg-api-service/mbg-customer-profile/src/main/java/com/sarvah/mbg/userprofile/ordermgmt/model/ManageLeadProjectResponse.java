/**
 * 
 */
package com.sarvah.mbg.userprofile.ordermgmt.model;

import java.util.List;

/**
 * @author Shivu
 *
 */
public class ManageLeadProjectResponse {
	private int totalPages;
	private long totalElements;
	private int number;
	private int size;
	private List<LeadProjectResponse> leadProjects;
	private int endusers;
	private int civilEngineers;
	private int civilContractors;
	private int plumbers;
	private int others;

	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages
	 *            the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalElements
	 */
	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * @param totalElements
	 *            the totalElements to set
	 */
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

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
