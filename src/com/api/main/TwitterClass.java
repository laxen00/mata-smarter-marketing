package com.api.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;

import com.web.config.ConfigProperties;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClass {
	
	private String consumerKey = "KoJ0Oe1kTapQaG616m01frERa";
	private String consumerSecret = "ORNNZ1rQcr6uRbjtqHAkvwAgognlgsErXckmYaS5LqxQsPiYEi";
	private String oauthToken = "4924349774-ehTKsDoocLOkLJzKyyIZON5AdzBvFRKIYjjsEQp";
	private String oauthSecret = "3QQUGWxZFmc0qCe8TG4Bi0nd3Hr4KhFPPCXCzRC3iGoqi";
	private ConfigurationBuilder twitterConfigBuilder = new ConfigurationBuilder();
	public Twitter twitter;
	private ConfigProperties config = new ConfigProperties("config.properties");
	
	private List<Status> conversations;
//	private List<String> conversations2 = null;
	private User user1;
	public User thisUser;
	
	public TwitterClass() throws Exception {
		JSONObject confObj = config.getPropValues();
		consumerKey = confObj.getString("consumerKey");
		consumerSecret = confObj.getString("consumerSecret");
		oauthToken = confObj.getString("oauthToken");
		oauthSecret = confObj.getString("oauthSecret");
		
	   	twitterConfigBuilder.setDebugEnabled(true);
	    twitterConfigBuilder.setOAuthConsumerKey(consumerKey);
	    twitterConfigBuilder.setOAuthConsumerSecret(consumerSecret);
	    twitterConfigBuilder.setOAuthAccessToken(oauthToken);
	    twitterConfigBuilder.setOAuthAccessTokenSecret(oauthSecret);

	    twitter = new TwitterFactory(twitterConfigBuilder.build()).getInstance();
	    thisUser = twitter.showUser(twitter.getId());
	}
	
	public TwitterClass (String consumerKey, String consumerSecret, String oauthToken, String oauthSecret) throws Exception {
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
		this.oauthToken = oauthToken;
		this.oauthSecret = oauthSecret;
		
	   	twitterConfigBuilder.setDebugEnabled(true);
	    twitterConfigBuilder.setOAuthConsumerKey(consumerKey);
	    twitterConfigBuilder.setOAuthConsumerSecret(consumerSecret);
	    twitterConfigBuilder.setOAuthAccessToken(oauthToken);
	    twitterConfigBuilder.setOAuthAccessTokenSecret(oauthSecret);

	    twitter = new TwitterFactory(twitterConfigBuilder.build()).getInstance();
	    thisUser = twitter.showUser(twitter.getId());
	}
	
	private static final Comparator<DirectMessage> DM_ORDER_BY_DATE = new Comparator<DirectMessage>() {
		public int compare(DirectMessage d1, DirectMessage d2) {
			return d1.getCreatedAt().compareTo(d2.getCreatedAt());
		}
	};
	
	private static final Comparator<Status> STATUS_ORDER_BY_DATE = new Comparator<Status>() {
		public int compare(Status s1, Status s2) {
			return s1.getCreatedAt().compareTo(s2.getCreatedAt());
		}
	};
	
	public List<DirectMessage> getDMConversation (String userString) throws Exception {
		List<DirectMessage> dmAll = twitter.getDirectMessages();
		System.out.println("get DM conversation");
		dmAll.clear();
		User user = twitter.showUser(userString);
		
//		Paging page = new Paging(1, 200);
		List<DirectMessage> dmReceived = twitter.getDirectMessages();
		List<DirectMessage> dmSent = twitter.getSentDirectMessages(); 
//		List<DirectMessage> dmReceived2 = twitter.getDirectMessages();
		
		for (DirectMessage dm : dmReceived)  if (dm.getSender().equals(user))  dmAll.add(dm);
		for (DirectMessage dm : dmSent)  if (dm.getRecipient().equals(user))  dmAll.add(dm);
		
		Collections.sort(dmAll, DM_ORDER_BY_DATE);
		
		for (DirectMessage dm : dmAll) {
			System.out.println(dm.getSenderScreenName() + " --> " + dm.getText());
		}
		
		return dmAll;
	}
	
//	public List<Status> getReplyConversation (long id) throws Exception {
//		Status startingStatus = twitter.showStatus(id);
//		
//		conversations = twitter.getHomeTimeline(new Paging(1,5));
//		conversations.clear();
//		
//		user1 = startingStatus.getUser();
//		conversations.add(startingStatus);
//		convCount = 1;
//		buildReplyConversation(id, startingStatus.getUser());
//		return conversations;
//	}
	
	public List<Status> getReplyConversation (long id) throws Exception {
		System.out.println("get Twitter conversation for id : "+id);
		Status startingStatus = twitter.showStatus(id);
		
		conversations = twitter.getHomeTimeline(new Paging(1,5));
		conversations.clear();
		
		user1 = startingStatus.getUser();
		conversations.add(startingStatus);
		searchRelatedTweets(user1.getScreenName(), id);
//		buildReplyConversation(id, startingStatus.getUser());
		return conversations;
	}
	
//	public void buildReplyConversation (long id, User user) throws Exception {
//		Query q = new Query("@" + user.getScreenName());
////		q.setSince("2015-01-01");
////		System.out.println(q);
//		QueryResult results = twitter.search(q);
////		System.out.println(results);
//		List<Status> mentionsAll = results.getTweets();
//		for (Status status : mentionsAll) {
////			System.out.println(status.getText());
//			if (user.getId() == user1.getId()) {
//				if (status.getInReplyToStatusId() == id && status.getUser().getId() == thisUser.getId()) {
//					conversations.add(status);
//					convCount++;
//					if (convCount > 10) break;
//					buildReplyConversation(status.getId(), status.getUser());
//					break;
//				}
//			}
//			else {
//				if (status.getInReplyToStatusId() == id && status.getUser().getId() == user1.getId()) {
//					conversations.add(status);
//					convCount++;
//					if (convCount > 10) break;
//					buildReplyConversation(status.getId(), status.getUser());
//					break;
//				}
//			}
//		}
//	}
	
	public static long getStatusIdFromUrl (String url) {
		String[] temp = url.split("/");
		String stringId = temp[temp.length-1];
		long id = Long.parseLong(stringId);
		return id;
	}
	
	public boolean twitterReply(String url, String message) throws Exception {
		System.out.println("send reply to url : "+url);
		boolean out = false;
		long id = getStatusIdFromUrl(url);
		Status statusToReply = twitter.showStatus(id);
		User userToReply = statusToReply.getUser();
		String prefix = "@" + userToReply.getScreenName();
		if (!message.startsWith(prefix)) message = prefix + " " + message;
		StatusUpdate reply = new StatusUpdate(message);
		reply.setInReplyToStatusId(id);
		try {
			twitter.updateStatus(reply);
			out = true;
		}
		catch (Exception e) {
			out = false;
			e.printStackTrace();
		}
		return out;
	}
	
	public boolean twitterReplyWithMedia(String url, String message, String mediaPath) throws Exception {
		System.out.println("send reply to url : "+url);
		boolean out = false;
		mediaPath = mediaPath.trim();
		long id = getStatusIdFromUrl(url);
		Status statusToReply = twitter.showStatus(id);
		User userToReply = statusToReply.getUser();
		String prefix = "@" + userToReply.getScreenName();
		if (!message.startsWith(prefix)) message = prefix + " " + message;
		StatusUpdate reply = new StatusUpdate(message);
		File media = new File(mediaPath);
		reply.setInReplyToStatusId(id);
		reply.setMedia(media);
		try {
			twitter.updateStatus(reply);
			out = true;
		}
		catch (Exception e) {
			out = false;
			e.printStackTrace();
		}
		return out;
	}
	
	public boolean twitterDM(String url, String message) throws Exception {
		System.out.println("send DM to url : "+url);
		boolean out = false;
		long id = getStatusIdFromUrl(url);
		Status s = twitter.showStatus(id);
		String screenname = s.getUser().getScreenName();
		try {
			twitter.sendDirectMessage(screenname, message);
			out = true;
		}
		catch (Exception e) {
			out = false;
			e.printStackTrace();
		}
		return out;
	}
	
	public boolean twitterRetweet(String postUrl) throws Exception {
		boolean out = false;
		System.out.println("Rate Limit => " + twitter.getRateLimitStatus().toString());
		long id = getStatusIdFromUrl(postUrl);
		Status s = twitter.showStatus(id);
		boolean retweeted = false;
		if (s.getCurrentUserRetweetId() != -1L) {
			retweeted = true;
		}
		System.out.println("check status if already retweeted : "+s.getCurrentUserRetweetId()+ " ==> " + retweeted);
		if (retweeted) {
			  List<Status> retweets = twitter.getRetweets(id);
		        for (Status retweet : retweets) {
//					System.out.println("A: " + retweet.getText());
//					System.out.println("B: " + retweet.getRetweetedStatus().getText());
		            if (retweet.getRetweetedStatus().getId() == id) {
		                twitter.destroyStatus(retweet.getId());
		            }
		        }
			out = true;
		}
		else {
			try {
				twitter.retweetStatus(id);
				out = true;
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				out = false;
				e.printStackTrace();
			}
		}
		System.out.println("Rate Limit => " + twitter.getRateLimitStatus().toString());
		return out;
	}
	
	private List<Status> getRelatedTweet(String screennames, long statusIds){
		System.out.println("get all conversation for user: "+screennames+" (this may takes a while)");
		int pageNumber = 1;
		int maxPageNumber = 1;
		
		String[] parties = {screennames, thisUser.getScreenName()};
		
		for(String party : parties){
			List<Status> statuses = null;
			List<Long> ids = new ArrayList<Long>();
			int lastStatusCount = 0;
			
			Paging page = new Paging (pageNumber, 200);
			page.setSinceId(statusIds);
			
			for(int j = pageNumber; j <= maxPageNumber; j++){
				page.setPage(j);
				
				try {
					statuses = twitter.getUserTimeline(party, page);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
				
				if(statuses != null && statuses.size() > 0){
					for(Status status : statuses){
						if(!ids.contains(status.getId())){
							ids.add(status.getId());
						}
						
						if(status.getInReplyToStatusId() == statusIds){
							getRelatedTweet(status.getUser().getScreenName(), 
									status.getId());
							
							Calendar c = Calendar.getInstance();
							c.setTime(status.getCreatedAt());						    
						    conversations.add(status);
						}
					}
					
					if(ids.size() <= lastStatusCount){
						break;
					}else{
						lastStatusCount = ids.size();
					}
				}else{
					break;
				}
			}
		}
		
		Collections.sort(conversations, STATUS_ORDER_BY_DATE);
		return conversations;
	}
	
	public List<Status> searchRelatedTweets(String screenname, long statusId){		
		getRelatedTweet(screenname,statusId);	
		return conversations;
	}
	
	public static boolean validateTwitter(JSONObject obj) {
		boolean out = false;
		try {
			TwitterClass twitter2 = new TwitterClass(
					obj.getString("consumerKey"),
					obj.getString("consumerSecret"),
					obj.getString("oauthToken"),
					obj.getString("oauthSecret"));
			System.out.println(twitter2.thisUser.getScreenName());
			out = true;
		}
		catch (Exception e) {
			out = false;
			e.printStackTrace();
		}
		return out;
	}
}
