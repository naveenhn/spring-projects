/*package com.sarvah.mbg.project.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sarvah.mbg.MbgProjectTestConfiguration;
import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.project.service.ProjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MbgProjectTestConfiguration.class)
public class ProjectServiceImplTest {
	@Autowired
	private ProjectService projectService;

	@Test
	public void contextLoads() {
		assertEquals(1, 1);
	}

	@Test
	public void testGetSystemProject() {
		List<Project> project = projectService.getSystemProject("MyBuildGuru",
				null, null, null, null, null, null, null, null, null,null, null,
				null,null);
		assertTrue(project.size() == 1);
	}
	
	@Test
	public void testGetProjectById() throws Exception{
		
		Project project=projectService.getProjectById("11");
		
		assertEquals("MyBuildGuru",project.getName());
		
	}

	@Test
	public void testCountProjectBidsById() throws Exception{
		
		long bidCount=projectService.countProjectBidsById("11");
		
		assertEquals(50l,bidCount);
		
	}
	
	@Test
	public void testCreateBid() throws Exception{
		
		Bid bid=projectService.createBid("1", "bid now", "22", "22255");
		
		Integer amt=Integer.parseInt("22255");
		assertEquals(bid.getQuoteAmount(),amt);
		
	}

	@Test
	public void testUpdateBidForProject() throws Exception {
		Bid bid = projectService.updateBidForProject("123", "2",
				"bid for Project", "275000");
		String quoteAmount = String.valueOf(bid.getQuoteAmount());
		assertEquals("275000", quoteAmount);
	}

	@Test
	public void testDeleteBidForProject() throws Exception {
		String bidId = projectService.deleteBidForProject("123", "4");
		assertEquals("4", bidId);
	}
}
*/