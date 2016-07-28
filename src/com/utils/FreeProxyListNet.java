package com.utils;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FreeProxyListNet {
	private String website = null; 
	/*private static final int[] PORTS_EXPECTED = {
		8080,80,3128};*/
	
	static {
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", 
				"org.apache.commons.logging.impl.NoOpLog");
	}
	
	public FreeProxyListNet(String countryCode){
		website = "http://www.freeproxylists.net/?"
				+ "c="+ countryCode + "&"
				+ "pt=80&"
				+ "pr=HTTP&"
				+ "a%5B%5D=0&"
				+ "a%5B%5D=1&"
				+ "a%5B%5D=2&"
				+ "u=70";
	}
	
	public ArrayList<String> findBestProxy(int top, WebClient wb){
		ArrayList<String> proxies = new ArrayList<String>();
		String page = getPage(wb);
		
		ArrayList<String> ips = new ArrayList<String>();
		ArrayList<String> ports = new ArrayList<String>();
		ArrayList<Float> uptime = new ArrayList<Float>();
		ArrayList<Float> response = new ArrayList<Float>();
		ArrayList<Float> transfer = new ArrayList<Float>();
		ArrayList<Float> scores = new ArrayList<Float>();
		
		Document doc = Jsoup.parse(page);
		
		Elements ipEls = doc.select(
				"table.DataGrid > tbody > tr[class~=(Odd|Even)] > "
				+ "td:eq(0):not([colspan])");
		Elements portEls = doc.select(
				"table.DataGrid > tbody > tr[class~=(Odd|Even)] > td:eq(1)");
		Elements uptimeEls = doc.select(
				"table.DataGrid > tbody > tr[class~=(Odd|Even)] > td:eq(7)");
		Elements responseEls = doc.select(
				"table.DataGrid > tbody > tr[class~=(Odd|Even)] > " +
				"td:eq(8) > div > span");
		Elements transferEls = doc.select(
				"table.DataGrid > tbody > tr[class~=(Odd|Even)] > " +
				"td:eq(9) > div > span");
		
		for(int i = 0; i < portEls.size(); i++){
			String portText = portEls.get(i).ownText();
			
			int port = 0;
			try{
				port = Integer.parseInt(portText);
			}catch(Exception e){}
			
			if(port > 0){
				String ipText = ipEls.get(i).text();
				ips.add(ipText);
				ports.add(portText);
				
				float uptimeRate = Float.parseFloat(uptimeEls.get(i).ownText().
						replace("%", ""));
				uptime.add(uptimeRate);
				
				float responseRate = Float.parseFloat(responseEls.get(i).
						attr("style").
						split("%;")[0].
						split(":")[1]);
				response.add(responseRate);
				
				float transferRate = Float.parseFloat(transferEls.get(i).
						attr("style").
						split("%;")[0].
						split(":")[1]);
				transfer.add(transferRate);

				/*float avResTr = (responseRate + transferRate) / 2;
				float total = uptimeRate + avResTr;*/
				float total = (responseRate + transferRate) / 2;
				
				scores.add(total);
			}
		}
		
		ArrayList<Float> oldScores = new ArrayList<Float>();
		oldScores.addAll(scores);
		
		Collections.sort(scores);
		
		for(int i = 1; i <= top; i++){
			int maxIndex = oldScores.indexOf(scores.get(scores.size() - i));
			
			String bestProxy = ips.get(maxIndex) + ":" + ports.get(maxIndex); 
			
			if(!proxies.contains(bestProxy))
				proxies.add(bestProxy);
		}
		
		return proxies;
	}
	
	private String getPage(WebClient wb){
		String page = null;
			
		try
		{
			wb.getOptions().setJavaScriptEnabled(true);
			HtmlPage html = wb.getPage(website);
			
			page = html.asXml();
			
			/*FileWriter fw = new FileWriter("C:\\debug.txt", false);
			fw.write(htmlStr);
			fw.close();*/

		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(wb != null){
				
			}
		}
		
		return page;
	}
	
	/*private boolean isPortValid(int port){
		for(int portExpected : PORTS_EXPECTED){
			if(port == portExpected){
				return true;
			}
		}
		return false;
	}*/
}
