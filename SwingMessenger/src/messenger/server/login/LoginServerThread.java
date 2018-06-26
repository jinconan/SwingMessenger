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
					jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				//소켓에서 출력스트림을 얻고, DAO에서 인증 처리를 하여 얻은 결과를 클라이언트에 전송한다.
				//인증 처리 후 로그인서버는 클라이언트와 연결을 종료한다.
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
	 * 클라이언트의 로그인 요청 처리 메소드
	 * @param msg : 클라이언트가 보낸 메시지
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
	 * 클라이언트의 회원가입 요청 처리 메소드
	 * @param msg : 클라이언트가 보낸 메시지
	 */
	private void sendJoinResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * 클라이언트의 회원정보 수정 요청 처리 메소드
	 * @param msg : 클라이언트가 보낸 메시지
	 */
	private void sendModifyResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * 클라이언트의 아이디 중복검사 요청 처리 메소드
	 * @param msg : 클라이언트가 보낸 메시지
	 */
	private void sendIDCheckResult(Message<MemberVO> msg) {
		
	}
	
	/**
	 * 클라이언트에게 메시지 전달 메소드
	 * @param msg : 전달할 메시지
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
