<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ibm.green.kostat.rest.services.util.*, com.ibm.green.kostat.dto.*, com.ibm.green.kostat.enums.*, com.ibm.green.kostat.rest.services.*"%>
<%@page import="com.ibm.green.kostat.boot.KoStatSysInfo.KEY"%>
<%@page import="com.ibm.green.kostat.boot.KoStatSysInfo"%>
<%
	HttpSession ourSession = request.getSession(false);
	if (ourSession != null) {
		UserDTO user = (UserDTO) session.getAttribute(AuthService.SESSKEY_USER);
		ourSession.invalidate();
	}
	
	String loginURL = KoStatSysInfo.getInst().get(KEY.URL_LOGIN);
	String ctxRoot = request.getContextPath();
	// New location to be redirected
	
	String site = ctxRoot + "/" + loginURL;
	response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	response.setHeader("Location", site);	
 %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Logout Page</title>
</head>
<body>

</body>
</html>