package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;

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
			JOptionPane.showMessageDialog(cf.jtf_gid, "���̵� �Է����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				socket = new Socket(Server.IP, Port.MEMBER);
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
				System.out.println("���̵� �۽�"+ cf.jtf_gid.getText());

				try {
					if (message.getResponse() == null) {
						System.out.println("���̵� ���Ұ���"+ cf.jtf_gid.getText());
						JOptionPane.showMessageDialog(cf.jtf_gid, "�̹� ������� ���̵��Դϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
 						answer=3;
 					}else {
 						answer=1;
 						System.out.println("���̵� ��밡��"+ cf.jtf_gid.getText());
 						if (cf.jtf_gid.getText().equals(message.getResponse().get(0).getMem_id())) {
							answer = JOptionPane.showConfirmDialog(cf.jtf_gid, "�� ���̵� ����Ͻðڽ��ϱ�?", "��밡���� ���̵��Դϴ�.",
									JOptionPane.YES_NO_OPTION);
							if (answer == JOptionPane.YES_OPTION) {
								answer = 1;
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
