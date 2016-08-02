package com.api.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.DirectMessage;
import twitter4j.Relationship;
import twitter4j.Status;

import com.db.lookup.Db2LookUp;
import com.web.config.ConfigProperties;

public class DataClass {
//	static Connection con;
	String srcIp = "dev.summit.com.sg";
	String dbname = "TOYOTA";
	String dbuser = "toyotadashboard";
	String dbpass = "P@ssw0rd";
	static ConfigProperties config = new ConfigProperties("config.properties");
	
	private static final String UI_DATE_FORMAT = "MM/dd/yyyy";
	private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
	private static final String[] CATEGORY_NAMES = {"New Car Any Brand","New Car Any Toyota","New Car Toyota Model","New Car Toyota and Other Brand", "Dealer", "Spare Parts", "Service"};
	private static final String[] CATEGORY_CODES = {"CR-N1A","CR-N1B","CR-N1C","CR-N1D","CR-D","CR-S","CR-R"};
	private static final String[] STATUS_NAMES = {"Not Responded Yet","No Need To Reply","Ongoing","Closed Communication","Closed Communication By Phone","Shared To Dealer","Closed Dealer Communication (Buy)","Closed Dealer Communication (Not Buy)","Saved As Draft"};
	
	public static JSONArray getList(int page, String searchQuery) throws Exception {
		System.out.println("Get list page number : "+page);
		JSONArray arr = new JSONArray();
		JSONObject searchObj = new JSONObject(searchQuery);
		int rowPerPage = 50;
		int startingRow = ((page - 1) * rowPerPage) + 1;
		int endRow = startingRow + rowPerPage - 1;
//		System.out.println(page + "\t" + startingRow + "\t" + endRow);
		
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
//		System.out.println("Database connected.");
		
		String start = "SELECT p.* FROM " +
				 "( "
				 + "SELECT \"_ID\", \"BODY\", \"GENDER\", \"SCREENNAME\", \"NAME\","
				 + "\"DATE\", \"QUERY\", \"SCORE\", \"SOURCE\", \"STATUS\","
				 + "\"MESSAGE\", \"USERNAMELOGIN\", \"READSTATUS\", ROW_NUMBER() "
				 + "OVER (ORDER BY \"DATE\" DESC) AS row_num "
				 + "FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA ";
		
		String end = ") AS p "
				 + "WHERE p.row_num BETWEEN " + startingRow + " AND " + endRow;
		
		String searchEntries = buildSearchEntry(searchObj);

		String q = "";
//		System.out.println(searchEntries);
		if (searchEntries.length() > 0) {
			q = start + "WHERE " + searchEntries + end;
		}
		else {
			q = start + end;
		}
		
		System.out.println("Query: " + q);
		
		Statement stmt = null;
		ResultSet rset;
		stmt = con.createStatement();
		rset = stmt.executeQuery(q);
		ResultSetMetaData rsmd = rset.getMetaData();
		int maxColumn = rsmd.getColumnCount() - 1;
		int column = 1;		
		
		String[] colNames = {
				"id", "text_content", "criteriaid", "gender", "screenname", "name",
				"postingdate", "postingdatestr", "posturl", "statusurl", "query", "score", "source", "status",
				"message", "usernamelogin", "readstatus"
		};
		
		String id = "";
		String body = "";
		String gender = "";
		String screenname = "";
		String name = "";
		String date = "";
		String query = "";
		String score = "";
		String source = "";
		String status = "";
		String message = "";
		String usernamelogin = "";
		String readstatus = "";
		
		while (rset.next()){
			JSONObject obj = new JSONObject();
			for (int i = 1; i <= maxColumn; i++) {
				if (i == 1) { id = rset.getString(i); continue; }
				if (i == 2) { body = rset.getString(i); continue; }
				if (i == 3) { gender = rset.getString(i); continue; }
				if (i == 4) { screenname = rset.getString(i); continue; }
				if (i == 5) { name = rset.getString(i); continue; }
				if (i == 6) { date = rset.getString(i); continue; }
				if (i == 7) { query = rset.getString(i); continue; }
				if (i == 8) { score = rset.getString(i); continue; }
				if (i == 9) { source = rset.getString(i); continue; }
				if (i == 10) { status = rset.getString(i); continue; }
				if (i == 11) { message = rset.getString(i); continue; }
				if (i == 12) { usernamelogin = rset.getString(i); continue; }
				if (i == 13) { readstatus = rset.getString(i); continue; }
			}
			for (int j = 0; j < colNames.length; j++) {
				if (j == 0) { obj.put(colNames[j], id); continue; }
				if (j == 1) { obj.put(colNames[j], body); continue; }
				if (j == 2) { obj.put(colNames[j], ""); continue; }
				if (j == 3) { obj.put(colNames[j], gender); continue; }
				if (j == 4) { obj.put(colNames[j], screenname); continue; }
				if (j == 5) { obj.put(colNames[j], name); continue; }
				if (j == 6) { obj.put(colNames[j], date); continue; }
				if (j == 7) { obj.put(colNames[j], date); continue; }
				if (j == 8) { obj.put(colNames[j], id); continue; }
				if (j == 9) { obj.put(colNames[j], id); continue; }
				if (j == 10) { obj.put(colNames[j], query); continue; }
				if (j == 11) { obj.put(colNames[j], score); continue; }
				if (j == 12) { obj.put(colNames[j], source); continue; }
				if (j == 13) { obj.put(colNames[j], status); continue; }
				if (j == 14) { obj.put(colNames[j], message); continue; }
				if (j == 15) { obj.put(colNames[j], usernamelogin); continue; }
				if (j == 16) { obj.put(colNames[j], readstatus); continue; }
//				obj.put(colNames[column-1], rset.getString(column));
//				System.out.println(colNames[column-1] + " ===> " + rset.getString(column));
				column++;
			}
//			System.out.println(obj);
			column = 1;
			arr.put(obj);
		}
		rset.close();
//		stmt.close();
//		System.out.println("Result: " + arr);
		dblookup.closeDB(con);
		if (arr.length() < 1) return null;
		return arr;
	}
	
	public static int getTotalPage(String searchQuery) throws Exception {
		int totalPage = 0;
		int totalData = 0;
		int rowPerPage = 50;
		
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
//		System.out.println("Database connected.");
		
		String q = "SELECT COUNT(1) AS TOTALROW "
		 + "FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA";
		
		if (searchQuery.length() > 0) {
			JSONObject searchObj = new JSONObject(searchQuery);
			String searchEntries = buildSearchEntry(searchObj);
			if (searchEntries.length() > 0) {
				q = q + " WHERE " + searchEntries;
			}
		}
		
//		System.out.println("Query: " + q);
		Statement stmt = null;
		ResultSet rset;
		stmt = con.createStatement();
		rset = stmt.executeQuery(q);
		
		while (rset.next()){
			totalData = rset.getInt("TOTALROW");
		}
		rset.close();
//		stmt.close();
		
		if (totalData % rowPerPage == 0) totalPage = totalData / rowPerPage;
		else totalPage = (totalData / rowPerPage) + 1;
		
		if (totalPage == 0) totalPage = 1;
		dblookup.closeDB(con);
		return totalPage;
	}
	
	private static String buildSearchEntry(JSONObject searchObj) throws Exception {
		System.out.println("add filter to get list");
		String searchEntries = "";
		String searchFromDate = searchObj.getString("searchFromDate");
		String searchToDate = searchObj.getString("searchToDate");
		String searchCategory = searchObj.getString("searchCategory");
		String searchSource = searchObj.getString("searchSource");
		String searchStatus = searchObj.getString("searchStatus");
		String searchSentiment = searchObj.getString("searchSentiment");
//		String replied = searchObj.getString("replied");
		
		boolean addAND = false;
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jakarta"));
		if (!searchFromDate.equalsIgnoreCase("") && !searchToDate.equalsIgnoreCase("")) {
			SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_FORMAT);
			Date d = sdf.parse(searchFromDate);
//			c.setTime(d);
//			c.add(Calendar.DATE, -1);
//			d = c.getTime();
			sdf.applyPattern(SQL_DATE_FORMAT);
			searchFromDate = sdf.format(d);
			
			sdf = new SimpleDateFormat(UI_DATE_FORMAT);
			d = sdf.parse(searchToDate);
//			c.setTime(d);
//			c.add(Calendar.DATE, 1);
//			d = c.getTime();
			sdf.applyPattern(SQL_DATE_FORMAT);
			searchToDate = sdf.format(d);
			if (!searchCategory.equalsIgnoreCase("") || !searchSource.equalsIgnoreCase("") || !searchStatus.equalsIgnoreCase("")) {
				searchEntries = "(" + searchEntries + "POSTINGDATE BETWEEN '" + searchFromDate + "' AND '"+ searchToDate + "')";
			}
			else {
				searchEntries = searchEntries + "POSTINGDATE BETWEEN '" + searchFromDate + "' AND '"+ searchToDate + "' ";
			}
			
			addAND = true;
		}
		else {
			if (!searchFromDate.equalsIgnoreCase("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_FORMAT);
				Date d = sdf.parse(searchFromDate);
				c.setTime(d);
				c.add(Calendar.DATE, -1);
				d = c.getTime();
				sdf.applyPattern(SQL_DATE_FORMAT);
				searchFromDate = sdf.format(d);
				searchEntries = searchEntries + "POSTINGDATE > '" + searchFromDate + "' ";
				addAND = true;
			}
			if (!searchToDate.equalsIgnoreCase("")) {
				SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_FORMAT);
				Date d = sdf.parse(searchToDate);
				c.setTime(d);
				c.add(Calendar.DATE, 1);
				d = c.getTime();
				sdf.applyPattern(SQL_DATE_FORMAT);
				searchFromDate = sdf.format(d);
				if (addAND) searchEntries = searchEntries + " AND ";
				else addAND = true;
				searchEntries = searchEntries + "POSTINGDATE < '" + searchToDate + "' ";
			}
		}
		if (!searchCategory.equalsIgnoreCase("")) {
			if (addAND) searchEntries = searchEntries + " AND ";
			else addAND = true;
			ArrayList<String> categoryCodes = new ArrayList<String>();
			String[] categoryInput = searchCategory.split("\\|");
			for (int j=0;j<categoryInput.length;j++) {
				for (int i=0;i<CATEGORY_NAMES.length;i++) {
					if (categoryInput[j].equalsIgnoreCase(CATEGORY_NAMES[i])) {
						categoryCodes.add(CATEGORY_CODES[i]);
						break;
					}
				}
			}
			if (categoryInput.length > 1) searchEntries = searchEntries + "(";
			for (int k=0;k<categoryCodes.size();k++) {
				if (k == 0) searchEntries = searchEntries + "CRITERIAID2 LIKE '%" + categoryCodes.get(k) + "%' ";
				else if (k == (categoryCodes.size()-1)) searchEntries = searchEntries + "OR " + "CRITERIAID2 LIKE '%" + categoryCodes.get(k) + "%' ";
				else searchEntries = searchEntries + "OR " + "CRITERIAID2 LIKE '%" + categoryCodes.get(k) + "%' ";
			}
			if (categoryInput.length > 1) searchEntries = searchEntries + ")";
		}
		if (!searchSentiment.equalsIgnoreCase("")) {
			if (addAND) searchEntries = searchEntries + " AND ";
			else addAND = true;
			String[] sentimentInput = searchSentiment.split("\\|");
			if (sentimentInput.length > 1) searchEntries = searchEntries + "(";
			for (int k=0;k<sentimentInput.length;k++) {
				if (k == 0) searchEntries = searchEntries + "SENTIMENT_POLARITY LIKE '%" + sentimentInput[k] + "%' ";
				else if (k == (sentimentInput.length-1)) searchEntries = searchEntries + "OR " + "SENTIMENT_POLARITY LIKE '%" + sentimentInput[k] + "%' ";
				else searchEntries = searchEntries + "OR " + "SENTIMENT_POLARITY LIKE '%" + sentimentInput[k] + "%' ";
			}
			if (sentimentInput.length > 1) searchEntries = searchEntries + ")";
		}
		if (!searchSource.equalsIgnoreCase("")) {
			if (addAND) searchEntries = searchEntries + " AND ";
			else addAND = true;
			String[] sourceInput = searchSource.split("\\|");
			if (sourceInput.length > 1) searchEntries = searchEntries + "(";
			for (int k=0;k<sourceInput.length;k++) {
				if (k == 0) searchEntries = searchEntries + "SOURCE='" + sourceInput[k] + "' ";
				else if (k == (sourceInput.length-1)) searchEntries = searchEntries + "OR " + "SOURCE='" + sourceInput[k] + "' ";
				else searchEntries = searchEntries + "OR " + "SOURCE='" + sourceInput[k] + "' ";
			}
			if (sourceInput.length > 1) searchEntries = searchEntries + ")";
		}
		if (!searchStatus.equalsIgnoreCase("")) {
			if (addAND) searchEntries = searchEntries + " AND ";
			else addAND = true;
			String[] statusInput = searchStatus.split("\\|");
			if (statusInput.length > 1) searchEntries = searchEntries + "(";
			for (int k=0;k<statusInput.length;k++) {
				if (k == 0) searchEntries = searchEntries + "STATUS='" + statusInput[k] + "' ";
				else if (k == (statusInput.length-1)) searchEntries = searchEntries + "OR " + "STATUS='" + statusInput[k] + "' ";
				else searchEntries = searchEntries + "OR " + "STATUS='" + statusInput[k] + "' ";
			}
			if (statusInput.length > 1) searchEntries = searchEntries + ")";
		}
//		if (newArrived.equalsIgnoreCase("true")) {
//			if (addAND) searchEntries = searchEntries + " AND ";
//			else addAND = true;
//			SimpleDateFormat sdf = new SimpleDateFormat(UI_DATE_FORMAT);
//			Date now = new Date();
//			sdf.applyPattern(SQL_DATE_FORMAT);
//			String currentDate = sdf.format(now);
//			searchEntries = searchEntries + "POSTINGDATE='" + currentDate + "' ";
//		}
//		if (replied.equalsIgnoreCase("true")) {
//			if (addAND) searchEntries = searchEntries + " AND ";
//			else addAND = true;
//			searchEntries = searchEntries + "STATUS='REPLIED' ";
//		}
		return searchEntries;
	}
	
	public static JSONArray getConversationJSON (String postid, String url, String type, JSONObject userlogin) throws Exception {
		System.out.println("get conversation for type : "+type);
		JSONArray arr = new JSONArray();
		JSONObject confObj = config.getPropValues();
		if (type.equalsIgnoreCase("Twitter")) {
			long id = TwitterClass.getStatusIdFromUrl(url);
			TwitterClass twitter;
			List<Status> conv;
			try {
				twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
				conv = twitter.getReplyConversation(id);
				for (Status s : conv) {
					JSONObject obj = new JSONObject();
					obj.put("createdAt", s.getCreatedAt().toString());
					obj.put("screenname", s.getUser().getScreenName());
					obj.put("profileImageUrl", s.getUser().getProfileImageURL());
					obj.put("text", s.getText());
					obj.put("tweetId", s.getId());
					boolean retweeted = false;
					if (s.getCurrentUserRetweetId() != -1L) {
						retweeted = true;
					}
					System.out.println("Retweet check : " + s.getCurrentUserRetweetId() + " ==> " + retweeted);
					obj.put("isRetweeted", retweeted);
					obj.put("postUrl", "https://twitter.com/"+s.getUser().getScreenName()+"/status/"+s.getId());
					String notThisUser = "true";
					String thisUserScreenName = twitter.thisUser.getScreenName();
					if (s.getUser().getScreenName().equalsIgnoreCase(thisUserScreenName)) notThisUser = "false";
					obj.put("notThisUser", notThisUser);
					arr.put(obj);
				}
			}
			catch (Exception e) {
				Db2LookUp dblookup = new Db2LookUp();
				dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
				Connection con = dblookup.getConnected();
//				System.out.println("Database connected.");
				
				String q = "SELECT \"_ID\", \"BODY\", \"GENDER\", \"SCREENNAME\", \"NAME\","
							 + "\"DATE\", \"QUERY\", \"SCORE\", \"SOURCE\", \"STATUS\","
							 + "\"MESSAGE\", \"USERNAMELOGIN\", \"READSTATUS\" FROM "
							 + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
							 + "WHERE \"_ID\"='"+postid+"'";
				
//				System.out.println("Query: " + q);
				
				Statement stmt = null;
				ResultSet rset;
				stmt = con.createStatement();
				rset = stmt.executeQuery(q);
//				ResultSetMetaData rsmd = rset.getMetaData();	
				while(rset.next()) {
					JSONObject obj = new JSONObject();
					obj.put("createdAt", rset.getString("DATE"));
					obj.put("screenname", rset.getString("SCREENNAME"));
					obj.put("profileImageUrl", "../images/anonymous.png");
					obj.put("text", rset.getString("BODY"));
					obj.put("tweetId", id);
					obj.put("isRetweeted", "false");
					obj.put("postUrl", url);
					obj.put("notThisUser", "false");
					arr.put(obj);
				}
				dblookup.closeDB(con);
			}
		}
		else {
			ArrayList<String> conv = KaskusClass.getKaskusConv(postid);
			JSONObject obj = new JSONObject();
			obj.put("screenname", conv.get(0));
			obj.put("createdAt", conv.get(1));
			obj.put("text", conv.get(2));
			String profPicUrl;
			try {
				profPicUrl = KaskusClass.getKaskusPic(conv.get(0));
				if(profPicUrl.length()<2){
					profPicUrl = "../images/anonymous.png";
				}
			}
			catch (Exception e) {
				profPicUrl = "../images/anonymous.png";
			}
//			System.out.println("kaskus picture : "+profPicUrl);
			obj.put("profileImageUrl", profPicUrl);
			obj.put("tweetId", "");
			obj.put("isRetweeted", "false");
			obj.put("postUrl", url);
			obj.put("notThisUser", "true");
			arr.put(obj);
			try {
				String message = conv.get(3);
				if (!message.equalsIgnoreCase("null")) {
					obj = new JSONObject();
					obj.put("screenname", userlogin.getString("kaskusUser"));
					obj.put("createdAt", "");
					obj.put("text", message);
					String profPicUrl2;
					try {
						profPicUrl2 = KaskusClass.getKaskusPic(userlogin.getString("kaskusUser"));
//						System.out.println(profPicUrl2);
						if(profPicUrl2.length()<2){
							profPicUrl2 = "../images/anonymous.png";
						}
					}
					catch (Exception e) {
						profPicUrl2 = "../images/anonymous.png";
					}
					obj.put("profileImageUrl", profPicUrl2);
					obj.put("tweetId", "");
					obj.put("isRetweeted", "false");
					obj.put("postUrl", url);
					obj.put("notThisUser", "false");
					arr.put(obj);
				}
			}
			catch (Exception e)  {
				
			}
		}
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		String q = "UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA SET "
				+ "\"ALERT\"=0 "
				+ "WHERE \"_ID\"='"+postid+"'";
		System.out.println(q);
		dblookup.executeStatement(q, con);
		dblookup.closeDB(con);
		return arr;
	}
	
	public static JSONArray getDMJSON (String postid, String url, String type, JSONObject userlogin) throws Exception {
		System.out.println("get DM for type : "+type);
		JSONArray arr = new JSONArray();
//		JSONObject confObj = config.getPropValues();
		if (type.equalsIgnoreCase("Twitter")) {
			try {
				TwitterClass twitter;
				List<DirectMessage> conv;
				Status s;
				String screenname;
				long id = TwitterClass.getStatusIdFromUrl(url);
				twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
				s = twitter.twitter.showStatus(id);
				screenname = s.getUser().getScreenName();
				conv = twitter.getDMConversation(screenname);
				for (DirectMessage dm : conv) {
					JSONObject obj = new JSONObject();
					obj.put("createdAt", dm.getCreatedAt().toString());
					obj.put("screenname", dm.getSender().getScreenName());
					obj.put("profileImageUrl", dm.getSender().getProfileImageURL());
					obj.put("text", dm.getText());
					arr.put(obj);
				}
			}
			catch (Exception e) {
				JSONObject obj = new JSONObject();
				obj.put("createdAt", "");
				obj.put("screenname", "");
				obj.put("profileImageUrl", "");
				obj.put("text", "Problem in retrieving direct messages, please try again.");
				arr.put(obj);
				e.printStackTrace();
			}
		}
		else {
			List<String> conv_ = KaskusClass.getKaskusConv(postid);
			String userFromTo = "ice1217";
			try{
				userFromTo = conv_.get(0);
			}
			catch(Exception e){
				
			}
			System.out.println("get dm conversation with : "+userFromTo);
			List<String> conv = UserClass.kc.getInboxOutbox(userFromTo, userlogin.getString("kaskusUser"), userlogin.getString("kaskusPass"));
			for (String con : conv) {
				JSONObject obj = new JSONObject();
//				System.out.println(con);
				String[] pm = con.split("\n");
				obj.put("createdAt", pm[0]);
				obj.put("screenname", pm[2]);
				String profPicUrl;
				try {
					profPicUrl = KaskusClass.getKaskusPic(pm[2]);
					if(profPicUrl.length()<2){
						profPicUrl = "../images/anonymous.png";
					}
				}
				catch (Exception e) {
					profPicUrl = "../images/anonymous.png";
				}
				obj.put("profileImageUrl", profPicUrl);
				obj.put("text", pm[4]);
				arr.put(obj);
			}
		}
		return arr;
	}
	
	
	
	public static boolean setDraftMessage(String id, String message, String usernameLogin) throws Exception{
		System.out.println("save to draft as user: "+usernameLogin);
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		String idParse = id;
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		dblookup.executeStatement("UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA SET "
				+ "STATUS='Saved As Draft', MESSAGE='"+message+"', USERNAMELOGIN='"+usernameLogin+"', READSTATUS='READ' "
						+ "WHERE \"_ID\"='"+idParse+"'" , con);
		dblookup.closeDB(con);
		return true;
	}
	
	public static String getDraftMessage(String id) throws Exception{
		System.out.println("get draft message id : "+id);
		String message = "";
		int replied = checkIfDrafted(id);
		System.out.println("responded status : "+replied);
		if (replied == 1) {
			ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
			JSONObject confObj = config.getPropValues();
			Db2LookUp dblookup = new Db2LookUp();
			String idParse = id;
			dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
			Connection con = dblookup.getConnected();
			convs = dblookup.executeSelect("SELECT \"_ID\", MESSAGE FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
					+ "WHERE \"_ID\"='"+idParse+"'", con, false);
			dblookup.closeDB(con);
			for(ArrayList<String> conv : convs){
				message = conv.get(1);
			}				
		}
		return message;
	}
	
	public static boolean sendReply(String url, String type, String id, JSONObject userlogin, String message, String mediaPath) throws Exception {
		System.out.println("send reply to url : "+url);
		boolean out = false;
		boolean reply = false;
//		JSONObject confObj = config.getPropValues();
		if (type.equalsIgnoreCase("twitter")) {
			TwitterClass twitter;
			twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
			if (mediaPath.equalsIgnoreCase("") || mediaPath.length() < 1 || mediaPath == null || mediaPath.equalsIgnoreCase("null")) {
				reply = twitter.twitterReply(url, message);
			}
			else {
				reply = twitter.twitterReplyWithMedia(url, message, mediaPath);
			}
		}
		else {
			String title = "\"REPLY\"";
			reply = UserClass.kc.postReply(url, title, message, userlogin.getString("kaskusUser"), userlogin.getString("kaskusPass"));
		}
		if (reply) {
			out = doneReply(id, message, userlogin.getString("username"));
		}
		return out;
	}
	
	public static boolean sendDM(String id, String type, String url, String message, JSONObject userlogin) throws Exception {
		System.out.println("send DM to url : "+url);
		boolean out = false;
		JSONObject confObj = config.getPropValues();
		if (type.equalsIgnoreCase("twitter")) {
				TwitterClass twitter;
				twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
				out = twitter.twitterDM(url, message);
		}
		else {
			Db2LookUp dblookup = new Db2LookUp();
			if(id.length()<2){
				String screenname = "ice1217";
				out = UserClass.kc.sendDM(userlogin.getString("kaskusUser"), userlogin.getString("kaskusPass"), message, screenname);
			}
			else{
				String idParse = id;
				dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
				String q = "SELECT SCREENNAME "
						 + "FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
						 + "WHERE \"_ID\"='"+idParse+"'";
				Connection con = dblookup.getConnected();
				Statement stmt = null;
				ResultSet rset;
				stmt = con.createStatement();
				rset = stmt.executeQuery(q);
//				ResultSetMetaData rsmd = rset.getMetaData();
//				int maxColumn = rsmd.getColumnCount() - 1;
//				int column = 1;
				String screenname = "";
				while(rset.next()) {
					screenname = rset.getString("SCREENNAME");
				}
				dblookup.closeDB(con);
				out = UserClass.kc.sendDM(userlogin.getString("kaskusUser"), userlogin.getString("kaskusPass"), message, screenname);
			}
		}
		return out;
	}
	
	public static boolean doneReply(String id, String message, String usernameLogin) throws Exception {
		System.out.println("setup db after replying for id : "+id);
		long millis = System.currentTimeMillis();
		
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		String idParse = id;
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		int currentReply = setReplytoCurrent(dblookup, con, idParse);
		dblookup.executeStatement("UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA SET "
				+ "STATUS='Ongoing', MESSAGE='"+message+"', USERNAMELOGIN='"+usernameLogin+"', READSTATUS='READ', \"ALERT\"=0, UPDATEDATE="+millis+", \"REPLY\"="+currentReply+" "
						+ "WHERE \"_ID\"='"+idParse+"'" , con);
		dblookup.closeDB(con);
		return true;
	}
	
	private static int setReplytoCurrent(Db2LookUp dblookup, Connection con, String idParse) throws Exception{
		JSONObject confObj = config.getPropValues();
		ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
		convs = dblookup.executeSelect("SELECT \"REPLY\" FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
				+ "WHERE \"_ID\"='"+idParse+"'", con, false);
		for(ArrayList<String> conv : convs){
			String out = conv.get(0);
			try{
				return Integer.parseInt(out) + 1;
			}
			catch (Exception e){
				
			}
			
		}
		return 1;
	}
	
	public static boolean sendRetweet(String postUrl, JSONObject userlogin) throws Exception {
		System.out.println("sending retweet for url : "+postUrl);
		boolean out = false;
//		JSONObject confObj = config.getPropValues();
		TwitterClass twitter;
		twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
		out = twitter.twitterRetweet(postUrl);
		return out;
	}
	
	public static boolean changeStatus(int num, String id, String username) throws Exception {
		System.out.println("change status for id : "+id+" set as : "+STATUS_NAMES[num]);
		boolean out = false;
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		String idParse = id;
		String status = STATUS_NAMES[num];
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		try {
			String q = "UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA SET "
					+ "STATUS='"+status+"' "
					+ "WHERE \"_ID\"='"+idParse+"'";
//			System.out.println(q);
			dblookup.executeStatement(q, con);
			dblookup.closeDB(con);
			out = true;
		}
		catch (Exception e) {
			out = false;
			dblookup.closeDB(con);
			e.printStackTrace();
		}
		return out;
	}
	
	public static void setAlltoWaiting() throws Exception{
		ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
//		String idParse = id;
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		convs = dblookup.executeSelect("SELECT COUNT(1) FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
				+ "WHERE STATUS IS NULL", con, false);
		for(ArrayList<String> conv : convs) {
//			System.out.println(conv);
			int total = Integer.parseInt(conv.get(0));
			if (total>0){
				System.out.println("null status found, set all to Not Responded Yet");
				dblookup.executeStatement("UPDATE "+ confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
						+ "SET STATUS='Not Responded Yet' WHERE STATUS IS NULL", con);
			}
			break;
		}
		dblookup.closeDB(con);
	}
	
	public static int checkFollowingTwitter(String url, JSONObject userlogin) throws Exception {
		System.out.println("check if user following status");
		int out = 0;
		long id = TwitterClass.getStatusIdFromUrl(url);
//		System.out.println(id);
		TwitterClass twitter = new TwitterClass(userlogin.getString("consumerKey"),userlogin.getString("consumerSecret"),userlogin.getString("oauthToken"),userlogin.getString("oauthSecret"));
		Status s = twitter.twitter.showStatus(id);
		String user = s.getUser().getScreenName();
		Relationship relationship = twitter.twitter.showFriendship(user,twitter.thisUser.getScreenName());
//		System.out.println(relationship);
		boolean following = relationship.isSourceFollowingTarget();
//		System.out.println("following? " + following);
		if (following) out = 1;
		return out;
	}
	
	public static ArrayList<Integer> listCurrentStatusDocs() throws Exception{
		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> dataList = new ArrayList<Integer>();
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
//		String idParse = id;
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		
		String q = "SELECT sum(case when STATUS='Not Responded Yet' then 1 end) as tNRY, "
				+ "sum(case when STATUS='No Need To Reply' then 1 end) as tSpam, "
				+ "sum(case when STATUS='Ongoing' then 1 end) as tOngoing, "
				+ "sum(case when STATUS='Closed Communication' then 1 end) as tClosed, "
				+ "sum(case when STATUS='Closed Communication By Phone' then 1 end) as tClosedPhone, "
				+ "sum(case when STATUS='Shared To Dealer' then 1 end) as tShared, "
				+ "sum(case when STATUS='Closed Dealer Communication (Buy)' then 1 end) as tBuy, "
				+ "sum(case when STATUS='Closed Dealer Communication (Not Buy)' then 1 end) as tNotBuy, "
				+ "sum(case when STATUS='Saved As Draft' then 1 end) as tSAD "
				+ "FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA";
		System.out.println("Query: " + q);
		lists = dblookup.executeSelect(q, con, false);
		
		for(ArrayList<String> list : lists){
			for(String data : list){
				if(data==null){
					data = "0";
				}		
				dataList.add(Integer.valueOf(data));
			}
		}
		
		dblookup.closeDB(con);
		return dataList;
	}
	
	private static int checkIfDrafted (String id) throws Exception {
		int drafted = 0;
		Db2LookUp dblookup = new Db2LookUp();
		Connection con = dblookup.getConnected();
		JSONObject confObj = config.getPropValues();
		ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
		try {
			convs = dblookup.executeSelect("SELECT STATUS FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
					+ "WHERE \"_ID\"='"+id+"'", con, false);
			for(ArrayList<String> conv : convs){
				String status = conv.get(0);
				if (status.equalsIgnoreCase("Saved As Draft")) drafted = 1;
			}
		} catch (Exception e) {
			
		}
		dblookup.closeDB(con);
		return drafted;
	}
	
	public static JSONArray checkNotif() throws Exception {
		JSONArray arr = new JSONArray();
		Db2LookUp dblookup = new Db2LookUp();
		Connection con = dblookup.getConnected();
		JSONObject confObj = config.getPropValues();
		ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>();
		try {
			String q = "SELECT \"_ID\", \"BODY\", \"GENDER\", \"SCREENNAME\", \"NAME\","
					 + "\"DATE\", \"QUERY\", \"SCORE\", \"SOURCE\", \"STATUS\","
					 + "\"MESSAGE\", \"USERNAMELOGIN\", \"READSTATUS\" FROM "
					 + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA "
					 + "WHERE \"\"ALERT\"\"=1 ORDER BY \"DATE\" DESC";
			results = dblookup.executeSelect(q, con, false);
//			System.out.println(q);
			String[] colNames = {
					"id", "text_content", "criteriaid", "gender", "screenname", "name",
					"postingdate", "postingdatestr", "posturl", "statusurl", "query", "score", "source", "status",
					"message", "usernamelogin", "readstatus"
			};
			
			String id = "";
			String body = "";
			String gender = "";
			String screenname = "";
			String name = "";
			String date = "";
			String query = "";
			String score = "";
			String source = "";
			String status = "";
			String message = "";
			String usernamelogin = "";
			String readstatus = "";
			
			for (ArrayList<String> result : results) {
				JSONObject obj = new JSONObject();
				for (int i = 0; i < 13; i++) {
					if (i == 0) { id = result.get(i); continue; }
					if (i == 1) { body = result.get(i); continue; }
					if (i == 2) { gender = result.get(i);; continue; }
					if (i == 3) { screenname = result.get(i); continue; }
					if (i == 4) { name = result.get(i);; continue; }
					if (i == 5) { date = result.get(i); continue; }
					if (i == 6) { query = result.get(i); continue; }
					if (i == 7) { score = result.get(i); continue; }
					if (i == 8) { source = result.get(i); continue; }
					if (i == 9) { status = result.get(i); continue; }
					if (i == 10) { message = result.get(i); continue; }
					if (i == 11) { usernamelogin = result.get(i); continue; }
					if (i == 12) { readstatus = result.get(i);; continue; }
				}
				
				for (int j = 0; j < colNames.length; j++) {
					if (j == 0) { obj.put(colNames[j], id); continue; }
					if (j == 1) { obj.put(colNames[j], body); continue; }
					if (j == 2) { obj.put(colNames[j], ""); continue; }
					if (j == 3) { obj.put(colNames[j], gender); continue; }
					if (j == 4) { obj.put(colNames[j], screenname); continue; }
					if (j == 5) { obj.put(colNames[j], name); continue; }
					if (j == 6) { obj.put(colNames[j], date); continue; }
					if (j == 7) { obj.put(colNames[j], date); continue; }
					if (j == 8) { obj.put(colNames[j], id); continue; }
					if (j == 9) { obj.put(colNames[j], id); continue; }
					if (j == 10) { obj.put(colNames[j], query); continue; }
					if (j == 11) { obj.put(colNames[j], score); continue; }
					if (j == 12) { obj.put(colNames[j], source); continue; }
					if (j == 13) { obj.put(colNames[j], status); continue; }
					if (j == 14) { obj.put(colNames[j], message); continue; }
					if (j == 15) { obj.put(colNames[j], usernamelogin); continue; }
					if (j == 16) { obj.put(colNames[j], readstatus); continue; }
//					obj.put(colNames[column-1], rset.getString(column));
//					System.out.println(colNames[column-1] + " ===> " + rset.getString(column));
//					column++;
				}
				
//				for (int y = 0 ; y < result.size() ; y++){
//					obj.put(colNames[y], result.get(y));
//					System.out.println(colNames[y] + " ===> " + result.get(y));
//				}
				System.out.println(obj);
				arr.put(obj);
			}
		} catch (Exception e) {
			
		}
		return arr;
	}
	
	public static int validateTwitterKaskus (int type, JSONObject obj) {
		int out = 0;
		if (type == 0) {
			boolean twitterValidate = TwitterClass.validateTwitter(obj);
			if (twitterValidate) out = 1;
		}
		else {
			boolean kaskusValidate = UserClass.kc.validateKaskus(obj.getString("kaskusUser"), obj.getString("kaskusPass"));
			if (kaskusValidate) out = 1;
		}
		return out;
	}
	
	public String putToWebServer(InputStream input, String filename) throws Exception{
		
		File uploadedFolder = new File("C:\\WWWRoot\\images\\uploaded\\");
		if(!uploadedFolder.exists()){
			uploadedFolder.mkdirs();
		}
		String webPath = "http://web.summit.com.sg:82/images/uploaded/"+filename+"";
		
		FileOutputStream outputStream = new FileOutputStream(new File("C:\\WWWRoot\\images\\uploaded\\"+filename+""));
		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = input.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
		outputStream.close();
		input.close();
		
		return webPath;
	}
	
//	public static boolean changeSettingUser(String username, String usernameKaskus, String passwordKaskus, String consumerKey, 
//			String consumerSecret, String accessToken, String accessTokenSecret) throws Exception{
//		
//		String defaultUsernameKaskus = "";
//		String defaultPasswordKaskus = "";
//		String defaultConsumerKey = "";
//		String defaultConsumerSecret = "";
//		String defaultAccessToken = "";
//		String defaultAccessTokenSecret = "";
//		
//		ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
//		JSONObject confObj = config.getPropValues();
//		Db2LookUp dblookup = new Db2LookUp();
////		String idParse = id;
//		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
//		Connection con = dblookup.getConnected();
//		lists = dblookup.executeSelect("SELECT USERNAMELOGIN, USERNAME, PASSWORD, CONSUMERKEY, CONSUMERSECRET, ACCESSTOKEN, ACCESSTOKENSECRET "
//				+ "FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING "
//				+ "WHERE USERNAMELOGIN='"+username+"'", con, false);
//				
//		for(ArrayList<String> list : lists){
//			defaultUsernameKaskus = list.get(0);
//			defaultPasswordKaskus = list.get(1);
//			defaultConsumerKey = list.get(2);
//			defaultConsumerSecret = list.get(3);
//			defaultAccessToken = list.get(4);
//			defaultAccessTokenSecret = list.get(5);
//		}
//		
//		if(usernameKaskus.length()>1){
//			defaultUsernameKaskus = usernameKaskus;
//		}
//		if(passwordKaskus.length()>1){
//			defaultPasswordKaskus = passwordKaskus;
//		}
//		if(consumerKey.length()>1){
//			defaultConsumerKey = consumerKey;
//		}
//		if(consumerSecret.length()>1){
//			defaultConsumerSecret = consumerSecret;
//		}
//		if(accessToken.length()>1){
//			defaultAccessToken = accessToken;
//		}
//		if(accessTokenSecret.length()>1){
//			defaultAccessTokenSecret = accessTokenSecret;
//		}
//		
//		dblookup.executeStatement("UPDATE " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".LOGINSETTING "
//				+ "SET USERNAME='"+defaultUsernameKaskus+"', PASSWORD='"+defaultPasswordKaskus+"', "
//				+ "CONSUMERKEY='"+defaultConsumerKey+"', CONSUMERSECRET='"+defaultConsumerSecret+"', "
//				+ "ACCESSTOKEN='"+defaultAccessToken+"', ACCESSTOKENSECRET='"+defaultAccessTokenSecret+"' "
//				+ "WHERE USERNAMELOGIN='"+username+"'",con);
//		
//		return true;
//	}
	
}
