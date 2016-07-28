package com.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupParser {
	
	public JsoupParser(){
		
	}
	
	public Document getDoc(String dataInput, boolean fromWeb){
		
		Document doc = null;
		if(fromWeb){
			try {
				doc = Jsoup.connect(dataInput).timeout(60000).userAgent("CHROME").get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			doc = Jsoup.parse(dataInput);			
		}
		
		return doc;
	}

	public ArrayList<String> getBlocksContent(Document doc, String cssQuery){
		
		Elements elements = doc.select(cssQuery);
		ArrayList<String> listBlocksContent = new ArrayList<String>();
		
		for(Element element : elements){
			listBlocksContent.add(element.html());
		}
		return listBlocksContent;	
	}
	
	public ArrayList<String> getAllLinks(Document doc){
		
		ArrayList<String> listAllLinks = new ArrayList<String>();
		
		 Elements links = doc.select("a[href]");
		
         for (Element link : links) {
             if(link.attr("abs:href").contains("/lelang/view/")){
             	listAllLinks.add(link.attr("abs:href"));
             }
         }	 
		return listAllLinks;
	}
}
