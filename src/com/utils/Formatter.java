package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Formatter {

	public Formatter(){
		
	}
	
	public static String dateFormat(String inputDate, String inputFormat, String outputFormat)
	{        
        String dateName = "";
	    try
	    {
	       dateName = (new SimpleDateFormat(outputFormat)).format((new SimpleDateFormat(inputFormat)).parse(inputDate));
	    }
	    catch(ParseException parseexception) { }
	       return dateName;
	}
	
	public String AsciiToBinary(String asciiString){  
        
        byte[] bytes = asciiString.getBytes();  
        StringBuilder binary = new StringBuilder();  
        for (byte b : bytes)  
        {  
           int val = b;  
           for (int i = 0; i < 8; i++)  
           {  
              binary.append((val & 128) == 0 ? 0 : 1);  
              val <<= 1;  
           }  
          // binary.append(' ');  
        }  
        return binary.toString();  
  } 
	
	public String toCamelCase(String s){
		   String[] parts = s.split(" ");
		   String camelCaseString = "";
		   for (String part : parts){
		      camelCaseString = camelCaseString + toProperCase(part)+" ";
		   }
		   return camelCaseString;
		}

	public  String toProperCase(String s) {
		    return s.substring(0, 1).toUpperCase() +
		               s.substring(1).toLowerCase();
		}
	
	public String removeLastChar(String str) {
     return str.substring(0,str.length()-1);
 }
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public String dateConverter(String inputString){
		inputString = inputString.toLowerCase();
		
		inputString = inputString.replaceAll("updated: ", "");
		inputString = inputString.replaceAll("|.* ", "");
		inputString = inputString.replaceAll("&nbsp;", "");
		inputString = inputString.replaceAll(" waktu.*", "");
		inputString = inputString.replaceAll(" gmt", "");
		inputString = inputString.replaceAll(" wib", "");
		inputString = inputString.replaceAll(" wita", "");
		inputString = inputString.replaceAll(" wit", "");
		
		inputString = inputString.replaceAll("senin", "Monday");
		inputString = inputString.replaceAll("selasa", "Tuesday");
		inputString = inputString.replaceAll("rabu", "Wednesday");
		inputString = inputString.replaceAll("kamis", "Thursday");
		inputString = inputString.replaceAll("jumat", "Friday");
		inputString = inputString.replaceAll("jum'at", "Friday");
		inputString = inputString.replaceAll("sabtu", "Saturday");
		inputString = inputString.replaceAll("minggu", "Sunday");
		inputString = inputString.replaceAll("januari", "January");
		inputString = inputString.replaceAll("februari", "February");
		inputString = inputString.replaceAll("pebruari", "February");
		inputString = inputString.replaceAll("maret", "March");
		inputString = inputString.replaceAll("april", "April");
		inputString = inputString.replaceAll("mei", "May");
		inputString = inputString.replaceAll("juni", "June");
		inputString = inputString.replaceAll("juli", "July");
		inputString = inputString.replaceAll("agustus", "August");
		inputString = inputString.replaceAll("september", "September");
		inputString = inputString.replaceAll("oktober", "October");
		inputString = inputString.replaceAll("nopember", "November");
		inputString = inputString.replaceAll("desember", "December");
		
		return inputString;
	}
	
	public String normalizeToXML(String input){
		input = input.replace("\"", "&quot;");
		input = input.replace("'", "&apos;");
		input = input.replace("<", "&lt;");
		input = input.replace(">", "&gt;");
		input = input.replace("&", "&amp;");
		
		return input;
	}
	
	public String normalizeFromXML(String input){
		input = input.replace("&quot;", "\"");
		input = input.replace("&apos;", "'");
		input = input.replace("&lt;", "<");
		input = input.replace("&gt;", ">");
		input = input.replace("&amp;", "&");
		
		return input;
	}
	
	public ArrayList<String> listFormatDate(){
		ArrayList<String> listFormat = new ArrayList<String>();
		
		listFormat.add("dd MMMMM yyyy");
		listFormat.add("dd MMM yyyy");
		listFormat.add("dd MMMMM yy");
		listFormat.add("dd MMM yy");
		listFormat.add("dd-MM-yyyy");
		listFormat.add("dd-MM-yy");
		listFormat.add("dd.MM.yyyy");
		listFormat.add("dd.MMM.yy");		
		
		return listFormat;
	}
}
