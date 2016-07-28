<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="
org.apache.commons.fileupload.*,
org.apache.commons.fileupload.servlet.*,
org.apache.commons.fileupload.disk.*,
java.io.*,
java.util.*
"
%>

<%
	String uploadedFilePath = "";
	try {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("isMultiPart: " + isMultipart);
	
		//Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		
		//Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		//Parse the request
		List<FileItem> items = upload.parseRequest(request);
		
		// Process a file upload
		File dir = new File("C:\\upload");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		for (FileItem item : items) {
			System.out.println("Filename: " + items.get(0).getName());
		    File uploadedFile = new File(dir.getAbsoluteFile() + File.separator + item.getName());
		    item.write(uploadedFile);
		    uploadedFilePath = dir.getAbsoluteFile() + File.separator + item.getName();
		    System.out.println(uploadedFilePath);
		}
	}
	catch (Exception e) {
		uploadedFilePath = "";
		e.printStackTrace();
	}
	out.print(uploadedFilePath);
%>