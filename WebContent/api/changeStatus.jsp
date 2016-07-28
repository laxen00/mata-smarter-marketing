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
	String numString = request.getParameter("num");
	int num = Integer.parseInt(numString);
	String id = request.getParameter("id");
	String username = request.getParameter("username");
	boolean status = false;
	try {
		status = DataClass.changeStatus(num, id, username);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Change status => " + status);
	out.println(status);
%>