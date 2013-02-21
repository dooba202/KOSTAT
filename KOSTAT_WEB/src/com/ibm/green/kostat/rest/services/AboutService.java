package com.ibm.green.kostat.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.ibm.green.config.GreenConfig;
import com.ibm.green.exception.ErrorCode;
import com.ibm.green.kostat.boot.KoStatSysInfo;
import com.ibm.green.kostat.rest.AbstractRESTResource;
import com.ibm.green.kostat.rest.ErrorMessage;

/**
 * JAX-RS resource class for general information API called 'about'
 */
@Path("/about")
@Produces(MediaType.APPLICATION_JSON)
public class AboutService extends AbstractRESTResource {
	
	public AboutService() {
		
	}
	
	/**
	 * <code>GET /about</code><br>
	 * Retrieve the general information of KoStat REST API.
	 * 
	 * @return Response
	 */
	@GET
	public Response getAbout() {
		
		final String CUR_METHOD = "AboutService#getAbout()";

		try {
			logReq(CUR_METHOD);
			
			Map<String, String> map = AboutService.getAboutMap();
			
			return Response.ok(map).build();
			
		} catch (Exception ex) {
			logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);

			ErrorMessage errMsg = generateErrorMessage(ex);
			return Response.serverError().entity(errMsg).build();
		}
	}
	
	/**
	 * <code>GET /about/errorcodes</code><br>
	 * Retrieve all error codes and descriptions that are defined in the system
	 * 
	 * @return Response
	 */
	@GET
	@Path("errorcodes")
	public Response getErrorCodeList() {
		
		List<ErrorMessage> errList = new ArrayList<ErrorMessage>();
		for (ErrorCode errCode : ErrorCode.values()) {
			
			if (errCode == ErrorCode.DUMMY_PLACEHOLDER) continue;
			errList.add(generateErrorMessage(errCode));
		}
		
		return Response.ok(errList).build();
	}
	
	/**
	 * <code>Get /about/errorcodes/{code}</code><br>
	 * Retrieve details of the given error code.
	 * 
	 * @param code error code. ex) COM001
	 * @return
	 */
	@GET
	@Path("errorcodes/{code}")
	public Response getErrorMessage(@PathParam("code") String code) {
		
		try {
			ErrorCode errorCode = ErrorCode.fromCode(code);
			
			return Response.ok(generateErrorMessage(errorCode)).build();
		} catch (Exception ex) {
			logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);

			ErrorMessage errMsg = generateErrorMessage(ex);
			return Response.status(Status.NOT_FOUND).entity(errMsg).build();
		}
		
	}
	
	/**
	 * Return a <code>Map</code> that contains system about information.
	 * 
	 * @return
	 */
	public static Map<String, String> getAboutMap() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("apiVersion", GreenConfig.getString("KoStat.restapi.version", "unknown"));
		map.put("lastUpdate",  GreenConfig.getString("KoStat.restapi.lastupdate", "unknown"));
		
		return map;
	}
}
