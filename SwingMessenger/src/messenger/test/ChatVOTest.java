package messenger.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import messenger._db.vo.MemberVO;

public class ChatVOTest {

	
	public static void main(String[] args) {
		ChatVOTest test = new ChatVOTest();
		Thread s = new Thread(test.new server());
		Thread c = new Thread(test.new client());
		
		s.start();
		c.start();

	}

	class server implements Runnable {
		@Override
		public void run() {
			ServerSocket severSocket;
			try {
				severSocket = new ServerSocket(9000);
				Socket socket = severSocket.accept();
				ObjectInputStream oos = new ObjectInputStream(socket.getInputStream());
				
				try {
					ChatVO chatVO = (ChatVO)oos.readObject();
					System.out.println(chatVO.getMemVO().getMem_id());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	class client implements Runnable {
		@Override
		public void run() {
			try {
				Socket socket =new Socket("localhost", 9000);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				MemberVO memVo = new MemberVO(0, "admin", null, null, null, null, null, null, null);
				ChatVO chatVO = new ChatVO(0, 0, "гоюл", null, memVo);
				
				oos.writeObject(chatVO);
				while(true);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
