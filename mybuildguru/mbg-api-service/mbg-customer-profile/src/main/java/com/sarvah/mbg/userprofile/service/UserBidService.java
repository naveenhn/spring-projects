/**
 * 
 */
package com.sarvah.mbg.userprofile.service;

import java.util.List;

import com.sarvah.mbg.domain.mongo.aceproject.Bid;
import com.sarvah.mbg.userprofile.response.BidResponses;

/**
 * @author shivu
 *
 */
public interface UserBidService {

	/**
	 * Method to get bid of the particular user for the particular project
	 * 
	 * @param uid
	 * @param view
	 * @param proName
	 * @return
	 * @throws Exception
	 */
	List<BidResponses> getBid(String uid, String proName, String view)
			throws Exception;

	/**
	 * Method to get bid bone by the particular user
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<BidResponses> getAllBids(String userId) throws Exception;

	/**
	 * Method to Count User bids
	 * 
	 * This method returns the count of bids available for the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @PathParam would be the userId, by which the bids for the particular user
	 *            will be obtained
	 */
	long countUserBids(String uid) throws Exception;

	/**
	 * Method to Update user related bids, user id is necessary field.
	 * 
	 * @param userId
	 * @param bidId
	 * @param bidUpdateRequest
	 * @return
	 * @throws MBGAppException
	 */
	Bid updateBid(String uid, String bidid, String desc, String quoteAmount)
			throws Exception;

	/**
	 * Method Delete a user bid
	 * 
	 * This method deletes a bid for the particular user
	 * 
	 * @throws MBGAppException
	 * 
	 * @PathParam would be userId and the bidId, by which the particular bid
	 *            gets deleted
	 * 
	 */
	String deleteUserBids(String uid, String bidid) throws Exception;

}
