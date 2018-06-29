package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import SemiProjectChat.Protocol;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class Joongbok {
	ClientFrame cf = null;
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;

	Message<MemberVO> message = null;
	List<MemberVO> request = null;

	int answer = 3;

	public Joongbok(ClientFrame clientFrame) {
		// TODO Auto-generated constructor stub
		this.cf = clientFrame;
	}

	public int Gumsa() {
		if (cf.jtf_gid.getText().equals("")) {
			JOptionPane.showMessageDialog(cf.jtf_gid, "아이디를 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				socket = new Socket("192.168.0.235", Port.LOGIN);
				oos = new ObjectOutputStream(socket.getOutputStream());
				message = new Message<MemberVO>();
				request = new ArrayList<MemberVO>();
				MemberVO mv = new MemberVO();
				mv.setMem_id(cf.jtf_gid.getText());
				request.add(mv);
				message.setType(Message.MEMBER_IDCHECK);
				message.setRequest(request);
				oos.writeObject(message);
				ois = new ObjectInputStream(socket.getInputStream());
				message = (Message<MemberVO>) ois.readObject();
				System.out.println(cf.jtf_gid.getText());

				try {
					if (message.getResponse() == null) {
						JOptionPane.showInputDialog(cf.jtf_gid, "이미 사용중인 아이디입니다.", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						if (cf.jtf_gid.getText() == message.getResponse().get(0).getMem_id()) {
							answer = JOptionPane.showConfirmDialog(cf.jtf_gid, "이 아이디를 사용하시겠습니까?", "사용가능한 아이디입니다.",
									JOptionPane.YES_NO_OPTION);
							if (answer == JOptionPane.YES_OPTION) {
								answer = 0;
							} else if (answer == JOptionPane.NO_OPTION) {
								cf.jtf_gid.setText("");
							}
							System.out.println(answer);
						}
					}
					
				} catch (NullPointerException e) {
					// TODO: handle exception
					System.out.println(message.getResponse());
					return answer;
				}
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} ///////// end try catch

		} ///////// end if(ELSE)
		return answer;
	}//////// end Gumsa()

}/////// end class
