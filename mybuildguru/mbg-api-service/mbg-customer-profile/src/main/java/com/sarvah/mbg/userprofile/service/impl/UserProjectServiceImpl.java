/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.commons.address.AddressLookupService;
import com.sarvah.mbg.commons.communication.UserCommunicationService;
import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.aceproject.LeadProject;
import com.sarvah.mbg.domain.mongo.aceproject.Project;
import com.sarvah.mbg.domain.mongo.aceproject.ProjectType;
import com.sarvah.mbg.domain.mongo.aceproject.TeleCallHistory;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.ordermgmt.Order;
import com.sarvah.mbg.domain.user.UserInfo;
import com.sarvah.mbg.domain.user.audit.UserAudit;
import com.sarvah.mbg.domain.user.audit.UserAuditType;
import com.sarvah.mbg.notification.service.impl.NotificationServiceImpl;
import com.sarvah.mbg.privilege.service.PrivilegeService;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserAuditTypeRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserOrderRepository;
import com.sarvah.mbg.userprofile.dao.jpa.UserRepository;
import com.sarvah.mbg.userprofile.dao.mongo.LeadProjectDAO;
import com.sarvah.mbg.userprofile.dao.mongo.ProjectBidsDAO;
import com.sarvah.mbg.userprofile.dao.mongo.TeleCallHistoryDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserProjectDAO;
import com.sarvah.mbg.userprofile.ordermgmt.model.LeadProjectResponse;
import com.sarvah.mbg.userprofile.ordermgmt.model.ManageLeadProjectResponse;
import com.sarvah.mbg.userprofile.response.BidResponses;
import com.sarvah.mbg.userprofile.response.ProjectResponse;
import com.sarvah.mbg.userprofile.service.UserProjectService;

/**
 * @author shivu
 *
 */
@Service
public class UserProjectServiceImpl implements UserProjectService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserProjectDAO userProjectDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private ProjectBidsDAO bidsDAO;

	@Autowired
	private NotificationServiceImpl notificationServiceImpl;

	@Autowired
	private UserCommunicationService userCommunicationService;

	@Autowired
	private UserAuditRepository userAuditRepository;

	@Autowired
	private UserAuditTypeRepository userAuditTypeRepository;

	@Autowired
	private UserRepository userRepository;

	/** The address lookup service. */
	@Autowired
	private AddressLookupService addressLookupService;

	@Autowired
	private LeadProjectDAO leadProjectDAO;

	@Autowired
	private UserOrderRepository userOrderRepository;

	@Autowired
	private TeleCallHistoryDAO teleCallHistoryDAO;

	// cont mgnt
	/**
	 * Method to Get User projects
	 * 
	 * This method returns the list projects available for the particular user
	 * 
	 * @throws Exception
	 * 
	 * @QueryParam would be the projectname,type,addr line1,addr
	 *             line2,city,zipcode with @PathParam being the Userid
	 *
	 */
	@Override
	public List<ProjectResponse> getUserProjects(String uid, String projName,
			String type, String addrLine1, String addrLine2, String city,
			String zipcode, Point point) throws Exception {

		List<Project> projects = new ArrayList<>();
		List<ProjectResponse> projectsInfo = new ArrayList<>();
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				logger.info("finding user projects");
				if (StringUtils.isNotBlank(projName)) {
					List<Project> project = userProjectDAO.findByNameAndUser(
							projName, user);
					projects.addAll(project);
				} else if (StringUtils.isNotBlank(type)) {
					List<Project> project = userProjectDAO.findByTypeAndUser(
							type, user);
					projects.addAll(project);
				}

				else if (StringUtils.isNotBlank(addrLine1)) {
					List<Project> project = userProjectDAO
							.findByAddress_AddressLine1AndUser(addrLine1, user);
					projects.addAll(project);
				}

				else if (StringUtils.isNotBlank(addrLine2)) {
					List<Project> project = userProjectDAO
							.findByAddress_AddressLine2AndUser(addrLine2, user);
					projects.addAll(project);
				}

				else if (StringUtils.isNotBlank(city)) {
					List<Project> project = userProjectDAO
							.findByAddress_CityAndUser(city, user);
					projects.addAll(project);
				}

				else if (StringUtils.isNotBlank(zipcode)) {
					List<Project> project = userProjectDAO
							.findByAddress_ZipcodeAndUser(
									Integer.parseInt(zipcode), user);
					projects.addAll(project);
				} else if (point != null) {
					List<Project> project = userProjectDAO
							.findByAddress_Location(point);
					projects.addAll(project);
				} else {
					List<Project> project = userProjectDAO.findByUser(user);
					projects.addAll(project);
				}

				for (Project project : projects) {
					if (project != null) {
						ProjectResponse projectResponse = new ProjectResponse();
						// projectId
						projectResponse.setId(project.getId());
						// project created date
						if (project.getCreatedDate() != null) {
							projectResponse.setCreatedDate(project
									.getCreatedDate());
						}
						// project name
						if (project.getName() != null) {
							projectResponse.setName(project.getName());
						}
						// description
						if (project.getDesc() != null) {
							projectResponse.setDesc(project.getDesc());
						}
						// budget
						if (project.getBudget() != null) {
							projectResponse.setBudget(project.getBudget());
						}
						// projectType
						if (project.getType() != null) {
							projectResponse.setType(project.getType());
						}
						// address
						if (project.getAddress() != null) {
							projectResponse.setAddress(project.getAddress());
						}
						// user
						if (project.getUser() != null) {
							projectResponse.setUser(project.getUser());
						}
						// productIds
						if (project.getProductIds() != null
								&& project.getProductIds().size() != 0) {
							projectResponse.setProductIds(project
									.getProductIds());
						}
						// status
						if (project.getStatus() != null) {
							projectResponse.setStatus(project.getStatus());
						}
						// constructionType
						projectResponse.setConstructionNew(project
								.isConstructionNew());
						// userType
						projectResponse.setUserType(project.getUserType());

						projectsInfo.add(projectResponse);
					}
				}
			} else {
				logger.info("unable to find user project");
				throw new Exception("unable to find user project");
			}
		}
		return projectsInfo;
	}

	/**
	 * Method to get bid responses for the particular project by either
	 * Architect/Interior Designer
	 * 
	 * @param userId
	 * @param projName
	 * @param userType
	 * @return
	 */
	@Override
	public List<BidResponses> getBidResponses(String userId, String projName,
			String userType) {
		List<Project> projects = new ArrayList<>();
		List<BidResponses> bidList = new ArrayList<BidResponses>();
		BidResponses bidResponse = new BidResponses();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null) {
				logger.info("finding user projects");
				List<Project> project = userProjectDAO.findByUser(user);
				projects.addAll(project);
				for (Project userProject : project) {

					if (StringUtils.isNotBlank(projName)
							&& userProject.getName().equalsIgnoreCase(projName)) {
						List<Bid> bids = bidsDAO.findByProject(userProject);
						for (Bid bid : bids) {
							User bidUser = bid.getUser();
							Role userRole = new Role();
							for (Role role : bidUser.getRoles()) {
								userRole.setName(role.getName());
								if (userRole.getName().equalsIgnoreCase(
										userType)
										&& bid.getProject().getName()
												.equalsIgnoreCase(projName)) {
									// First name
									if (bidUser.getFirstName() != null) {
										bidResponse.setFirstName(bidUser
												.getFirstName());
									}
									// Last name
									if (bidUser.getLastName() != null) {
										bidResponse.setLastName(bidUser
												.getLastName());
									}
									// Bidding user Full name
									if (bidUser.getFirstName() != null
											&& bidUser.getLastName() != null) {
										bidResponse.setBiddingUserName(bidUser
												.getFirstName()
												+ " "
												+ bidUser.getLastName());
									} else if (bidUser.getFirstName() != null) {
										bidResponse.setBiddingUserName(bidUser
												.getFirstName());
									}

									bidResponse.setBidDescription(bid.getDesc()
											.getVal());
									bidResponse.setUserImage(bidUser
											.getProfilePicture());
									bidResponse.setAssetId(bid.getAssetIds());

									bidResponse.setPostedDate(bid.getProject()
											.getCreatedDate());
									bidResponse.setProjectName(bid.getProject()
											.getName());
									// Full name
									if (bid.getProject().getUser()
											.getFirstName() != null
											&& bid.getProject().getUser()
													.getLastName() != null) {
										bidResponse.setFullName(bid
												.getProject().getUser()
												.getFirstName()
												+ " "
												+ bid.getProject().getUser()
														.getLastName());
									} else if (bid.getProject().getUser()
											.getFirstName() != null) {
										bidResponse.setFullName(bid
												.getProject().getUser()
												.getFirstName());
									}

									bidResponse.setProjectType(bid.getProject()
											.getType());
									bidResponse.setDescription(bid.getProject()
											.getDesc().getVal());
									bidResponse.setBiddedDate(bid
											.getCreatedDate());
									bidResponse.setAddress(bid.getProject()
											.getAddress());
									bidList.add(bidResponse);
								}
							}
						}
					}
				}
			}
		}
		return bidList;
	}

	/**
	 * Method to get list of all available projects for the logged in
	 * Architect/interior designer
	 * 
	 * @param userId
	 * @return
	 */
	@Override
	public List<Project> getAvailableProjects(String userId) {

		List<Project> availableProjects = new ArrayList<Project>();
		List<Bid> allBids = new ArrayList<Bid>();
		Set<Project> bidProjects = new HashSet<Project>();

		User bidUser = userDAO.findById(userId);
		// Get all bids done by the logged in user
		allBids = bidsDAO.findByUser(bidUser);

		for (Bid bid : allBids) {
			bidProjects.add(bid.getProject());
		}

		// Get all the projects
		List<Project> allProjects = userProjectDAO.findAll();
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
				} else {
					logger.info("bidding done!!");
				}
			}
		}

		for (String proid : availaProjIds) {
			Project project = userProjectDAO.findOne(proid);
			availableProjects.add(project);
		}
		// Returning all available projects
		return availableProjects;
	}

	/**
	 * Method for count user related projects, we can also count this projects
	 * based on bellow parameters.
	 * 
	 * @param userId
	 * @param name
	 * @param type
	 * @param address1
	 * @param address2
	 * @param city
	 * @return
	 * @throws Exception
	 */

	@Override
	public Long countProjects(String uid, String name, ProjectType type,
			String addressLine1, String addressLine2, String city)
			throws Exception {
		Long count = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				logger.info("counting user projects");
				if (StringUtils.isNotBlank(name)) {
					count = userProjectDAO.countByNameAndUser(name, user);
				} else if (type != null) {
					count = userProjectDAO.countByTypeAndUser(type, user);
				} else if (StringUtils.isNotBlank(addressLine1)) {
					count = userProjectDAO
							.countByAddress_AddressLine1LikeAndUser(
									addressLine1, user);
				} else if (StringUtils.isNotBlank(addressLine2)) {
					count = userProjectDAO
							.countByAddress_AddressLine2LikeAndUser(
									addressLine2, user);
				} else if (StringUtils.isNotBlank(city)) {
					count = userProjectDAO.countByAddress_CityAndUser(city,
							user);
				} else {
					count = userProjectDAO.countByUser(user);
				}
			} else {
				logger.info("unable to count projects for user");
				throw new Exception("unable to count projects for user");
			}
		}
		return count;
	}

	/**
	 * Method to Post new Project
	 * 
	 * @param uid
	 * @param name
	 * @param desc
	 * @param budget
	 * @param projectType
	 * @param addrId
	 * @param addrLine1
	 * @param addrLine2
	 * @param city
	 * @param state
	 * @param country
	 * @param zipcode
	 * @param point
	 * @param email
	 * @param phoneType
	 * @param number
	 * @param status
	 * @param constructionNew
	 * @return
	 * @throws Exception
	 */
	@Override
	public Project createProject(String uid, String name, String desc,
			String budget, String projectType, String userType, String addrId,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, Point point, String email,
			String phoneType, String number, String status,
			String constructionType, String asSoonRequired) throws Exception {
		Project project = new Project();
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			UserInfo userInfo = userRepository.findByMongoUserId(uid);
			if (user != null && userInfo != null) {

				if (StringUtils.isNotBlank(name)) {
					project.setName(name);
				}
				if (StringUtils.isNotBlank(desc)) {
					Description desc1 = new Description("eng", desc);
					project.setDesc(desc1);
				}
				if (StringUtils.isNotBlank(budget)) {
					project.setBudget(Double.parseDouble(budget));
				}
				if (StringUtils.isNotBlank(projectType)) {
					project.setType(ProjectType.valueOf(projectType));
				}
				if (StringUtils.isNotBlank(userType)) {
					project.setUserType(userType);
				}

				if (StringUtils.isNotBlank(asSoonRequired)) {
					project.setRequiredByDate(asSoonRequired);
				}

				Address address = new Address();
				if (StringUtils.isNotBlank(addrId)) {
					address.setAddressId(Integer.parseInt(addrId));
				}
				if (StringUtils.isNotBlank(addrLine1)) {
					address.setAddressLine1(addrLine1);
				}
				if (StringUtils.isNotBlank(addrLine2)) {
					address.setAddressLine2(addrLine2);
				}
				if (StringUtils.isNotBlank(city)) {
					address.setCity(city);
				}
				if (StringUtils.isNotBlank(state)) {
					address.setState(state);
				}
				if (StringUtils.isNotBlank(country)) {
					address.setCountry(country);
				}
				if (StringUtils.isNotBlank(zipcode)) {
					address.setZipcode(Integer.parseInt(zipcode));
				}
				if (point != null) {
					address.setLocation(point);
				}
				if (StringUtils.isNotBlank(email)) {
					address.setEmail(email);
				}

				Set<Phone> phoneSet = new HashSet<>();
				Phone phone = new Phone();
				if (StringUtils.isNotBlank(phoneType)) {
					phone.setType(PhoneType.valueOf(phoneType));
				}

				if (StringUtils.isNotBlank(number)) {
					phone.setNumber(number);
				}
				phoneSet.add(phone);
				address.setPhoneNumbers(phoneSet);

				project.setAddress(address);

				if (StringUtils.isNotBlank(status)) {
					project.setStatus(status);
				} else {
					project.setStatus("ACTIVE");
				}

				if (StringUtils.isNotBlank(constructionType)) {
					if (StringUtils.containsAny(constructionType, "Existing")) {
						project.setConstructionNew(false);
					} else if (StringUtils.containsAny(constructionType, "New")) {
						project.setConstructionNew(true);
					}
				}

				project.setUser(user);

				// later we have to uncomment lines of code.
				// if (privilegeService.checkPrivilege(uid, "POST", "PROJECT"))
				// {
				// userProjectDAO.insert(project);
				// logger.info("creating user project");
				// } else {
				// throw new Exception(
				// "Cannot Post more Projects, upgrade your package...!!");
				// }

				userProjectDAO.insert(project);
				logger.info("creating user project");
				Set<User> userSet = new HashSet<>();
				if (userType.equalsIgnoreCase("ARCHITECT")) {
					List<User> architectList = userDAO
							.findByRoles_NameAndStatus("ARCHITECT", "ACTIVE");

					userSet.addAll(architectList);

				} else if (userType.equalsIgnoreCase("INTERIORDESIGNER")) {
					List<User> interiorList = userDAO
							.findByRoles_NameAndStatus("INTERIORDESIGNER",
									"ACTIVE");

					userSet.addAll(interiorList);
				} else {
					List<User> architectList = userDAO
							.findByRoles_NameAndStatus("ARCHITECT", "ACTIVE");
					List<User> interiorList = userDAO
							.findByRoles_NameAndStatus("INTERIORDESIGNER",
									"ACTIVE");

					userSet.addAll(architectList);
					userSet.addAll(interiorList);
				}

				Integer customerZipcode = null;
				Set<Address> addresses = user.getAddresses();
				for (Address addr : addresses) {
					customerZipcode = addr.getZipcode();
				}

				DecimalFormat df = new DecimalFormat("####0.00");

				double projectBudget = Double.valueOf(budget);

				// Email and SMS
				// userCommunicationService.sendProjectPostedInfo(userSet,
				// user.getFullName(), customerZipcode, projectType,
				// df.format(projectBudget), constructionType);

				List<User> users = new ArrayList<User>();
				if (userType.equalsIgnoreCase("ARCHITECT")) {
					List<User> userArchitect = userDAO
							.findByRoles_Name("ARCHITECT");
					users.addAll(userArchitect);

				} else if (userType.equalsIgnoreCase("INTERIORDESIGNER")) {
					List<User> userInterior = userDAO
							.findByRoles_Name("INTERIORDESIGNER");
					users.addAll(userInterior);
				} else {
					List<User> userArchitect = userDAO
							.findByRoles_Name("ARCHITECT");
					List<User> userInterior = userDAO
							.findByRoles_Name("INTERIORDESIGNER");
					users.addAll(userArchitect);
					users.addAll(userInterior);

				}

				for (User archId : users) {
					if (archId.getRoles() != null) {
						for (Role role : archId.getRoles()) {
							if (role.getName().equalsIgnoreCase("ARCHITECT")) {
								String userId = archId.getId();
								notificationServiceImpl
										.createNotificationChangeEvent(userId,
												"Project", "2", "ADD");
							}
							if (role.getName().equalsIgnoreCase(
									"INTERIORDESIGNER")) {
								String userId = archId.getId();
								notificationServiceImpl
										.createNotificationChangeEvent(userId,
												"Project", "2", "ADD");
							}
						}
					}
				}

				String note = "User successfully posted the " + name
						+ " Project";

				updateUserAudit(userInfo, "POSTPROJECT", note);
			} else {
				logger.info("unable to create project for user");
				throw new Exception("unable to create project for user");
			}
		}
		return project;
	}

	/**
	 * Method to Update User's project details
	 * 
	 * This method updates the project details of the particular user
	 * 
	 * @throws Exception
	 * 
	 * @PutParam would be the projectname,type,budget,addr line1,addr
	 *           line2,city,state,country,zipcode,email,status with @PathParam
	 *           being the Userid and the projectId
	 */
	@Override
	public Project updateUserProject(String uid, String projid,
			String projName, String projdesc, String budget, String type,
			String addrLine1, String addrLine2, String city, String state,
			String country, String zipcode, String email, String status)
			throws Exception {
		Project projects = null;
		User user = null;
		if (StringUtils.isNotBlank(uid)) {
			user = userDAO.findOne(uid);
			if (user != null) {
				if (StringUtils.isNotBlank(projid)) {
					projects = userProjectDAO.findOne(projid);
					if (projects != null) {
						logger.info("updating user project");
						if (StringUtils.isNotBlank(projName)) {
							projects.setName(projName);
						}
						if (StringUtils.isNotBlank(type)) {
							projects.setType(ProjectType.valueOf(type));
						}
						if (StringUtils.isNotBlank(budget)) {
							projects.setBudget(Double.parseDouble(budget));
						}
						if (StringUtils.isNotBlank(projdesc)) {
							Description des = new Description();
							des.setLang("Eng");
							des.setVal(projdesc);
							projects.setDesc(des);
						}
						if (StringUtils.isNotBlank(addrLine1)) {
							Address adr = projects.getAddress();
							adr.setAddressLine1(addrLine1);
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(addrLine2)) {
							Address adr = projects.getAddress();
							adr.setAddressLine2(addrLine2);
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(city)) {
							Address adr = projects.getAddress();
							adr.setCity(city);
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(state)) {
							Address adr = projects.getAddress();
							adr.setState(state);
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(country)) {
							Address adr = projects.getAddress();
							adr.setCity(city);
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(zipcode)) {
							Address adr = projects.getAddress();
							adr.setZipcode(Integer.parseInt(zipcode));
							projects.setAddress(adr);
						}
						if (StringUtils.isNotBlank(email)) {
							Address adr = projects.getAddress();
							adr.setEmail(email);
							projects.setAddress(adr);
						}

						if (StringUtils.isNotBlank(status)) {
							projects.setStatus(status);
						}
					}
					userProjectDAO.save(projects);
				} else {
					logger.info("project is null, so cannot remove project");
					throw new Exception(
							"project is null, so cannot remove project");
				}
			} else {
				logger.info("user is null, so cannot update user project");
				throw new Exception(
						"user is null, so cannot update user project");
			}
		}
		return projects;
	}

	/**
	 * Method to delete the particular project of the user
	 * 
	 * Here the particular project of the particular user would be deleted
	 * 
	 * @throws Exception
	 * 
	 * @PathParam will the userId and the ProjectId
	 */
	@Override
	public String deleteUserProject(String uid, String projid) throws Exception {
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				if (StringUtils.isNotBlank(projid)) {
					Project project = userProjectDAO.findByIdAndUser(uid, user);
					if (project != null) {
						userProjectDAO.delete(projid);
					} else {
						logger.info("project is null");
						throw new Exception("project is null");
					}
				}
			} else {
				logger.info("user is empty, unable to delete user project");
				throw new Exception(
						"user is empty, unable to delete user project");
			}
		}
		return projid;
	}

	private void updateUserAudit(UserInfo userInfo, String userAuditTypeVal,
			String note) {
		if (userInfo != null) {
			UserAudit userAudit = new UserAudit();
			// user id
			userAudit.setUserId(userInfo.getUserid());
			// user audit type
			UserAuditType userAuditType = userAuditTypeRepository
					.findByValue(userAuditTypeVal);
			userAudit.setUserAuditType(userAuditType);
			// note
			userAudit.setNote(note);
			// created by
			userAudit.setCreatedBy("Buildonn");
			// last modified by
			userAudit.setModifiedBy("Buildonn");
			// created date
			userAudit.setCreatedTime(new Date());
			// last modified date
			userAudit.setLastModifiedTime(new Date());

			userAuditRepository.save(userAudit);
		}
	}

	@Override
	public LeadProject createLeadProject(String name, String projectType,
			String addrId, String addrLine1, String addrLine2, String city,
			String state, String country, String zipcode, String area,
			String salesExecutiveId, String ownerId, String projectStage,
			String followupDate, String contactedDate, String note,
			String requirementNote, String referencedBy, String called,
			String reasonForReject, String latVal, String longVal,
			String status, String customerResponse) throws Exception {
		LeadProject leadProject = null;
		int endUsers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;

		int requirementGivenNewLead = 0;
		int callAfterOneToTwoDaysNewLead = 0;
		int callAfterOneToTwoWeeksNewLead = 0;
		int callAfterFifteenToThirtyDaysNewLead = 0;
		int callAfterThirtyToSixtyDaysNewLead = 0;
		int anySpecificNewLead = 0;

		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();

		if (StringUtils.isNotBlank(salesExecutiveId)
				&& StringUtils.isNotBlank(ownerId)) {
			User salesExecutive = userDAO.findById(salesExecutiveId);
			User owner = userDAO.findById(ownerId);
			if (salesExecutive != null && owner != null) {
				List<LeadProject> leads = leadProjectDAO.findByOwner_Id(owner
						.getId());
				if (leads.size() == 0) {
					leadProject = new LeadProject();
					// Project name
					if (StringUtils.isNotBlank(name)) {
						leadProject.setName(name);
					}
					// ProjectType
					if (StringUtils.isNotBlank(projectType)) {
						leadProject.setType(ProjectType.valueOf(projectType));
					}
					// SatesExecutive
					leadProject.setSalesExecutive(salesExecutive);
					// Owner
					leadProject.setOwner(owner);
					// Project Stage
					if (StringUtils.isNotBlank(projectStage)) {
						leadProject.setProjectStage(projectStage);
					}
					// Address
					Address address = new Address();
					if (StringUtils.isNotBlank(addrId)) {
						address.setAddressId(Integer.parseInt(addrId));
					}
					if (StringUtils.isNotBlank(addrLine1)) {
						address.setAddressLine1(addrLine1);
					}
					if (StringUtils.isNotBlank(addrLine2)) {
						address.setAddressLine2(addrLine2);
					}
					if (StringUtils.isNotBlank(city)) {
						address.setCity(city);
					}
					if (StringUtils.isNotBlank(state)) {
						address.setState(state);
					}
					if (StringUtils.isNotBlank(country)) {
						address.setCountry(country);
					}
					if (StringUtils.isNotBlank(zipcode)) {
						address.setZipcode(Integer.parseInt(zipcode));
						leadProject.setZipcode(zipcode);
					}

					Point point = null;
					if (StringUtils.isNotBlank(latVal)
							&& StringUtils.isNotBlank(longVal)) {
						point = new Point(Double.parseDouble(latVal),
								Double.parseDouble(longVal));
					} else {

						String addressPoint = addrLine1 + "," + addrLine2 + ","
								+ city + "," + state + "," + country + ","
								+ zipcode;
						point = addressLookupService
								.getGeoCodedAddress(addressPoint);
						if (point == null) {
							logger.info("based on Address search lat and lon null ");
							point = addressLookupService
									.getGeoCodedAddress(zipcode);
						}
					}

					if (point != null) {
						address.setLocation(point);
					}

					leadProject.setAddress(address);

					// Area
					if (StringUtils.isNotBlank(area)) {
						leadProject.setArea(area);
					}
					// Note
					if (StringUtils.isNotBlank(note)) {
						leadProject.setNote(note);
					}
					// Requirement note
					if (StringUtils.isNotBlank(requirementNote)) {
						leadProject.setRequirementNote(requirementNote);
					}
					// followUpDate
					if (StringUtils.isNotBlank(followupDate)) {
						leadProject.setFollowupDate(followupDate);
					}
					// contactedDate
					if (StringUtils.isNotBlank(contactedDate)) {
						leadProject.setContactedDate(contactedDate);
					}
					// referenced by
					if (StringUtils.isNotBlank(referencedBy)) {
						leadProject.setReferencedBy(referencedBy);
					}

					// called
					if (StringUtils.isNotBlank(called)) {
						if (called.equalsIgnoreCase("YES")) {
							leadProject.setCalled(true);
						}
					}
					// Reason for reject
					if (StringUtils.isNotBlank(reasonForReject)) {
						leadProject.setReasonForReject(reasonForReject);
					}
					// Status
					if (StringUtils.isNotBlank(status)) {
						leadProject.setStatus(status);
					}
					// CustomerResponse
					if (StringUtils.isNotBlank(customerResponse)) {
						leadProject.setCustomerResponse(customerResponse);
					}
					leadProject.setCreatedDate(new Date());
					leadProject.setLastModifiedDate(new Date());
					leadProjectDAO.save(leadProject);

					// TeleCall History
					TeleCallHistory teleCallHistory = teleCallHistoryDAO
							.findByTeleIdAndCreatedDateBetween(leadProject
									.getSalesExecutive().getId(), sdate, edate);
					if (teleCallHistory != null) {
						for (Role role : leadProject.getOwner().getRoles()) {
							if (role.getName().equalsIgnoreCase("ENDUSER")) {
								endUsers = teleCallHistory.getEndUsers();
								endUsers++;
								teleCallHistory.setEndUsers(endUsers);
							} else if (role.getName().equalsIgnoreCase(
									"CIVILCONTRACTOR")) {
								civilContractors = teleCallHistory
										.getCivilContractors();
								civilContractors++;
								teleCallHistory
										.setCivilContractors(civilContractors);
							} else if (role.getName().equalsIgnoreCase(
									"CIVILENGINEER")) {
								civilEngineers = teleCallHistory
										.getCivilEngineers();
								civilEngineers++;
								teleCallHistory
										.setCivilEngineers(civilEngineers);
							} else if (role.getName().equalsIgnoreCase(
									"PLUMBER")) {
								plumbers = teleCallHistory.getPlumbers();
								plumbers++;
								teleCallHistory.setPlumbers(plumbers);
							} else {
								others = teleCallHistory.getOthers();
								others++;
								teleCallHistory.setOthers(others);
							}
						}

						if (leadProject.getCustomerResponse() != null) {

							if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("RequirementGiven")) {
								requirementGivenNewLead = teleCallHistory
										.getRequirementGivenNewLead();
								requirementGivenNewLead++;
								teleCallHistory
										.setRequirementGivenNewLead(requirementGivenNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDaysNewLead = teleCallHistory
										.getCallAfterOneToTwoDaysNewLead();
								callAfterOneToTwoDaysNewLead++;
								teleCallHistory
										.setCallAfterOneToTwoDaysNewLead(callAfterOneToTwoDaysNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeksNewLead = teleCallHistory
										.getCallAfterOneToTwoWeeksNewLead();
								callAfterOneToTwoWeeksNewLead++;
								teleCallHistory
										.setCallAfterOneToTwoWeeksNewLead(callAfterOneToTwoWeeksNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDaysNewLead = teleCallHistory
										.getCallAfterFifteenToThirtyDaysNewLead();
								callAfterFifteenToThirtyDaysNewLead++;
								teleCallHistory
										.setCallAfterFifteenToThirtyDaysNewLead(callAfterFifteenToThirtyDaysNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDaysNewLead = teleCallHistory
										.getCallAfterThirtyToSixtyDaysNewLead();
								callAfterThirtyToSixtyDaysNewLead++;
								teleCallHistory
										.setCallAfterThirtyToSixtyDaysNewLead(callAfterThirtyToSixtyDaysNewLead);
							} else {
								anySpecificNewLead = teleCallHistory
										.getAnySpecificNewLead();
								anySpecificNewLead++;
								teleCallHistory
										.setAnySpecificNewLead(anySpecificNewLead);
							}
						}
						teleCallHistory.setTeleId(leadProject
								.getSalesExecutive().getId());
						teleCallHistoryDAO.save(teleCallHistory);
					} else {
						TeleCallHistory teleCallHistoryNew = new TeleCallHistory();

						for (Role role : leadProject.getOwner().getRoles()) {
							if (role.getName().equalsIgnoreCase("ENDUSER")) {
								endUsers++;
								teleCallHistoryNew.setEndUsers(endUsers);
							} else if (role.getName().equalsIgnoreCase(
									"CIVILCONTRACTOR")) {
								civilContractors++;
								teleCallHistoryNew
										.setCivilContractors(civilContractors);
							} else if (role.getName().equalsIgnoreCase(
									"CIVILENGINEER")) {
								civilEngineers++;
								teleCallHistoryNew
										.setCivilEngineers(civilEngineers);
							} else if (role.getName().equalsIgnoreCase(
									"PLUMBER")) {
								plumbers++;
								teleCallHistoryNew.setPlumbers(plumbers);
							} else {
								others++;
								teleCallHistoryNew.setOthers(others);
							}
						}

						if (leadProject.getCustomerResponse() != null) {

							if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("RequirementGiven")) {
								requirementGivenNewLead++;
								teleCallHistoryNew
										.setRequirementGivenNewLead(requirementGivenNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDaysNewLead++;
								teleCallHistoryNew
										.setCallAfterOneToTwoDaysNewLead(callAfterOneToTwoDaysNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeksNewLead++;
								teleCallHistoryNew
										.setCallAfterOneToTwoWeeksNewLead(callAfterOneToTwoWeeksNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDaysNewLead++;
								teleCallHistoryNew
										.setCallAfterFifteenToThirtyDaysNewLead(callAfterFifteenToThirtyDaysNewLead);
							} else if (leadProject.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDaysNewLead++;
								teleCallHistoryNew
										.setCallAfterThirtyToSixtyDaysNewLead(callAfterThirtyToSixtyDaysNewLead);
							} else {
								anySpecificNewLead++;
								teleCallHistoryNew
										.setAnySpecificNewLead(anySpecificNewLead);
							}
						}
						teleCallHistoryNew.setTeleId(leadProject
								.getSalesExecutive().getId());
						teleCallHistoryDAO.save(teleCallHistoryNew);
					}
				} else {
					throw new Exception(
							"Already lead is created for this Customer");
				}
			}
		}
		return leadProject;
	}

	@Override
	public List<LeadProject> manageLeadProjects(String projName, String type,
			String addrLine1, String addrLine2, String city, String zipcode,
			String projectStage, String area, String followupDate,
			String followupDateEntered, String contactedDate,
			String customerName, String salesExecutiveId, String referencedBy,
			String called, String reasonForReject, String requirement,
			String status, String toFollowupDate, String roleName, Point point,
			Sort sort) {
		List<LeadProject> projects = new ArrayList<>();
		if (StringUtils.isBlank(projName) && StringUtils.isBlank(type)
				&& StringUtils.isBlank(addrLine1)
				&& StringUtils.isBlank(addrLine2) && StringUtils.isBlank(city)
				&& StringUtils.isBlank(zipcode)
				&& StringUtils.isBlank(projectStage)
				&& StringUtils.isBlank(area)
				&& StringUtils.isBlank(followupDate)
				&& StringUtils.isBlank(followupDateEntered)
				&& StringUtils.isBlank(contactedDate)
				&& StringUtils.isBlank(customerName)
				&& StringUtils.isBlank(salesExecutiveId)
				&& StringUtils.isBlank(referencedBy)
				&& StringUtils.isBlank(called)
				&& StringUtils.isBlank(reasonForReject)
				&& StringUtils.isBlank(requirement)
				&& StringUtils.isBlank(status)
				&& StringUtils.isBlank(toFollowupDate)
				&& StringUtils.isBlank(roleName) && point == null) {
			projects = leadProjectDAO.findAll(sort);
		} else if (StringUtils.isNotBlank(projName)) {
			projects = leadProjectDAO.findByNameLikeAllIgnoreCase(projName);
		} else if (StringUtils.isNotBlank(type)) {
			projects = leadProjectDAO.findByType(ProjectType.valueOf(type));
		} else if (StringUtils.isNotBlank(addrLine1)) {
			projects = leadProjectDAO
					.findByAddress_AddressLine1LikeAllIgnoreCase(addrLine1);
		} else if (StringUtils.isNotBlank(addrLine2)) {
			projects = leadProjectDAO
					.findByAddress_AddressLine2LikeAllIgnoreCase(addrLine2);
		} else if (StringUtils.isNotBlank(city)) {
			projects = leadProjectDAO.findByAddress_CityAllIgnoreCase(city);
		} else if (StringUtils.isNotBlank(zipcode)) {
			projects = leadProjectDAO.findByAddress_Zipcode(Integer
					.parseInt(zipcode));
		} else if (point != null) {
			projects = leadProjectDAO.findByAddress_Location(point);
		} else if (StringUtils.isNotBlank(customerName)) {
			Set<User> customers = userDAO
					.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
							customerName, customerName, customerName,
							customerName, customerName);
			if (customers.size() != 0) {
				for (User customer : customers) {
					projects.addAll(leadProjectDAO.findByOwner_Id(customer
							.getId()));
				}
			}

		} else if (StringUtils.isNotBlank(salesExecutiveId)) {
			User salesExecutive = userDAO.findById(salesExecutiveId);
			if (salesExecutive != null) {
				projects = leadProjectDAO
						.findBySalesExecutive_Id(salesExecutive.getId());
			}
		} else if (StringUtils.isNotBlank(area)) {
			projects = leadProjectDAO.findByAreaLikeAllIgnoreCase(area);
		} else if (StringUtils.isNotBlank(projectStage)) {
			projects = leadProjectDAO
					.findByProjectStageLikeAllIgnoreCase(projectStage);
		} else if (StringUtils.isNotBlank(followupDate)
				&& StringUtils.isNotBlank(toFollowupDate)) {
			Set<String> followupDateSet = new HashSet<>();
			followupDateSet.add(followupDate);
			String[] fdateAry = toFollowupDate.split(",");
			for (String fdate : fdateAry) {
				followupDateSet.add(fdate);
			}
			projects = leadProjectDAO.findByFollowupDateIn(followupDateSet);
		} else if (StringUtils.isNotBlank(followupDate)
				&& StringUtils.isNotBlank(called)
				&& called.equalsIgnoreCase("NO")) {
			Set<String> followupDateSet = new HashSet<>();
			String[] fdateAry = followupDate.split(",");
			for (String fdate : fdateAry) {
				followupDateSet.add(fdate);
			}
			projects = leadProjectDAO.findByFollowupDateInAndCalledIsFalse(
					followupDateSet, Boolean.valueOf(called));
		} else if (StringUtils.isNotBlank(followupDate)) {
			projects = leadProjectDAO.findByFollowupDate(followupDate);
		} else if (StringUtils.isNotBlank(contactedDate)) {
			projects = leadProjectDAO.findByContactedDate(contactedDate);
		} else if (StringUtils.isNotBlank(referencedBy)) {
			projects = leadProjectDAO
					.findByReferencedByLikeAllIgnoreCase(referencedBy);
		} else if (StringUtils.isNotBlank(called)) {
			if (called.equalsIgnoreCase("YES")) {
				projects = leadProjectDAO.findByCalledIsTrue(Boolean
						.valueOf(called));
			} else if (called.equalsIgnoreCase("NO")) {
				projects = leadProjectDAO.findByCalledIsFalse(Boolean
						.valueOf(called));
			}
		} else if (StringUtils.isNotBlank(reasonForReject)) {
			projects = leadProjectDAO
					.findByReasonForRejectLikeAllIgnoreCase(reasonForReject);
		} else if (StringUtils.isNotBlank(requirement)) {
			projects = leadProjectDAO
					.findByRequirementNoteContains(requirement);
		} else if (StringUtils.isNotBlank(status)) {
			projects = leadProjectDAO.findByStatus(status);
		} else if (StringUtils.isNotBlank(followupDateEntered)) {
			if (StringUtils.isNotBlank(followupDateEntered)
					&& followupDateEntered.equalsIgnoreCase("YES")) {
				projects = leadProjectDAO.findByFollowupDateNotNull();
			} else if (StringUtils.isNotBlank(followupDateEntered)
					&& followupDateEntered.equalsIgnoreCase("No")) {
				projects = leadProjectDAO.findByFollowupDateNull();
			}
		} else if (StringUtils.isNotBlank(roleName)) {
			Set<User> users = userDAO.findByRoles_NameAllIgnoreCase(roleName);
			Set<String> userIds = new HashSet<>();
			for (User user : users) {
				userIds.add(user.getId());
			}
			if (userIds.size() >= 1) {
				projects = leadProjectDAO.findByOwner_IdIn(userIds);
			}
		}
		return projects;
	}

	@Override
	public List<LeadProject> manageFieldAssociateLeadProjectsMap(
			String fieldAssociateId, String projName, String type,
			String addrLine1, String addrLine2, String city, String zipcode,
			String projectStage, String area, String followupDate,
			String followupDateEntered, String contactedDate,
			String customerName, String referencedBy, String called,
			String reasonForReject, String requirement, String status,
			String toFollowupDate, String roleName, Point point, Sort sort) {

		List<LeadProject> projects = new ArrayList<>();
		if (StringUtils.isNotBlank(fieldAssociateId)) {
			if (StringUtils.isBlank(projName) && StringUtils.isBlank(type)
					&& StringUtils.isBlank(addrLine1)
					&& StringUtils.isBlank(addrLine2)
					&& StringUtils.isBlank(city)
					&& StringUtils.isBlank(zipcode)
					&& StringUtils.isBlank(projectStage)
					&& StringUtils.isBlank(area)
					&& StringUtils.isBlank(followupDate)
					&& StringUtils.isBlank(followupDateEntered)
					&& StringUtils.isBlank(contactedDate)
					&& StringUtils.isBlank(customerName)
					&& StringUtils.isBlank(referencedBy)
					&& StringUtils.isBlank(called)
					&& StringUtils.isBlank(reasonForReject)
					&& StringUtils.isBlank(requirement)
					&& StringUtils.isBlank(status)
					&& StringUtils.isBlank(roleName) && point == null) {
				projects = leadProjectDAO
						.findBySalesExecutive_Id(fieldAssociateId);
			} else if (StringUtils.isNotBlank(projName)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndNameLikeAllIgnoreCase(
								fieldAssociateId, projName);
			} else if (StringUtils.isNotBlank(type)) {
				projects = leadProjectDAO.findBySalesExecutive_IdAndType(
						fieldAssociateId, ProjectType.valueOf(type));
			} else if (StringUtils.isNotBlank(addrLine1)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAddress_AddressLine1LikeAllIgnoreCase(
								fieldAssociateId, addrLine1);
			} else if (StringUtils.isNotBlank(addrLine2)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAddress_AddressLine2LikeAllIgnoreCase(
								fieldAssociateId, addrLine2);
			} else if (StringUtils.isNotBlank(city)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAddress_CityAllIgnoreCase(
								fieldAssociateId, city);
			} else if (StringUtils.isNotBlank(zipcode)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAddress_Zipcode(
								fieldAssociateId, Integer.parseInt(zipcode));
			} else if (point != null) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAddress_Location(
								fieldAssociateId, point);
			} else if (StringUtils.isNotBlank(customerName)) {
				Set<User> customers = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								customerName, customerName, customerName,
								customerName, customerName);
				if (customers.size() != 0) {
					for (User customer : customers) {
						projects.addAll(leadProjectDAO
								.findBySalesExecutive_IdAndOwner_Id(
										fieldAssociateId, customer.getId()));
					}
				}

			} else if (StringUtils.isNotBlank(area)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndAreaLikeAllIgnoreCase(
								fieldAssociateId, area);
			} else if (StringUtils.isNotBlank(projectStage)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndProjectStageLikeAllIgnoreCase(
								fieldAssociateId, projectStage);
			} else if (StringUtils.isNotBlank(followupDate)
					&& StringUtils.isNotBlank(toFollowupDate)) {
				Set<String> followupDateSet = new HashSet<>();
				followupDateSet.add(followupDate);
				String[] fdateAry = toFollowupDate.split(",");
				for (String fdate : fdateAry) {
					followupDateSet.add(fdate);
				}
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndFollowupDateIn(
								fieldAssociateId, followupDateSet);
			} else if (StringUtils.isNotBlank(followupDate)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndFollowupDate(
								fieldAssociateId, followupDate);
			} else if (StringUtils.isNotBlank(contactedDate)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndContactedDate(
								fieldAssociateId, contactedDate);
			} else if (StringUtils.isNotBlank(referencedBy)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndReferencedByLikeAllIgnoreCase(
								fieldAssociateId, referencedBy);
			} else if (StringUtils.isNotBlank(called)) {
				if (called.equalsIgnoreCase("YES")) {
					projects = leadProjectDAO
							.findBySalesExecutive_IdAndCalledIsTrue(
									fieldAssociateId, Boolean.valueOf(called));
				} else if (called.equalsIgnoreCase("NO")) {
					projects = leadProjectDAO
							.findBySalesExecutive_IdAndCalledIsFalse(
									fieldAssociateId, Boolean.valueOf(called));
				}
			} else if (StringUtils.isNotBlank(reasonForReject)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndReasonForRejectLikeAllIgnoreCase(
								fieldAssociateId, reasonForReject);
			} else if (StringUtils.isNotBlank(requirement)) {
				projects = leadProjectDAO
						.findBySalesExecutive_IdAndRequirementNoteContains(
								fieldAssociateId, requirement);
			} else if (StringUtils.isNotBlank(status)) {
				projects = leadProjectDAO.findBySalesExecutive_IdAndStatus(
						fieldAssociateId, status);
			} else if (StringUtils.isNotBlank(followupDateEntered)) {
				if (followupDateEntered.equalsIgnoreCase("YES")) {
					projects = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDateNotNull(fieldAssociateId);
				} else if (followupDateEntered.equalsIgnoreCase("No")) {
					projects = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDateNull(fieldAssociateId);
				}
			} else if (StringUtils.isNotBlank(roleName)) {
				Set<User> users = userDAO
						.findByRoles_NameAllIgnoreCase(roleName);
				Set<String> userIds = new HashSet<>();
				for (User user : users) {
					userIds.add(user.getId());
				}
				if (userIds.size() >= 1) {
					projects = leadProjectDAO.findByOwner_IdIn(userIds);
				}
			}
		}
		return projects;
	}

	@Override
	public LeadProject getLeadProjectViewDetails(String leadProjectId) {
		LeadProject project = null;
		if (StringUtils.isNotBlank(leadProjectId)) {
			project = leadProjectDAO.findOne(leadProjectId);
		}
		return project;
	}

	@Override
	public LeadProject deleteLeadProjectById(String leadProjectId) {
		LeadProject project = null;
		if (StringUtils.isNotBlank(leadProjectId)) {
			project = leadProjectDAO.findOne(leadProjectId);
			if (project != null) {
				leadProjectDAO.delete(project);
			}
		}
		return project;
	}

	@Override
	public LeadProject updateLeadProjectById(String leadId, String projName,
			String note, String requirementNote, String type, String addrLine1,
			String addrLine2, String city, String state, String country,
			String zipcode, String latVal, String longVal, String projectStage,
			String area, String followupDate, String contactedDate,
			String salesExecutiveId, String referencedBy, String called,
			String reasonForReject, String status, String customerResponse) {

		LeadProject project = null;
		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();

		int endUsers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;

		int requirementGiven = 0;
		int switchedOff = 0;
		int wrongNumber = 0;
		int notPicked = 0;
		int callAfterOneToTwoDays = 0;
		int callAfterOneToTwoWeeks = 0;
		int callAfterFifteenToThirtyDays = 0;
		int callAfterThirtyToSixtyDays = 0;
		int anySpecific = 0;

		int requirementGivenNewLead = 0;
		int callAfterOneToTwoDaysNewLead = 0;
		int callAfterOneToTwoWeeksNewLead = 0;
		int callAfterFifteenToThirtyDaysNewLead = 0;
		int callAfterThirtyToSixtyDaysNewLead = 0;
		int anySpecificNewLead = 0;

		if (StringUtils.isNotBlank(leadId)) {
			project = leadProjectDAO.findOne(leadId);
			if (project != null) {
				if (StringUtils.isNotBlank(projName)) {
					project.setName(projName);
				}
				if (StringUtils.isNotBlank(type)) {
					project.setType(ProjectType.valueOf(type));
				}

				if (StringUtils.isNotBlank(note)) {
					project.setNote(note);
				}

				// requirement note
				if (StringUtils.isNotBlank(requirementNote)) {
					project.setRequirementNote(requirementNote);
				}
				if (StringUtils.isNotBlank(addrLine1)) {
					Address adr = project.getAddress();
					adr.setAddressLine1(addrLine1);
					project.setAddress(adr);
				}
				if (StringUtils.isNotBlank(addrLine2)) {
					Address adr = project.getAddress();
					adr.setAddressLine2(addrLine2);
					project.setAddress(adr);
				}
				if (StringUtils.isNotBlank(city)) {
					Address adr = project.getAddress();
					adr.setCity(city);
					project.setAddress(adr);
				}
				if (StringUtils.isNotBlank(state)) {
					Address adr = project.getAddress();
					adr.setState(state);
					project.setAddress(adr);
				}
				if (StringUtils.isNotBlank(country)) {
					Address adr = project.getAddress();
					adr.setCity(city);
					project.setAddress(adr);
				}
				if (StringUtils.isNotBlank(zipcode)) {
					Address adr = project.getAddress();
					adr.setZipcode(Integer.parseInt(zipcode));
					project.setAddress(adr);
					project.setZipcode(zipcode);
				}

				if (StringUtils.isNotBlank(latVal)
						&& StringUtils.isNotBlank(longVal)) {
					Point point = new Point(Double.parseDouble(latVal),
							Double.parseDouble(longVal));
					if (point != null) {
						Address adr = project.getAddress();
						adr.setLocation(point);
						project.setAddress(adr);
					}
				}
				if (StringUtils.isNotBlank(projectStage)) {
					project.setProjectStage(projectStage);
				}

				if (StringUtils.isNotBlank(area)) {
					project.setArea(area);
				}

				if (StringUtils.isNotBlank(followupDate)) {
					project.setFollowupDate(followupDate);
				}

				if (StringUtils.isNotBlank(contactedDate)) {
					project.setContactedDate(contactedDate);
				}
				// Referenced By
				if (StringUtils.isNotBlank(referencedBy)) {
					project.setReferencedBy(referencedBy);
				}
				// Called
				if (StringUtils.isNotBlank(called)) {
					if (called.equalsIgnoreCase("YES")) {
						project.setCalled(true);
					} else {
						project.setCalled(false);
					}
				}
				// Reason for reject
				if (StringUtils.isNotBlank(reasonForReject)) {
					project.setReasonForReject(reasonForReject);
				}
				if (StringUtils.isNotBlank(salesExecutiveId)) {
					User salesExecutive = userDAO.findById(salesExecutiveId);
					if (salesExecutive != null) {
						project.setSalesExecutive(salesExecutive);
					}
				}
				// status
				if (StringUtils.isNotBlank(status)) {
					project.setStatus(status);
				}
				// Customer Response
				if (StringUtils.isNotBlank(customerResponse)) {
					project.setCustomerResponse(customerResponse);
				}
				project.setLastModifiedDate(new Date());

				leadProjectDAO.save(project);

				// TeleCall History
				TeleCallHistory teleCallHistory = teleCallHistoryDAO
						.findByTeleIdAndCreatedDateBetween(project
								.getSalesExecutive().getId(), sdate, edate);
				if (teleCallHistory != null) {
					for (Role role : project.getOwner().getRoles()) {
						if (role.getName().equalsIgnoreCase("ENDUSER")) {
							endUsers = teleCallHistory.getEndUsers();
							endUsers++;
							teleCallHistory.setEndUsers(endUsers);
						} else if (role.getName().equalsIgnoreCase(
								"CIVILCONTRACTOR")) {
							civilContractors = teleCallHistory
									.getCivilContractors();
							civilContractors++;
							teleCallHistory
									.setCivilContractors(civilContractors);
						} else if (role.getName().equalsIgnoreCase(
								"CIVILENGINEER")) {
							civilEngineers = teleCallHistory
									.getCivilEngineers();
							civilEngineers++;
							teleCallHistory.setCivilEngineers(civilEngineers);
						} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
							plumbers = teleCallHistory.getPlumbers();
							plumbers++;
							teleCallHistory.setPlumbers(plumbers);
						} else {
							others = teleCallHistory.getOthers();
							others++;
							teleCallHistory.setOthers(others);
						}
					}

					if (project.getCustomerResponse() != null) {
						LeadProject lp = leadProjectDAO
								.findByIdAndCreatedDateBetween(project.getId(),
										sdate, edate);
						if (lp != null) {

							if (project.getCustomerResponse().equalsIgnoreCase(
									"RequirementGiven")) {
								requirementGivenNewLead = teleCallHistory
										.getRequirementGivenNewLead();
								requirementGivenNewLead++;
								teleCallHistory
										.setRequirementGivenNewLead(requirementGivenNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDaysNewLead = teleCallHistory
										.getCallAfterOneToTwoDaysNewLead();
								callAfterOneToTwoDaysNewLead++;
								teleCallHistory
										.setCallAfterOneToTwoDaysNewLead(callAfterOneToTwoDaysNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeksNewLead = teleCallHistory
										.getCallAfterOneToTwoWeeksNewLead();
								callAfterOneToTwoWeeksNewLead++;
								teleCallHistory
										.setCallAfterOneToTwoWeeksNewLead(callAfterOneToTwoWeeksNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDaysNewLead = teleCallHistory
										.getCallAfterFifteenToThirtyDaysNewLead();
								callAfterFifteenToThirtyDaysNewLead++;
								teleCallHistory
										.setCallAfterFifteenToThirtyDaysNewLead(callAfterFifteenToThirtyDaysNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDaysNewLead = teleCallHistory
										.getCallAfterThirtyToSixtyDaysNewLead();
								callAfterThirtyToSixtyDaysNewLead++;
								teleCallHistory
										.setCallAfterThirtyToSixtyDaysNewLead(callAfterThirtyToSixtyDaysNewLead);
							} else {
								anySpecificNewLead = teleCallHistory
										.getAnySpecificNewLead();
								anySpecificNewLead++;
								teleCallHistory
										.setAnySpecificNewLead(anySpecificNewLead);
							}

						} else {

							if (project.getCustomerResponse().equalsIgnoreCase(
									"RequirementGiven")) {
								requirementGiven = teleCallHistory
										.getRequirementGiven();
								requirementGiven++;
								teleCallHistory
										.setRequirementGiven(requirementGiven);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("SwitchedOff")) {
								switchedOff = teleCallHistory.getSwitchedOff();
								switchedOff++;
								teleCallHistory.setSwitchedOff(switchedOff);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("WrongNumber")) {
								wrongNumber = teleCallHistory.getWrongNumber();
								wrongNumber++;
								teleCallHistory.setWrongNumber(wrongNumber);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("NotPicked")) {
								notPicked = teleCallHistory.getNotPicked();
								notPicked++;
								teleCallHistory.setNotPicked(notPicked);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDays = teleCallHistory
										.getCallAfterOneToTwoDays();
								callAfterOneToTwoDays++;
								teleCallHistory
										.setCallAfterOneToTwoDays(callAfterOneToTwoDays);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeks = teleCallHistory
										.getCallAfterOneToTwoWeeks();
								callAfterOneToTwoWeeks++;
								teleCallHistory
										.setCallAfterOneToTwoWeeks(callAfterOneToTwoWeeks);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDays = teleCallHistory
										.getCallAfterFifteenToThirtyDays();
								callAfterFifteenToThirtyDays++;
								teleCallHistory
										.setCallAfterFifteenToThirtyDays(callAfterFifteenToThirtyDays);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDays = teleCallHistory
										.getCallAfterThirtyToSixtyDays();
								callAfterThirtyToSixtyDays++;
								teleCallHistory
										.setCallAfterThirtyToSixtyDays(callAfterThirtyToSixtyDays);
							} else {
								anySpecific = teleCallHistory.getAnySpecific();
								anySpecific++;
								teleCallHistory.setAnySpecific(anySpecific);
							}
						}
					}
					teleCallHistory.setTeleId(project.getSalesExecutive()
							.getId());
					teleCallHistoryDAO.save(teleCallHistory);
				} else {
					TeleCallHistory teleCallHistory1 = new TeleCallHistory();

					for (Role role : project.getOwner().getRoles()) {
						if (role.getName().equalsIgnoreCase("ENDUSER")) {
							endUsers++;
							teleCallHistory1.setEndUsers(endUsers);
						} else if (role.getName().equalsIgnoreCase(
								"CIVILCONTRACTOR")) {
							civilContractors++;
							teleCallHistory1
									.setCivilContractors(civilContractors);
						} else if (role.getName().equalsIgnoreCase(
								"CIVILENGINEER")) {
							civilEngineers++;
							teleCallHistory1.setCivilEngineers(civilEngineers);
						} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
							plumbers++;
							teleCallHistory1.setPlumbers(plumbers);
						} else {
							others++;
							teleCallHistory1.setOthers(others);
						}
					}

					if (project.getCustomerResponse() != null) {
						LeadProject lp = leadProjectDAO
								.findByIdAndCreatedDateBetween(project.getId(),
										sdate, edate);
						if (lp != null) {

							if (project.getCustomerResponse().equalsIgnoreCase(
									"RequirementGiven")) {
								requirementGivenNewLead++;
								teleCallHistory1
										.setRequirementGivenNewLead(requirementGivenNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDaysNewLead++;
								teleCallHistory1
										.setCallAfterOneToTwoDaysNewLead(callAfterOneToTwoDaysNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeksNewLead++;
								teleCallHistory1
										.setCallAfterOneToTwoWeeksNewLead(callAfterOneToTwoWeeksNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDaysNewLead++;
								teleCallHistory1
										.setCallAfterFifteenToThirtyDaysNewLead(callAfterFifteenToThirtyDaysNewLead);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDaysNewLead++;
								teleCallHistory1
										.setCallAfterThirtyToSixtyDaysNewLead(callAfterThirtyToSixtyDaysNewLead);
							} else {
								anySpecificNewLead++;
								teleCallHistory1
										.setAnySpecificNewLead(anySpecificNewLead);
							}

						} else {

							if (project.getCustomerResponse().equalsIgnoreCase(
									"RequirementGiven")) {
								requirementGiven++;
								teleCallHistory1
										.setRequirementGiven(requirementGiven);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("SwitchedOff")) {
								switchedOff++;
								teleCallHistory1.setSwitchedOff(switchedOff);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("WrongNumber")) {
								wrongNumber++;
								teleCallHistory1.setWrongNumber(wrongNumber);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("NotPicked")) {
								notPicked++;
								teleCallHistory1.setNotPicked(notPicked);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoDays")) {
								callAfterOneToTwoDays++;
								teleCallHistory1
										.setCallAfterOneToTwoDays(callAfterOneToTwoDays);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase("CallAfterOneToTwoWeeks")) {
								callAfterOneToTwoWeeks++;
								teleCallHistory1
										.setCallAfterOneToTwoWeeks(callAfterOneToTwoWeeks);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterFifteenToThirtyDays")) {
								callAfterFifteenToThirtyDays++;
								teleCallHistory1
										.setCallAfterFifteenToThirtyDays(callAfterFifteenToThirtyDays);
							} else if (project.getCustomerResponse()
									.equalsIgnoreCase(
											"CallAfterThirtyToSixtyDays")) {
								callAfterThirtyToSixtyDays++;
								teleCallHistory1
										.setCallAfterThirtyToSixtyDays(callAfterThirtyToSixtyDays);
							} else {
								anySpecific++;
								teleCallHistory1.setAnySpecific(anySpecific);
							}
						}
					}
					teleCallHistory1.setTeleId(project.getSalesExecutive()
							.getId());
					teleCallHistoryDAO.save(teleCallHistory1);
				}
			}
		}
		return project;
	}

	@Override
	public ManageLeadProjectResponse manageLeadProjects(String searchVal,
			String projName, String type, String addrLine1, String addrLine2,
			String city, String zipcode, String projectStage, String area,
			String followupDate, String followupDateEntered,
			String contactedDate, String customerName, String salesExecutiveId,
			String ownersCount, String referencedBy, String called,
			String reasonForReject, String requirement, String status,
			String toFollowupDate, String roleName, Point point, int page,
			int size, Sort sort) {
		Page<LeadProject> pageable = null;
		List<LeadProject> leadProjects = new ArrayList<>();
		ManageLeadProjectResponse manageLeadProjectResponse = new ManageLeadProjectResponse();
		List<LeadProjectResponse> leadProjectResponseList = new ArrayList<>();
		int endusers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;
		if (StringUtils.isBlank(ownersCount)) {
			ownersCount = "NO";
		}

		if (!ownersCount.equalsIgnoreCase("YES")) {
			if (StringUtils.isBlank(searchVal) && StringUtils.isBlank(projName)
					&& StringUtils.isBlank(type)
					&& StringUtils.isBlank(addrLine1)
					&& StringUtils.isBlank(addrLine2)
					&& StringUtils.isBlank(city)
					&& StringUtils.isBlank(zipcode)
					&& StringUtils.isBlank(projectStage)
					&& StringUtils.isBlank(area)
					&& StringUtils.isBlank(followupDate)
					&& StringUtils.isBlank(followupDateEntered)
					&& StringUtils.isBlank(contactedDate)
					&& StringUtils.isBlank(customerName)
					&& StringUtils.isBlank(salesExecutiveId)
					&& StringUtils.isBlank(referencedBy)
					&& StringUtils.isBlank(called)
					&& StringUtils.isBlank(reasonForReject)
					&& StringUtils.isBlank(requirement)
					&& StringUtils.isBlank(status)
					&& StringUtils.isBlank(toFollowupDate)
					&& StringUtils.isBlank(roleName) && point == null) {
				pageable = leadProjectDAO.findAll(new PageRequest(page, size,
						sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(searchVal)
					&& StringUtils.isNotBlank(type)
					&& StringUtils.isNotBlank(projectStage)) {
				Set<User> owners = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								searchVal, searchVal, searchVal, searchVal,
								searchVal);
				if (owners.size() != 0) {
					for (User owner : owners) {
						pageable = leadProjectDAO
								.findByOwner_IdAndTypeAndProjectStage(owner
										.getId(), ProjectType.valueOf(type),
										projectStage, new PageRequest(page,
												size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else {
					pageable = leadProjectDAO
							.findByTypeAndProjectStageLikeOrNameLikeOrAreaLikeOrFollowupDateOrAddress_ZipcodeOrReferencedByLikeOrRequirementNoteContainsAllIgnoreCase(
									ProjectType.valueOf(type), projectStage,
									searchVal, searchVal, searchVal, searchVal,
									searchVal, searchVal, new PageRequest(page,
											size, sort));
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(searchVal)
					&& StringUtils.isNotBlank(type)) {
				Set<User> owners = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								searchVal, searchVal, searchVal, searchVal,
								searchVal);
				if (owners.size() != 0) {
					for (User owner : owners) {
						pageable = leadProjectDAO.findByOwner_IdAndType(
								owner.getId(), ProjectType.valueOf(type),
								new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else {
					pageable = leadProjectDAO
							.findByTypeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
									ProjectType.valueOf(type), searchVal,
									searchVal, searchVal, searchVal,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(type)
					&& StringUtils.isNotBlank(projectStage)) {
				pageable = leadProjectDAO
						.findByTypeAndProjectStageAllIgnoreCase(
								ProjectType.valueOf(type), projectStage,
								new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(searchVal)
					&& StringUtils.isNotBlank(projectStage)) {
				Set<User> owners = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								searchVal, searchVal, searchVal, searchVal,
								searchVal);
				if (owners.size() != 0) {
					for (User owner : owners) {
						pageable = leadProjectDAO
								.findByOwner_IdAndProjectStageAllIgnoreCase(
										owner.getId(), projectStage,
										new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else {
					pageable = leadProjectDAO
							.findByProjectStageLikeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
									projectStage, searchVal, searchVal,
									searchVal, searchVal, new PageRequest(page,
											size, sort));
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(searchVal)) {

				Set<User> owners = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								searchVal, searchVal, searchVal, searchVal,
								searchVal);
				if (owners.size() != 0) {
					for (User owner : owners) {
						pageable = leadProjectDAO.findByOwner_Id(owner.getId(),
								new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else {
					if (searchVal.contains("/")) {
						pageable = leadProjectDAO
								.findByNameLikeOrAreaLikeOrFollowupDateAllIgnoreCase(
										searchVal, searchVal, searchVal,
										new PageRequest(page, size, sort));
					} else {
						pageable = leadProjectDAO
								.findByNameLikeOrZipcodeOrAreaLikeOrFollowupDateAllIgnoreCase(
										searchVal, searchVal, searchVal,
										searchVal, new PageRequest(page, size,
												sort));
					}
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(projName)) {
				pageable = leadProjectDAO.findByNameLikeAllIgnoreCase(projName,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(type)) {
				pageable = leadProjectDAO.findByType(ProjectType.valueOf(type),
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(addrLine1)) {
				pageable = leadProjectDAO
						.findByAddress_AddressLine1LikeAllIgnoreCase(addrLine1,
								new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(addrLine2)) {
				pageable = leadProjectDAO
						.findByAddress_AddressLine2LikeAllIgnoreCase(addrLine2,
								new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(city)) {
				pageable = leadProjectDAO.findByAddress_CityAllIgnoreCase(city,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(zipcode)) {
				pageable = leadProjectDAO.findByAddress_Zipcode(Integer
						.parseInt(zipcode), new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (point != null) {
				pageable = leadProjectDAO.findByAddress_Location(point,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(customerName)) {
				Set<User> customers = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								customerName, customerName, customerName,
								customerName, customerName);
				if (customers.size() != 0) {
					for (User customer : customers) {
						pageable = leadProjectDAO.findByOwner_Id(customer
								.getId(), new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				}
			} else if (StringUtils.isNotBlank(salesExecutiveId)) {
				User salesExecutive = userDAO.findById(salesExecutiveId);
				if (salesExecutive != null) {
					pageable = leadProjectDAO.findBySalesExecutive_Id(
							salesExecutive.getId(), new PageRequest(page, size,
									sort));
				}
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(area)) {
				pageable = leadProjectDAO.findByAreaLikeAllIgnoreCase(area,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(projectStage)) {
				pageable = leadProjectDAO.findByProjectStageLikeAllIgnoreCase(
						projectStage, new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(followupDate)
					&& StringUtils.isNotBlank(toFollowupDate)) {
				Set<String> followupDateSet = new HashSet<>();
				followupDateSet.add(followupDate);
				String[] fdateAry = toFollowupDate.split(",");
				for (String fdate : fdateAry) {
					followupDateSet.add(fdate);
				}
				pageable = leadProjectDAO.findByFollowupDateIn(followupDateSet,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(followupDate)) {
				pageable = leadProjectDAO.findByFollowupDate(followupDate,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(contactedDate)) {
				pageable = leadProjectDAO.findByContactedDate(contactedDate,
						new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(referencedBy)) {
				pageable = leadProjectDAO.findByReferencedByLikeAllIgnoreCase(
						referencedBy, new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(called)) {
				if (called.equalsIgnoreCase("YES")) {
					pageable = leadProjectDAO
							.findByCalledIsTrue(Boolean.valueOf(called),
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (called.equalsIgnoreCase("NO")) {
					pageable = leadProjectDAO
							.findByCalledIsFalse(Boolean.valueOf(called),
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(reasonForReject)) {
				pageable = leadProjectDAO
						.findByReasonForRejectLikeAllIgnoreCase(
								reasonForReject, new PageRequest(page, size,
										sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(requirement)) {
				pageable = leadProjectDAO.findByRequirementNoteContains(
						requirement, new PageRequest(page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(status)) {
				pageable = leadProjectDAO.findByStatus(status, new PageRequest(
						page, size, sort));
				leadProjects.addAll(pageable.getContent());
			} else if (StringUtils.isNotBlank(followupDateEntered)) {
				if (followupDateEntered.equalsIgnoreCase("YES")) {
					pageable = leadProjectDAO
							.findByFollowupDateNotNull(new PageRequest(page,
									size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (followupDateEntered.equalsIgnoreCase("No")) {
					pageable = leadProjectDAO
							.findByFollowupDateNull(new PageRequest(page, size,
									sort));
					leadProjects.addAll(pageable.getContent());
				}
			} else if (StringUtils.isNotBlank(roleName)) {
				Set<User> users = userDAO
						.findByRoles_NameAllIgnoreCase(roleName);
				Set<String> userIds = new HashSet<>();
				for (User user : users) {
					userIds.add(user.getId());
				}
				if (userIds.size() >= 1) {
					pageable = leadProjectDAO.findByOwner_IdIn(userIds,
							new PageRequest(page, size, sort));
				}
				leadProjects.addAll(pageable.getContent());
			}

			for (LeadProject leadProject : leadProjects) {
				LeadProjectResponse leadProjectResponse = new LeadProjectResponse();
				leadProjectResponse.setLeadProjectId(leadProject.getId());
				leadProjectResponse.setName(leadProject.getName());
				leadProjectResponse.setType(leadProject.getType().toString());
				leadProjectResponse.setSalesExecutive(leadProject
						.getSalesExecutive());
				leadProjectResponse.setOwner(leadProject.getOwner());
				leadProjectResponse.setProjectStage(leadProject
						.getProjectStage());
				leadProjectResponse.setAddress(leadProject.getAddress());
				leadProjectResponse.setArea(leadProject.getArea());
				// Note
				if (leadProject.getNote() != null) {
					leadProjectResponse.setNote(leadProject.getNote());
				}
				// Requirement note
				if (leadProject.getRequirementNote() != null) {
					leadProjectResponse.setRequirementNote(leadProject
							.getRequirementNote());
				}
				if (leadProject.getFollowupDate() != null) {
					leadProjectResponse.setFollowupDate(leadProject
							.getFollowupDate());
				}
				// Contacted date
				leadProjectResponse.setContactedDate(leadProject
						.getContactedDate());
				// Created date
				leadProjectResponse
						.setCreatedDate(leadProject.getCreatedDate());
				// ReferencedBy
				if (leadProject.getReferencedBy() != null) {
					leadProjectResponse.setReferencedBy(leadProject
							.getReferencedBy());
				}
				// called
				if (leadProject.isCalled()) {
					leadProjectResponse.setCalled("YES");
				} else {
					leadProjectResponse.setCalled("NO");
				}
				// Reason for reject
				if (leadProject.getReasonForReject() != null) {
					leadProjectResponse.setReasonForReject(leadProject
							.getReasonForReject());
				}
				// status
				if (leadProject.getStatus() != null) {
					leadProjectResponse.setStatus(leadProject.getStatus());
				}
				// Customer Response
				if (leadProject.getCustomerResponse() != null) {
					leadProjectResponse.setCustomerResponse(leadProject
							.getCustomerResponse());
				}
				UserInfo userInfo = userRepository
						.findByMongoUserId(leadProject.getOwner().getId());

				if (userInfo != null) {
					// OrderPurchased Days
					List<Order> orderList = userOrderRepository
							.findByUserInfo(userInfo);

					if (orderList != null && orderList.size() != 0) {
						Date orderedDate = orderList.get(orderList.size() - 1)
								.getOrderedDate();
						Date currentDate = new Date();

						int diffInDays = (int) ((currentDate.getTime() - orderedDate
								.getTime()) / (1000 * 60 * 60 * 24));
						leadProjectResponse.setLastOrderDays(diffInDays);
					}
				}
				leadProjectResponseList.add(leadProjectResponse);
			}

			manageLeadProjectResponse.setLeadProjects(leadProjectResponseList);
			manageLeadProjectResponse.setTotalPages(pageable.getTotalPages());
			manageLeadProjectResponse.setTotalElements(pageable
					.getTotalElements());
			manageLeadProjectResponse.setNumber(pageable.getNumber());
			manageLeadProjectResponse.setSize(pageable.getSize());
		} else {
			if (StringUtils.isBlank(projName) && StringUtils.isBlank(type)
					&& StringUtils.isBlank(addrLine1)
					&& StringUtils.isBlank(addrLine2)
					&& StringUtils.isBlank(city)
					&& StringUtils.isBlank(zipcode)
					&& StringUtils.isBlank(projectStage)
					&& StringUtils.isBlank(area)
					&& StringUtils.isBlank(followupDate)
					&& StringUtils.isBlank(followupDateEntered)
					&& StringUtils.isBlank(contactedDate)
					&& StringUtils.isBlank(customerName)
					&& StringUtils.isBlank(salesExecutiveId)
					&& StringUtils.isBlank(referencedBy)
					&& StringUtils.isBlank(called)
					&& StringUtils.isBlank(reasonForReject)
					&& StringUtils.isBlank(requirement)
					&& StringUtils.isBlank(status)
					&& StringUtils.isBlank(toFollowupDate)
					&& StringUtils.isBlank(roleName) && point == null) {
				leadProjects = leadProjectDAO.findAll();
			} else if (StringUtils.isNotBlank(projName)) {
				leadProjects = leadProjectDAO
						.findByNameLikeAllIgnoreCase(projName);
			} else if (StringUtils.isNotBlank(type)) {
				leadProjects = leadProjectDAO.findByType(ProjectType
						.valueOf(type));
			} else if (StringUtils.isNotBlank(addrLine1)) {
				leadProjects = leadProjectDAO
						.findByAddress_AddressLine1LikeAllIgnoreCase(addrLine1);
			} else if (StringUtils.isNotBlank(addrLine2)) {
				leadProjects = leadProjectDAO
						.findByAddress_AddressLine2LikeAllIgnoreCase(addrLine2);
			} else if (StringUtils.isNotBlank(city)) {
				leadProjects = leadProjectDAO
						.findByAddress_CityAllIgnoreCase(city);
			} else if (StringUtils.isNotBlank(zipcode)) {
				leadProjects = leadProjectDAO.findByAddress_Zipcode(Integer
						.parseInt(zipcode));
			} else if (point != null) {
				leadProjects = leadProjectDAO.findByAddress_Location(point);
			} else if (StringUtils.isNotBlank(customerName)) {
				Set<User> customers = userDAO
						.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
								customerName, customerName, customerName,
								customerName, customerName);
				if (customers.size() != 0) {
					for (User customer : customers) {
						leadProjects.addAll(leadProjectDAO
								.findByOwner_Id(customer.getId()));
					}
				}

			} else if (StringUtils.isNotBlank(salesExecutiveId)) {
				User salesExecutive = userDAO.findById(salesExecutiveId);
				if (salesExecutive != null) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_Id(salesExecutive.getId());
				}
			} else if (StringUtils.isNotBlank(area)) {
				leadProjects = leadProjectDAO.findByAreaLikeAllIgnoreCase(area);
			} else if (StringUtils.isNotBlank(projectStage)) {
				leadProjects = leadProjectDAO
						.findByProjectStageLikeAllIgnoreCase(projectStage);
			} else if (StringUtils.isNotBlank(followupDate)
					&& StringUtils.isNotBlank(toFollowupDate)) {
				Set<String> followupDateSet = new HashSet<>();
				followupDateSet.add(followupDate);
				String[] fdateAry = toFollowupDate.split(",");
				for (String fdate : fdateAry) {
					followupDateSet.add(fdate);
				}
				leadProjects = leadProjectDAO
						.findByFollowupDateIn(followupDateSet);
			} else if (StringUtils.isNotBlank(followupDate)) {
				leadProjects = leadProjectDAO.findByFollowupDate(followupDate);
			} else if (StringUtils.isNotBlank(contactedDate)) {
				leadProjects = leadProjectDAO
						.findByContactedDate(contactedDate);
			} else if (StringUtils.isNotBlank(referencedBy)) {
				leadProjects = leadProjectDAO
						.findByReferencedByLikeAllIgnoreCase(referencedBy);
			} else if (StringUtils.isNotBlank(called)) {
				if (called.equalsIgnoreCase("YES")) {
					leadProjects = leadProjectDAO.findByCalledIsTrue(Boolean
							.valueOf(called));
				} else if (called.equalsIgnoreCase("NO")) {
					leadProjects = leadProjectDAO.findByCalledIsFalse(Boolean
							.valueOf(called));
				}
			} else if (StringUtils.isNotBlank(reasonForReject)) {
				leadProjects = leadProjectDAO
						.findByReasonForRejectLikeAllIgnoreCase(reasonForReject);
			} else if (StringUtils.isNotBlank(requirement)) {
				leadProjects = leadProjectDAO
						.findByRequirementNoteContains(requirement);
			} else if (StringUtils.isNotBlank(status)) {
				leadProjects = leadProjectDAO.findByStatus(status);
			} else if (StringUtils.isNotBlank(followupDateEntered)) {
				if (followupDateEntered.equalsIgnoreCase("YES")) {
					leadProjects = leadProjectDAO.findByFollowupDateNotNull();
				} else if (followupDateEntered.equalsIgnoreCase("No")) {
					leadProjects = leadProjectDAO.findByFollowupDateNull();
				}
			} else if (StringUtils.isNotBlank(roleName)) {
				Set<User> users = userDAO
						.findByRoles_NameAllIgnoreCase(roleName);
				Set<String> userIds = new HashSet<>();
				for (User user : users) {
					userIds.add(user.getId());
				}
				if (userIds.size() >= 1) {
					leadProjects = leadProjectDAO.findByOwner_IdIn(userIds);
				}
			}

			for (LeadProject leadProject : leadProjects) {

				for (Role role : leadProject.getOwner().getRoles()) {
					if (role.getName().equalsIgnoreCase("ENDUSER")) {
						endusers++;
					} else if (role.getName().equalsIgnoreCase(
							"CIVILCONTRACTOR")) {
						civilContractors++;
					} else if (role.getName().equalsIgnoreCase("CIVILENGINEER")) {
						civilEngineers++;
					} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
						plumbers++;
					} else {
						others++;
					}
				}
			}
			manageLeadProjectResponse.setEndusers(endusers);
			manageLeadProjectResponse.setCivilEngineers(civilEngineers);
			manageLeadProjectResponse.setCivilContractors(civilContractors);
			manageLeadProjectResponse.setPlumbers(plumbers);
			manageLeadProjectResponse.setOthers(others);
		}
		return manageLeadProjectResponse;
	}

	@Override
	public ManageLeadProjectResponse manageFieldAssociateLeads(
			String fieldAssociateId, String searchVal, String projName,
			String type, String addrLine1, String addrLine2, String city,
			String zipcode, String projectStage, String area,
			String followupDate, String followupDateEntered,
			String contactedDate, String customerName, String ownersCount,
			String referencedBy, String called, String reasonForReject,
			String requirement, String status, String toFollowupDate,
			String roleName, Point point, int page, int size, Sort sort) {
		Page<LeadProject> pageable = null;
		List<LeadProject> leadProjects = new ArrayList<>();
		ManageLeadProjectResponse manageLeadProjectResponse = new ManageLeadProjectResponse();
		List<LeadProjectResponse> leadProjectResponseList = new ArrayList<>();
		int endusers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;

		if (StringUtils.isNotBlank(fieldAssociateId)) {
			if (StringUtils.isBlank(ownersCount)) {
				ownersCount = "NO";
			}

			if (!ownersCount.equalsIgnoreCase("YES")) {
				if (StringUtils.isBlank(searchVal)
						&& StringUtils.isBlank(projName)
						&& StringUtils.isBlank(type)
						&& StringUtils.isBlank(addrLine1)
						&& StringUtils.isBlank(addrLine2)
						&& StringUtils.isBlank(city)
						&& StringUtils.isBlank(zipcode)
						&& StringUtils.isBlank(projectStage)
						&& StringUtils.isBlank(area)
						&& StringUtils.isBlank(followupDate)
						&& StringUtils.isBlank(followupDateEntered)
						&& StringUtils.isBlank(contactedDate)
						&& StringUtils.isBlank(customerName)
						&& StringUtils.isBlank(referencedBy)
						&& StringUtils.isBlank(called)
						&& StringUtils.isBlank(reasonForReject)
						&& StringUtils.isBlank(requirement)
						&& StringUtils.isBlank(status)
						&& StringUtils.isBlank(toFollowupDate)
						&& StringUtils.isBlank(roleName) && point == null) {
					pageable = leadProjectDAO
							.findBySalesExecutive_Id(fieldAssociateId,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(searchVal)
						&& StringUtils.isNotBlank(type)
						&& StringUtils.isNotBlank(projectStage)) {
					Set<User> owners = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									searchVal, searchVal, searchVal, searchVal,
									searchVal);
					if (owners.size() != 0) {
						for (User owner : owners) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndOwner_IdAndTypeAndProjectStage(
											fieldAssociateId, owner.getId(),
											ProjectType.valueOf(type),
											projectStage, new PageRequest(page,
													size, sort));
							leadProjects.addAll(pageable.getContent());
						}
					} else {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndTypeAndProjectStageLikeOrNameLikeOrAreaLikeOrFollowupDateOrAddress_ZipcodeOrReferencedByLikeOrRequirementNoteContainsAllIgnoreCase(
										fieldAssociateId, ProjectType
												.valueOf(type), projectStage,
										searchVal, searchVal, searchVal,
										searchVal, searchVal, searchVal,
										new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(searchVal)
						&& StringUtils.isNotBlank(type)) {
					Set<User> owners = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									searchVal, searchVal, searchVal, searchVal,
									searchVal);
					if (owners.size() != 0) {
						for (User owner : owners) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndOwner_IdAndType(
											fieldAssociateId, owner.getId(),
											ProjectType.valueOf(type),
											new PageRequest(page, size, sort));
							leadProjects.addAll(pageable.getContent());
						}
					} else {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndTypeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
										fieldAssociateId,
										ProjectType.valueOf(type), searchVal,
										searchVal, searchVal, searchVal,
										new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(type)
						&& StringUtils.isNotBlank(projectStage)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndTypeAndProjectStageAllIgnoreCase(
									fieldAssociateId,
									ProjectType.valueOf(type), projectStage,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(searchVal)
						&& StringUtils.isNotBlank(projectStage)) {
					Set<User> owners = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									searchVal, searchVal, searchVal, searchVal,
									searchVal);
					if (owners.size() != 0) {
						for (User owner : owners) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndOwner_IdAndProjectStageAllIgnoreCase(
											fieldAssociateId, owner.getId(),
											projectStage, new PageRequest(page,
													size, sort));
							leadProjects.addAll(pageable.getContent());
						}
					} else {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndProjectStageLikeAndNameLikeOrAreaLikeOrFollowupDateOrZipcodeAllIgnoreCase(
										fieldAssociateId, projectStage,
										searchVal, searchVal, searchVal,
										searchVal, new PageRequest(page, size,
												sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(searchVal)) {

					Set<User> owners = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									searchVal, searchVal, searchVal, searchVal,
									searchVal);
					if (owners.size() != 0) {
						for (User owner : owners) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndOwner_Id(
											fieldAssociateId, owner.getId(),
											new PageRequest(page, size, sort));
							leadProjects.addAll(pageable.getContent());
						}
					} else {
						if (searchVal.contains("/")) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndNameLikeOrAreaLikeOrFollowupDateAllIgnoreCase(
											fieldAssociateId, searchVal,
											searchVal, searchVal,
											new PageRequest(page, size, sort));
						} else {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndNameLikeOrZipcodeOrAreaLikeOrFollowupDateAllIgnoreCase(
											fieldAssociateId, searchVal,
											searchVal, searchVal, searchVal,
											new PageRequest(page, size, sort));
						}
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(projName)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndNameLikeAllIgnoreCase(
									fieldAssociateId, projName,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(type)) {
					pageable = leadProjectDAO.findBySalesExecutive_IdAndType(
							fieldAssociateId, ProjectType.valueOf(type),
							new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(addrLine1)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_AddressLine1LikeAllIgnoreCase(
									fieldAssociateId, addrLine1,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(addrLine2)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_AddressLine2LikeAllIgnoreCase(
									fieldAssociateId, addrLine2,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(city)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_CityAllIgnoreCase(
									fieldAssociateId, city, new PageRequest(
											page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(zipcode)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_Zipcode(
									fieldAssociateId,
									Integer.parseInt(zipcode), new PageRequest(
											page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (point != null) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_Location(
									fieldAssociateId, point, new PageRequest(
											page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(customerName)) {
					Set<User> customers = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									customerName, customerName, customerName,
									customerName, customerName);
					if (customers.size() != 0) {
						for (User customer : customers) {
							pageable = leadProjectDAO
									.findBySalesExecutive_IdAndOwner_Id(
											fieldAssociateId, customer.getId(),
											new PageRequest(page, size, sort));
							leadProjects.addAll(pageable.getContent());
						}
					}
				} else if (StringUtils.isNotBlank(area)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndAreaLikeAllIgnoreCase(
									fieldAssociateId, area, new PageRequest(
											page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(projectStage)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndProjectStageLikeAllIgnoreCase(
									fieldAssociateId, projectStage,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(followupDate)
						&& StringUtils.isNotBlank(toFollowupDate)) {
					Set<String> followupDateSet = new HashSet<>();
					followupDateSet.add(followupDate);
					String[] fdateAry = toFollowupDate.split(",");
					for (String fdate : fdateAry) {
						followupDateSet.add(fdate);
					}
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDateIn(
									fieldAssociateId, followupDateSet,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(followupDate)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDate(
									fieldAssociateId, followupDate,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(contactedDate)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndContactedDate(
									fieldAssociateId, contactedDate,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(referencedBy)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndReferencedByLikeAllIgnoreCase(
									fieldAssociateId, referencedBy,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(called)) {
					if (called.equalsIgnoreCase("YES")) {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndCalledIsTrue(
										fieldAssociateId,
										Boolean.valueOf(called),
										new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					} else if (called.equalsIgnoreCase("NO")) {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndCalledIsFalse(
										fieldAssociateId,
										Boolean.valueOf(called),
										new PageRequest(page, size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(reasonForReject)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndReasonForRejectLikeAllIgnoreCase(
									fieldAssociateId, reasonForReject,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(requirement)) {
					pageable = leadProjectDAO
							.findBySalesExecutive_IdAndRequirementNoteContains(
									fieldAssociateId, requirement,
									new PageRequest(page, size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(status)) {
					pageable = leadProjectDAO.findBySalesExecutive_IdAndStatus(
							fieldAssociateId, status, new PageRequest(page,
									size, sort));
					leadProjects.addAll(pageable.getContent());
				} else if (StringUtils.isNotBlank(followupDateEntered)) {
					if (followupDateEntered.equalsIgnoreCase("YES")) {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndFollowupDateNotNull(
										fieldAssociateId, new PageRequest(page,
												size, sort));
						leadProjects.addAll(pageable.getContent());
					} else if (followupDateEntered.equalsIgnoreCase("No")) {
						pageable = leadProjectDAO
								.findBySalesExecutive_IdAndFollowupDateNull(
										fieldAssociateId, new PageRequest(page,
												size, sort));
						leadProjects.addAll(pageable.getContent());
					}
				} else if (StringUtils.isNotBlank(roleName)) {
					Set<User> users = userDAO
							.findByRoles_NameAllIgnoreCase(roleName);
					Set<String> userIds = new HashSet<>();
					for (User user : users) {
						userIds.add(user.getId());
					}
					if (userIds.size() >= 1) {
						pageable = leadProjectDAO.findByOwner_IdIn(userIds,
								new PageRequest(page, size, sort));
					}
				}

				for (LeadProject leadProject : leadProjects) {
					LeadProjectResponse leadProjectResponse = new LeadProjectResponse();
					leadProjectResponse.setLeadProjectId(leadProject.getId());
					leadProjectResponse.setName(leadProject.getName());
					leadProjectResponse.setType(leadProject.getType()
							.toString());
					leadProjectResponse.setSalesExecutive(leadProject
							.getSalesExecutive());
					leadProjectResponse.setOwner(leadProject.getOwner());
					leadProjectResponse.setProjectStage(leadProject
							.getProjectStage());
					leadProjectResponse.setAddress(leadProject.getAddress());
					leadProjectResponse.setArea(leadProject.getArea());
					// Note
					if (leadProject.getNote() != null) {
						leadProjectResponse.setNote(leadProject.getNote());
					}
					// Requirement note
					if (leadProject.getRequirementNote() != null) {
						leadProjectResponse.setRequirementNote(leadProject
								.getRequirementNote());
					}
					if (leadProject.getFollowupDate() != null) {
						leadProjectResponse.setFollowupDate(leadProject
								.getFollowupDate());
					}
					// Contacted date
					leadProjectResponse.setContactedDate(leadProject
							.getContactedDate());
					// Created date
					leadProjectResponse.setCreatedDate(leadProject
							.getCreatedDate());
					// ReferencedBy
					if (leadProject.getReferencedBy() != null) {
						leadProjectResponse.setReferencedBy(leadProject
								.getReferencedBy());
					}
					// called
					if (leadProject.isCalled()) {
						leadProjectResponse.setCalled("YES");
					} else {
						leadProjectResponse.setCalled("NO");
					}
					// Reason for reject
					if (leadProject.getReasonForReject() != null) {
						leadProjectResponse.setReasonForReject(leadProject
								.getReasonForReject());
					}
					// Status
					if (leadProject.getStatus() != null) {
						leadProjectResponse.setStatus(leadProject.getStatus());
					}
					// Cusomer Response
					if (leadProject.getCustomerResponse() != null) {
						leadProjectResponse.setCustomerResponse(leadProject
								.getCustomerResponse());
					}
					leadProjectResponseList.add(leadProjectResponse);
				}

				manageLeadProjectResponse
						.setLeadProjects(leadProjectResponseList);
				manageLeadProjectResponse.setTotalPages(pageable
						.getTotalPages());
				manageLeadProjectResponse.setTotalElements(pageable
						.getTotalElements());
				manageLeadProjectResponse.setNumber(pageable.getNumber());
				manageLeadProjectResponse.setSize(pageable.getSize());
			} else {
				if (StringUtils.isBlank(projName) && StringUtils.isBlank(type)
						&& StringUtils.isBlank(addrLine1)
						&& StringUtils.isBlank(addrLine2)
						&& StringUtils.isBlank(city)
						&& StringUtils.isBlank(zipcode)
						&& StringUtils.isBlank(projectStage)
						&& StringUtils.isBlank(area)
						&& StringUtils.isBlank(followupDate)
						&& StringUtils.isBlank(followupDateEntered)
						&& StringUtils.isBlank(contactedDate)
						&& StringUtils.isBlank(customerName)
						&& StringUtils.isBlank(referencedBy)
						&& StringUtils.isBlank(called)
						&& StringUtils.isBlank(reasonForReject)
						&& StringUtils.isBlank(requirement)
						&& StringUtils.isBlank(status)
						&& StringUtils.isBlank(toFollowupDate)
						&& StringUtils.isBlank(roleName) && point == null) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_Id(fieldAssociateId);
				} else if (StringUtils.isNotBlank(projName)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndNameLikeAllIgnoreCase(
									fieldAssociateId, projName);
				} else if (StringUtils.isNotBlank(type)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndType(fieldAssociateId,
									ProjectType.valueOf(type));
				} else if (StringUtils.isNotBlank(addrLine1)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_AddressLine1LikeAllIgnoreCase(
									fieldAssociateId, addrLine1);
				} else if (StringUtils.isNotBlank(addrLine2)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_AddressLine2LikeAllIgnoreCase(
									fieldAssociateId, addrLine2);
				} else if (StringUtils.isNotBlank(city)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_CityAllIgnoreCase(
									fieldAssociateId, city);
				} else if (StringUtils.isNotBlank(zipcode)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_Zipcode(
									fieldAssociateId, Integer.parseInt(zipcode));
				} else if (point != null) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAddress_Location(
									fieldAssociateId, point);
				} else if (StringUtils.isNotBlank(customerName)) {
					Set<User> customers = userDAO
							.findByFirstNameLikeOrLastNameLikeOrFullNameLikeOrUsernameLikeOrUserPhoneNumberLikeAllIgnoreCase(
									customerName, customerName, customerName,
									customerName, customerName);
					if (customers.size() != 0) {
						for (User customer : customers) {
							leadProjects
									.addAll(leadProjectDAO
											.findBySalesExecutive_IdAndOwner_Id(
													fieldAssociateId,
													customer.getId()));
						}
					}

				} else if (StringUtils.isNotBlank(area)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndAreaLikeAllIgnoreCase(
									fieldAssociateId, area);
				} else if (StringUtils.isNotBlank(projectStage)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndProjectStageLikeAllIgnoreCase(
									fieldAssociateId, projectStage);
				} else if (StringUtils.isNotBlank(followupDate)
						&& StringUtils.isNotBlank(toFollowupDate)) {
					Set<String> followupDateSet = new HashSet<>();
					followupDateSet.add(followupDate);
					String[] fdateAry = toFollowupDate.split(",");
					for (String fdate : fdateAry) {
						followupDateSet.add(fdate);
					}
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDateIn(
									fieldAssociateId, followupDateSet);
				} else if (StringUtils.isNotBlank(followupDate)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndFollowupDate(
									fieldAssociateId, followupDate);
				} else if (StringUtils.isNotBlank(contactedDate)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndContactedDate(
									fieldAssociateId, contactedDate);
				} else if (StringUtils.isNotBlank(referencedBy)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndReferencedByLikeAllIgnoreCase(
									fieldAssociateId, referencedBy);
				} else if (StringUtils.isNotBlank(called)) {
					if (called.equalsIgnoreCase("YES")) {
						leadProjects = leadProjectDAO
								.findBySalesExecutive_IdAndCalledIsTrue(
										fieldAssociateId,
										Boolean.valueOf(called));
					} else if (called.equalsIgnoreCase("NO")) {
						leadProjects = leadProjectDAO
								.findBySalesExecutive_IdAndCalledIsFalse(
										fieldAssociateId,
										Boolean.valueOf(called));
					}
				} else if (StringUtils.isNotBlank(reasonForReject)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndReasonForRejectLikeAllIgnoreCase(
									fieldAssociateId, reasonForReject);
				} else if (StringUtils.isNotBlank(requirement)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndRequirementNoteContains(
									fieldAssociateId, requirement);
				} else if (StringUtils.isNotBlank(status)) {
					leadProjects = leadProjectDAO
							.findBySalesExecutive_IdAndStatus(fieldAssociateId,
									status);
				} else if (StringUtils.isNotBlank(followupDateEntered)) {
					if (followupDateEntered.equalsIgnoreCase("YES")) {
						leadProjects = leadProjectDAO
								.findBySalesExecutive_IdAndFollowupDateNotNull(fieldAssociateId);
					} else if (followupDateEntered.equalsIgnoreCase("No")) {
						leadProjects = leadProjectDAO
								.findBySalesExecutive_IdAndFollowupDateNull(fieldAssociateId);
					}
				} else if (StringUtils.isNotBlank(roleName)) {
					Set<User> users = userDAO
							.findByRoles_NameAllIgnoreCase(roleName);
					Set<String> userIds = new HashSet<>();
					for (User user : users) {
						userIds.add(user.getId());
					}
					if (userIds.size() >= 1) {
						leadProjects = leadProjectDAO.findByOwner_IdIn(userIds);
					}
				}

				for (LeadProject leadProject : leadProjects) {

					for (Role role : leadProject.getOwner().getRoles()) {
						if (role.getName().equalsIgnoreCase("ENDUSER")) {
							endusers++;
						} else if (role.getName().equalsIgnoreCase(
								"CIVILCONTRACTOR")) {
							civilContractors++;
						} else if (role.getName().equalsIgnoreCase(
								"CIVILENGINEER")) {
							civilEngineers++;
						} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
							plumbers++;
						} else {
							others++;
						}
					}
				}
				manageLeadProjectResponse.setEndusers(endusers);
				manageLeadProjectResponse.setCivilEngineers(civilEngineers);
				manageLeadProjectResponse.setCivilContractors(civilContractors);
				manageLeadProjectResponse.setPlumbers(plumbers);
				manageLeadProjectResponse.setOthers(others);
			}
		}
		return manageLeadProjectResponse;
	}

	@Override
	public LeadProject getFieldAssociateLeadViewDetails(
			String fieldAssociateId, String leadProjectId) {
		LeadProject project = null;
		if (StringUtils.isNotBlank(leadProjectId)) {
			project = leadProjectDAO.findBySalesExecutive_IdAndId(
					fieldAssociateId, leadProjectId);
		}
		return project;
	}

	@Override
	public List<LeadProject> getTodayUpdatedLeads() {
		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();
		edate.setHours(edate.getHours() + 23);
		edate.setMinutes(edate.getMinutes() + 59);
		List<LeadProject> projects = leadProjectDAO
				.findByLastModifiedDateBetween(sdate, edate);
		return projects;
	}

	public ManageLeadProjectResponse getTodayUpdatedLeadsWithPagination(
			String ownersCount, int page, int size, Sort sort) {

		Page<LeadProject> pageable = null;
		List<LeadProject> leadProjects = new ArrayList<>();
		ManageLeadProjectResponse manageLeadProjectResponse = new ManageLeadProjectResponse();
		List<LeadProjectResponse> leadProjectResponseList = new ArrayList<>();
		int endusers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;
		if (StringUtils.isBlank(ownersCount)) {
			ownersCount = "NO";
		}

		if (!ownersCount.equalsIgnoreCase("YES")) {

			Date sdate = new Date();
			sdate.setHours(1);
			sdate.setMinutes(01);
			Date edate = new Date();
			edate.setHours(edate.getHours() + 23);
			edate.setMinutes(edate.getMinutes() + 59);

			pageable = leadProjectDAO.findByLastModifiedDateBetween(sdate,
					edate, new PageRequest(page, size, sort));

			leadProjects.addAll(pageable.getContent());
			for (LeadProject leadProject : leadProjects) {
				LeadProjectResponse leadProjectResponse = new LeadProjectResponse();
				leadProjectResponse.setLeadProjectId(leadProject.getId());
				leadProjectResponse.setName(leadProject.getName());
				leadProjectResponse.setType(leadProject.getType().toString());
				leadProjectResponse.setSalesExecutive(leadProject
						.getSalesExecutive());
				leadProjectResponse.setOwner(leadProject.getOwner());
				leadProjectResponse.setProjectStage(leadProject
						.getProjectStage());
				leadProjectResponse.setAddress(leadProject.getAddress());
				leadProjectResponse.setArea(leadProject.getArea());
				// Note
				if (leadProject.getNote() != null) {
					leadProjectResponse.setNote(leadProject.getNote());
				}
				// Requirement note
				if (leadProject.getRequirementNote() != null) {
					leadProjectResponse.setRequirementNote(leadProject
							.getRequirementNote());
				}
				if (leadProject.getFollowupDate() != null) {
					leadProjectResponse.setFollowupDate(leadProject
							.getFollowupDate());
				}
				// Contacted date
				leadProjectResponse.setContactedDate(leadProject
						.getContactedDate());
				// Created date
				leadProjectResponse
						.setCreatedDate(leadProject.getCreatedDate());
				// ReferencedBy
				if (leadProject.getReferencedBy() != null) {
					leadProjectResponse.setReferencedBy(leadProject
							.getReferencedBy());
				}
				// called
				if (leadProject.isCalled()) {
					leadProjectResponse.setCalled("YES");
				} else {
					leadProjectResponse.setCalled("NO");
				}
				// Reason for reject
				if (leadProject.getReasonForReject() != null) {
					leadProjectResponse.setReasonForReject(leadProject
							.getReasonForReject());
				}
				// status
				if (leadProject.getStatus() != null) {
					leadProjectResponse.setStatus(leadProject.getStatus());
				}
				// Customer Response
				if (leadProject.getCustomerResponse() != null) {
					leadProjectResponse.setCustomerResponse(leadProject
							.getCustomerResponse());
				}
				leadProjectResponseList.add(leadProjectResponse);
			}

			manageLeadProjectResponse.setLeadProjects(leadProjectResponseList);
			manageLeadProjectResponse.setTotalPages(pageable.getTotalPages());
			manageLeadProjectResponse.setTotalElements(pageable
					.getTotalElements());
			manageLeadProjectResponse.setNumber(pageable.getNumber());
			manageLeadProjectResponse.setSize(pageable.getSize());
		} else {

			Date sdate = new Date();
			sdate.setHours(1);
			sdate.setMinutes(01);
			Date edate = new Date();
			edate.setHours(edate.getHours() + 23);
			edate.setMinutes(edate.getMinutes() + 59);

			leadProjects = leadProjectDAO.findByLastModifiedDateBetween(sdate,
					edate);
			for (LeadProject leadProject : leadProjects) {

				for (Role role : leadProject.getOwner().getRoles()) {
					if (role.getName().equalsIgnoreCase("ENDUSER")) {
						endusers++;
					} else if (role.getName().equalsIgnoreCase(
							"CIVILCONTRACTOR")) {
						civilContractors++;
					} else if (role.getName().equalsIgnoreCase("CIVILENGINEER")) {
						civilEngineers++;
					} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
						plumbers++;
					} else {
						others++;
					}
				}
			}
			manageLeadProjectResponse.setEndusers(endusers);
			manageLeadProjectResponse.setCivilEngineers(civilEngineers);
			manageLeadProjectResponse.setCivilContractors(civilContractors);
			manageLeadProjectResponse.setPlumbers(plumbers);
			manageLeadProjectResponse.setOthers(others);
		}
		return manageLeadProjectResponse;

	}

	@Override
	public List<LeadProject> getTodayTeleAssociateUpdatedLeads(
			String fieldAssociateId) {
		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();
		edate.setHours(edate.getHours() + 23);
		edate.setMinutes(edate.getMinutes() + 59);
		List<LeadProject> projects = leadProjectDAO
				.findByLastModifiedDateBetweenAndSalesExecutive_Id(sdate,
						edate, fieldAssociateId);
		return projects;
	}

	@Override
	public ManageLeadProjectResponse getTodayTeleAssociateUpdatedLeadsWithPage(
			String fieldAssociateId, String ownersCount, int page, int size,
			Sort sort) {
		ManageLeadProjectResponse manageLeadProjectResponse = new ManageLeadProjectResponse();
		List<LeadProjectResponse> leadProjectResponseList = new ArrayList<>();
		List<LeadProject> leadProjectsWithPagination = new ArrayList<>();
		List<LeadProject> leadProjects = null;
		int endusers = 0;
		int civilEngineers = 0;
		int civilContractors = 0;
		int plumbers = 0;
		int others = 0;
		Date sdate = new Date();
		sdate.setHours(1);
		sdate.setMinutes(01);
		Date edate = new Date();
		edate.setHours(edate.getHours() + 23);
		edate.setMinutes(edate.getMinutes() + 59);

		if (StringUtils.isBlank(ownersCount)) {
			ownersCount = "NO";
		}
		if (!ownersCount.equalsIgnoreCase("YES")) {
			Page<LeadProject> pageable = leadProjectDAO
					.findByLastModifiedDateBetweenAndSalesExecutive_Id(sdate,
							edate, fieldAssociateId, new PageRequest(page,
									size, sort));
			leadProjectsWithPagination.addAll(pageable.getContent());
			for (LeadProject leadProject : leadProjectsWithPagination) {
				LeadProjectResponse leadProjectResponse = new LeadProjectResponse();
				leadProjectResponse.setLeadProjectId(leadProject.getId());
				leadProjectResponse.setName(leadProject.getName());
				leadProjectResponse.setType(leadProject.getType().toString());
				leadProjectResponse.setSalesExecutive(leadProject
						.getSalesExecutive());
				leadProjectResponse.setOwner(leadProject.getOwner());
				leadProjectResponse.setProjectStage(leadProject
						.getProjectStage());
				leadProjectResponse.setAddress(leadProject.getAddress());
				leadProjectResponse.setArea(leadProject.getArea());
				// Note
				if (leadProject.getNote() != null) {
					leadProjectResponse.setNote(leadProject.getNote());
				}
				// Requirement note
				if (leadProject.getRequirementNote() != null) {
					leadProjectResponse.setRequirementNote(leadProject
							.getRequirementNote());
				}
				if (leadProject.getFollowupDate() != null) {
					leadProjectResponse.setFollowupDate(leadProject
							.getFollowupDate());
				}
				// Contacted date
				leadProjectResponse.setContactedDate(leadProject
						.getContactedDate());
				// Created date
				leadProjectResponse
						.setCreatedDate(leadProject.getCreatedDate());
				// ReferencedBy
				if (leadProject.getReferencedBy() != null) {
					leadProjectResponse.setReferencedBy(leadProject
							.getReferencedBy());
				}
				// called
				if (leadProject.isCalled()) {
					leadProjectResponse.setCalled("YES");
				} else {
					leadProjectResponse.setCalled("NO");
				}
				// Reason for reject
				if (leadProject.getReasonForReject() != null) {
					leadProjectResponse.setReasonForReject(leadProject
							.getReasonForReject());
				}
				// status
				if (leadProject.getStatus() != null) {
					leadProjectResponse.setStatus(leadProject.getStatus());
				}
				// Customer Response
				if (leadProject.getCustomerResponse() != null) {
					leadProjectResponse.setCustomerResponse(leadProject
							.getCustomerResponse());
				}
				UserInfo userInfo = userRepository
						.findByMongoUserId(leadProject.getOwner().getId());

				if (userInfo != null) {
					// OrderPurchased Days
					List<Order> orderList = userOrderRepository
							.findByUserInfo(userInfo);

					if (orderList != null && orderList.size() != 0) {
						Date orderedDate = orderList.get(orderList.size() - 1)
								.getOrderedDate();
						Date currentDate = new Date();

						int diffInDays = (int) ((currentDate.getTime() - orderedDate
								.getTime()) / (1000 * 60 * 60 * 24));
						leadProjectResponse.setLastOrderDays(diffInDays);
					}
				}
				leadProjectResponseList.add(leadProjectResponse);
			}

			manageLeadProjectResponse.setLeadProjects(leadProjectResponseList);
			manageLeadProjectResponse.setTotalPages(pageable.getTotalPages());
			manageLeadProjectResponse.setTotalElements(pageable
					.getTotalElements());
			manageLeadProjectResponse.setNumber(pageable.getNumber());
			manageLeadProjectResponse.setSize(pageable.getSize());
		} else {

			leadProjects = leadProjectDAO
					.findByLastModifiedDateBetweenAndSalesExecutive_Id(sdate,
							edate, fieldAssociateId);
			for (LeadProject leadProject : leadProjects) {

				for (Role role : leadProject.getOwner().getRoles()) {
					if (role.getName().equalsIgnoreCase("ENDUSER")) {
						endusers++;
					} else if (role.getName().equalsIgnoreCase(
							"CIVILCONTRACTOR")) {
						civilContractors++;
					} else if (role.getName().equalsIgnoreCase("CIVILENGINEER")) {
						civilEngineers++;
					} else if (role.getName().equalsIgnoreCase("PLUMBER")) {
						plumbers++;
					} else {
						others++;
					}
				}
			}
			manageLeadProjectResponse.setEndusers(endusers);
			manageLeadProjectResponse.setCivilEngineers(civilEngineers);
			manageLeadProjectResponse.setCivilContractors(civilContractors);
			manageLeadProjectResponse.setPlumbers(plumbers);
			manageLeadProjectResponse.setOthers(others);
		}
		return manageLeadProjectResponse;
	}
}
