package com.ibm.green.kostat.rest.services;

import java.net.URI;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.green.auth.AccountServiceException;
import com.ibm.green.exception.ErrorCode;
import com.ibm.green.exception.GreenException;
import com.ibm.green.exception.GreenRuntimeException;
import com.ibm.green.kostat.auth.AccountManager;
import com.ibm.green.kostat.boot.KoStatSysInfo;
import com.ibm.green.kostat.boot.KoStatSysInfo.KEY;
import com.ibm.green.kostat.dto.UserDTO;
import com.ibm.green.kostat.enums.UserType;
import com.ibm.green.kostat.rest.AbstractRESTResource;
import com.ibm.green.kostat.rest.ErrorMessage;

/**
 * JAX-RS Resource Class for  Authentication handling
 */
@Path("/auth")
public class AuthService extends AbstractRESTResource {
	
	public final static String PARAMKEY_CODE = "code";
	
	
	public AuthService() {
		
	}

	@POST
	@Path("form")
	public Response authenticate(@FormParam("userID") String userID, @FormParam("password") String password) {
		
		final String CUR_METHOD = "AuthService#authenticate(@FormParam(\"userID\") String userID, @FormParam(\"password\") String password))";	
		
		try {
			logReq(CUR_METHOD, userID);
			
			logger.info("[Login] User [" + userID + "] is trying to logging in.");
			UserDTO user = AccountManager.getInst().authenticateUser(userID, password);

			createNewSession(user);
			logger.info("[Login] User [" + userID + "] logged in successfully.");			

			URI mainURI = generateURI(KoStatSysInfo.getInst().get(KEY.URL_MAIN));
			
			return Response.seeOther(mainURI).build();
					
		} catch (Exception ex) {
			
			if (ex instanceof AccountServiceException) {
				logger.info("[Login] User [" + userID + "] failed to log in : " + ex.getLocalizedMessage());				
			} else {
				logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);
			}
			ErrorCode code = ErrorCode.COMMON_UNKNOWN;
			if (ex instanceof GreenException) {
				code = ((GreenException)ex).getErrorCode();
			} else if (ex instanceof GreenRuntimeException) {
				code = ((GreenRuntimeException)ex).getErrorCode();
			} 

			HashMap<String, String> queryParam = new HashMap<String, String>();
			queryParam.put(PARAMKEY_CODE, code.getCode());

			URI loginURI = generateURI(KoStatSysInfo.getInst().get(KEY.URL_LOGIN), queryParam);
			
			return Response.seeOther(loginURI).build();
			
		} finally {
		}		
		
	}

	
	@POST
	@Path("admin/create")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response createUser(@FormParam("userID") String userID, @FormParam("password") String password,
							   @FormParam("userType") String userType) {
		
		final String CUR_METHOD = "AuthService#createUser(.....)";	
		
		try {
			logReq(CUR_METHOD);
			
			AccountManager.getInst().createAccount(userID, password, UserType.valueOf(userType));

			return Response.ok().build();

		} catch (Exception ex) {
			logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);
			ErrorMessage errMsg = generateErrorMessage(ex);
			
			return Response.serverError().entity(errMsg).build();
		} finally {

		}		
		
	}	
	
	@GET
	@Path("redirect")
	@Produces(MediaType.APPLICATION_JSON)
	public Response redirectToLogin(@QueryParam("format") String format) {
		
		final String CUR_METHOD = "AuthService#redirectToLogin(@QueryParam(\"format\") String format)";	
		
		try {
			logReq(CUR_METHOD);
			
			if (format.equalsIgnoreCase("json")) {
				
				HashMap<String, String> queryParam = new HashMap<String, String>();
				queryParam.put(PARAMKEY_CODE, ErrorCode.COMMON_AUTH_REQUIRED.getCode());

				URI loginURI = generateURI(KoStatSysInfo.getInst().get(KEY.URL_LOGIN), queryParam);
				ErrorMessage errMsg = generateErrorMessage(ErrorCode.COMMON_AUTH_REQUIRED, loginURI.toString());
				
				return Response.serverError().entity(errMsg).build();
				
			} else if (format.equalsIgnoreCase("header")) {
				// just redirect by response code and location header
				HashMap<String, String> queryParam = new HashMap<String, String>();
				queryParam.put(PARAMKEY_CODE, ErrorCode.COMMON_AUTH_REQUIRED.getCode());

				URI loginURI = generateURI(KoStatSysInfo.getInst().get(KEY.URL_LOGIN), queryParam);	
				return Response.seeOther(loginURI).build();
				
			} else {
				logger.error("invalid format value was given for redirectToLogin  : "  + format);
				return Response.serverError().entity(generateErrorMessage(ErrorCode.COMMON_INVALID_REQ)).build();
			}
					
		} catch (Exception ex) {
			logger.error("Exception occurs during handling request : " + uriInfo.getPath(), ex);
			ErrorMessage errMsg = generateErrorMessage(ex);
			
			return Response.serverError().entity(errMsg).build();
			
		} finally {
		}		
				
	}
	
	
	private HttpSession createNewSession(UserDTO user) {
		
		int nTimeout = Integer.parseInt(KoStatSysInfo.getInst().get(KEY.SESSION_TIMEOUT));
		
		HttpSession session = httpRequest.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		session = httpRequest.getSession(true);
		session.setMaxInactiveInterval(nTimeout);
		session.setAttribute(SESSKEY_USER, user);
		
		return session;
	}

}
