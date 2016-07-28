<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.api.main.UserClass" %>
<%
	session.removeAttribute("username");
	session.removeAttribute("userDetail");
%>

<script>window.top.location.href="../pages/index.jsp";</script>