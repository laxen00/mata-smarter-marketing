package com.api.tester;

import java.net.URLDecoder;
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
		
		String username = "administrator";
		String password = "password";
		JSONObject user = new JSONObject();
		
		username = username.toUpperCase();
		int result = UserClass.login(username, password);
		if (result == 1) {
			user = UserClass.getSetting(username);
		}
		else {
			System.out.println("error.");
		}
		
		String url = "https://twitter.com/listiarinirahay/status/758560987020603392";
//	 	String url = "https://twitter.com/ice1217/status/692197182150148096";
		String type = "Twitter";
		String id = "https://twitter.com/listiarinirahay/status/758560987020603392";
		url = URLDecoder.decode(url, "UTF-8");
		System.out.println(url);
		JSONArray arr = new JSONArray();
		try {
			arr = DataClass.getConversationJSON(id, url, type, user);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}