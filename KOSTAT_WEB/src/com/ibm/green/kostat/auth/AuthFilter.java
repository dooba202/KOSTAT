package com.ibm.green.kostat.auth;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ibm.green.kostat.boot.KoStatSysInfo;
import com.ibm.green.kostat.boot.KoStatSysInfo.KEY;
import com.ibm.green.kostat.dto.UserDTO;
import com.ibm.green.kostat.enums.UserType;
import com.ibm.green.kostat.rest.services.AuthService;


@WebFilter(urlPatterns={"/*"} )
public class AuthFilter implements Filter {
	
	FilterConfig filterConfig;
	ServletContext servletCtx;
	
	final static String ADMIN_URI_PREFIX = "/admin";
	
	/*
	 * Servlet Path Set that is accessible without authentication
	 */
	HashSet<String> allowedServletPaths;
	
	/*
	 * Request URI Set(w/o ctx root) that are accessible without authentication 
	 */
	HashSet<String> allowedRequestURIs;
	
	KoStatSysInfo sysInfo = KoStatSysInfo.getInst();

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) req;
		
		String servletPath = httpRequest.getServletPath();
		
		if (servletPath.startsWith("/")) {
			servletPath = servletPath.substring(1); // remove heading slash
		}
		
		// check if it is allowed request (with Servlet Name)
		if (allowedServletPaths.contains(servletPath))  { 
			chain.doFilter(req, res);
			return;
		}
		
		String requestURI = httpRequest.getRequestURI();
		String contextPath = httpRequest.getContextPath();
		String requestURIWithoutCtx = requestURI.substring(contextPath.length() + 1);
		
		// Session validation
		UserDTO user = (UserDTO) httpRequest.getSession().getAttribute(AuthService.SESSKEY_USER);
		
		if (user != null) {
			
			// normal service
			if ((requestURIWithoutCtx.toLowerCase().indexOf(ADMIN_URI_PREFIX) == -1)) {
				chain.doFilter(req, res);
				return;
			}
			
			// admin service				
			if (user.getUserType() == UserType.Admin) {
				chain.doFilter(req, res);
				return;
			} 
		}
		
		// even if user is not authorized, allowedRequestURIs can be accessible from anyone.
		for (String uri : allowedRequestURIs) {
			if (requestURIWithoutCtx.startsWith(uri)) {
				chain.doFilter(req, res);
				return;	
			}
		}
		
		// Invalid user. redirection handling
		// If request is rest api call, response json message with redirect url.
		// If not, just send redirect response code with location header
		String queryParam;
		if (servletPath.equals(sysInfo.get(KEY.REST_SERVLETPATH))) {
			queryParam = "?format=json";
		} else {
			queryParam = "?format=header";
		}
		
		servletCtx.getRequestDispatcher("/" + sysInfo.get(KEY.AUTH_REDIRECTPATH) + queryParam).forward(req, res);
		
		return;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
		this.filterConfig = config;
		this.servletCtx = config.getServletContext();
		
		allowedServletPaths = new HashSet<String>();
		allowedServletPaths.add(""); // default home
		allowedServletPaths.add(sysInfo.get(KEY.URL_LOGIN)); // login url
		
		allowedRequestURIs = new HashSet<String>();
		allowedRequestURIs.add(sysInfo.get(KEY.AUTH_FORMPATH)); // authenticate form submit url
		
		allowedRequestURIs.add("js/");
		allowedRequestURIs.add("images/");	
		allowedRequestURIs.add("css/");	
		allowedRequestURIs.add("json/");
		allowedRequestURIs.add("test/");		
		
		
	}

	@Override
	public void destroy() {

	}

}
