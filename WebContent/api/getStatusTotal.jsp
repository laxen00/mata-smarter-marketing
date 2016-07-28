<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
com.api.main.DataClass,
org.json.JSONArray,
org.json.JSONObject,
org.apache.commons.lang3.StringEscapeUtils,
java.net.URLDecoder,
java.util.ArrayList
" %>
<%
	ArrayList<Integer> totalArray = new ArrayList<Integer>();
	JSONObject obj = new JSONObject();
	String[] names = {"tNRY","tNR","tO","tCC","tCCP","tSD","tCDB","tCDNB","tSAD"};
	try {
		totalArray = DataClass.listCurrentStatusDocs();
		for (int i=0;i<totalArray.size();i++) {
			obj.put(names[i], totalArray.get(i));
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	System.out.println("Total => " + obj.toString());
	out.println(obj.toString());
%>