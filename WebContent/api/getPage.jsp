<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.DataClass,
org.json.JSONArray,
org.json.JSONObject,
java.net.URLDecoder
" %>
<%
	int pageNum = Integer.parseInt(request.getParameter("page"));
	String q = request.getParameter("q");
// 	System.out.println(q);
	q = URLDecoder.decode(q, "UTF-8");
	System.out.println(q);
	JSONArray arr = new JSONArray();
	try {
		arr = DataClass.getList(pageNum, q);
		if (arr == null) arr = new JSONArray();
	}
	catch (Exception e) {
		arr = new JSONArray();
		e.printStackTrace();
	}
	out.print(arr.toString());
%>