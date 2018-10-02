package com.sarvah.mbg.banners.service.impl;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sarvah.mbg.banners.service.BannerService;
import com.sarvah.mbg.commons.storage.AzureFileStorage;
import com.sarvah.mbg.commons.storage.FileStorage;
import com.sarvah.mbg.domain.common.asset.FileType;
import com.sarvah.mbg.domain.common.asset.Image;
import com.sarvah.mbg.domain.mongo.marketing.Banner;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.userprofile.dao.mongo.PromotionBannerDAO;
import com.sarvah.mbg.userprofile.dao.mongo.UserDAO;
import com.sarvah.mbg.userprofile.response.BannerViewDetails;
import com.sarvah.mbg.userprofile.response.ManageBannersView;

@Service
public class BannerServiceImpl implements BannerService {
	private static final Logger logger = LoggerFactory
			.getLogger(BannerServiceImpl.class);

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PromotionBannerDAO bannerDAO;

	

	@Autowired
	private FileStorage filestorage;

	/**
	 * Method to get Banners for the particular user
	 * 
	 * This method returns the banner for the particular user
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be userId, by which the particular banner would return
	 *            as a result
	 * 
	 */
	@Override
	public List<Banner> getUserBanners(String uid) throws Exception {
		List<Banner> banner = new ArrayList<Banner>();
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				logger.info("finding user Banner for the product");
				banner = bannerDAO.findByUser(user);
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		}
		return banner;
	}

	/**
	 * Method to count Banners for the particular user
	 * 
	 * This method returns the count of banners for the given userId
	 * 
	 * @throws Exception
	 * 
	 * @PathParam would be the userID, by which the count of banners for the
	 *            user would return as a result
	 */
	@Override
	public Long getUserBannerCount(String uid) throws Exception {
		Long count = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findOne(uid);
			if (user != null) {
				logger.info("counting user Banner for the product");
				count = bannerDAO.countByUser(user);
			} else {
				logger.info("User doesn't exists");
				throw new Exception("User doesn't exists");
			}
		}
		return count;
	}

	/**
	 * method for create banner.
	 * 
	 * @param uid
	 * @param bannerCreateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Banner createBanner(String uid, String name, String place,
			String startDate, String endDate, List<String> pIds)
			throws Exception {
		Banner banner = null;
		if (StringUtils.isNotBlank(uid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				logger.info("user creating banner for product");
				banner = new Banner();
				if (StringUtils.isNotBlank(place)) {
					banner.setLocation(place);
				}
				banner.setUser(user);

				if (pIds != null) {
					banner.setProductIds(pIds);
				}
				if (StringUtils.isNotBlank(startDate)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/mm/yyyy");
					Date sDate = formatter.parse(startDate);
					banner.setStartDate(sDate);
				}
				if (StringUtils.isNotBlank(endDate)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/mm/yyyy");
					Date eDate = formatter.parse(endDate);
					banner.setEndDate(eDate);
				}
				bannerDAO.insert(banner);

			} else {
				logger.info("user does not exist");
				throw new Exception("user does not exist");
			}
		}
		return banner;
	}

	/**
	 * Method for update banner.
	 * 
	 * @param uid
	 * @param banid
	 * @param bannerUpdateRequestParam
	 * @return
	 * @throws Exception
	 */
	@Override
	public Banner updateBanner(String uid, String banid, String desc,
			String startDate, String endDate) throws Exception {
		Banner banner = null;
		if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(banid)) {
			User user = userDAO.findById(uid);
			if (user != null) {
				logger.info("updating banner");
				// if (StringUtils.isNotBlank(desc)) {
				banner = bannerDAO.findOne(banid);
				// Description desc1 = banner.getDesc();
				// desc1.setVal(desc);
				// }

				if (StringUtils.isNotBlank(startDate)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/mm/yyyy");
					Date sDate = formatter.parse(startDate);
					banner.setStartDate(sDate);
				}

				if (StringUtils.isNotBlank(endDate)) {
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/mm/yyyy");
					Date eDate = formatter.parse(endDate);
					banner.setEndDate(eDate);
				}
			} else {
				logger.info("user does not exist");
				throw new Exception("user does not exist");
			}
		}
		bannerDAO.save(banner);
		return banner;
	}

	/**
	 * Method to delete particular banner of the user
	 * 
	 * This method deletes the banner for the particular user
	 * 
	 * @throws Exception
	 * 
	 * @Pathparam would be the userId and the bannerId
	 */
	@Override
	public String deleteUserBanner(String uid, String banid) throws Exception {

		User user = userDAO.findOne(uid);
		if (StringUtils.isNotBlank(uid)) {
			if (user != null) {
				if (StringUtils.isNotBlank(banid)) {
					Banner banner = bannerDAO.findOne(banid);
					if (banner != null) {
						logger.info("deleting banner");
						bannerDAO.delete(banid);
					} else {
						logger.info("Banner doesn't exist");
						throw new Exception("Banner doesn't exist");
					}
				}
			} else {
				logger.info("User doesn't exist");
				throw new Exception("User doesn't exist");
			}

		}

		return banid;
	}

	// File and Image upload
	@Override
	public String uploadFile(String bannerId, String fileName,
			String fileTypeValue, InputStream fileInputStream,
			long contentLength) throws Exception {
		String locationName = null;
		String url = null;
		// UserProjectProfile userProjectProfile = null;
		// String projectName = null;
		Banner banner = null;
		String bannerName = null;
		if (StringUtils.isNotBlank(bannerId)) {
			logger.info("BannerResource : uploadFile : " + bannerId);
			banner = bannerDAO.findOne(bannerId);
			if (banner != null) {
				bannerName = banner.getUser().getFullName();
				logger.info(bannerName);
				locationName = "user/banner/" + bannerName.charAt(0) + "/"
						+ bannerName.substring(0, 2) + "/" + fileName;
				logger.info(locationName);
				// String fileNameStr[] = fileName.split("\\.");
				if (fileTypeValue.equals("File")) {

					url = filestorage.uploadFile(
							AzureFileStorage.FILE_CONTAINER, locationName,
							fileInputStream, contentLength);
				} else {
					url = filestorage.uploadFile(
							AzureFileStorage.IMG_CONTAINER, locationName,
							fileInputStream, contentLength);
				}

				// ProductAsset productAsset = product.getAssets();
				// if (productAsset != null) {
				// logger.debug("ProductResource : uploadFile : "
				// + productAsset);
				if (StringUtils.equals(fileTypeValue.toLowerCase(), "image")) {
					Image image = new Image(fileName, url);
					URL uri = new URL(url);
					logger.debug(url);				
					image.setSize(uri.openConnection().getContentLengthLong());
					logger.debug("Image Size set in image");
					image.setFileType(FileType.IMAGE);
					// List<Image> imageList = (List<Image>)
					// banner.getBannerImages();
					// if (imageList != null) {
					// logger.debug("BannerResource : uploadFile : "
					// + imageList);
					// imageList.add(image);
					// } else {
					// imageList = new ArrayList<>();
					// imageList.add(image);
					// }
					// productAsset.setImages(imageList);
					banner.setBannerImage(image);
					// product.setAssets(productAsset);
					bannerDAO.save(banner);
					logger.debug("BannerResource : uploadFile : image uploaded");
				} else {
					// productAsset = new ProductAsset();
					if (StringUtils
							.equals(fileTypeValue.toLowerCase(), "image")) {
						Image image = new Image(fileName, url);
						URL uri = new URL(url);			
					
						image.setSize(uri.openConnection().getContentLength());
						logger.debug("image size set in image");
						image.setFileType(FileType.IMAGE);
						// List<Image> imageList = new ArrayList<>();
						// imageList.add(image);
						// productAsset.setImages(imageList);
						banner.setBannerImage(image);
						// product.setAssets(productAsset);
						bannerDAO.save(banner);
						// productDAO.save(product);
						logger.debug("BannerResource : uploadFile : Image uploaded");
					}

				}
			}
		} else {
			throw new Exception("Sorry banner id did not match in database");
		}
		return banner.getId();
	}

	/**
	 * Method to get banners
	 * 
	 * @return
	 */
	@Override
	public List<Banner> getAllBanners() {
		// TODO Auto-generated method stub

		List<Banner> banners = new ArrayList<>();

		banners = bannerDAO.findAll();
		return banners;
	}

	@Override
	public List<ManageBannersView> getSuperAdminManageBanners()
			throws Exception {
		// TODO Auto-generated method stub

		List<ManageBannersView> manageBannersViewList = new ArrayList<>();

		List<Banner> bannersList = new ArrayList<>();

		bannersList = bannerDAO.findAll();
		if (bannersList.size() != 0) {
			for (Banner banner : bannersList) {

				ManageBannersView manageBannersView = new ManageBannersView();
				Set<Role> roles = banner.getUser().getRoles();
				for (Role role : roles) {
					manageBannersView.setRole(role.getName());
					manageBannersView.setCustomerName(banner.getUser()
							.getUsername());
					manageBannersView.setStartDate(banner.getStartDate());
					manageBannersView.setEndDate(banner.getEndDate());
					manageBannersView.setLocation(banner.getLocation());
					Long count = bannerDAO.countByUser(banner.getUser());
					manageBannersView.setCount(count);

					Date todayDate = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String format1 = sdf.format(banner.getEndDate());
					String format2 = sdf.format(todayDate);
					if (format2.compareTo(format1) <= 0) {
						logger.info("Active");
						manageBannersView.setStatus("ACTIVE");
					} else {
						logger.info("Inactive");
						manageBannersView.setStatus("INACTIVE");
					}
				}
				manageBannersViewList.add(manageBannersView);
			}
		} else {
			throw new Exception("No banners to display");
		}

		return manageBannersViewList;
	}

	@Override
	public Banner getBannerById(String banId) throws Exception {
		// TODO Auto-generated method stub
		Banner banner = new Banner();

		if (StringUtils.isNotBlank(banId)) {
			banner = bannerDAO.findOne(banId);
		} else {
			throw new Exception("Banner Id is empty");
		}
		return banner;
	}

	@Override
	public BannerViewDetails getBannerViewDetails(String banId)
			throws Exception {
		// TODO Auto-generated method stub
		Banner banner = new Banner();
		BannerViewDetails bannerViewDetails = new BannerViewDetails();

		if (StringUtils.isNotBlank(banId)) {
			banner = bannerDAO.findOne(banId);
			if (banner != null) {
				Set<Role> roles = banner.getUser().getRoles();
				for (Role role : roles) {
					bannerViewDetails.setRole(role.getName());
					bannerViewDetails.setCustomerName(banner.getUser()
							.getUsername());
					bannerViewDetails.setStartDate(banner.getStartDate());
					bannerViewDetails.setEndDate(banner.getEndDate());
					bannerViewDetails.setLocation(banner.getLocation());

					// List<Image> images = banner.getBannerImages();
					// for (Image image : images) {
					bannerViewDetails.setFileFormat(banner.getBannerImage()
							.getFileType());
					bannerViewDetails.setFileSize(banner.getBannerImage()
							.getSize());
					// }

					Date todayDate = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String format1 = sdf.format(banner.getEndDate());
					String format2 = sdf.format(todayDate);
					if (format2.compareTo(format1) <= 0) {
						logger.info("Active");
						bannerViewDetails.setStatus("ACTIVE");
					} else {
						logger.info("Inactive");
						bannerViewDetails.setStatus("INACTIVE");
					}
				}

			} else {
				throw new Exception("Banner doesn't exist");
			}

		} else {
			throw new Exception("Banner Id is empty");
		}
		return bannerViewDetails;

	}
}
