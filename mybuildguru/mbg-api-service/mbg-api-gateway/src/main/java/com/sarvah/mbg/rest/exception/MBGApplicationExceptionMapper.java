/**
 * 
 */
package com.sarvah.mbg.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sarvah.mbg.rest.model.ErrorResponse;

/**
 * @author naveen
 *
 */
@Provider
public class MBGApplicationExceptionMapper implements ExceptionMapper<MBGAppException> {

	@Override
	public Response toResponse(MBGAppException ex) {
		
		ErrorResponse er = new ErrorResponse(ex);
		return Response.status(er.getStatus()).entity(er).type(MediaType.APPLICATION_JSON).build();
		
	}

}
