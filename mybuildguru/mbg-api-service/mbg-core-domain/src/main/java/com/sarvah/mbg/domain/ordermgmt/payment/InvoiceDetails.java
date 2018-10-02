/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt.payment;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sarvah.mbg.domain.ordermgmt.Order;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "invoice_details", schema = "mbgdb")
public class InvoiceDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "invoice_details_id")
	private int taxInvoiceId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@Column(name = "file_url")
	private String fileUrl;

	@Column(name = "invoicedate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date invoiceDate;

	@Column(name = "lastmodifieddate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Column(name = "createdby")
	private String createdBy;

	@Column(name = "lastmodifiedby")
	private String lastModifiedBy;

	/** Flag to check whether mail sent to Enduser **/
	@Column(name = "senttouser")
	private boolean sentTouser;

	/** UserId reference to identify for which invoice belongs **/
	@Column(name = "userid")
	private String userId;

	/** MBG invoice ID **/
	@Column(name = "mbginvoiceid")
	private String mbgInvoiceId;

	/**
	 * @return the taxInvoiceId
	 */
	public int getTaxInvoiceId() {
		return taxInvoiceId;
	}

	/**
	 * @param taxInvoiceId
	 *            the taxInvoiceId to set
	 */
	public void setTaxInvoiceId(int taxInvoiceId) {
		this.taxInvoiceId = taxInvoiceId;
	}

	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * @return the invoiceDate
	 */
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	/**
	 * @param invoiceDate
	 *            the invoiceDate to set
	 */
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * @param fileUrl
	 *            the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the mbgInvoiceId
	 */
	public String getMbgInvoiceId() {
		return mbgInvoiceId;
	}

	/**
	 * @param mbgInvoiceId
	 *            the mbgInvoiceId to set
	 */
	public void setMbgInvoiceId(String mbgInvoiceId) {
		this.mbgInvoiceId = mbgInvoiceId;
	}

	/**
	 * @return the sentTouser
	 */
	public boolean isSentTouser() {
		return sentTouser;
	}

	/**
	 * @param sentTouser
	 *            the sentTouser to set
	 */
	public void setSentTouser(boolean sentTouser) {
		this.sentTouser = sentTouser;
	}
}