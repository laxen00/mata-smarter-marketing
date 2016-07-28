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
	JSONArray arr = new JSONArray();
	try {
		arr = DataClass.checkNotif();
	}
	catch (Exception e) {
		e.printStackTrace();
	}
// 	System.out.println("Notif => " + arr);
	out.print(arr.toString());
%>