/**
* @Author: Riccardo Petrolo <riccardo>
* @Date:   2016-02-26T09:52:37+01:00
* @Email:  riccardo.petrolo@inria.fr
* @Last modified by:   riccardo
* @Last modified time: 2016-03-30T18:27:11+02:00
*/



package eu.vital.discoverer.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class DiscoveryApplicationExceptionHandler implements ExceptionMapper<DiscoveryApplicationException> {
	public Response toResponse(DiscoveryApplicationException exception)
	    {
	        return Response.status(Status.BAD_REQUEST).build();
	    }
}
