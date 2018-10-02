/**
 * 
 */
package com.sarvah.mbg.userprofile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.domain.mongo.common.Description;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.userprofile.dao.mongo.UserBidDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.response.BidResponses;
import com.sarvah.mbg.userprofile.service.UserBidService;

/**
 * @author shivu
 *
 */
@Service
public class UserBidServiceImpl implements UserBidService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserBidDAO userBidDAO;

	/**
	 * Method to get bid of the particular user for the particular project
	 * 
	 * @param uid
	 * @param view
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BidResponses> getBid(String uid, String proName, String view)
			throws Exception {
		List<Bid> bids = null;
		List<BidResponses> bidList = new ArrayList<BidResponses>();
		BidResponses bidResponse = new BidResponses();

		if (StringUtils.isNotBlank(uid)) {

			User user = userDAO.findById(uid);
			if (user != null) {
				logger.info("finding user bid for project");
				bids = userBidDAO.findByUser(user);
				for (Bid bid : bids) {
					User bidUser = bid.getUser();
					for (Role role : bidUser.getRoles()) {
						if (role.getName().equalsIgnoreCase(view)
								&& bid.getProject().getName()
										.equalsIgnoreCase(proName)) {
							bidResponse.setDescription(bid.getDesc().getVal());
							bidResponse.setFullName(bidUser.getFullName());

							bidList.add(bidResponse);
						} else {
							logger.info("No bids for the project");
						}
					}

				}
			} else {
				logger.info("user is null");
				throw new Exception("user is null");
			}
		}
		return bidList;
	}

	/**
	 * Method to get all bids from the user
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<BidResponses> getAllBids(String userId) throws Exception {
		List<Bid> bids = null;
		List<BidResponses> bidResponseList = new ArrayList<>();
		if (StringUtils.isNotBlank(userId)) {
			User user = userDAO.findById(userId);
			if (user != null) {
				logger.info("finding user bid for project");
				bids = userBidDAO.findByUser(user);
				for (Bid bid : bids) {
					BidResponses bidResponse = new BidResponses();
					// Address
					bidResponse.setAddress(bid.getProject().getAddress());
					// Bid description
					bidResponse.setBidDescription(bid.getDesc().getVal());
					// Project description
					bidResponse.setDescription(bid.getProject().getDesc()
							.getVal());
					// Project name
					bidResponse.setProjectName(bid.getProject().getName());

					// Project user fullname
					if (bid.getProject().getUser().getFirstName() != null
							&& bid.getProject().getUser().getLastName() != null) {
						bidResponse.setFullName(bid.getProject().getUser()
								.getFirstName()
								+ " "
								+ bid.getProject().getUser().getLastName());
					} else {
						bidResponse.setFullName(bid.getProject().getUser()
								.getFirstName());
					}

					// Bid done users fullname
					if (bid.getUser().getFirstName() != null
							&& bid.getUser().getLastName() != null) {
						bidResponse.setBiddingUserName(bid.getUser()
								.getFirstName()
								+ " "
								+ bid.getUser().getLastName());
					} else {
						bidResponse.setBiddingUserName(bid.getUser()
								.getFirstName());
					}
					// project user profile image
					bidResponse.setUserImage(bid.getProject().getUser()
							.getProfilePicture());
					// bid done date
					bidResponse.setBiddedDate(bid.getCreatedDate());

					bidResponse.setConstructionType(bid.getProject()
							.isConstructionNew());

					bidResponse.setProjectType(bid.getProject().getType());
					bidResponse
							.setPostedDate(bid.getProject().getCreatedDate());
					bidResponse.setBudget(bid.getProject().getBudget());
					bidResponseList.add(bidResponse);
				}
			} else {
				logger.info("user is null");
				throw new Exception("user is null");
			}
		}
		return bidResponseList;
	}

	/**
	 * Method to Count User bids
	 * 
	 * This method returns the count of bids available for the particular user
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be the userId, by which the bids for the particular user
	 *            will be obtained
	 */
	@Override
	public long countUserBids(String uid) throws Exception {
		Long count = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			count = null;
			if (user != null) {
				logger.info("counting user bids for project");
				count = userBidDAO.countByUser(user);
			} else {
				logger.info("user is null");
				throw new Exception("user is null");
			}
		}
		return count;
	}

	/**
	 * Method to Update user related bids, user id is necessary field.
	 * 
	 * @param userId
	 * @param bidId
	 * @param bidUpdateRequest
	 * @return
	 * @throws Exception
	 */
	@Override
	public Bid updateBid(String uid, String bidid, String desc,
			String quoteAmount) throws Exception {
		Bid bid = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				if (StringUtils.isNotBlank(bidid)) {
					bid = userBidDAO.findOne(bidid);
					if (bid != null) {
						logger.info("updating user bid for project");
						Description desc1 = new Description();
						desc1.setLang("eng");
						desc1.setVal(desc);
						bid.setDesc(desc1);
						bid.setQuoteAmount(Integer.parseInt(quoteAmount));
						userBidDAO.save(bid);
					}
				}
			} else {
				logger.info("user is null");
				throw new Exception("user is null");
			}
		}
		return bid;
	}

	/**
	 * Method Delete a user bid
	 * 
	 * This method deletes a bid for the particular user
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be userId and the bidId, by which the particular bid
	 *            gets deleted
	 * 
	 */
	@Override
	public String deleteUserBids(String uid, String bidid) throws Exception {
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null && StringUtils.isNotBlank(bidid)) {
				Bid bid = userBidDAO.findOne(bidid);
				if (bid != null) {
					logger.info("deleting user bid for project");
					userBidDAO.delete(bidid);
				} else {
					logger.info("bid is null");
					throw new Exception("bid is null");
				}
			} else {
				logger.info("user is null or bidId field is blank");
				throw new Exception("user is null or bidId field is blank");
			}
		}
		return bidid;
	}

}
