package messenger._db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * �� Ŭ������ ���������� �ʿ��� �ڵ带 �߰��ϴ� Ŭ������.
 * �ܵ����� ����Ǵ� Ŭ������ �ƴ�.
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
	 * ChatDAO�� �ν��Ͻ��� ��´�.
	 * @return ChatDAO�� �ν��Ͻ�
	 */
 	public static DBConnection getInstance() {
		return LazyHolder.INSTANCE;
	}
 	
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); //Ŭ������ �޸𸮿� �ε�. �̸��� ��ã���� ����� - ����ó��
			con = DriverManager.getConnection(LOCAL_URL, _USER, _PW);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
