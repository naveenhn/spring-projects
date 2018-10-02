/**
 * 
 */
package com.sarvah.mbg.domain.ordermgmt;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sarvah.mbg.domain.ordermgmt.payment.InvoiceDetails;
import com.sarvah.mbg.domain.ordermgmt.payment.PaymentDetails;
import com.sarvah.mbg.domain.ordermgmt.shipping.OrderAddress;
import com.sarvah.mbg.domain.ordermgmt.shipping.ShippingType;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * @author naveen
 *
 */
@Entity
@Table(name = "order", schema = "mbgdb")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "mbg_order_id")
	private String mbgOrderId;

	@ManyToOne
	@JoinColumn(name = "shipping_type_id", nullable = false)
	private ShippingType shippingType;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserInfo userInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
	private List<OrderDetails> items;

	@ManyToOne
	@JoinColumn(name = "order_status_id", nullable = false)
	private OrderStatus orderStatus;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "shipping_address_id", nullable = false)
	private OrderAddress shippingAddress;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id", nullable = false)
	private OrderAddress billingAddress;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<InvoiceDetails> invoices;

	@Column(name = "createdby")
	private String createBy;

	@Column(name = "lastmodifiedby")
	private String lastModifiedBy;

	@Column(name = "ordereddate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderedDate;

	@Column(name = "lastmodifieddate_dtm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastmodifiedDate;

	@Column(name = "totalsavings")
	private double totalSavings;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PaymentDetails> paymentDetails;

	@Column(name = "shippingchrge")
	private double shippingCharge;

	@Column(name = "extbenefits")
	private double extraBenefits;

	@Column(name = "invoicenum")
	private String invoiceNumber;

	@Column(name = "paymentdone")
	private boolean paymentDone;

	/**
	 * @return the orderId
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the shippingType
	 */
	public ShippingType getShippingType() {
		return shippingType;
	}

	/**
	 * @param shippingType
	 *            the shippingType to set
	 */
	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the items
	 */
	public List<OrderDetails> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(List<OrderDetails> items) {
		this.items = items;
	}

	/**
	 * @return the orderStatus
	 */
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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
	 * @return the orderedDate
	 */
	public Date getOrderedDate() {
		return orderedDate;
	}

	/**
	 * @param orderedDate
	 *            the orderedDate to set
	 */
	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}

	/**
	 * @return the lastmodifiedDate
	 */
	public Date getLastmodifiedDate() {
		return lastmodifiedDate;
	}

	/**
	 * @param lastmodifiedDate
	 *            the lastmodifiedDate to set
	 */
	public void setLastmodifiedDate(Date lastmodifiedDate) {
		this.lastmodifiedDate = lastmodifiedDate;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy
	 *            the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the shippingAddress
	 */
	public OrderAddress getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(OrderAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @return the billingAddress
	 */
	public OrderAddress getBillingAddress() {
		return billingAddress;
	}

	/**
	 * @param billingAddress
	 *            the billingAddress to set
	 */
	public void setBillingAddress(OrderAddress billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * @return the totalSavings
	 */
	public double getTotalSavings() {
		return totalSavings;
	}

	/**
	 * @param totalSavings
	 *            the totalSavings to set
	 */
	public void setTotalSavings(double totalSavings) {
		this.totalSavings = totalSavings;
	}

	/**
	 * @return the mbgOrderId
	 */
	public String getMbgOrderId() {
		return mbgOrderId;
	}

	/**
	 * @param mbgOrderId
	 *            the mbgOrderId to set
	 */
	public void setMbgOrderId(String mbgOrderId) {
		this.mbgOrderId = mbgOrderId;
	}

	/**
	 * @return the invoices
	 */
	public Set<InvoiceDetails> getInvoices() {
		return invoices;
	}

	/**
	 * @param invoices
	 *            the invoices to set
	 */
	public void setInvoices(Set<InvoiceDetails> invoices) {
		this.invoices = invoices;
	}

	/**
	 * @return the paymentDetails
	 */
	public Set<PaymentDetails> getPaymentDetails() {
		return paymentDetails;
	}

	/**
	 * @param paymentDetails
	 *            the paymentDetails to set
	 */
	public void setPaymentDetails(Set<PaymentDetails> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public double getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(double shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	/**
	 * @return the extraBenefits
	 */
	public double getExtraBenefits() {
		return extraBenefits;
	}

	/**
	 * @param extraBenefits
	 *            the extraBenefits to set
	 */
	public void setExtraBenefits(double extraBenefits) {
		this.extraBenefits = extraBenefits;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *            the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the paymentDone
	 */
	public boolean isPaymentDone() {
		return paymentDone;
	}

	/**
	 * @param paymentDone
	 *            the paymentDone to set
	 */
	public void setPaymentDone(boolean paymentDone) {
		this.paymentDone = paymentDone;
	}
}