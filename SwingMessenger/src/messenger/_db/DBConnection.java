package messenger._db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 이 클래스는 공통적으로 필요한 코드를 추가하는 클래스임.
 * 단독으로 실행되는 클래스가 아님.
 */
public class DBConnection {
	final String LOCAL_URL = "jdbc:oracle:thin:@localhost:1521:orcl11";  
	final String _USER = "scott";
	final String _PW = "tiger";
	Connection con = null;
	
	private DBConnection() {}
	
	private static class LazyHolder {
		private static final DBConnection INSTANCE = new DBConnection();
	}
	
	/**
	 * ChatDAO의 인스턴스를 얻는다.
	 * @return ChatDAO의 인스턴스
	 */
 	public static DBConnection getInstance() {
		return LazyHolder.INSTANCE;
	}
 	
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //클래스를 메모리에 로딩. 이름을 못찾으면 어떡하지 - 예외처리
			con = DriverManager.getConnection(LOCAL_URL, _USER, _PW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
