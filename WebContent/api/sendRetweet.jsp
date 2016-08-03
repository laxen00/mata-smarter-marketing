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
	String postUrl = request.getParameter("postUrl");
	JSONObject user = new JSONObject(session.getAttribute("userDetail").toString());
// 	String postUrl = "https://twitter.com/dnaelw/status/689351613387915266";
	postUrl = URLDecoder.decode(postUrl, "UTF-8");
	System.out.println(postUrl);
	
	String retweetString = "";

	if (user.getString("kaskusUser").equalsIgnoreCase("N/A")
	||	user.getString("kaskusPass").equalsIgnoreCase("N/A")
	||	user.getString("consumerKey").equalsIgnoreCase("N/A")
	||	user.getString("consumerSecret").equalsIgnoreCase("N/A")
	||	user.getString("oauthToken").equalsIgnoreCase("N/A")
	||	user.getString("oauthSecret").equalsIgnoreCase("N/A")) {
		retweetString = "notset";
	}
	else {
		boolean retweet = false;
		try {
			retweet = DataClass.sendRetweet(postUrl, user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		retweetString = Boolean.toString(retweet);
	}
	System.out.println("Retweet => " + retweetString);
	out.print(retweetString);
%>