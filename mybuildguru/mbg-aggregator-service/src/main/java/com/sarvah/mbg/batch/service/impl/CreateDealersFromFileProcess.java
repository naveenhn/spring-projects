package com.sarvah.mbg.batch.service.impl;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Point;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.sarvah.mbg.batch.dao.jpa.UserInfoRepository;
import com.sarvah.mbg.batch.dao.mongo.ProductDAO;
import com.sarvah.mbg.batch.dao.mongo.RoleDAO;
import com.sarvah.mbg.batch.dao.mongo.UserDAO;
import com.sarvah.mbg.batch.model.DealerDetails;
import com.sarvah.mbg.batch.service.MBGCommandBase;
import com.sarvah.mbg.commons.address.AddressLookupService;
import com.sarvah.mbg.domain.common.asset.File;
import com.sarvah.mbg.domain.mongo.catalog.Product;
import com.sarvah.mbg.domain.mongo.common.contact.Address;
import com.sarvah.mbg.domain.mongo.common.contact.Phone;
import com.sarvah.mbg.domain.mongo.common.contact.PhoneType;
import com.sarvah.mbg.domain.mongo.userprofile.User;
import com.sarvah.mbg.domain.mongo.userprofile.UserStatus;
import com.sarvah.mbg.domain.mongo.userprofile.UserType;
import com.sarvah.mbg.domain.mongo.userprofile.role.Role;
import com.sarvah.mbg.domain.user.AuthRoles;
import com.sarvah.mbg.domain.user.UserInfo;

/**
 * The Class DealerInfoProcess.
 */
@Component("CreateDealersFromFileProcess")
public class CreateDealersFromFileProcess implements MBGCommandBase {

	/** The name. */
	@Value("${name:Naveen}")
	private String name;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(CreateDealersFromFileProcess.class);

	/** The user repository. */
	@Autowired
	private UserInfoRepository userRepository;

	/** The role dao. */
	@Autowired
	private RoleDAO roleDAO;

	/** The user dao. */
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ProductDAO productDAO;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AddressLookupService addressLookupService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sarvah.batch.service.MBGCommandBase#execute()
	 */
	@Override
	public void execute() throws Exception {
		System.out.println("Dealer Creation service running ==== CSV Parser");
		try {
			List<Product> products = productDAO.findAll();

			// we need to modify the code to read the file, if the time of
			// creation of product is <=24 hours
			for (Product product : products) {
				// Product product = productDAO.findOne("4");
				List<File> files = product.getAssets().getRelatedFiles();
				for (File file : files) {
					String fileName = file.getName();
					if (fileName.equalsIgnoreCase("provider_dealers.csv")) {
						String filePath = file.getUrl();
						parseCSVToBean(filePath);
					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Parses the csv to bean.
	 * 
	 * @throws Exception
	 */
	private void parseCSVToBean(String url) throws Exception {

		// later we have to implement to check created time of the CSV file

		ColumnPositionMappingStrategy<DealerDetails> strat = new ColumnPositionMappingStrategy<>();
		strat.setType(DealerDetails.class);
		String[] columns = new String[] { "firstName", "lastName", "username",
				"contactNumber", "panNumber", "vatNumber", "bankName",
				"bankAccountNumber", "websiteUrl", "address",
				"branchTelNumber", "country", "state", "city", "zipcode" };
		strat.setColumnMapping(columns);

		CsvToBean<DealerDetails> csvToBean = new CsvToBean<DealerDetails>();

		// URL fileURL = new URL(url);
		// BufferedReader br = new BufferedReader(new InputStreamReader(
		// fileURL.openStream()));

		URL csvFileURL = new URL(url);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(csvFileURL.openStream()));

		CSVReader reader = new CSVReader(bufferedReader);

		List<DealerDetails> dealers = csvToBean.parse(strat, reader);

		for (DealerDetails dealerDetails : dealers) {
			// create user
			createUser(dealerDetails);
		}

	}

	/**
	 * Creates the user.
	 *
	 * @param dealerDetails
	 *            the dealer details
	 * @throws Exception
	 */
	private void createUser(DealerDetails dealerDetails) throws Exception {

		if (dealerDetails != null && isNotBlank(dealerDetails.getUsername())) {
			String username = dealerDetails.getUsername();

			String password = getTempPassword(10);
			// "Email validation"
			boolean validUserName = EmailValidator.getInstance().isValid(
					username);

			if (validUserName) {

				// check if the user already exists
				UserInfo userInfo = userRepository.findByUsername(username
						.toLowerCase());

				if (userInfo != null
						&& equalsIgnoreCase(userInfo.getUsername()
								.toLowerCase(), username.toLowerCase())) {
					logger.info(
							"Duplicate user, a user with same username already exists {}",
							username);
					return;

				}

				// first create the user in mongo
				User user = new User();
				if (StringUtils.isNoneBlank(dealerDetails.getFirstName())
						&& isNotBlank(dealerDetails.getUsername())
						&& isNotBlank(dealerDetails.getBankName())
						&& isNotBlank(dealerDetails.getBankAccountNumber())
						&& isNotBlank(dealerDetails.getPanNumber())
						&& isNotBlank(dealerDetails.getVatNumber())) {

					user.setUsername(username.toLowerCase());
					user.setFirstName(dealerDetails.getFirstName());
					user.setLastName(dealerDetails.getLastName());
					user.setFullName(dealerDetails.getFirstName() + " "
							+ dealerDetails.getLastName());
					user.setStatus(UserStatus.INACTIVE);
					// add the default roles first depending on type

					com.sarvah.mbg.domain.mongo.userprofile.role.Role role = roleDAO
							.findByType(UserType.DEALER);
					Set<Role> roles = new HashSet<>();
					roles.add(role);
					user.setRoles(roles);
					user.setBankName(dealerDetails.getBankName());
					user.setBankAccountNumber(dealerDetails
							.getBankAccountNumber());
					user.setVatNumber(dealerDetails.getVatNumber());
					user.setPanNumber(dealerDetails.getPanNumber());
					user.setWebsiteUrl(dealerDetails.getWebsiteUrl());

					if (StringUtils.isNotBlank(dealerDetails.getAddress())
							&& isNotBlank(dealerDetails.getCity())
							&& isNotBlank(dealerDetails.getState())
							&& isNotBlank(dealerDetails.getCountry())
							&& isNotBlank(dealerDetails.getZipcode())) {

						Address address = new Address();
						// creating for the first time so its the first address
						address.setAddressId(1);
						address.setAddressLine1(dealerDetails.getAddress());
						address.setCity(dealerDetails.getCity());
						address.setState(dealerDetails.getState());
						address.setCountry(dealerDetails.getCountry());
						int zipcode = Integer.parseInt(dealerDetails
								.getZipcode());
						address.setZipcode(zipcode);

						// lat and log implementation
						Point point = null;
						String addressPoint = dealerDetails.getAddress() + ","
								+ dealerDetails.getCity() + ","
								+ dealerDetails.getState() + ","
								+ dealerDetails.getCountry() + ","
								+ dealerDetails.getZipcode();
						point = addressLookupService
								.getGeoCodedAddress(addressPoint);
						if (point != null) {
							address.setLocation(point);
						} else {
							logger.info("based on Address search lat and lon null ");
							point = addressLookupService
									.getGeoCodedAddress(Integer
											.parseInt(dealerDetails
													.getZipcode()));
							if (point != null) {
								address.setLocation(point);
							}
						}

						// address.setLocation(new Point(0.0, 0.0));
						address.setEmail(dealerDetails.getUsername());

						Phone phone = new Phone();
						phone.setPrimary(true);
						phone.setNumber(dealerDetails.getContactNumber());
						phone.setType(PhoneType.MOBILE);
						Set<Phone> phoneSet = new HashSet<>();
						phoneSet.add(phone);
						Phone phone2 = new Phone();
						phone2.setPrimary(false);
						phone2.setNumber(dealerDetails.getBranchTelNumber());
						phone2.setType(PhoneType.OFFICE);
						phoneSet.add(phone2);

						address.setPhoneNumbers(phoneSet);
						user.addAddress(address);
					} else {
						logger.info("Unable to create new user, address information is incomplete");
						return;
					}
					// save to mongo
					user = userDAO.save(user);
					logger.info("User information saved into mongo successfully");
				} else {
					logger.info("Unable to create new user, it does not have minimum properties like firstname,username,bankname, bank accno,vat,pan");
					return;
				}
				try {
					// mongoid to mysql, now save userInfo object to mysql which
					// is
					// primarlily for auth only

					userInfo = new UserInfo(username.toLowerCase(),
							passwordEncoder.encode(password),
							dealerDetails.getFirstName(),
							dealerDetails.getLastName(),
							AuthRoles.AUTHENTICATED, "SYSTEM");
					// add phone number as well
					if (StringUtils
							.isNotEmpty(dealerDetails.getContactNumber())) {

						long phonenumber = Long.parseLong(dealerDetails
								.getContactNumber().trim());
						userInfo.setPhonenumber(phonenumber);
					}

					userInfo.setCreatedtime(new Date());
					userInfo.setLastmodifiedtime(new Date());

					// IMP: this is where there is link establised between mongo
					// &
					// SQL for any JustInCase reason
					userInfo.setMongoUserId(user.getId());
					// save to SQL database
					userInfo = userRepository.save(userInfo);
					logger.info("User information saved into SQL database successfully");
					// special case, hence enclose with try, just in case
					// something
					// fails, revert mongo save
				} catch (Exception e) {
					logger.error(
							"Save User to SQL Datastore failed for user - {}",
							username);
					// save failed, revert mongo save as well
					userDAO.delete(user.getId());
					return;
				}

			} else {
				logger.info("Unable to create new user, it does not have minimum properties like username");
				return;
			}
		} else {
			logger.info("Unable to create new user, it doesn't have minimum properties like username");
			return;
		}
	}

	/** The Constant symbols. */
	private static final char[] symbols;

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch)
			tmp.append(ch);
		for (char ch = 'a'; ch <= 'z'; ++ch)
			tmp.append(ch);
		symbols = tmp.toString().toCharArray();
	}

	/**
	 * Gets the temp password.
	 *
	 * @param length
	 *            the length
	 * @return the temp password
	 */
	private String getTempPassword(int length) {
		Random rand = new Random(9845936902l);
		char[] buf = new char[length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[rand.nextInt(symbols.length)];
		return new String(buf);
	}

}
