/**
 * 
 */
package com.nav.azure.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nav.azure.storage.FileStorage;

/**
 * @author naveen
 *
 */

@Service
@Path("/file")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FileResource {

	@Autowired
	private FileStorage filestorage;

	/**
	 * 
	 * @param filenames
	 * @param form
	 * @return
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(FormDataMultiPart form) {
		
		try {
			Map<String, List<FormDataBodyPart>> formdatas = form.getFields();
			
			for (String partName : formdatas.keySet()) {

				List<FormDataBodyPart> fileparts = formdatas.get(partName);

				for (FormDataBodyPart formDataBodyPart : fileparts) {
					if (!formDataBodyPart.isSimple()) {
						ContentDisposition contentDisposition = formDataBodyPart.getContentDisposition();
						InputStream fileInputStream = formDataBodyPart.getValueAs(InputStream.class);
						String fileName = contentDisposition.getFileName();
						long contentLength = contentDisposition.getSize();

						// testing how subdirectories work

						String uri = filestorage.uploadFile("images", "catalog/product/A/AN/" + fileName, fileInputStream,
								contentLength);
						
						System.out.println(uri);
						// save in temp folder
						// String destinationLocation = "/tmp/" +
						// contentDisposition.getFileName();
						// saveFileToAzure(fileInputStream,
						// destinationLocation);
					}else {

						System.out.println(formDataBodyPart.getName());
						System.out.println(formDataBodyPart.getValue());
					}
				}



			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		
		
		

		return Response.ok().entity("Files uploaded").build();

	}



	private void saveFileToAzure( InputStream uploadInputStream, String fileLocation) {
		
		try {
			File file = new File(fileLocation);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("File Exists");
			}

			OutputStream oStream = new FileOutputStream(file);
			 int read = 0;
			 byte[] bytes = new byte[1024];
			 
			 
			while ((read = uploadInputStream.read(bytes)) != -1) {
				oStream.write(bytes, 0, read);
			}

			oStream.flush();
			oStream.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
