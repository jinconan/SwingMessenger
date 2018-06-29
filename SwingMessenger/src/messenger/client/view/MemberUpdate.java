package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class MemberUpdate<T> {
	Socket socket = new Socket();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	SubMenu sm = new SubMenu();
	
	Message<MemberVO> message = null;
	List<MemberVO> 	  request = null;

	public void Update() {
		
		try {
			
			socket = new Socket("192.168.0.235",Port.LOGIN);
			oos = new ObjectOutputStream(socket.getOutputStream());
			message = new Message<MemberVO>();
			request = new ArrayList<MemberVO>();
			MemberVO mv = new MemberVO();
			//MemberVO에 수정한 회원정보 담기
			mv.setMem_nick(sm.jtf_unick.getText());
			mv.setMem_pw(sm.jtf_upw.getText());
			mv.setMem_name(sm.jtf_uname.getText());
			mv.setMem_gender((String)sm.jtf_ugender.getSelectedItem());
			mv.setMem_hp(sm.jtf_uhp.getText());
			request.add(mv);
			message.setType(Message.MEMBER_MODIFY);
			message.setRequest(request);
			oos.writeObject(message);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}///////// end try catch

	}///////// end Update()

}///////// end class
