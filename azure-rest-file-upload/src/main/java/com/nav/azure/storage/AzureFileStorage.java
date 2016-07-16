/**
 * 
 */
package com.nav.azure.storage;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

/**
 * @author naveen
 *
 */
@Component
public class AzureFileStorage implements FileStorage {

	public static final String IMG_CONTAINER = "images";
	public static final String FILE_CONTAINER = "files";

	private static final Logger log = LoggerFactory.getLogger(AzureFileStorage.class);
	private CloudStorageAccount storageAccount;
	private CloudBlobClient blobClient;

	@Value("${storage.connectionStr}")
	private String connectionStr;

	@PostConstruct
	public void init() {
		if (StringUtils.isNotEmpty(connectionStr)) {
			try {
				// storageAccountCloudStorageAccount.getDevelopmentStorageAccount();
				storageAccount = CloudStorageAccount.parse(connectionStr);

				blobClient = storageAccount.createCloudBlobClient();

			} catch (InvalidKeyException | URISyntaxException e) {
				log.error("Error occured establishing connection with Azure Storage account");
				throw new RuntimeException(e);
			}

		} else {
			log.error("Storage credentialStr is empty");
		}
	}

	@Override
	public void createContainer(String containerName) throws Exception {
		if (blobClient != null) {
			CloudBlobContainer container;
			try {
				container = blobClient.getContainerReference(containerName);
				container.createIfNotExists();

				// Make the container public
				// Create a permissions object
				BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

				// Include public access in the permissions object
				containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

				// Set the permissions on the container
				container.uploadPermissions(containerPermissions);

			} catch (URISyntaxException | StorageException e) {
				throw e;
			}

		}

	}

	@Override
	public String uploadFile(String containerName, String fileName, InputStream fileInputStream, long contentLength)
			throws Exception {

		if (blobClient != null) {
			boolean containerExists = false;

			for (CloudBlobContainer container : blobClient.listContainers()) {
				if (StringUtils.equals(containerName, container.getName())) {
					containerExists = true;
					break;
				}
			}

			// create container if it does not exist
			if (!containerExists) {
				createContainer(containerName);
			}

			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			

			CloudBlob blob = container.getBlockBlobReference(fileName);
			if (contentLength == 0)
				contentLength = -1; // set content length to unknown

			blob.upload(fileInputStream, contentLength);

			// return blob url
			return blob.getUri().toString();

		}

		return null;

	}

	@Override
	public List<String> listFiles(String containerName, String directory) throws Exception {
		List<String> fileUrls = new ArrayList<>();
		if (blobClient != null) {

			CloudBlobContainer container = null;
			try {
				container = blobClient.getContainerReference(containerName);

				if (!container.exists())
					return null;

				Iterable<ListBlobItem> blobs = container.listBlobs(directory);

				for (ListBlobItem listBlobItem : blobs) {
					fileUrls.add(listBlobItem.getUri().toString());

				}

			} catch (URISyntaxException | StorageException e) {
				throw e;
			}

		}
		return fileUrls;
	}


	@Override
	public boolean deleteFile(String containerName, String fileName) throws Exception {
		
		if (blobClient != null) {
			CloudBlobContainer container = null;
			try {
				container = blobClient.getContainerReference(containerName);

				if (!container.exists())
					return false;

				CloudBlob blob = container.getBlockBlobReference(fileName);

				return blob.deleteIfExists();

			} catch (URISyntaxException | StorageException e) {
				throw e;
			}

		}

		return false;
			

	}



}
