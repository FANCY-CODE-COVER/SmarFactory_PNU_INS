package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Test;



public class MsSQLTest {
	 private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
     private static final String URL = "jdbc:sqlserver://192.168.3.2:1455;databaseName=SFKR_MES";
     private static final String USER = "psu"; //DB ����ڸ�
     private static final String PW = "@psu!234@";   //DB ����� ��й�ȣ
 
     @Test
     public void testConnection() throws Exception{
    	 System.out.println("start");
         Class.forName(DRIVER); //com.microsoft.sqlserver.jdbc.SQLServerDriver JDBC Driver class �ε�
         Connection con=null;
         try{
	         con = DriverManager.getConnection(URL, USER, PW); // java.sql.Connection ��ü����
	         System.out.println(con);
         
             System.out.println(con);
              
             System.out.println(con.isClosed()); // connection ���� ����
 
             Statement stmt = con.createStatement(); // Statement ��ü����
 
             String sql = "SELECT USER_ID, PASSWORD FROM SYS_USER"; // ������
 
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
