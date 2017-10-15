package exercise2;

import java.sql.*;
import java.util.List;

public class Jsql {
	public static void createTable(){
		Connection c = null;
		Statement stmt = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/Database",
	            "postgres", "123");
	         System.out.println("Opened database successfully");
	         stmt = c.createStatement();
	         System.out.println("Dropping previous table...");
	         String sql ="DROP TABLE ASSIGNMENT";
		     stmt.executeUpdate(sql);
		     System.out.println("Table dropped succesfully" + "\n Creating new table...");
	         sql = "CREATE TABLE ASSIGNMENT " +
		    		  		"(ID INT PRIMARY KEY NOT NULL," +
		    		  		"NAME TEXT NOT NULL)";
		     stmt.executeUpdate(sql);
		     System.out.println("Table created successfully");
		     sql = "INSERT INTO ASSIGNMENT (ID,NAME)"
		    		 + "VALUES (185465, 'JOHN');";
		     stmt.executeUpdate(sql);
		     System.out.println("John inserted into database successfully");
		     insertNames(stmt);
		     System.out.println("Table filled successfully");
		     findJohn1(stmt);
		     System.out.println("Indexing...");
		     findJohn2(stmt);
		     stmt.close();
		     c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }     
	}
	
	public static void insertNames(Statement stmt) throws Exception{
		int number = 1000000;
		Randomnames rand = new Randomnames(number);
		int johnid = 185465;
		List<String> list = rand.getNames();
		String sql = "INSERT INTO ASSIGNMENT (ID,NAME)"
	    		 + "VALUES ";
		int i = 1;
		for(String s:list){
			if(i < johnid)
					stmt.executeUpdate(sql + "(" + (i++) + ",'"+s+"');");
			else{
				i++;
					stmt.executeUpdate(sql + "(" + (i) + ",'"+s+"');");
			}
		}
	}
	
	public static void findJohn1(Statement stmt) throws Exception{		
		long start = System.nanoTime();
		int id = 0;
		String name = "";
		ResultSet rs = stmt.executeQuery("SELECT * FROM ASSIGNMENT WHERE NAME = 'JOHN';");
		while(rs.next()){
			id = rs.getInt("id");
			name = rs.getString("name");
		}
		System.out.println("Finding Waldo (John) took: "+((System.nanoTime() - start)) + " nanoseconds");
		System.out.println("id = " + id + " name = " + name);
	}
	
	public static void findJohn2(Statement stmt) throws Exception{
		createIndex(stmt);
		long start = System.nanoTime();
		int id = 0;
		String name = "";
		ResultSet rs = stmt.executeQuery("SELECT * FROM ASSIGNMENT WHERE NAME = 'JOHN';");
		while(rs.next()){
			id = rs.getInt("id");
			name = rs.getString("name");
		}
		System.out.println("Finding Waldo (John) took: "+((System.nanoTime() - start)) + " nanoseconds");
		System.out.println("id = " + id + " name = " + name);
	}
	
	public static void createIndex(Statement stmt) throws Exception{
		String sql = "CREATE INDEX nameIndex ON Assignment(NAME)";
		stmt.execute(sql);
	}
	
	public static void main(String[] args) {
		createTable();
	  }
}
