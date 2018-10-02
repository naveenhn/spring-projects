package com.sarvah.mbg.project.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.commons.storage.AzureFileStorage;
import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.dashboard.service.impl.DashboardProjectComparator;
import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.project.dao.mongo.BidsDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectDAO;
import com.sarvah.mbg.project.dao.mongo.ProjectUserDAO;
import com.sarvah.mbg.project.dao.mongo.UserRoleDAO;
import com.sarvah.mbg.project.model.BidCountResponse;
import com.sarvah.mbg.project.model.BidDescriptionResponse;
import com.sarvah.mbg.project.model.BudgetResponse;
import com.sarvah.mbg.project.model.ProjectDescriptionResponse;
import com.sarvah.mbg.project.privilege.service.ProjectPrivilegeService;
import com.sarvah.mbg.project.service.ProjectService;
import com.sarvah.mbg.promotion.dao.DashboardUserInfoDAO;

@Service
public class ProjectServiceImpl implements ProjectService {

	private static final Logger logger = LoggerFactory
			.getLogger(ProjectServiceImpl.class);

	@Autowired
	private ProjectDAO projectDAO;

	@Autowired
	private BidsDAO bidsDAO;

	@Autowired
	private FileStorage filestorage;

	@Autowired
	private ProjectUserDAO userDAO;

	@Autowired
	private ProjectPrivilegeService projectPrivilegeService;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Autowired
	private UserRoleDAO userRoleDAO;

	// @Autowired
	// private BidUserAuditRepository userAuditRepository;
	//
	// @Autowired
	// private BidUserAuditTypeRepository userAuditTypeRepository;

	@Autowired
	private DashboardUserInfoDAO userRepository;

	/**
	 * Method to get all projects
	 * 
	 * @param name
	 * @param budget
	 * @param constructionType
	 * @param projectType
	 * @param status
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param email
	 * @param phoneType
	 * @param point
	 * @return
	 * @throws Exception
	 */
	@Override
	public Set<ProjectDescriptionResponse> getSystemProject(String name,
			String budget, String constructionType, String projectType,
			String status, String addressLine1, String addressLine2,
			String city, String state, String country, String zipcode,
			String email, String phoneType, String roleName, Point point)
			throws Exception {
		Set<Project> allProjects = new HashSet<>();

		Set<ProjectDescriptionResponse> projectResponseList = new HashSet<>();
		logger.info("finding all the projects");
		if (StringUtils.isBlank(name) && StringUtils.isBlank(budget)
				&& StringUtils.isBlank(projectType)
				&& StringUtils.isBlank(constructionType)
				&& StringUtils.isBlank(status)
				&& StringUtils.isBlank(addressLine1)
				&& StringUtils.isBlank(addressLine2)
				&& StringUtils.isBlank(city) && StringUtils.isBlank(state)
				&& StringUtils.isBlank(country) && StringUtils.isBlank(zipcode)
				&& StringUtils.isBlank(email) && point == null) {
			allProjects.addAll(projectDAO.findByStatusAllIgnoreCase("ACTIVE"));

			for (Project project : allProjects) {
				if (project.getUserType().equalsIgnoreCase(roleName)
						|| project.getUserType().equalsIgnoreCase(
								"BOTHARCHITECTANDINTERIORDESIGNER")
						|| roleName.equalsIgnoreCase("SUPERADMIN")) {

					ProjectDescriptionResponse projectResponse = new ProjectDescriptionResponse();
					// ProjectId
					projectResponse.setProjectId(project.getId());
					// First name
					if (project.getUser().getFirstName() != null) {
						projectResponse.setFirstName(project.getUser()
								.getFirstName());
					}
					// Last name
					if (project.getUser().getLastName() != null) {
						projectResponse.setLastName(project.getUser()
								.getLastName());
					}
					// Full name
					if (project.getUser().getFirstName() != null
							&& project.getUser().getLastName() != null) {
						projectResponse.setFullName(project.getUser()
								.getFirstName()
								+ " "
								+ project.getUser().getLastName());
					} else if (project.getUser().getFirstName() != null) {
						projectResponse.setFullName(project.getUser()
								.getFirstName());
					}
					// Project name
					projectResponse.setProjectName(project.getName());
					// Project address
					projectResponse.setAddress(project.getAddress());
					// Project description
					projectResponse.setDescription(project.getDesc().getVal());
					// Project type
					projectResponse.setProjectType(project.getType().name());
					// user image
					projectResponse.setUserImage(project.getUser()
							.getProfilePicture());
					// budget
					projectResponse.setBudget(project.getBudget());
					// Created date
					projectResponse.setCreatedDate(project.getCreatedDate());
					// user id
					projectResponse.setUserId(project.getUser().getId());
					// construction type
					if (project.isConstructionNew()) {
						projectResponse.setConstrutionType(true);
					} else {
						projectResponse.setConstrutionType(false);
					}
					// status
					if (project.getStatus() != null) {
						projectResponse.setStatus(project.getStatus());
					}
					projectResponseList.add(projectResponse);
				}
			}
			return projectResponseList;
		} else {
			if (StringUtils.isNotBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;
					boolean constructionTypeVal = Boolean
							.parseBoolean(constructionType);
					int zipcodeVal = Integer.parseInt(zipcode);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
											min, max, projectType,
											constructionTypeVal, zipcodeVal,
											"ACTIVE"));
				}
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;
					boolean constructionTypeVal = Boolean
							.parseBoolean(constructionType);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndTypeAndConstructionNewAndStatusAllIgnoreCase(
											min, max, projectType,
											constructionTypeVal, "ACTIVE"));
				}
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;

					int zipcodeVal = Integer.parseInt(zipcode);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
											min, max, projectType, zipcodeVal,
											"ACTIVE"));
				}
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;
					boolean constructionTypeVal = Boolean
							.parseBoolean(constructionType);
					int zipcodeVal = Integer.parseInt(zipcode);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
											min, max, constructionTypeVal,
											zipcodeVal, "ACTIVE"));
				}
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {

				boolean constructionTypeVal = Boolean
						.parseBoolean(constructionType);
				int zipcodeVal = Integer.parseInt(zipcode);
				allProjects
						.addAll(projectDAO
								.findByTypeAndConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
										projectType, constructionTypeVal,
										zipcodeVal, "ACTIVE"));
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;

					allProjects.addAll(projectDAO
							.findByBudgetBetweenAndTypeAndStatusAllIgnoreCase(
									min, max, projectType, "ACTIVE"));
				}
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;
					boolean constructionTypeVal = Boolean
							.parseBoolean(constructionType);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndConstructionNewAndStatusAllIgnoreCase(
											min, max, constructionTypeVal,
											"ACTIVE"));
				}
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;
					int zipcodeVal = Integer.parseInt(zipcode);
					allProjects
							.addAll(projectDAO
									.findByBudgetBetweenAndAddress_ZipcodeAndStatusAllIgnoreCase(
											min, max, zipcodeVal, "ACTIVE"));
				}
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {

				boolean constructionTypeVal = Boolean
						.parseBoolean(constructionType);
				allProjects.addAll(projectDAO
						.findByTypeAndConstructionNewAndStatusAllIgnoreCase(
								projectType, constructionTypeVal, "ACTIVE"));
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {

				int zipcodeVal = Integer.parseInt(zipcode);

				allProjects.addAll(projectDAO
						.findByTypeAndAddress_ZipcodeAndStatusAllIgnoreCase(
								projectType, zipcodeVal, "ACTIVE"));
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {

				boolean constructionTypeVal = Boolean
						.parseBoolean(constructionType);
				int zipcodeVal = Integer.parseInt(zipcode);
				allProjects
						.addAll(projectDAO
								.findByConstructionNewAndAddress_ZipcodeAndStatusAllIgnoreCase(
										constructionTypeVal, zipcodeVal,
										"ACTIVE"));
			} else if (StringUtils.isNotBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {
				String[] maxminValue = budget.split(",");

				if (maxminValue.length == 2) {
					double max = Double.parseDouble(maxminValue[0]) + 1;
					double min = Double.parseDouble(maxminValue[1]) - 1;

					allProjects.addAll(projectDAO
							.findByBudgetBetweenAndStatusAllIgnoreCase(min,
									max, "ACTIVE"));
				}
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isNotBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {
				allProjects.addAll(projectDAO.findByTypeAndStatusAllIgnoreCase(
						ProjectType.valueOf(projectType), "ACTIVE"));
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isNotBlank(constructionType)
					&& StringUtils.isBlank(zipcode)) {

				boolean constructionTypeVal = Boolean
						.parseBoolean(constructionType);
				allProjects.addAll(projectDAO
						.findByConstructionNewAndStatusAllIgnoreCase(
								constructionTypeVal, "ACTIVE"));
			} else if (StringUtils.isBlank(budget)
					&& StringUtils.isBlank(projectType)
					&& StringUtils.isBlank(constructionType)
					&& StringUtils.isNotBlank(zipcode)) {

				int zipcodeVal = Integer.parseInt(zipcode);
				allProjects.addAll(projectDAO
						.findByAddress_ZipcodeAndStatusAllIgnoreCase(
								zipcodeVal, "ACTIVE"));
			} else if (StringUtils.isNotBlank(name)) {
				allProjects.addAll(projectDAO.findByNameAndStatusAllIgnoreCase(
						name, "ACTIVE"));
			} else if (StringUtils.isNotBlank(status)) {
				allProjects.addAll(projectDAO.findByStatus(status));
			} else if (StringUtils.isNotBlank(addressLine1)) {
				allProjects.addAll(projectDAO
						.findByAddress_AddressLine1AndStatusAllIgnoreCase(
								addressLine1, "ACTIVE"));
			} else if (StringUtils.isNotBlank(addressLine2)) {
				allProjects.addAll(projectDAO
						.findByAddress_AddressLine2AndStatusAllIgnoreCase(
								addressLine2, "ACTIVE"));
			} else if (StringUtils.isNotBlank(city)) {
				allProjects.addAll(projectDAO
						.findByAddress_CityAndStatusAllIgnoreCase(city,
								"ACTIVE"));
			} else if (StringUtils.isNotBlank(state)) {
				allProjects.addAll(projectDAO
						.findByAddress_StateAndStatusAllIgnoreCase(state,
								"ACTIVE"));
			} else if (StringUtils.isNotBlank(country)) {
				allProjects.addAll(projectDAO
						.findByAddress_CountryAndStatusAllIgnoreCase(country,
								"ACTIVE"));
			} else if (StringUtils.isNotBlank(email)) {
				allProjects.addAll(projectDAO
						.findByAddress_EmailAndStatusAllIgnoreCase(email,
								"ACTIVE"));
			} else if (point != null) {
				allProjects.addAll(projectDAO
						.findByAddress_LocationAndStatusAllIgnoreCase(point,
								"ACTIVE"));
			}
			for (Project project : allProjects) {
				if (project.getUserType().equalsIgnoreCase(roleName)
						|| project.getUserType().equalsIgnoreCase(
								"BOTHARCHITECTANDINTERIORDESIGNER")
						|| roleName.equalsIgnoreCase("SUPERADMIN")) {

					ProjectDescriptionResponse projectResponse = new ProjectDescriptionResponse();

					// ProjectId
					projectResponse.setProjectId(project.getId());

					// First name
					if (project.getUser().getFirstName() != null) {
						projectResponse.setFirstName(project.getUser()
								.getFirstName());
					}
					// Last name
					if (project.getUser().getLastName() != null) {
						projectResponse.setLastName(project.getUser()
								.getLastName());
					}
					// Full name
					if (project.getUser().getFirstName() != null
							&& project.getUser().getLastName() != null) {
						projectResponse.setFullName(project.getUser()
								.getFirstName()
								+ " "
								+ project.getUser().getLastName());
					} else if (project.getUser().getFirstName() != null) {
						projectResponse.setFullName(project.getUser()
								.getFirstName());
					}
					// Project name
					projectResponse.setProjectName(project.getName());
					// Project Address
					projectResponse.setAddress(project.getAddress());
					// Project description
					projectResponse.setDescription(project.getDesc().getVal());
					// Project type
					projectResponse.setProjectType(project.getType().name());
					// profile pictures
					projectResponse.setUserImage(project.getUser()
							.getProfilePicture());
					// Budget
					projectResponse.setBudget(project.getBudget());
					// Created data
					projectResponse.setCreatedDate(project.getCreatedDate());
					// Project user id
					projectResponse.setUserId(project.getUser().getId());
					// construction type
					if (project.isConstructionNew()) {
						projectResponse.setConstrutionType(true);
					} else {
						projectResponse.setConstrutionType(false);
					}
					// status
					if (project.getStatus() != null) {
						projectResponse.setStatus(project.getStatus());
					}

					projectResponseList.add(projectResponse);
				}
			}
		}
		return projectResponseList;
	}

	/**
	 * Method to get available projects to bid
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<ProjectDescriptionResponse> getAvailableProjects(String roleName)
			throws Exception {
		List<Project> projectList = null;
		List<Project> allProjectList = new ArrayList<>();
		List<ProjectDescriptionResponse> projectResponseList = new ArrayList<>();

		projectList = projectDAO.findAll();

		// sorting
		Collections.sort(projectList, new DashboardProjectComparator());
		for (Project project : projectList) {
			String status = project.getStatus();
			if (status.equalsIgnoreCase("ACTIVE")) {
				allProjectList.add(project);
			} else {
				logger.info("Project Status InActive");
			}
		}
		for (Project availableProject : allProjectList) {
			if (availableProject.getUserType().equalsIgnoreCase(roleName)
					|| availableProject.getUserType().equalsIgnoreCase(
							"BOTHARCHITECTANDINTERIORDESIGNER")
					|| roleName.equalsIgnoreCase("SUPERADMIN")) {

				ProjectDescriptionResponse projectResponse = new ProjectDescriptionResponse();

				projectResponse.setProjectId(availableProject.getId());
				// First name
				if (availableProject.getUser().getFirstName() != null) {
					projectResponse.setFirstName(availableProject.getUser()
							.getFirstName());
				}
				// Last name
				if (availableProject.getUser().getLastName() != null) {
					projectResponse.setLastName(availableProject.getUser()
							.getLastName());
				}
				// Full name
				if (availableProject.getUser().getFirstName() != null
						&& availableProject.getUser().getLastName() != null) {
					projectResponse.setFullName(availableProject.getUser()
							.getFirstName()
							+ " "
							+ availableProject.getUser().getLastName());
				} else if (availableProject.getUser().getFirstName() != null) {
					projectResponse.setFullName(availableProject.getUser()
							.getFirstName());
				}
				// Project name
				projectResponse.setProjectName(availableProject.getName());
				// Address
				projectResponse.setAddress(availableProject.getAddress());
				// Description
				projectResponse.setDescription(availableProject.getDesc()
						.getVal());
				// ProjectType
				projectResponse.setProjectType(availableProject.getType()
						.name());
				// User image
				projectResponse.setUserImage(availableProject.getUser()
						.getProfilePicture());
				// Budget
				projectResponse.setBudget(availableProject.getBudget());
				// ConstructionType
				projectResponse.setConstrutionType(availableProject
						.isConstructionNew());
				// CreatedDate
				projectResponse.setCreatedDate(availableProject
						.getCreatedDate());
				// UserId
				projectResponse.setUserId(availableProject.getUser().getId());

				projectResponseList.add(projectResponse);
			}
		}
		return projectResponseList;
	}

	@Override
	public List<ProjectDescriptionResponse> getAdminManageProjects(
			String roleName) throws Exception {
		List<Project> projectList = null;
		List<ProjectDescriptionResponse> projectResponseList = new ArrayList<>();

		projectList = projectDAO.findAll();

		// sorting
		Collections.sort(projectList, new DashboardProjectComparator());

		for (Project availableProject : projectList) {
			if (availableProject != null) {
				if (availableProject.getUserType().equalsIgnoreCase(roleName)
						|| availableProject.getUserType().equalsIgnoreCase(
								"BOTHARCHITECTANDINTERIORDESIGNER")
						|| roleName.equalsIgnoreCase("SUPERADMIN")) {

					ProjectDescriptionResponse projectResponse = new ProjectDescriptionResponse();

					// Id
					projectResponse.setProjectId(availableProject.getId());

					// First name
					if (availableProject.getUser().getFirstName() != null) {
						projectResponse.setFirstName(availableProject.getUser()
								.getFirstName());
					}
					// Last name
					if (availableProject.getUser().getLastName() != null) {
						projectResponse.setLastName(availableProject.getUser()
								.getLastName());
					}
					// Full name
					if (availableProject.getUser().getFirstName() != null
							&& availableProject.getUser().getLastName() != null) {
						projectResponse.setFullName(availableProject.getUser()
								.getFirstName()
								+ " "
								+ availableProject.getUser().getLastName());
					} else if (availableProject.getUser().getFirstName() != null) {
						projectResponse.setFullName(availableProject.getUser()
								.getFirstName());
					}
					// Project name
					projectResponse.setProjectName(availableProject.getName());
					// Address
					projectResponse.setAddress(availableProject.getAddress());
					// Description
					projectResponse.setDescription(availableProject.getDesc()
							.getVal());
					// Project type
					projectResponse.setProjectType(availableProject.getType()
							.name());
					// Profile image
					projectResponse.setUserImage(availableProject.getUser()
							.getProfilePicture());
					// Budget
					projectResponse.setBudget(availableProject.getBudget());
					// ConstuctionType
					projectResponse.setConstrutionType(availableProject
							.isConstructionNew());
					// Created date
					projectResponse.setCreatedDate(availableProject
							.getCreatedDate());
					// UserId
					projectResponse.setUserId(availableProject.getUser()
							.getId());
					// Status
					projectResponse.setStatus(availableProject.getStatus());

					projectResponseList.add(projectResponse);
				}
			}
		}
		return projectResponseList;
	}

	/**
	 * Method to search projects by project Id
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be the projID
	 * 
	 */
	@Override
	public Project getProjectById(String projid) throws Exception {
		Project project = null;
		if (StringUtils.isNotBlank(projid)) {
			logger.info("find the project by id");
			project = projectDAO.findById(projid);
		} else {
			logger.info("projid is null");
			throw new Exception("projid is null");
		}
		return project;
	}

	/**
	 * Method for delete system level project based on project id. project id is
	 * necessary field
	 * 
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Project deleteSystemProject(String projId) throws Exception {
		Project project = null;
		if (StringUtils.isNotBlank(projId)) {
			project = projectDAO.findOne(projId);
			if (project != null) {
				logger.info("deleting project by id");
				projectDAO.delete(projId);
			} else {
				logger.info("project is null, so unable to delete project");
				throw new Exception(
						"project is null, so unable to delete project");
			}
		}
		return project;
	}

	/**
	 * Method for get one or more number bids for project, project id is
	 * necessary field
	 * 
	 * @param pId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BidDescriptionResponse> getBidsForProject(String projectId,
			String roleName) throws Exception {
		List<BidDescriptionResponse> bidResponseList = new ArrayList<BidDescriptionResponse>();

		List<Bid> bids = null;

		if (StringUtils.isNotBlank(projectId)) {
			Project project = projectDAO.findOne(projectId);
			if (project != null) {
				logger.info("finding bid for project");
				if (StringUtils.isNotBlank(roleName)) {
					logger.info("Finding Bids based on the roleName");
					Role role = userRoleDAO.findByName(roleName.toUpperCase());
					if (role != null) {
						bids = bidsDAO.findByProject(project);
						for (Bid bid : bids) {
							Set<Role> roles = bid.getUser().getRoles();
							for (Role role1 : roles) {
								if (role1.getName().equalsIgnoreCase(roleName)) {
									BidDescriptionResponse bidResponse = new BidDescriptionResponse();

									bidResponse.setUser(bid.getUser());
									bidResponse.setAddress(bid.getUser()
											.getAddresses());
									bidResponse.setBidDescription(bid.getDesc()
											.getVal());
									bidResponse.setBiddedDate(bid
											.getCreatedDate());

									if (bid.getAssetIds() != null) {
										bidResponse.setAssetIds(bid
												.getAssetIds());
									}

									bidResponseList.add(bidResponse);
								}
							}

						}
					} else {
						logger.info("Invalid Role Name");
						throw new Exception("Invalid Role Name");
					}

				} else {
					bids = bidsDAO.findByProject(project);
					for (Bid bid : bids) {

						BidDescriptionResponse bidResponse = new BidDescriptionResponse();

						bidResponse.setUser(bid.getUser());
						bidResponse.setAddress(bid.getUser().getAddresses());
						bidResponse.setBidDescription(bid.getDesc().getVal());
						bidResponse.setBiddedDate(bid.getCreatedDate());

						if (bid.getAssetIds() != null) {
							bidResponse.setAssetIds(bid.getAssetIds());
						}

						bidResponseList.add(bidResponse);
					}

				}
			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}
		return bidResponseList;
	}

	/**
	 * Method to get count of bids for project
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be the projID
	 */
	@Override
	public long countProjectBidsById(String projid) throws Exception {

		long bidCount = 0;
		if (StringUtils.isNotBlank(projid)) {
			Project project = projectDAO.findOne(projid);
			if (project != null) {
				logger.info("counting bid of project");
				bidCount = bidsDAO.countByProject(project);
			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}
		return bidCount;
	}

	@Override
	public BidCountResponse countProjectBidsByProjectAndRole(String projid)
			throws Exception {
		// TODO Auto-generated method stub
		BidCountResponse bidCountResponse = new BidCountResponse();

		long architectBidsCount = 0;
		long InteriorDesignerBidsCount = 0;
		long superAdminBidsCount = 0;
		long totalBidsCount = 0;
		if (StringUtils.isNotBlank(projid)) {
			Project project = projectDAO.findOne(projid);
			if (project != null) {
				logger.info("counting bid of project");

				/*
				 * architectBidsCount =
				 * bidsDAO.countByProjectAndUser_Roles_Name( project,
				 * "ARCHITECT"); InteriorDesignerBidsCount = bidsDAO
				 * .countByProjectAndUser_Roles_Name(project,
				 * "INTERIORDESIGNER"); superAdminBidsCount =
				 * bidsDAO.countByProjectAndUser_Roles_Name( project,
				 * "SUPERADMIN");
				 */
				List<Bid> bids = bidsDAO.findByProject(project);
				for (Bid bid : bids) {
					Set<Role> roles = bid.getUser().getRoles();
					for (Role role : roles) {
						if (role.getName().equalsIgnoreCase("ARCHITECT")) {
							architectBidsCount++;
						} else if (role.getName().equalsIgnoreCase(
								"INTERIORDESIGNER")) {
							InteriorDesignerBidsCount++;
						} else if (role.getName()
								.equalsIgnoreCase("SUPERADMIN")) {
							superAdminBidsCount++;
						} else {
							logger.info(
									"Bidded not done by Architect/ID/SuperAdmin : {}",
									role.getName());
						}
					}
				}

				totalBidsCount = architectBidsCount + InteriorDesignerBidsCount
						+ superAdminBidsCount;
				bidCountResponse.setArchitectBidsCount(architectBidsCount);
				bidCountResponse
						.setInteriorDesignerBidsCount(InteriorDesignerBidsCount);
				bidCountResponse.setSuperAdminBidsCount(superAdminBidsCount);
				bidCountResponse.setTotalCount(totalBidsCount);

			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}

		return bidCountResponse;
	}

	/**
	 * Method to create bid for a project
	 * 
	 * @param projid
	 * @param bidCreateRequestParam
	 * @return
	 * @throws MBGAppException
	 */
	@Override
	public Bid createBid(String userId, String projid, String desc,
			String assetId, String quoteAmt) throws Exception {

		UserInfo userInfo = userRepository.findByMongoUserId(userId);
		Bid bid = new Bid();
		if (StringUtils.isNotBlank(projid) && userInfo != null) {
			Project project = projectDAO.findById(projid);
			if (project != null) {
				logger.info("creating bid");
				if (StringUtils.isNotBlank(desc)) {
					Description descr = new Description();
					descr.setLang("Eng");
					descr.setVal(desc);
					bid.setDesc(descr);
				}
				if (StringUtils.isNotBlank(assetId)) {
					List<String> assetIds = new LinkedList<>();
					assetIds.add(assetId);
					bid.setAssetIds(assetIds);
				}
				if (StringUtils.isNotBlank(quoteAmt)) {
					bid.setQuoteAmount(Integer.parseInt(quoteAmt));
				}

				bid.setProject(project);

				User user = userDAO.findById(userId);
				bid.setUser(user);

				Set<Role> roles = user.getRoles();
				for (Role role : roles) {
					if (role.getUserPackage() != null) {
						if (projectPrivilegeService.checkPrivilege(userId,
								"BID ", "PROJECT")) {
							project.setUser(user);
							bidsDAO.insert(bid);
							logger.info("Bidding for a project");
						} else {
							throw new Exception(
									"Cannot Bid more Projects, upgrade your package...!!");
						}
					}
				}

				String architectOrInteriorRoleName = null;
				for (Role role : roles) {
					architectOrInteriorRoleName = role.getName();
				}
				Set<Address> addresses = user.getAddresses();
				Integer architectOrInteriorZipcode = 0;
				String contactNumber = null;
				for (Address addrs : addresses) {
					Set<Phone> phs = addrs.getPhoneNumbers();
					if (phs != null) {
						for (Phone ph : phs) {
							if (ph.isPrimary()) {
								contactNumber = ph.getNumber();
								architectOrInteriorZipcode = addrs.getZipcode();
							}

						}
					}
				}
				userCommunicationService.sendBidDoneInfo(project.getUser(),
						user.getFullName(), architectOrInteriorRoleName,
						architectOrInteriorZipcode, contactNumber);

				// String note = "User " + user.getId()
				// + " Bided for the project " + project.getName();
				//
				// updateUserAudit(userInfo, "11", note);

			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}

		return bid;
	}

	// File and Image upload
	@Override
	public String uploadFile(String bidId, String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception {
		String locationName = null;
		String url = null;
		Bid bid = null;
		List<String> assetId = null;
		String projectName = null;
		if (StringUtils.isNotBlank(bidId)) {
			logger.info("ProductResource : uploadFile : " + bidId);
			bid = bidsDAO.findOne(bidId);
			if (bid != null) {
				Project project = bid.getProject();
				projectName = project.getName().toUpperCase();
				logger.info(projectName);
				locationName = "catalog/product/" + projectName.charAt(0) + "/"
						+ projectName.substring(0, 2) + "/" + fileName;
				logger.info(locationName);
				if (fileTypeValue.equals("File")) {

					url = filestorage.uploadFile(
							AzureFileStorage.FILE_CONTAINER, locationName,
							fileInputStream, contentLength);
				} else {
					throw new Exception("unable to upload file");
				}
				assetId = bid.getAssetIds();
				if (assetId != null) {
					assetId.add(url);
				} else {
					List<String> assetid = new ArrayList<>();
					assetid.add(url);
					bid.setAssetIds(assetid);
				}
				bidsDAO.save(bid);
			}
		}
		return bid.getId();
	}

	/**
	 * Method for update bid for project, projectId and bidId is necessary
	 * field.
	 * 
	 * @param projId
	 * @param bidId
	 * @param bidUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Bid updateBidForProject(String projId, String bidId, String desc,
			String quoteAmount) throws Exception {
		Bid bid = null;
		if (StringUtils.isNotBlank(projId)) {
			Project project = projectDAO.findOne(projId);
			if (project != null) {
				if (StringUtils.isNotBlank(bidId)) {
					bid = bidsDAO.findOne(bidId);
					if (bid != null) {
						if (StringUtils.isNotBlank(desc)) {
							Description discription = new Description();
							discription.setLang("eng");
							discription.setVal(desc);
							bid.setDesc(discription);
						}
						if (StringUtils.isNotBlank(quoteAmount)) {
							bid.setQuoteAmount(Integer.parseInt(quoteAmount));
						}
					}
					logger.info("updating bid");
					bidsDAO.save(bid);
				}
			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}
		return bid;
	}

	/**
	 * Method for delete bid for Project, projectId and bidId is necessary
	 * field.
	 * 
	 * @param projId
	 * @param bidId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String deleteBidForProject(String projId, String bidId)
			throws Exception {
		Bid bid = null;
		if (StringUtils.isNotBlank(projId)) {
			Project project = projectDAO.findOne(projId);
			if (project != null) {
				if (StringUtils.isNotBlank(bidId)) {
					bid = bidsDAO.findOne(bidId);
					if (bid != null) {
						logger.info("deleting bid of project");
						bidsDAO.delete(bidId);
					}
				}
			} else {
				logger.info("project is null");
				throw new Exception("project is null");
			}
		}
		return bidId;
	}

	/**
	 * method to get the minimum and maximum budget from all projects
	 * 
	 * @return
	 */
	@Override
	public BudgetResponse getMinMaxBudget() {
		BudgetResponse budgetResponse = new BudgetResponse();

		List<Project> projectList = new ArrayList<>();
		List<Project> allProjectList = new ArrayList<Project>();

		projectList = projectDAO.findAll();

		// sorting
		Collections.sort(projectList, new DashboardProjectComparator());
		for (Project project : projectList) {
			String status = project.getStatus();
			if (status.equalsIgnoreCase("ACTIVE")) {
				allProjectList.add(project);
			} else {
				logger.info("Project Status InActive");
			}
		}

		double minBudget = allProjectList.get(0).getBudget();
		double maxBudget = allProjectList.get(0).getBudget();
		for (Project availableProject : allProjectList) {

			if (maxBudget < availableProject.getBudget()) {
				maxBudget = availableProject.getBudget();
				logger.info("MaxBudget changed");
			} else {
				logger.info("MaxBudget is unchanged");
			}

			if (availableProject.getBudget() < minBudget) {
				minBudget = availableProject.getBudget();
				logger.info("minBudget changed");
			} else {
				logger.info("minBudget unchanged");

			}
		}
		budgetResponse.setMinBudget(minBudget);
		budgetResponse.setMaxBudget(maxBudget);
		return budgetResponse;
	}

	/**
	 * Method to get list of all available projects for the logged in
	 * Architect/interior designer
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<ProjectDescriptionResponse> getAvailableProjectsToBid(
			String userId, String roleName) {

		List<Project> availableProjects = new ArrayList<Project>();
		List<Bid> allBids = new ArrayList<Bid>();
		Set<Project> bidProjects = new HashSet<Project>();
		List<ProjectDescriptionResponse> projectResponseList = new ArrayList<>();

		User bidUser = userDAO.findById(userId);
		// Get all bids done by the logged in user
		allBids = bidsDAO.findByUser(bidUser);

		for (Bid bid : allBids) {
			bidProjects.add(bid.getProject());
		}

		// Get all the projects
		List<Project> allProjects = projectDAO
				.findByStatusAllIgnoreCase("ACTIVE");

		Set<String> availaProjIds = new HashSet<String>();
		for (Project project : allProjects) {
			availaProjIds.add(project.getId());
		}

		// Iterating all the bidded projects and all the other projects to check
		// whether bidding is already done or not
		for (Project bidproject : bidProjects) {
			for (Project project : allProjects) {
				if (bidproject.getId().equalsIgnoreCase(project.getId())) {
					availaProjIds.remove(project.getId());
				}
			}
		}

		for (String proid : availaProjIds) {
			Project project = projectDAO.findOne(proid);
			availableProjects.add(project);
		}

		for (Project availableProject : availableProjects) {
			if (availableProject.getUserType().equalsIgnoreCase(roleName)
					|| availableProject.getUserType().equalsIgnoreCase(
							"BOTHARCHITECTANDINTERIORDESIGNER")
					|| roleName.equalsIgnoreCase("SUPERADMIN")) {

				ProjectDescriptionResponse projectResponse = new ProjectDescriptionResponse();
				// Project Id
				projectResponse.setProjectId(availableProject.getId());
				// Project user First name
				if (availableProject.getUser().getFirstName() != null) {
					projectResponse.setFirstName(availableProject.getUser()
							.getFirstName());
				}
				// Project user Last name
				if (availableProject.getUser().getLastName() != null) {
					projectResponse.setLastName(availableProject.getUser()
							.getLastName());
				}
				// Project user Full name
				if (availableProject.getUser().getFirstName() != null
						&& availableProject.getUser().getLastName() != null) {
					projectResponse.setFullName(availableProject.getUser()
							.getFirstName()
							+ " "
							+ availableProject.getUser().getLastName());
				} else if (availableProject.getUser().getFirstName() != null) {
					projectResponse.setFullName(availableProject.getUser()
							.getFirstName());
				}
				// project name
				projectResponse.setProjectName(availableProject.getName());
				// project address
				projectResponse.setAddress(availableProject.getAddress());
				// project description
				projectResponse.setDescription(availableProject.getDesc()
						.getVal());
				// project type
				projectResponse.setProjectType(availableProject.getType()
						.name());
				// user image
				projectResponse.setUserImage(availableProject.getUser()
						.getProfilePicture());
				// budget
				projectResponse.setBudget(availableProject.getBudget());
				// construction type
				projectResponse.setConstrutionType(availableProject
						.isConstructionNew());
				// created date
				projectResponse.setCreatedDate(availableProject
						.getCreatedDate());
				// project user id
				projectResponse.setUserId(availableProject.getUser().getId());

				// project status
				if (availableProject.getStatus() != null) {
					projectResponse.setStatus(availableProject.getStatus());
				}

				projectResponseList.add(projectResponse);
			}
		}
		// Returning all available projects
		return projectResponseList;
	}

	// private void updateUserAudit(UserInfo userInfo, String userAuditTypeVal,
	// String note) {
	// if (userInfo != null) {
	// UserAudit userAudit = new UserAudit();
	// // user id
	// userAudit.setUserId(userInfo.getUserid());
	// // user audit type
	// UserAuditType userAuditType = userAuditTypeRepository
	// .findByValue(userAuditTypeVal);
	// userAudit.setUserAuditType(userAuditType);
	// // note
	// userAudit.setNote(note);
	// // created by
	// userAudit.setCreatedBy("Buildonn");
	// // last modified by
	// userAudit.setModifiedBy("Buildonn");
	// // created date
	// userAudit.setCreatedTime(new Date());
	// // last modified date
	// userAudit.setLastModifiedTime(new Date());
	//
	// userAuditRepository.save(userAudit);
	// }
	// }
}
