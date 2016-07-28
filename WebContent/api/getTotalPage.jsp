<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.DataClass,
org.json.JSONArray,
org.json.JSONObject,
java.net.URLDecoder
" %>
<%
	String q = request.getParameter("q");
// 	System.out.println(q);
	q = URLDecoder.decode(q, "UTF-8");
	System.out.println(q);
	int totalPage = 0;
	try {
		totalPage = DataClass.getTotalPage(q);
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Total page => " + totalPage);
	out.print(totalPage);
%>