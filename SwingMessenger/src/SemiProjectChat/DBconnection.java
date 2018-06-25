package SemiProjectChat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {

	private Connection con = null;
	final   String     URL = "jdbc:oracle:thin:@192.168.0.235:1521:ORCL11";
	final   String     ID = "scott";
	final   String     PW = "tiger";
	
	public Connection Connect(){
	
		    
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    con = DriverManager.getConnection(URL, ID, PW);
			System.out.println("연동성공");
			
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		return con;
		
	}

}
