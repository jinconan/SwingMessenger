package messenger.server.friend;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

/*********************************************
 * 친구서버
 * 데이터베이스 연결관리
 * 커넥션이 이루어지는것을 확인
 * 
 *********************************************/
public class FriendServer {
//	final String _URL = "jdbc:oracle:thin:@192.168.0.218:1521:orcl11";
//	final String _USER = "scott";
//	final String _PW = "tiger";
//	Connection 	con 		= null;
//	public Connection getConnetion() {
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");//클래스를 메모리에 로딩 클래스 이름을 못찾으면 어떡하지?
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
			//서버 접속 단계 클라이언트에서 접속 요청이 되면,
			//해당 서버로 접속 해서 접속이 완료가 되면 자동으로 친구목록 데이터베이스에 접속함.
			System.out.println("서버 생성대기");
			ServerSocket s_socket = new ServerSocket(8080);//서버 접속하기 위한 소켓[포트번호]
			System.out.println("서버 생성");
			System.out.println("클라이언트 대기중");
			//			Socket c_socket = s_socket.accept();//클라이언트가 서버에 접속.

			String ServerMsg = "Server접속 성공";
			while(true) {
				Socket	c_socket = s_socket.accept();//무한루프로 인해 접속을 대기중.
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void main(String[] args) {
		new FriendServer();
	}
}
