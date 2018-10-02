package com.sarvah.mbg.config.populator;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.geo.Point;

import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;

public class ProjectPopulatorBean {
	UserProjectDAO userProjectDAO;
	UserDAO userDAO;

	ProjectPopulatorBean(UserProjectDAO userProjectDAO, UserDAO userDAO) {
		this.userProjectDAO = userProjectDAO;
		this.userDAO = userDAO;
		}

	public void initProject() {
		userProjectDAO.deleteAll();
		/*// project1
		User user = userDAO.findByUsername("admin@havells.com");
		if (user != null) {
			Project project = new Project();
			project.setName("Apartment");

			Description desc = new Description("eng", "Apartment Project");
			project.setDesc(desc);

			project.setBudget(50000.00);

			project.setType(ProjectType.COMMERCIAL);
			project.setUserType("ARCHITECT");

			Address address1 = new Address();
			address1.setAddressId(1);
			address1.setAddressLine1("No 42, Navodaya");
			address1.setAddressLine2("23rd Cross, Ittamadu Main Rd, Bsk 3rd Stage");
			address1.setCity("Bengaluru");
			address1.setState("karnataka");
			address1.setCountry("India");
			address1.setZipcode(571114);
			address1.setEmail("admin@jaquar123.com");
			Point point1 = new Point(12.827124, 77.682919);
			address1.setLocation(point1);

			project.setAddress(address1);
			project.setUser(user);
			Set<String> productIds = new HashSet<>();
			productIds.add("115");
			productIds.add("227");
			productIds.add("347");
			project.setProductIds(productIds);
			project.setStatus("INACTIVE");
			project.setConstructionNew(true);
			userProjectDAO.insert(project);
		}
		// project2

		User user1 = userDAO.findByUsername("admin@havells.com");
		if (user1 != null) {
			Project project1 = new Project();
			project1.setName("Villa");

			Description desc1 = new Description("eng", "Villa project");
			project1.setDesc(desc1);

			project1.setBudget(100000.00);

			project1.setType(ProjectType.RESIDENTIAL);
			project1.setUserType("INTERIOR DESGINER");

			Address address2 = new Address();
			address2.setAddressId(1);
			address2.setAddressLine1("No 125, Navodaya");
			address2.setAddressLine2("23rd Cross, Ittamadu Main Rd, Bsk 3rd Stage");
			address2.setCity("Bengaluru");
			address2.setState("karnataka");
			address2.setCountry("India");
			address2.setZipcode(571114);
			address2.setEmail("admin@jaquar123.com");
			Point point2 = new Point(12.827124, 77.682919);
			address2.setLocation(point2);

			project1.setAddress(address2);
			project1.setUser(user1);
			Set<String> productIds1 = new HashSet<>();
			productIds1.add("115");
			productIds1.add("227");
			productIds1.add("347");
			project1.setProductIds(productIds1);
			project1.setConstructionNew(true);
			project1.setStatus("ACTIVE");

			userProjectDAO.insert(project1);

		}

		// project3

		User user2 = userDAO.findByUsername("admin@havells.com");
		if (user2 != null) {
			Project project2 = new Project();
			project2.setName("Independent house");

			Description desc1 = new Description("eng",
					"Independent house project");
			project2.setDesc(desc1);

			project2.setBudget(50000.00);

			project2.setType(ProjectType.RESIDENTIAL);
			project2.setUserType("ARCHITECT");

			Address address2 = new Address();
			address2.setAddressId(1);
			address2.setAddressLine1("No 125, Navodaya");
			address2.setAddressLine2("23rd Cross, Ittamadu Main Rd, Bsk 3rd Stage");
			address2.setCity("Bengaluru");
			address2.setState("karnataka");
			address2.setCountry("India");
			address2.setZipcode(571114);
			address2.setEmail("admin@jaquar123.com");
			Point point2 = new Point(12.827124, 77.682919);
			address2.setLocation(point2);

			project2.setAddress(address2);
			project2.setUser(user2);
			Set<String> productIds1 = new HashSet<>();
			productIds1.add("115");
			productIds1.add("227");
			productIds1.add("347");
			project2.setProductIds(productIds1);
			project2.setStatus("ACTIVE");
			project2.setConstructionNew(false);
			userProjectDAO.insert(project2);
		}*/
	}
}
