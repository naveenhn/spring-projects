/**
 * 
 */
package com.nav.json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author naveen
 *
 */
public class Employee {
	@JsonProperty
	private String name;
	@JsonProperty
	private Double salary;
	@JsonProperty
	private Integer id;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
