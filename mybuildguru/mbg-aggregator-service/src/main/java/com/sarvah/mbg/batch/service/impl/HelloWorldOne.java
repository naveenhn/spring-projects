/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sarvah.mbg.batch.service.MBGCommandBase;

/**
 * @author naveen
 *
 */
@Component("HelloWorldOne")
public class HelloWorldOne implements MBGCommandBase{
	

	@Value("${name:World}")
	private String name;

	

	@Override
	public void execute() {
		System.out.println( "First service running...... Hello " + this.name);
		
	}

}
