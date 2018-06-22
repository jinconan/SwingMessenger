package messenger.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class LoginTest {

	public static void main(String[] args) {
		
		try {
			
			Socket socket= new Socket("localhost", Port.LOGIN);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			Message<MemberVO> msg = new Message<MemberVO>();
			ArrayList<MemberVO> request = new ArrayList<MemberVO>();
			MemberVO account = new MemberVO();
			account.setMem_id("test1");
			account.setMem_pw("1q2w3e4r");
			request.add(account);
			msg.setType(Message.MEMBER_LOGIN);
			msg.setRequest(request);
			oos.writeObject(msg);
			
			oos.flush();
			
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			msg = (Message<MemberVO>)ois.readObject();
			
		}catch(ConnectException e) {
			System.out.println("연결실패");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
