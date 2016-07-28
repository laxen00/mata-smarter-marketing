package com.db.lookup;

import java.sql.*;
import java.util.ArrayList;

public class Db2LookUp
{

    static String serverName = "jdbc:db2://192.168.1.19:50000/";
    static String databaseName = "SAMPLE";
    static String userName = "db2admin";
    static String password = "P@ssw0rd";
    static String jdbcClassName = "com.ibm.db2.jcc.DB2Driver";
    public String stringTables[];
    public static Connection connection = null;

    public Db2LookUp()
    {
    	
    }
    
    public void setData(String serverHost, String database, String userName, String password){
    	Db2LookUp.serverName = "jdbc:db2://"+serverHost+":50000/";
    	Db2LookUp.databaseName = database;
    	Db2LookUp.userName = userName;
    	Db2LookUp.password = password;
    }
    

    public Connection getConnected() throws Exception
    {
    	Class.forName(jdbcClassName);
        connection = DriverManager.getConnection((new StringBuilder(String.valueOf(serverName))).append(databaseName).toString(), userName, password);
        if(connection != null)
        {
            System.out.println("Connected successfully.");
        }
        return connection;
    }
    
    
    public boolean closeDB(Connection con) throws Exception{
		con.close();
		return true;
	}
    
	public boolean executeStatement(String query, Connection con) throws Exception{
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
		con.commit();
		stmt.close();
		return true;
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
//				System.out.println(maxColumn);
				while(column<=maxColumn){
					outlist.add(rsmd.getColumnName(column));
//					System.out.println(rsmd.getColumnName(column));
					column++;
				}
				System.out.println(outlist);
				rset.close();
				stmt.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return outlist;
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
//					System.out.println(rsmd.getColumnTypeName(current));
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
	  
	    public ArrayList<String> getColumnName(String query, Connection con){
	    	
	    	ArrayList<String> finalArray = new ArrayList<String>();
	    	ResultSet rset;
	    	
	    	try{
				Statement stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int maxColumn = rsmd.getColumnCount();
				int current = 1;
				
				while(current<=maxColumn){
					finalArray.add(rsmd.getColumnName(current)+"="+rsmd.getColumnTypeName(current));
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
	    
	    public ArrayList<ArrayList<String>> executeSelect(String query, Connection con, boolean useHeader){
			
	    	ArrayList<ArrayList<String>> finalArray = new ArrayList<ArrayList<String>>();
			
			ResultSet rset;
			try {
				Statement stmt = con.createStatement();
				rset = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rset.getMetaData();
				int maxColumn = rsmd.getColumnCount();
//				System.out.println(maxColumn);
				int column = 1;		
				ArrayList<String> outlist = new ArrayList<String>();
				if(useHeader){
					while(column<=maxColumn){
						outlist.add(rsmd.getColumnName(column));
						column++;
					}
					finalArray.add(outlist);
//					System.out.println(finalArray);
				}
				column = 1;
				while (rset.next()){
//					System.out.println("writing all result query");
					outlist = new ArrayList<String>();
					while(column<=maxColumn){
						outlist.add(rset.getString(column));
						column++;
					}
					column = 1;
//					System.out.println(outlist);
					finalArray.add(outlist);			      
				}
				rset.close();
				stmt.close();
//				System.out.println(finalArray);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return finalArray;
		}

    
    public ArrayList<String> getTables (String tablename, String schema, String catalog, Connection con){
    	
    	ArrayList<String> finalList = new ArrayList<String>();
    	String query = "";
    	
    	if(tablename.length()>1 && schema.length()>1){
    		query = "select TABNAME,LOCATION from SYSHADOOP.HCAT_TABLES where TABSCHEMA='"+schema+"' AND TABNAME='"+tablename+"'";
    	}
    	
    	else if(schema.length()>1){
    		query = "select TABNAME,LOCATION from SYSHADOOP.HCAT_TABLES where TABSCHEMA='"+schema+"'";
    	}
    	
    	else if(tablename.length()>1){
    		query = "select TABNAME,LOCATION from SYSHADOOP.HCAT_TABLES where TABSCHEMA='"+schema+"'";
    	}
    	    	
    	ResultSet rset;
		try {
			Statement stmt = con.createStatement();
			rset = stmt.executeQuery(query);
			while (rset.next()){
				if(!finalList.contains(rset.getString(1))){
					finalList.add(rset.getString(1));
				}
			}
			
			rset.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(finalList);
    	return finalList;
    	
    }
    
    public ArrayList<String> runCreateLoadQueriesHadoop (Connection con, ArrayList<String> tables) throws Exception{
    	ArrayList<String> output = new ArrayList<String>();
    	for(String table : tables){
    		String splitCol = getHeader(table, con).get(0);
        	String loadQuery = "LOAD USING JDBC CONNECTION URL " +
        			"'"+Db2LookUp.serverName+Db2LookUp.databaseName+"' " +
        			"WITH PARAMETERS ('user'='"+Db2LookUp.userName+"','password'='"+Db2LookUp.password+"') " +
        			"FROM TABLE "+Db2LookUp.userName.toUpperCase()+"."+table+"_HADOOP " +
        			"SPLIT COLUMN "+splitCol+" INTO TABLE "+Db2LookUp.userName.toUpperCase()+"."+table+"_HADOOPDB " +
        			"OVERWRITE " +
        			"WITH LOAD PROPERTIES ('num.map.tasks'=3)";
        	output.add(loadQuery);
    	}
    	return output;
    }
}
