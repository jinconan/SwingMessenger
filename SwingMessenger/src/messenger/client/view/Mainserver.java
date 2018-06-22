package messenger.client.view;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Mainserver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			ServerSocket s_socket = new ServerSocket(8888);
			//서버 소켓생성(포트넘버)
			Socket c_socket = s_socket.accept();
			//소켓을 인스턴스화 해서 억세브 메소드 호출
			OutputStream output_data = c_socket.getOutputStream();
			String sendDataString = "Welcome to My Server";
			output_data.write(sendDataString.getBytes());
			
			s_socket.close();
			c_socket.close();			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
