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
					jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				//소켓에서 출력스트림을 얻고, DAO에서 인증 처리를 하여 얻은 결과를 클라이언트에 전송한다.
				//인증 처리 후 로그인서버는 클라이언트와 연결을 종료한다.
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
							jta_log.append("로그인 : " + memberVO.getMem_id()+"\n");
					}
					else {
						System.out.println("로그인 실패 : " + socket.getInetAddress());
					}
					msg.setResponse(response);
					oout.writeObject(msg);
					oout.flush();
					if(jta_log != null)
						jta_log.append("응답 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
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
