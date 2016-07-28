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
	String url = request.getParameter("url");
// 	String url = "https://twitter.com/ice1217/status/692197182150148096";
	String type = request.getParameter("type");
	String id = request.getParameter("id");
	String username = session.getAttribute("username").toString();
	url = URLDecoder.decode(url, "UTF-8");
	System.out.println(url);
	JSONArray arr = new JSONArray();
	JSONObject user = new JSONObject(session.getAttribute("userDetail").toString());
	try {
		arr = DataClass.getDMJSON(id, url, type, user);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("DMs => " + arr);
	out.print(arr.toString());
%>