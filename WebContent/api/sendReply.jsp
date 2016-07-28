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
	String type = request.getParameter("type");
	String id = request.getParameter("id");
	String username = session.getAttribute("username").toString();
	String text = request.getParameter("text");
	String mediaPath = request.getParameter("mediaPath");
	JSONObject user = new JSONObject(session.getAttribute("userDetail").toString());
// 	String url = "https://twitter.com/ice1217/status/696942631771906048";
// 	System.out.println(url);
	url = URLDecoder.decode(url, "UTF-8");
	System.out.println(url);

// 	System.out.println(type);
// 	System.out.println(id);
// 	System.out.println(username);
// 	System.out.println(text);
	boolean reply = false; 
	try {
		reply = DataClass.sendReply(url, type, id, user, text, mediaPath);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Reply => " + reply);
	out.print(reply);
%>