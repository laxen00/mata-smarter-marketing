package com.db.lookup;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlLookUp {
	
	static String serverHost = "jdbc:sqlserver://192.168.1.182:1433;";
    static String database = "databaseName=cvis";
    static String userName = "p8admin";
    static String password = "P@ssw0rd";
//    static String time = getCurrentTime();
    
    public SqlLookUp(){
    	
    }
    
    public void setData(String serverHost, String database, String userName, String password){
    	SqlLookUp.serverHost = "jdbc:sqlserver://"+serverHost+";";
    	SqlLookUp.database = "databaseName="+database;
    	SqlLookUp.userName = userName;
    	SqlLookUp.password = password;
    }
    
    public Connection getConnected() throws Exception{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection con = null;
		con = DriverManager.getConnection(serverHost+database, userName, password);
//		System.out.println("connected");
		return con;
    }
    
    public void closeDB(Connection con) throws Exception{
    	con.close();
    }
    
	public boolean executeStatement(String query, Connection con){
		
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);
			con.commit();
			stmt.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
    
    public ArrayList<String> getHeader(String table, Connection con){
		
		ArrayList<String> outlist = new ArrayList<String>();
		
		ResultSet rset;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery("select * from "+table+"");
			ResultSetMetaData rsmd = rset.getMetaData();
			int maxColumn = rsmd.getColumnCount();
			int column = 1;
//			System.out.println(maxColumn);
			while(column<=maxColumn){
				outlist.add(rsmd.getColumnName(column));
//				System.out.println(rsmd.getColumnName(column));
				column++;
			}
			System.out.println(outlist);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outlist;
	}
    
    public ArrayList<String> getColumnName(String query, Connection con){
    	
    	ArrayList<String> finalArray = new ArrayList<String>();
    	ResultSet rset = null;
    	
    	try{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int maxColumn = rsmd.getColumnCount();
			int current = 1;
			
			while(current<=maxColumn){
				if(rsmd.getColumnTypeName(current).toLowerCase().contains("varchar")||rsmd.getColumnTypeName(current).toLowerCase().contains("numeric")){
					finalArray.add(rsmd.getColumnName(current)+"="+columnConverter((rsmd.getColumnTypeName(current)))+"("+rsmd.getColumnDisplaySize(current)+")");
				}
				else{
					finalArray.add(rsmd.getColumnName(current)+"="+columnConverter((rsmd.getColumnTypeName(current).toUpperCase())));
				}
				current++;
			}
	    	try {
				rset.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return finalArray;
    }
    
    public ArrayList<ArrayList<String>> executeSelect(String query, Connection con, boolean useHeader){
		
    	ArrayList<ArrayList<String>> finalArray = new ArrayList<ArrayList<String>>();
    	Statement stmt = null;
		ResultSet rset;
		try {
			stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int maxColumn = rsmd.getColumnCount();
//			System.out.println(maxColumn);
			int column = 1;		
			ArrayList<String> outlist = new ArrayList<String>();
			if(useHeader){
				while(column<=maxColumn){
					outlist.add(rsmd.getColumnName(column));
					column++;
				}
				finalArray.add(outlist);
//				System.out.println(finalArray);
			}
			column = 1;
			while (rset.next()){
//				System.out.println("writing all result query");
				outlist = new ArrayList<String>();
				while(column<=maxColumn){
					outlist.add(rset.getString(column));
					column++;
				}
				column = 1;
//				System.out.println(outlist);
				finalArray.add(outlist);			      
			}
			rset.close();
			stmt.close();
//			System.out.println(finalArray);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				stmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return finalArray;
	}
    
    public ArrayList<ArrayList<String>> getDDL(String tablename, String schema, String catalog, Connection con){
    	ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
    	String query = "";
    	if(tablename.length()>1 && catalog.length()>1 && schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"' AND table_name='"+tablename+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1 && catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1 && schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"' AND table_schema='"+schema+"'";
    	}
    	
    	else if(schema.length()>1 && catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"'";
    	}
    	
    	else if(catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"'";
    	}
    		
    	System.out.println(query);
    	
    	ResultSet rset;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			int loop = 1;
			while (rset.next()){
				ArrayList<String> outlist = new ArrayList<String>();
				while(loop<=23){
					outlist.add(rset.getString(3));
					loop++;
					break;
				}
				loop = 1;
				finalList.add(outlist);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return finalList;
    }
    
  	public String getPrimaryKeyName(String table, Connection con){
  		String primaryKey = null;
  		try {
			DatabaseMetaData dm = con.getMetaData();
			ResultSet rs = dm.getExportedKeys("", "", table);
			while(rs.next()){
				primaryKey = rs.getString("PKCOLUMN_NAME");
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		return primaryKey;
  	}
  	
    public ArrayList<String> getTables (String tablename, String schema, String catalog, Connection con){
    	
    	ArrayList<String> finalList = new ArrayList<String>();
    	String query = "";
    	if(tablename.length()>1 && catalog.length()>1 && schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"' AND table_name='"+tablename+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1 && catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1 && schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"' AND table_schema='"+schema+"'";
    	}
    	
    	else if(schema.length()>1 && catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"' AND table_catalog='"+catalog+"'";
    	}
    	
    	else if(schema.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_schema='"+schema+"'";
    	}
    	
    	else if(catalog.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_catalog='"+catalog+"'";
    	}
    	
    	else if(tablename.length()>1){
    		query = "select * from INFORMATION_SCHEMA.COLUMNS where table_name='"+tablename+"'";
    	}
    		
//    	System.out.println(query);
    	
    	ResultSet rset;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			while (rset.next()){
				if(!finalList.contains(rset.getString(3))){
					finalList.add(rset.getString(3));
				}
			}
			rset.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	return finalList;
    	
    }
    private String columnConverter(String colinput){
    	String outCol = "";
    	colinput = colinput.toLowerCase();
    	
    	if(colinput.equals("nvarchar")){
    		outCol = "varchar";
    	}
    	else if(colinput.equals("datetime")){
    		outCol = "timestamp";
    	}
    	else if(colinput.equals("numeric")){
    		outCol = "varchar";
    	}
    	else if(colinput.equals("int")){
    		outCol = "int";
    	}
    	else if(colinput.equals("float")){
    		outCol = "double";
    	}
    	else if(colinput.equals("double")){
    		outCol = "double";
    	}
    	else if(colinput.contains("tinyint")){
    		outCol = "int";
    	}
    	else if(colinput.contains("smallint")){
    		outCol = "int";
    	}
    	else if(colinput.contains("mediumint")){
    		outCol = "int";
    	}
    	else{
    		outCol = colinput;
    	}
    	
    	return outCol.toUpperCase();
    }
    
    public ArrayList<String> getColumnType(String query, Connection con){
    	
    	ArrayList<String> finalArray = new ArrayList<String>();
    	ResultSet rset;
    	
    	try{
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rset.getMetaData();
			int maxColumn = rsmd.getColumnCount();
			int current = 1;
			
			while(current<=maxColumn){
				finalArray.add(rsmd.getColumnTypeName(current));
				current++;
			}
			rset.close();
			stmt.close();
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	return finalArray;
    }
    
    private String createTableByColumns (String tableName, ArrayList<String> columns) {
    	String start = "CREATE HADOOP TABLE " + tableName +" (";
    	String end = ")";
    	String args = "";
    	int colCount = columns.size();
    	int count = 0;
    	for (String column : columns) {
    		String[] colSplit = column.split("=");
    		String colName = colSplit[0];
    		String colType = colSplit[1];
    		args = args + colName + " " + colType;
    		count++;
    		if (count != colCount) args = args + ",";
    	}
    	String query = start + args + end;
//    	System.out.println(query);
    	return query;
    }
    
    public ArrayList<String> runCreateLoadQueries (Connection con, ArrayList<String> tables, String schema, String regex, String destSchema) throws Exception{
    	ArrayList<String> output = new ArrayList<String>();
    	tables = filterTables(tables, regex);
    	for(String table : tables){
    		String splitCol = getHeader(table, con).get(0);
        	String loadQuery = "LOAD HADOOP USING JDBC CONNECTION URL " +
        			"'"+SqlLookUp.serverHost+SqlLookUp.database+"' " +
        			"WITH PARAMETERS ('user'='"+SqlLookUp.userName+"','password'='"+SqlLookUp.password+"') " +
        			"FROM TABLE "+schema+"."+table+" " +
        			"SPLIT COLUMN "+splitCol+" INTO TABLE "+SqlLookUp.database.split("=")[1]+schema+"_"+destSchema+"."+table+" " +
        			"APPEND " +
        			"WITH TARGET TABLE PROPERTIES ('drop.delimiter.chars'='true')" +
        			"WITH LOAD PROPERTIES ('num.map.tasks'=3)";
        	output.add(loadQuery);
    	}
    	return output;
    }
    
    public ArrayList<String> runCreateLoadQueriesHadoop (Connection con, ArrayList<String> tables, String regex, String destSchema, String HADOOPUSERNAME, String HADOOPPASSWORD) throws Exception{
    	ArrayList<String> output = new ArrayList<String>();
    	tables = filterTables(tables, regex);
    	for(String table : tables){
    		String splitCol = getHeader(table, con).get(0);
        	String loadQuery = "LOAD HADOOP USING JDBC CONNECTION URL " +
        			"'jdbc:db2://appilmsit01.btpn.com:51000/BIGSQL' " +
        			"WITH PARAMETERS ('user'='"+HADOOPUSERNAME+"','password'='"+HADOOPPASSWORD+"') " +
        			"FROM TABLE "+destSchema+"."+table+"_HADOOP " +
        			"SPLIT COLUMN "+splitCol+" INTO TABLE "+destSchema+"."+table+"_HADOOPDB " +
        			"OVERWRITE " +
        			"WITH TARGET TABLE PROPERTIES ('drop.delimiter.chars'='true')" +
        			"WITH LOAD PROPERTIES ('num.map.tasks'=3)";
        	output.add(loadQuery);
    	}
    	return output;
    }
    
    public ArrayList<String> listDeleteTableQueries (Connection con, ArrayList<String> tables, String schema, String regex, boolean drop) {
    	ArrayList<String> output = new ArrayList<String>();
    	tables = filterTables(tables, regex);
		for (String table : tables) {
			if(drop){
				output.add("DROP TABLE " +SqlLookUp.database.split("=")[1]+"."+schema+ "." + table);
			}
			else{
				output.add("DELETE FROM " +SqlLookUp.database.split("=")[1]+"."+schema+ "." + table);
			}
		}
    	return output;
    }
    
    private ArrayList<String> listCreateTableQueries (ArrayList<String> tables, Connection con, String schema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
    	ArrayList<String> columns = new ArrayList<String>();
    	String query = "";
		for (String table : tables) {
			System.out.println(table);
			columns = getColumnName("SELECT * FROM " + table, con);
			query = createTableByColumns(schema+table, columns);
			output.add(query);
		}
    	return output;
    }
    
    private ArrayList<String> listCreateTableQueriesHadoop (ArrayList<String> tables, Connection con, String destSchema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
    	ArrayList<String> columns = new ArrayList<String>();
    	String query = "";
		for (String table : tables) {
//			System.out.println(table);
			columns = getColumnName("SELECT * FROM " + table, con);
			query = createTableByColumns(destSchema+"."+table + "_HADOOP", columns).replace("CREATE HADOOP", "CREATE");
			output.add(query);
		}
    	return output;
    }
    
    private ArrayList<String> listCreateTableQueriesHadoopTmp (ArrayList<String> tables, Connection con, String destSchema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
    	ArrayList<String> columns = new ArrayList<String>();
    	String query = "";
		for (String table : tables) {
//			System.out.println(table);
			columns = getColumnName("SELECT * FROM " + table, con);
			query = createTableByColumns(destSchema+"."+table + "_HADOOPDB", columns);
			output.add(query);
		}
    	return output;
    }
    
	public ArrayList<String> filterTables (ArrayList<String> tables, String regex) {
    	ArrayList<String> newTableList = new ArrayList<String>();
    	Pattern p = Pattern.compile(regex);
    	Matcher m = null;
		for (String table : tables) {
			m = p.matcher(table);
			if (m.find()) {
				newTableList.add(table);
			}
		}
    	return newTableList;
    }
	
    public ArrayList<String> runcreateTableQueries (Connection con, String regexPattern, ArrayList<String> tables, String schema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
		tables = filterTables(tables, regexPattern);
		output = listCreateTableQueries(tables, con, schema);
    	return output;
    }
    
    public ArrayList<String> runcreateTableQueriesHadoop (Connection con, String regexPattern, ArrayList<String> tables, String destSchema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
		tables = filterTables(tables, regexPattern);
		output = listCreateTableQueriesHadoop(tables, con, destSchema);
    	return output;
    }
    
    public ArrayList<String> runcreateTableQueriesHadoopTmp (Connection con, String regexPattern, ArrayList<String> tables, String destSchema) throws Exception {
    	ArrayList<String> output = new ArrayList<String>();
		tables = filterTables(tables, regexPattern);
		output = listCreateTableQueriesHadoopTmp(tables, con, destSchema);
    	return output;
    }
    
	public static String getCurrentTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		Date date = new Date();
		return (dateFormat.format(date)); //2014/08/06 15:59:48
	}
}
