package com.api.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.db.lookup.Db2LookUp;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.utils.FileManager;
import com.web.config.ConfigProperties;

public class KaskusClass {
	
	static WebClient webClient = null;
	static ConfigProperties config = new ConfigProperties("config.properties");
	public static ArrayList<String> listProxy = null;
	public FileManager fm = new FileManager();
	private HtmlPage page = null;
	
	public KaskusClass(){
		listProxy = new ArrayList<String>();
		String[] splitProxyFile = fm.readData("c:/TOYOTA/proxylist.txt").split("\r\n");
		for(String proxyFile : splitProxyFile){
			listProxy.add(proxyFile);
		}	
		
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
	    java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
	    java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
	    
		String bestProxy = "";
		System.out.println("list proxys : "+listProxy);
		for(String proxys : listProxy){
			bestProxy = proxys;
			System.out.println("checking proxy :"+bestProxy+"");
			String proxyIp = bestProxy.split(":")[0];
			int proxyPort = Integer.parseInt(bestProxy.split(":")[1]);
			initWebClient(proxyIp, proxyPort);
			System.out.print("cheking compabilty proxy");
			try {
				webClient.getOptions().setJavaScriptEnabled(false);
				page = webClient.getPage("https://www.kaskus.co.id/user/login");
				System.out.println("proxy compatible!");
				break;
			} catch (FailingHttpStatusCodeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				closeWebClient();
			}
		}
	}
	
	public boolean postReply(String postUrl, String title, String message, String username, String password) throws Exception{
		
		if(!checkCookies(username)){
			createCookies(username, password);
		}		
		
		boolean isSucceeded = false;
		String threadId = postUrl.split("/")[4];
		String postId = postUrl.split("#")[1].replace("post", "");
		webClient.getOptions().setJavaScriptEnabled(false);
		
		try {
			page = webClient.getPage(
					"http://www.kaskus.co.id/post_reply/" + threadId + "/?post=" +
					postId);
//			System.out.println(page.asText());
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(page != null){
			HtmlForm form = page.getFormByName("postreply");
			try {
				form.getInputByName("title").type(title);
				
				String initMessage = page.getElementById("reply-messsage").
						getTextContent();
				
				message = initMessage + "\n" + message;
				page.getElementById("reply-messsage").setTextContent(message);;
				
				page = form.getInputByValue("Post").click();
//				System.out.print("done.");
				
				isSucceeded = true;
			} catch (ElementNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return isSucceeded;
	}
	
	public boolean sendDM(String username, String password, String text, String screenname) throws Exception {
		
		boolean out = false;
		
		System.out.println("checking previous cookies for user : "+username);
		if(!checkCookies(username)){
			System.out.println("cookies not found for user : "+username);
			createCookies(username, password);
			System.out.println("new cookies created for user : "+username);
		}
        
//		System.out.print("sent DM to :"+screenname);
		webClient.getOptions().setJavaScriptEnabled(false);
		page = webClient.getPage("http://www.kaskus.co.id/pm/compose");
        
        HtmlForm form = page.getFormByName("compose");
        final HtmlSubmitInput send = form.getInputByName("send");
        final HtmlTextInput recipient = form.getInputByName("recipient");
        final HtmlTextInput subject = form.getInputByName("subject");
        final HtmlTextArea message = form.getTextAreaByName("message");
        
        String recipients = "\""+screenname+"\"";
        String subjectInput = "DM";
        String messageInput = text;
        
        recipient.setValueAttribute(recipients);
        subject.setValueAttribute(subjectInput);
        message.setText(messageInput);
        
        try {
        	page = send.click();
        	out = true;
        }
        catch (Exception e) {
			System.out.println("===== COMPOSE LOAD ERROR START =====");
			e.printStackTrace();
			System.out.println("===== COMPOSE LOAD ERROR END   =====");
		}
		System.out.println("\r\nDM sent to : "+screenname+" from user : "+username);
		return out;
	}
	
	public boolean checkCookies(String username){
		File file = new File("c:/TOYOTA/cookies/"+username+".file");
		if(file.exists()){
			System.out.print("cookies file found, check if still valid");
			webClient.getOptions().setJavaScriptEnabled(false);
			
		    ObjectInputStream in;
			try {
				in = new ObjectInputStream(new FileInputStream(file));
			    @SuppressWarnings("unchecked")
				Set<Cookie> cookies = (Set<Cookie>) in.readObject();
			    in.close();
			    Iterator<Cookie> i = cookies.iterator();
			    
			    while (i.hasNext()){
			    	webClient.getCookieManager().addCookie(i.next());
			    }
			    
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}

			try {
				page = webClient.getPage("http://www.kaskus.co.id/");
			} catch (FailingHttpStatusCodeException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			if(page.asText().contains(username)){
				System.out.print("\r\ncookies valid!\r\n");
				return true;
			}
			else{
				return false;
			}
		}
		else{
			System.out.println("cookies file not found");
		}
		return false;
	}
	
	public boolean createCookies(String username, String password) throws Exception{
		webClient.getOptions().setJavaScriptEnabled(true);
		System.out.println("creating new cookies");
		System.out.println("get kaskus home page");
		page = webClient.getPage("https://www.kaskus.co.id/user/login");
		        
		HtmlForm form = page.getFirstByXPath("//form[@action='https://www.kaskus.co.id/user/login']");
		form.getInputByName("username").type(username);
		form.getInputByName("password").type(password);
		
		System.out.println("logging in");
		page = form.getInputByValue("Sign In").click();
		if (page.asText().contains("invalid username") && !page.asText().contains(username)) {
			return false;
		}
		
        File file = new File("c:/TOYOTA/cookies/");
        file.mkdirs();
        
        ObjectOutput outStream = new ObjectOutputStream(new FileOutputStream("c:/TOYOTA/cookies/"+username+".file"));
        outStream.writeObject(webClient.getCookieManager().getCookies());
        outStream.close();
        return true;
	}
	
//	@SuppressWarnings("unchecked")
//	private List<HtmlElement> listElement(String xPath, HtmlPage page){
//		
//		List<HtmlElement> hl = null;
//		
//		try{
//			hl = (List<HtmlElement>) page.getByXPath(xPath);
//		}
//		catch(Exception e){
//			
//		}
//		return hl;
//	}
	
	public ArrayList<String> getInboxOutbox(String fromTo, String username, String password) throws Exception{
		UnexpectedPage page = null;
		ArrayList<String> inboxOutboxes = new ArrayList<String>();
		webClient.getOptions().setJavaScriptEnabled(false);
		
//		System.out.println("checking previous cookies for user : "+username);
		if(!checkCookies(username)){
//			System.out.println("cookies not found for user : "+username);
			createCookies(username, password);
//			System.out.println("new cookies created for user : "+username);
		}
		
		try {
			page = webClient.getPage(
					"http://www.kaskus.co.id/pm/download/csvpm");
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(page != null){
			String csv = page.getWebResponse().getContentAsString();
			
			CSVFormat csvFormat = CSVFormat.EXCEL.withQuote('"').withHeader();
			
			CSVParser csvParser = null;
			try {
				csvParser = new CSVParser(new StringReader(csv), csvFormat);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for (CSVRecord record : csvParser) {
	            String from = record.get("From");
	            String to = record.get("To");
	            
	            if(from.equals(fromTo) || to.equals(fromTo)){
	            	String date = record.get("Date");
	            	String folder = record.get("Folder");
	            	String subject = record.get("Title");
	            	String message = record.get("Message");
	            	
	            	String combine = date + "\n" + folder + "\n" + from + "\n" + subject + "\n" + 
	            			message;
	            	inboxOutboxes.add(combine);
	            }
	        }
			
			Collections.sort(inboxOutboxes);
		}
		return inboxOutboxes;
	}
	
	public static ArrayList<String> getKaskusConv(String id) throws Exception{
		ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
		JSONObject confObj = config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
//		int idParse = Integer.valueOf(id);
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		if(id.length()<2){
			id = "0";
		}
		convs = dblookup.executeSelect("SELECT SCREENNAME, POSTINGDATESTR, TEXT_CONTENT, MESSAGE, POSTURL FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA2"
				+ "WHERE ID="+id+"", con, false);
		dblookup.closeDB(con);
		for(ArrayList<String> conv : convs) {
			return conv;
		}
		return null;
	}
	
	public static String getKaskusPic(String screenName) throws Exception{
		ArrayList<ArrayList<String>> convs = new ArrayList<ArrayList<String>>();
		JSONObject confObj = DataClass.config.getPropValues();
		Db2LookUp dblookup = new Db2LookUp();
		dblookup.setData(confObj.getString("hostname"), confObj.getString("dbname"), confObj.getString("dbuser"), confObj.getString("dbpass"));
		Connection con = dblookup.getConnected();
		convs = dblookup.executeSelect("SELECT PHOTOURL FROM " + confObj.getString("dbname") + "." + confObj.getString("schema") + ".SAHABATSAMPOERNA2"
				+ "WHERE SCREENNAME='"+screenName+"'", con, false);
		dblookup.closeDB(con);
		for(ArrayList<String> conv : convs) {
			String url = conv.get(0);
			url = url.replaceAll("s.kaskus.id", "dev.summit.com.sg:82");
			return url;
		}
		return null;
	}
	
	public void loginInitKaskus(String username, String password) throws Exception{
		
		System.out.println("checking previous cookies for user : "+username);
		if(!checkCookies(username)){
			System.out.println("\r\ncookies not valid for user : "+username+", creating new..");
			createCookies(username, password);
			System.out.println("new cookies created for user : "+username);
		}
	}
	
	private void initWebClient(String host, int port){
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setTimeout(60000);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setRedirectEnabled(true);
		
		ProxyConfig proxyConfig = new ProxyConfig(host, port);
		webClient.getOptions().setProxyConfig(proxyConfig);
	}
	
	private void closeWebClient(){
		if(webClient != null){
			webClient.getCookieManager().clearCookies();
			webClient.closeAllWindows();
		}
	}
	
	public boolean validateKaskus(String username, String password) {
		boolean out = false;
		try {
			out = createCookies(username, password);
		}
		catch (Exception e) {
			out = false;
			e.printStackTrace();
		}
		return out;
	}
}
