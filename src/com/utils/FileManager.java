package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileManager {

	  public String readData(String filename)
	    {
		  
	        BufferedReader br;
	        String outFinal;
	        br = null;
	        outFinal = "";
	        try
	        {
	            br = new BufferedReader(new FileReader(filename));
	            String sCurrentLine;
	            while((sCurrentLine = br.readLine()) != null) 
	            {
	                outFinal = (new StringBuilder(String.valueOf(outFinal))).append(sCurrentLine).append("\r\n").toString();
	            }
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        try
	        {
	            if(br != null)
	            {
	                br.close();
	            }
	        }
	        catch(IOException ex)
	        {
	            ex.printStackTrace();
	        }
	        
	        try
	        {
	            if(br != null)
	            {
	                br.close();
	            }
	        }
	        catch(IOException ex)
	        {
	            ex.printStackTrace();
	        }
	        
	        try
	        {
	            if(br != null)
	            {
	                br.close();
	            }
	        }
	        catch(IOException ex)
	        {
	            ex.printStackTrace();
	        }
	        return outFinal;
	    }
	  
	  public void fileWriter(String outfilename, String values, boolean writeAppend){
		  
		FileWriter f1;		  
		
		if(!writeAppend){
			try {
				  f1 = new FileWriter(outfilename);
				  f1.write(values);
				  f1.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		else{			
			try {
			    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outfilename, true)));
			    out.println(values);
			    out.close();
			} catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}
		}
	  }
	  
	  public String createDir(String fullpath){
		  
		  boolean success = (new File(fullpath)).mkdirs();
		  if (!success) {
		      // Directory creation failed
			  return fullpath;
		  }
		  return fullpath;
	  }
	  
	  public boolean deleteFile(String fileFullPath){
		  
		  File file = new File(fileFullPath);
		  
		  try{
			  if(file.delete()){
				  
			  }
		  }
		  catch(Exception e){
			  return false;
		  }
		  
		  
		  return true;
	  }
	  
	  public boolean moveFile(String fileFullPath, String folderFullPath)
	  {
		  try{
	   
			  File afile =new File(fileFullPath);
	   
	      	  if(afile.renameTo(new File(folderFullPath + afile.getName()))){
	      		  System.out.println("File is moved successful!");
	      	  }else{
	      		  System.out.println("File is failed to move!");
	      		  return false;
	      	  }
	   
	      	}catch(Exception e){
	      		e.printStackTrace();
	      		return false;
	      	}
		  return true;
	  }
	  
	  public boolean copyFile(String fileFullPath, String folderFullPath){
		  
		  InputStream input = null;
		  OutputStream output = null;
				try {
					input = new FileInputStream(fileFullPath);
					output = new FileOutputStream(folderFullPath);
					byte[] buf = new byte[1024];
					int bytesRead;
					while ((bytesRead = input.read(buf)) > 0) {
						output.write(buf, 0, bytesRead);
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block					
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				} finally {
					try {
						input.close();
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					}
				
				}
		  return true;
	  }
	  
	  public ArrayList<String> listFile(String fullPath){
		  
		  File folder = new File(fullPath);
		  File[] listOfFiles = folder.listFiles();
		  ArrayList<String> pathOutFiles = new ArrayList<String>();

		      for (int i = 0; i < listOfFiles.length; i++) {
		        if (listOfFiles[i].isFile()) {
		        	pathOutFiles.add(listOfFiles[i].getName());
		        	System.out.println("File " + listOfFiles[i].getName());
		        }
		      }
		  return pathOutFiles;
	  }
	  
	  public ArrayList<String> listFolder(String fullPath){
		  
		  File folder = new File(fullPath);
		  File[] listOfFiles = folder.listFiles();
		  ArrayList<String> pathOutFiles = new ArrayList<String>();

		      for (int i = 0; i < listOfFiles.length; i++) {
		        if (listOfFiles[i].isDirectory()) {
		        	pathOutFiles.add(listOfFiles[i].getName());
		        	System.out.println("Directory " + listOfFiles[i].getName());
		        }
		      }
		  return pathOutFiles;
	  }
	  
	  
	  public double checkFileSize(String filename){
		  
		  File file =new File(filename);
		  
		  double megabytes = 0.0000;
		  double gigabytes = (megabytes / 1024);
		  double terabytes = (gigabytes / 1024);
		  double petabytes = (terabytes / 1024);
		  double exabytes = (petabytes / 1024);
		  double zettabytes = (exabytes / 1024);
		  double yottabytes = (zettabytes / 1024);
		  
		  if(file.exists()){
			  
				double bytes = file.length();
				double kilobytes = (bytes / 1024);
				megabytes = (kilobytes / 1024);
				gigabytes = (megabytes / 1024);
				terabytes = (gigabytes / 1024);
				petabytes = (terabytes / 1024);
				exabytes = (petabytes / 1024);
				zettabytes = (exabytes / 1024);
				yottabytes = (zettabytes / 1024);
	 
			}else{
				 System.out.println("File does not exists!");
			}
		  return megabytes;
	  }
	  
	  public String getFullPath(String path){
		  File file = new File(path);
		  String dirPath = file.getAbsoluteFile().getParentFile().getAbsolutePath();
//		  assert dirPath.equals("/home/me/dev/target");
		  
		  return dirPath;
	  }
}
