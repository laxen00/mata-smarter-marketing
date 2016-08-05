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
	
	
	String dmString = "";

	if (user.getString("kaskusUser").equalsIgnoreCase("N/A")
	||	user.getString("kaskusPass").equalsIgnoreCase("N/A")
	||	user.getString("consumerKey").equalsIgnoreCase("N/A")
	||	user.getString("consumerSecret").equalsIgnoreCase("N/A")
	||	user.getString("oauthToken").equalsIgnoreCase("N/A")
	||	user.getString("oauthSecret").equalsIgnoreCase("N/A")) {
		dmString = "notset";
	}
	else {
		boolean dm = false; 
		try {
			dm = DataClass.sendDM(id, type, url, text, user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		dmString = Boolean.toString(dm);
	}
	System.out.println("Reply DM : " + dmString);
	out.print(dmString);
%>