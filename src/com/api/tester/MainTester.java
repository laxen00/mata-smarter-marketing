package com.api.tester;

import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.Status;

import com.api.main.DataClass;
import com.api.main.TwitterClass;
import com.api.main.UserClass;
import com.db.lookup.Db2LookUp;
import com.web.config.ConfigProperties;

public class MainTester {
	
	
	
//	private static String consumerKey = "KoJ0Oe1kTapQaG616m01frERa";
//	private static String consumerSecret = "ORNNZ1rQcr6uRbjtqHAkvwAgognlgsErXckmYaS5LqxQsPiYEi";
//	private static String oauthToken = "4924349774-ehTKsDoocLOkLJzKyyIZON5AdzBvFRKIYjjsEQp";
//	private static String oauthSecret = "3QQUGWxZFmc0qCe8TG4Bi0nd3Hr4KhFPPCXCzRC3iGoqi";
	static ConfigProperties config = new ConfigProperties("config.properties");
	static Connection con;
	
	public static void main(String[] args) throws Exception {
//			TwitterClass twitter = new TwitterClass();
//			long id = TwitterClass.getStatusIdFromUrl("https://twitter.com/nahel101819/status/705945504899674112");
//			Status s = twitter.twitter.showStatus(id);
//			System.out.println(s);
//		JSONObject dbObj = config.getPropValues();
//		Db2LookUp dblookup = new Db2LookUp();
//		dblookup.setData(dbObj.getString("hostname"), dbObj.getString("dbname"), dbObj.getString("dbuser"), dbObj.getString("dbpass"));
//		con = dblookup.getConnected();
//		String q = "{" 
//				+ "\"searchFromDate\" : \"\","
//				+ "\"searchToDate\" : \"\","
//				+ "\"searchCategory\" : \"\","
//				+ "\"searchSource\" : \"\","
//				+ "\"searchStatus\" : \"\","
//				+ "\"searchSentiment\" : \"\""
//				+ "}";
//		JSONArray arr = DataClass.getList(1,q);
//		System.out.println(arr);
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
	}
	}