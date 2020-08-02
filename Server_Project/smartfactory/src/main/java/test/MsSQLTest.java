package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Test;



public class MsSQLTest {
	 private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
     private static final String URL = "jdbc:sqlserver://192.168.3.2:1455;databaseName=SFKR_MES";
     private static final String USER = "psu"; //DB 사용자명
     private static final String PW = "@psu!234@";   //DB 사용자 비밀번호
 
     @Test
     public void testConnection() throws Exception{
    	 System.out.println("start");
         Class.forName(DRIVER); //com.microsoft.sqlserver.jdbc.SQLServerDriver JDBC Driver class 로딩
         Connection con=null;
         try{
	         con = DriverManager.getConnection(URL, USER, PW); // java.sql.Connection 객체생성
	         System.out.println(con);
         
             System.out.println(con);
              
             System.out.println(con.isClosed()); // connection 닫힘 유무
 
             Statement stmt = con.createStatement(); // Statement 객체생성
 
             String sql = "SELECT USER_ID, PASSWORD FROM SYS_USER"; // 쿼리문
 
             ResultSet rs = stmt.executeQuery(sql); // 
 
             while(rs.next()) {
                 System.out.println(rs.getString("USER_ID")+"///"+rs.getString("PASSWORD"));
             }
 
             con.close();
 
             System.out.println(con.isClosed());
         }catch (Exception e) {
        	 System.out.println(e.toString());
             e.printStackTrace();
             
         }finally {
             con.close();
         }
    }
}
