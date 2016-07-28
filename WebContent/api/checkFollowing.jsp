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
	int following = 0;
	try {
		following = DataClass.checkFollowingTwitter(postUrl, user);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Following => " + following);
	out.print(following);
%>