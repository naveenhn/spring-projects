/**
 * 
 */
package com.nav.json;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author naveen
 *
 */
public class DeserializerCheck {
	
	
	public static void main(String[] args) {
		try {
			
		
		//String filepath = DeserializerCheck.class.getClassLoader().getResource("/Employee.json").getPath();
		//System.out.println(filepath);
		File jsonFile = new File(DeserializerCheck.class.getClassLoader().getResource("Employee.json").getFile());
		ObjectMapper mapper = new ObjectMapper();
		Employee employee = mapper.readValue(jsonFile, Employee.class);
		System.out.println(employee);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
