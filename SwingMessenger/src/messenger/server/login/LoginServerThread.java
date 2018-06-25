package messenger.server.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
	private	Socket 				socket;
	private	JTextArea			jta_log;
	private	ObjectInputStream 	in 		= null;
	private	ObjectOutputStream 	out 	= null;
	public LoginServerThread(JTextArea	jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
				InputStream			is	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(is);
				ObjectInputStream	oin = new ObjectInputStream(is);
			){
				in = oin;
				Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
				if(jta_log != null)
					jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				//���Ͽ��� ��½�Ʈ���� ���, DAO���� ���� ó���� �Ͽ� ���� ����� Ŭ���̾�Ʈ�� �����Ѵ�.
				//���� ó�� �� �α��μ����� Ŭ���̾�Ʈ�� ������ �����Ѵ�.
				try(
					OutputStream			os		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(os);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
				){
					out = oout;
					int type =msg.getType();
					
					switch(type) {
					case Message.MEMBER_LOGIN:
						if(this.jta_log != null)
							jta_log.append("MEMBER_LOGIN  | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendLoginResult(msg);
						break;
					case Message.MEMBER_JOIN:
						if(this.jta_log != null)
							jta_log.append("MEMBER_JOIN   | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						
						break;
					case Message.MEMBER_MODIFY:
						if(this.jta_log != null)
							jta_log.append("MEMBER_MODIFY | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						
						break;
					case Message.MEMBER_IDCHECK:
						if(this.jta_log != null)
							jta_log.append("MEMBER_IDCHECK| " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						
						break;
					}
					
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

	/**
	 * Ŭ���̾�Ʈ�� �α��� ��û ó�� �޼ҵ�
	 * @param msg : Ŭ���̾�Ʈ�� ���� �޽���
	 */
	private void sendLoginResult(Message<MemberVO> msg) {
		LoginDAO dao = LoginDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		ArrayList<MemberVO> response = new ArrayList<MemberVO>();
		MemberVO memberVO = dao.login(request.get(0));

		if(memberVO != null) {
			response.add(memberVO);
		}
		msg.setResponse(response);
		sendMessage(msg);
	}
	
	/**
	 * Ŭ���̾�Ʈ�� ȸ������ ��û ó�� �޼ҵ�
	 * @param msg : Ŭ���̾�Ʈ�� ���� �޽���
	 */
	private void sendJoinResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * Ŭ���̾�Ʈ�� ȸ������ ���� ��û ó�� �޼ҵ�
	 * @param msg : Ŭ���̾�Ʈ�� ���� �޽���
	 */
	private void sendModifyResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * Ŭ���̾�Ʈ�� ���̵� �ߺ��˻� ��û ó�� �޼ҵ�
	 * @param msg : Ŭ���̾�Ʈ�� ���� �޽���
	 */
	private void sendIDCheckResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * Ŭ���̾�Ʈ���� �޽��� ���� �޼ҵ�
	 * @param msg : ������ �޽���
	 */
	private void sendMessage(Message<MemberVO> msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
