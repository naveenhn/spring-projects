/**
 * 
 */
package com.sarvah.mbg.domain.mongo.aceproject;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.sarvah.mbg.domain.mongo.common.AbstractDocument;

/**
 * @author Shivu
 *
 */
@Document
public class TeleCallHistory extends AbstractDocument {
	@NotNull(message = "teleId should not be null")
	private String teleId;

	private int endUsers;
	private int civilEngineers;
	private int civilContractors;
	private int plumbers;
	private int others;

	private int requirementGiven;
	private int switchedOff;
	private int wrongNumber;
	private int notPicked;
	private int callAfterOneToTwoDays;
	private int callAfterOneToTwoWeeks;
	private int callAfterFifteenToThirtyDays;
	private int callAfterThirtyToSixtyDays;
	private int anySpecific;

	private int requirementGivenNewLead;
	private int callAfterOneToTwoDaysNewLead;
	private int callAfterOneToTwoWeeksNewLead;
	private int callAfterFifteenToThirtyDaysNewLead;
	private int callAfterThirtyToSixtyDaysNewLead;
	private int anySpecificNewLead;

	/**
	 * @return the teleId
	 */
	public String getTeleId() {
		return teleId;
	}

	/**
	 * @param teleId
	 *            the teleId to set
	 */
	public void setTeleId(String teleId) {
		this.teleId = teleId;
	}

	/**
	 * @return the endUsers
	 */
	public int getEndUsers() {
		return endUsers;
	}

	/**
	 * @param endUsers
	 *            the endUsers to set
	 */
	public void setEndUsers(int endUsers) {
		this.endUsers = endUsers;
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

	/**
	 * @return the requirementGiven
	 */
	public int getRequirementGiven() {
		return requirementGiven;
	}

	/**
	 * @param requirementGiven
	 *            the requirementGiven to set
	 */
	public void setRequirementGiven(int requirementGiven) {
		this.requirementGiven = requirementGiven;
	}

	/**
	 * @return the switchedOff
	 */
	public int getSwitchedOff() {
		return switchedOff;
	}

	/**
	 * @param switchedOff
	 *            the switchedOff to set
	 */
	public void setSwitchedOff(int switchedOff) {
		this.switchedOff = switchedOff;
	}

	/**
	 * @return the wrongNumber
	 */
	public int getWrongNumber() {
		return wrongNumber;
	}

	/**
	 * @param wrongNumber
	 *            the wrongNumber to set
	 */
	public void setWrongNumber(int wrongNumber) {
		this.wrongNumber = wrongNumber;
	}

	/**
	 * @return the notPicked
	 */
	public int getNotPicked() {
		return notPicked;
	}

	/**
	 * @param notPicked
	 *            the notPicked to set
	 */
	public void setNotPicked(int notPicked) {
		this.notPicked = notPicked;
	}

	/**
	 * @return the callAfterOneToTwoDays
	 */
	public int getCallAfterOneToTwoDays() {
		return callAfterOneToTwoDays;
	}

	/**
	 * @param callAfterOneToTwoDays
	 *            the callAfterOneToTwoDays to set
	 */
	public void setCallAfterOneToTwoDays(int callAfterOneToTwoDays) {
		this.callAfterOneToTwoDays = callAfterOneToTwoDays;
	}

	/**
	 * @return the callAfterOneToTwoWeeks
	 */
	public int getCallAfterOneToTwoWeeks() {
		return callAfterOneToTwoWeeks;
	}

	/**
	 * @param callAfterOneToTwoWeeks
	 *            the callAfterOneToTwoWeeks to set
	 */
	public void setCallAfterOneToTwoWeeks(int callAfterOneToTwoWeeks) {
		this.callAfterOneToTwoWeeks = callAfterOneToTwoWeeks;
	}

	/**
	 * @return the callAfterFifteenToThirtyDays
	 */
	public int getCallAfterFifteenToThirtyDays() {
		return callAfterFifteenToThirtyDays;
	}

	/**
	 * @param callAfterFifteenToThirtyDays
	 *            the callAfterFifteenToThirtyDays to set
	 */
	public void setCallAfterFifteenToThirtyDays(int callAfterFifteenToThirtyDays) {
		this.callAfterFifteenToThirtyDays = callAfterFifteenToThirtyDays;
	}

	/**
	 * @return the callAfterThirtyToSixtyDays
	 */
	public int getCallAfterThirtyToSixtyDays() {
		return callAfterThirtyToSixtyDays;
	}

	/**
	 * @param callAfterThirtyToSixtyDays
	 *            the callAfterThirtyToSixtyDays to set
	 */
	public void setCallAfterThirtyToSixtyDays(int callAfterThirtyToSixtyDays) {
		this.callAfterThirtyToSixtyDays = callAfterThirtyToSixtyDays;
	}

	/**
	 * @return the anySpecific
	 */
	public int getAnySpecific() {
		return anySpecific;
	}

	/**
	 * @param anySpecific
	 *            the anySpecific to set
	 */
	public void setAnySpecific(int anySpecific) {
		this.anySpecific = anySpecific;
	}

	/**
	 * @return the requirementGivenNewLead
	 */
	public int getRequirementGivenNewLead() {
		return requirementGivenNewLead;
	}

	/**
	 * @param requirementGivenNewLead
	 *            the requirementGivenNewLead to set
	 */
	public void setRequirementGivenNewLead(int requirementGivenNewLead) {
		this.requirementGivenNewLead = requirementGivenNewLead;
	}

	/**
	 * @return the callAfterOneToTwoDaysNewLead
	 */
	public int getCallAfterOneToTwoDaysNewLead() {
		return callAfterOneToTwoDaysNewLead;
	}

	/**
	 * @param callAfterOneToTwoDaysNewLead
	 *            the callAfterOneToTwoDaysNewLead to set
	 */
	public void setCallAfterOneToTwoDaysNewLead(int callAfterOneToTwoDaysNewLead) {
		this.callAfterOneToTwoDaysNewLead = callAfterOneToTwoDaysNewLead;
	}

	/**
	 * @return the callAfterOneToTwoWeeksNewLead
	 */
	public int getCallAfterOneToTwoWeeksNewLead() {
		return callAfterOneToTwoWeeksNewLead;
	}

	/**
	 * @param callAfterOneToTwoWeeksNewLead
	 *            the callAfterOneToTwoWeeksNewLead to set
	 */
	public void setCallAfterOneToTwoWeeksNewLead(
			int callAfterOneToTwoWeeksNewLead) {
		this.callAfterOneToTwoWeeksNewLead = callAfterOneToTwoWeeksNewLead;
	}

	/**
	 * @return the callAfterFifteenToThirtyDaysNewLead
	 */
	public int getCallAfterFifteenToThirtyDaysNewLead() {
		return callAfterFifteenToThirtyDaysNewLead;
	}

	/**
	 * @param callAfterFifteenToThirtyDaysNewLead
	 *            the callAfterFifteenToThirtyDaysNewLead to set
	 */
	public void setCallAfterFifteenToThirtyDaysNewLead(
			int callAfterFifteenToThirtyDaysNewLead) {
		this.callAfterFifteenToThirtyDaysNewLead = callAfterFifteenToThirtyDaysNewLead;
	}

	/**
	 * @return the callAfterThirtyToSixtyDaysNewLead
	 */
	public int getCallAfterThirtyToSixtyDaysNewLead() {
		return callAfterThirtyToSixtyDaysNewLead;
	}

	/**
	 * @param callAfterThirtyToSixtyDaysNewLead
	 *            the callAfterThirtyToSixtyDaysNewLead to set
	 */
	public void setCallAfterThirtyToSixtyDaysNewLead(
			int callAfterThirtyToSixtyDaysNewLead) {
		this.callAfterThirtyToSixtyDaysNewLead = callAfterThirtyToSixtyDaysNewLead;
	}

	/**
	 * @return the anySpecificNewLead
	 */
	public int getAnySpecificNewLead() {
		return anySpecificNewLead;
	}

	/**
	 * @param anySpecificNewLead
	 *            the anySpecificNewLead to set
	 */
	public void setAnySpecificNewLead(int anySpecificNewLead) {
		this.anySpecificNewLead = anySpecificNewLead;
	}
}
