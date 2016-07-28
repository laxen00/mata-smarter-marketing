<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.DataClass,
org.json.JSONArray,
org.json.JSONObject,
org.apache.commons.lang3.StringEscapeUtils,
java.net.URLDecoder
" %>
<%
	int type = Integer.parseInt(request.getParameter("type"));
	String objString  = request.getParameter("obj");
	System.out.println("type: " + type + " ==> obj: " + objString);
	JSONObject obj = new JSONObject (objString);
	String username = session.getAttribute("username").toString();
	int validate = 0;
	try {
		validate = DataClass.validateTwitterKaskus(type, obj);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Validate => " + validate);
	out.println(validate);
%>