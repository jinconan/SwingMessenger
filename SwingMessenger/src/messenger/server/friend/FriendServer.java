package messenger.server.friend;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

/*********************************************
 * ģ������
 * �����ͺ��̽� �������
 * Ŀ�ؼ��� �̷�����°��� Ȯ��
 * 
 *********************************************/
public class FriendServer {
//	final String _URL = "jdbc:oracle:thin:@192.168.0.218:1521:orcl11";
//	final String _USER = "scott";
//	final String _PW = "tiger";
//	Connection 	con 		= null;
//	public Connection getConnetion() {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");//Ŭ������ �޸𸮿� �ε� Ŭ���� �̸��� ��ã���� �����?
//			con = DriverManager.getConnection(_URL, _USER, _PW);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.toString());
//		}
//		return con;
//	}
	FriendServer(){
//		con = 	this.getConnetion();
//		System.out.println("con : "+con);
		SocketStart();
	}
	public void SocketStart() {
		try {
			//���� ���� �ܰ� Ŭ���̾�Ʈ���� ���� ��û�� �Ǹ�,
			//�ش� ������ ���� �ؼ� ������ �Ϸᰡ �Ǹ� �ڵ����� ģ����� �����ͺ��̽��� ������.
			System.out.println("���� �������");
			ServerSocket s_socket = new ServerSocket(8080);//���� �����ϱ� ���� ����[��Ʈ��ȣ]
			System.out.println("���� ����");
			System.out.println("Ŭ���̾�Ʈ �����");
			//			Socket c_socket = s_socket.accept();//Ŭ���̾�Ʈ�� ������ ����.

			String ServerMsg = "Server���� ����";
			while(true) {
				Socket	c_socket = s_socket.accept();//���ѷ����� ���� ������ �����.
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void main(String[] args) {
		new FriendServer();
	}
}
