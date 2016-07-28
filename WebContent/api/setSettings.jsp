<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.UserClass,
org.json.JSONArray,
org.json.JSONObject,
org.apache.commons.lang3.StringEscapeUtils,
java.net.URLDecoder
" %>
<%
	String q = request.getParameter("q");
	JSONObject obj = new JSONObject(q);
	String username = session.getAttribute("username").toString();
	boolean set = false;
	try {
		set = UserClass.setSetting(username, obj.getString("kaskusUser"), obj.getString("kaskusPass"),
						obj.getString("consumerKey"), obj.getString("consumerSecret"), obj.getString("oauthToken"),
						obj.getString("oauthSecret"));
		JSONObject user = UserClass.getSetting(username);
		session.setAttribute("userDetail",user.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Set settings => " + set);
	out.print(set);
%>