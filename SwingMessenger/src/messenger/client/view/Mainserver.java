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
			//���� ���ϻ���(��Ʈ�ѹ�)
			Socket c_socket = s_socket.accept();
			//������ �ν��Ͻ�ȭ �ؼ� �＼�� �޼ҵ� ȣ��
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
