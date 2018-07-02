package messenger.server.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.dao.MemberDAO;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.chat.ChatServerThreadList;

/*******************************
 * 2018.06.20 쓰레드 클래스 코딩 스탑. - 아직 제대로 이해하지 못했기 때문에 이대로 코딩 할 경우 위험부담이 큼. -
 * 멀티쓰레드에대해 이해하기 2018.06.28 쓰레드 마스터 해서 다시 제대로 코딩하기 ! 학습 목표 - 멀티쓰레드에 대해 이해하고 응용할
 * 수 있다.
 * 
 *******************************/
public class MemberServerThread implements Runnable {
	private Socket socket;
	private JTextArea jta_log;

	public MemberServerThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public synchronized void run() {
		try (InputStream in = socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream oin = new ObjectInputStream(bin);

		) {

			Message<MemberVO> msg = (Message<MemberVO>) oin.readObject();
			if (jta_log != null)
				jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
			/*
			 * private List<T> request; //클라이언트가 데이터를 담는 부분 private List<T> response; //서버가
			 * 데이터를 담는 부분
			 */
			try (
				OutputStream out = socket.getOutputStream();
				BufferedOutputStream bout = new BufferedOutputStream(out);
				ObjectOutputStream oout = new ObjectOutputStream(bout);
			) {
				
				switch (msg.getType()) {
				case Message.MEMBER_LOGIN:
					if(this.jta_log != null)
						jta_log.append("MEMBER_LOGIN  | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendLoginResult(msg, oout);
					break;
				case Message.MEMBER_JOIN:// 회원가입
					if(this.jta_log != null)
						jta_log.append("MEMBER_JOIN   | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendJoinResult(msg,oout);
					break;
				case Message.MEMBER_MODIFY:// 회원정보 수정.
					if(this.jta_log != null)
						jta_log.append("MEMBER_MODIFY | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendModifyResult(msg,oout);
					break;
				case Message.MEMBER_IDCHECK:
					if(this.jta_log != null)
						jta_log.append("MEMBER_IDCHECK| " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendOverlapResult(msg,oout);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(jta_log != null)
					jta_log.append(e.toString()+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(jta_log != null)
				jta_log.append(e.toString()+"\n");
		}

	}
	/**
	 * 클라이언트의 로그인 요청 처리 메소드
	 * @param msg : 클라이언트가 보낸 메시지
	 */
	private void sendLoginResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		ArrayList<MemberVO> response = new ArrayList<MemberVO>();
		MemberVO memberVO = dao.login(request.get(0));

		if(memberVO != null) {
			//여기에 이미 접속 중인지도 따지는 부분 추가.
			response.add(memberVO);
		
		}
		msg.setResponse(response);
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendJoinResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		
		ArrayList<MemberVO> request = (ArrayList<MemberVO>)msg.getRequest();
		String result = dao.MemberInsert(request, Message.MEMBER_JOIN);
		
		if(result.equals("") != false) {
			ArrayList<MemberVO> response = new ArrayList<MemberVO>();
			response.add(request.get(0));
			msg.setResponse(response);
		}
		
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendModifyResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		
		ArrayList<MemberVO> request = (ArrayList<MemberVO>)msg.getRequest();
		String result = dao.MemberUpdate(request, Message.MEMBER_MODIFY);
		if(result.equals("") != true) {
			ArrayList<MemberVO> response = new ArrayList<MemberVO>();
			response.add(request.get(0));
			msg.setResponse(response);
		}
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendOverlapResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		int result = dao.MemberOverlap(request);
		if (result == 1) {
			//중복. response 널
		} else {
			ArrayList<MemberVO> response = new ArrayList<MemberVO>();
			response.add(request.get(0));
			msg.setResponse(response);
		}
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
