/**
 * 
 */
package com.sarvah.mbg.batch.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.sarvah.mbg.batch.model.Employee;
import com.sarvah.mbg.batch.service.MBGCommandBase;

/**
 * @author naveen
 *
 */
@Component("HelloWorldTwo")
public class HelloWorldTwo implements MBGCommandBase {
	@Value("${name:Naveen}")
	private String name;

	

	@Override
	public void execute() {
		System.out.println( "Second Service running ==== CSV Parser");
		try {
			parseCSVToBean();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void parseCSVToBean() throws FileNotFoundException {
		
		
		/*HeaderColumnNameTranslateMappingStrategy<Employee> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Employee>();
		beanStrategy.setType(Employee.class);
         
        Map<String, String> columnMapping = new HashMap<String, String>();
        columnMapping.put("ID", "id");
        columnMapping.put("Name", "name");
        columnMapping.put("Role", "role");
        beanStrategy.setColumnMapping(columnMapping);*/
        
        
        
        ColumnPositionMappingStrategy<Employee> strat = new ColumnPositionMappingStrategy<>(); 
        strat.setType(Employee.class);
        String[] columns = new String[] {"id", "name", "role"}; // the fields to bind do in your JavaBean
        strat.setColumnMapping(columns);
       
         
        
         
        CsvToBean<Employee> csvToBean = new CsvToBean<Employee>();
        CSVReader reader = new CSVReader(new FileReader("/tmp/sample.csv"), ',', '\"', 1);
        List<Employee> emps = csvToBean.parse(strat, reader);
        
        for (Employee employee : emps) {
        	System.out.println(employee);
		}
        
	}

	

}
