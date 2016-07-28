<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.UserClass,
org.json.JSONObject" %>

<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	
	username = username.toUpperCase();
	int result = UserClass.login(username, password);
	if (result == 1) {
		session.setAttribute("username", username);
		JSONObject user = UserClass.getSetting(username);
		session.setAttribute("userDetail",user.toString());
	}
	else {
		session.setAttribute("loginMessage", "Wrong username/password.");
	}
%>

<script>window.top.location.href="../pages/index.jsp";</script>