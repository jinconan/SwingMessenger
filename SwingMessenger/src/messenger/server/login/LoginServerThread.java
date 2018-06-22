package messenger.server.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import messenger._db.dao.LoginDAO;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.view.ServerView;

public class LoginServerThread implements Runnable{
	private Socket 		socket;
	private JTextArea	jta_log;
	
	public LoginServerThread(JTextArea	jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
				InputStream			in 	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream	oin = new ObjectInputStream(in);
			){
				
				Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
				if(jta_log != null)
					jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				//���Ͽ��� ��½�Ʈ���� ���, DAO���� ���� ó���� �Ͽ� ���� ����� Ŭ���̾�Ʈ�� �����Ѵ�.
				//���� ó�� �� �α��μ����� Ŭ���̾�Ʈ�� ������ �����Ѵ�.
				try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
				){
					LoginDAO dao = LoginDAO.getInstance();
					ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
					ArrayList<MemberVO> response = new ArrayList<MemberVO>();
					MemberVO memberVO = dao.login(request.get(0));

					if(memberVO != null) {
						response.add(memberVO);
						if(jta_log != null)
							jta_log.append("�α��� : " + memberVO.getMem_id()+"\n");
					}
					else {
						System.out.println("�α��� ���� : " + socket.getInetAddress());
					}
					msg.setResponse(response);
					oout.writeObject(msg);
					oout.flush();
					if(jta_log != null)
						jta_log.append("���� : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				}catch (Exception e) {
					e.printStackTrace();
					if(jta_log != null)
						jta_log.append(e.toString()+"\n");
				}
					
			}catch (Exception e) {
				e.printStackTrace();
				if(jta_log != null)
					jta_log.append(e.toString()+"\n");
			}
	}

}
