/**
 * 
 */
package com.sarvah.mbg.domain.mongo.store;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author naveen
 *
 */
public class Shipping {
	

	@JsonProperty("national_shipping_provider")
	private String nationalShippingProvider;

	@JsonProperty("national_shipping_charge")
	private Double nationalShippingChrg;

	@JsonProperty("nationalDelivery")
	private Boolean nationalDelivery;

	@JsonProperty("zonal_shipping_provider")
	private String zonalShippingProvider;

	@JsonProperty("zonal_shipping_charge")
	private Double zonalShippingChrg;

	@JsonProperty("zonalDelivery")
	private Boolean zonalDelivery;

	@JsonProperty("local_shipping_provider")
	private String localShippingProvider;

	@JsonProperty("local_shipping_charge")
	private Double localShippingChrg;

	@JsonProperty("localDelivery")
	private Boolean localDelivery;
	
	
	@JsonProperty("min_local_procurement_sla")
	private Integer minLocalProcurementSLA;

	@JsonProperty("max_local_procurement_sla")
	private Integer maxLocalProcurementSLA;

	@JsonProperty("min_zonal_procurement_sla")
	private Integer minZonalProcurementSLA;

	@JsonProperty("max_zonal_procurement_sla")
	private Integer maxZonalProcurementSLA;

	@JsonProperty("min_national_procurement_sla")
	private Integer minNationalProcurementSLA;

	@JsonProperty("max_national_procurement_sla")
	private Integer maxNationalProcurementSLA;

	/**
	 * @return the nationalShippingProvider
	 */
	public String getNationalShippingProvider() {
		return nationalShippingProvider;
	}

	/**
	 * @param nationalShippingProvider the nationalShippingProvider to set
	 */
	public void setNationalShippingProvider(String nationalShippingProvider) {
		this.nationalShippingProvider = nationalShippingProvider;
	}

	/**
	 * @return the nationalShippingChrg
	 */
	public Double getNationalShippingChrg() {
		return nationalShippingChrg;
	}

	/**
	 * @param nationalShippingChrg the nationalShippingChrg to set
	 */
	public void setNationalShippingChrg(Double nationalShippingChrg) {
		this.nationalShippingChrg = nationalShippingChrg;
	}

	/**
	 * @return the nationalDelivery
	 */
	public Boolean getNationalDelivery() {
		return nationalDelivery;
	}

	/**
	 * @param nationalDelivery the nationalDelivery to set
	 */
	public void setNationalDelivery(Boolean nationalDelivery) {
		this.nationalDelivery = nationalDelivery;
	}

	/**
	 * @return the zonalShippingProvider
	 */
	public String getZonalShippingProvider() {
		return zonalShippingProvider;
	}

	/**
	 * @param zonalShippingProvider the zonalShippingProvider to set
	 */
	public void setZonalShippingProvider(String zonalShippingProvider) {
		this.zonalShippingProvider = zonalShippingProvider;
	}

	/**
	 * @return the zonalShippingChrg
	 */
	public Double getZonalShippingChrg() {
		return zonalShippingChrg;
	}

	/**
	 * @param zonalShippingChrg the zonalShippingChrg to set
	 */
	public void setZonalShippingChrg(Double zonalShippingChrg) {
		this.zonalShippingChrg = zonalShippingChrg;
	}

	/**
	 * @return the zonalDelivery
	 */
	public Boolean getZonalDelivery() {
		return zonalDelivery;
	}

	/**
	 * @param zonalDelivery the zonalDelivery to set
	 */
	public void setZonalDelivery(Boolean zonalDelivery) {
		this.zonalDelivery = zonalDelivery;
	}

	/**
	 * @return the localShippingProvider
	 */
	public String getLocalShippingProvider() {
		return localShippingProvider;
	}

	/**
	 * @param localShippingProvider the localShippingProvider to set
	 */
	public void setLocalShippingProvider(String localShippingProvider) {
		this.localShippingProvider = localShippingProvider;
	}

	/**
	 * @return the localShippingChrg
	 */
	public Double getLocalShippingChrg() {
		return localShippingChrg;
	}

	/**
	 * @param localShippingChrg the localShippingChrg to set
	 */
	public void setLocalShippingChrg(Double localShippingChrg) {
		this.localShippingChrg = localShippingChrg;
	}

	/**
	 * @return the localDelivery
	 */
	public Boolean getLocalDelivery() {
		return localDelivery;
	}

	/**
	 * @param localDelivery the localDelivery to set
	 */
	public void setLocalDelivery(Boolean localDelivery) {
		this.localDelivery = localDelivery;
	}

	/**
	 * @return the minLocalProcurementSLA
	 */
	public Integer getMinLocalProcurementSLA() {
		return minLocalProcurementSLA;
	}

	/**
	 * @param minLocalProcurementSLA the minLocalProcurementSLA to set
	 */
	public void setMinLocalProcurementSLA(Integer minLocalProcurementSLA) {
		this.minLocalProcurementSLA = minLocalProcurementSLA;
	}

	/**
	 * @return the maxLocalProcurementSLA
	 */
	public Integer getMaxLocalProcurementSLA() {
		return maxLocalProcurementSLA;
	}

	/**
	 * @param maxLocalProcurementSLA the maxLocalProcurementSLA to set
	 */
	public void setMaxLocalProcurementSLA(Integer maxLocalProcurementSLA) {
		this.maxLocalProcurementSLA = maxLocalProcurementSLA;
	}

	/**
	 * @return the minZonalProcurementSLA
	 */
	public Integer getMinZonalProcurementSLA() {
		return minZonalProcurementSLA;
	}

	/**
	 * @param minZonalProcurementSLA the minZonalProcurementSLA to set
	 */
	public void setMinZonalProcurementSLA(Integer minZonalProcurementSLA) {
		this.minZonalProcurementSLA = minZonalProcurementSLA;
	}

	/**
	 * @return the maxZonalProcurementSLA
	 */
	public Integer getMaxZonalProcurementSLA() {
		return maxZonalProcurementSLA;
	}

	/**
	 * @param maxZonalProcurementSLA the maxZonalProcurementSLA to set
	 */
	public void setMaxZonalProcurementSLA(Integer maxZonalProcurementSLA) {
		this.maxZonalProcurementSLA = maxZonalProcurementSLA;
	}

	/**
	 * @return the minNationalProcurementSLA
	 */
	public Integer getMinNationalProcurementSLA() {
		return minNationalProcurementSLA;
	}

	/**
	 * @param minNationalProcurementSLA the minNationalProcurementSLA to set
	 */
	public void setMinNationalProcurementSLA(Integer minNationalProcurementSLA) {
		this.minNationalProcurementSLA = minNationalProcurementSLA;
	}

	/**
	 * @return the maxNationalProcurementSLA
	 */
	public Integer getMaxNationalProcurementSLA() {
		return maxNationalProcurementSLA;
	}

	/**
	 * @param maxNationalProcurementSLA the maxNationalProcurementSLA to set
	 */
	public void setMaxNationalProcurementSLA(Integer maxNationalProcurementSLA) {
		this.maxNationalProcurementSLA = maxNationalProcurementSLA;
	}

}
