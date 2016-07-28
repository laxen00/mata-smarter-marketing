package com.api.main;

import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONObject;

import com.db.lookup.Db2LookUp;
import com.web.config.ConfigProperties;

public class UserClass {
	
	static Connection con;
	String srcIp = "dev.summit.com.sg";
	String dbname = "TOYOTA";
	String dbuser = "toyotadashboard";
	String dbpass = "P@ssw0rd";
	static ConfigProperties config = new ConfigProperties("config.properties");
	public static KaskusClass kc;
//	public static TwitterClass tc;
	
	public static int login (String username, String password) throws Exception {
		int output = 0;
		JSONObject dbObj = config.getPropValues();
		username = username.toUpperCase();
		System.out.println("logging in.. : "+username);
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(dbObj.getString("hostname"), dbObj.getString("dbname"), dbObj.getString("dbuser"), dbObj.getString("dbpass"));
		con = dblookup.getConnected();
//		System.out.println("Database connected.");
		
		String q = "SELECT PASSWORDLOGIN FROM " + dbObj.getString("dbname") + "." + dbObj.getString("schema") + ".LOGINDETAIL WHERE USERNAMELOGIN='" + username + "'";
		ArrayList<ArrayList<String>> results = dblookup.executeSelect(q, con, false);

		for (ArrayList<String> result : results) {
			for (String data : result) {
				if (password.equals(data)) {
					output = 1;
					insertFirstTime(username);
					kc = new KaskusClass();
//					tc = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
					DataClass.setAlltoWaiting();
//					String kaskusUser = userlogin.getString("kaskusUser");
//					String kaskusPass = userlogin.getString("kaskusPass");
//					kc.loginInitKaskus(kaskusUser, kaskusPass);
					System.out.println("login succeded : "+username);
					break;
				}
				if (output == 1) break;
			}
		}
		return output;
	}
	
	public static boolean setSetting (String usernamelogin, String username, String password, String consumerKey
			, String consumerSecret, String oauthToken, String oauthSecret) throws Exception{
		System.out.println("save setting for user : "+usernamelogin);
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		dblookup.executeStatement("UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING SET "
				+ "USERNAME='"+username+"',PASSWORD='"+password+"',CONSUMERKEY='"+consumerKey+"',CONSUMERSECRET='"+consumerSecret+"',"
						+ "ACCESSTOKEN='"+oauthToken+"',ACCESSTOKENSECRET='"+oauthSecret+"' "
								+ "WHERE USERNAMELOGIN='"+usernamelogin+"'", con);
		dblookup.closeDB(con);
		return true;
	}
	
	public static JSONObject getSetting (String usernamelogin) throws Exception{
		System.out.println("get setting for user : "+usernamelogin);
		ArrayList<ArrayList<String>> userLoginDetails = new ArrayList<ArrayList<String>>(); 
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		userLoginDetails = dblookup.executeSelect("SELECT * FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING "
				+ "WHERE USERNAMELOGIN='"+usernamelogin+"'", con, false);
		dblookup.closeDB(con);
		
		JSONObject user = new JSONObject();
		String[] colNames = {"username","kaskusUser","kaskusPass","consumerKey",
							"consumerSecret","oauthToken","oauthSecret"
							};
		ArrayList<String> userLoginDetail = userLoginDetails.get(0);
		for (int i=0;i<userLoginDetail.size();i++) {
			user.put(colNames[i], userLoginDetail.get(i));
		}
		
		
		return user;
	}
	
	public static boolean insertFirstTime(String username)throws Exception{
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		ArrayList<ArrayList<String>> checkUser = new ArrayList<ArrayList<String>>(); 
		checkUser = dblookup.executeSelect("SELECT USERNAMELOGIN FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING WHERE USERNAMELOGIN='"+username+"'", con, false);
		if(checkUser.size()<1){
			System.out.println("setup user for first time");
			dblookup.executeStatement("INSERT INTO " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING VALUES ('"+username+"','N/A','N/A','N/A','N/A','N/A','N/A') ", con);
		}
		dblookup.closeDB(con);
		return true;
	}
	
}
