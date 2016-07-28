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
	String message = request.getParameter("message");
	String username = session.getAttribute("username").toString();
	boolean saveDraft = false;
	try {
		saveDraft = DataClass.setDraftMessage(id, message, username);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Save draft => " + saveDraft);
	out.println(saveDraft);
%>