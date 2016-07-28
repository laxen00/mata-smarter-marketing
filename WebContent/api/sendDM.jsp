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
	String id = request.getParameter("id");
	String url = request.getParameter("url");
// 	String url = "https://twitter.com/ice1217/status/692197182150148096";
	String type = request.getParameter("type");
	String text = request.getParameter("text");
	JSONObject user = new JSONObject(session.getAttribute("userDetail").toString());
	boolean dm = false; 
	try {
		dm = DataClass.sendDM(id, type, url, text, user);
	}
	catch (Exception e) {
		
	}
	System.out.println("Reply DM : " + dm);
	out.print(dm);
%>