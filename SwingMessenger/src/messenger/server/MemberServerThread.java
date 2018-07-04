package messenger.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.MemberDAO;
import messenger._db.vo.MemberVO;
import messenger._protocol.Message;

/*******************************
 * 2018.06.20 쓰레드 클래스 코딩 스탑. - 아직 제대로 이해하지 못했기 때문에 이대로 코딩 할 경우 위험부담이 큼. -
 * 멀티쓰레드에대해 이해하기 2018.06.28 쓰레드 마스터 해서 다시 제대로 코딩하기 ! 학습 목표 - 멀티쓰레드에 대해 이해하고 응용할
 * 수 있다.
 * 
 *******************************/
public class MemberServerThread implements Runnable {
	private Socket socket;
	private JTextArea jta_log;
	private Map<Integer, MemberVO> loginMap;
	
	public MemberServerThread(JTextArea jta_log, Socket socket, Map<Integer, MemberVO> loginMap) {
		this.jta_log = jta_log;
		this.socket = socket;
		this.loginMap = loginMap;
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
				case Message.FRIEND_ALL:
					if(this.jta_log != null)
						jta_log.append("FRIEND_ALL    | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendFriendAllResult(msg,oout);
					break;
				case Message.FRIEND_INSERT:
					if(this.jta_log != null)
						jta_log.append("FRIEND_INSERT | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendFriendInsertResult(msg, oout);
					break;
				case Message.FRIEND_DELETE:
					if(this.jta_log != null)
						jta_log.append("FRIEND_DELETE | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendFriendDeleteResult(msg, oout);
					break;
				case Message.FRIEND_SEARCH:
					if(this.jta_log != null)
						jta_log.append("FRIEND_SEARCH | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendFriendSearchResult(msg, oout);
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
			if(loginMap.containsKey(memberVO.getMem_no()) == false) {
				response.add(memberVO);
				loginMap.put(memberVO.getMem_no(), memberVO);
			}
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
		
		if(result.equals("") == false) {
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

	private void sendFriendAllResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		
		ArrayList<MemberVO> response = dao.FriendSelectALL(request);
		msg.setResponse(response);
		
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendFriendInsertResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		MemberVO fmvo = request.get(1); //클라이언트에서 받은 친구정보를 fmvo에 담음.
		String out_msg = dao.FriendInsert(request, 4); //이건 유저 자신의 정보를 가져오는거임... 그럼 친구인설트할 정보는... 어디다 담지?
		//친구정보까지 담기면 request 인덱스0에는 유저가 담기고 인덱스 1에는 친구정보가 담기겠지..?
		if(out_msg != null) {
			ArrayList<MemberVO> response = new ArrayList<MemberVO>();
			response.add(fmvo); //mvo 대신 친구VO였음 좋겠다
			msg.setResponse(response); //데이터 저장.
			//이건 자기 자신에 대한 정보를 담는거임.. 오는것과 가는것이 같음...
			//사실 이러면 의미가 없음...  그럼 리스폰스에는 뭘 담아야 될까..?
			//리스폰스는 어레이리스트 멤버VO타입인데, 출력결과가 널값이 아니면 그대로 자기 자신의 정보를 클라이언트에 보내주는것이고.
			//출력결과가 null이면 그냥 빈공간을 보내자
		}
		
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendFriendDeleteResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		MemberVO fmvo = request.get(1); //클라이언트에서 받은 친구정보를 fmvo에 담음.
		String out_msg = dao.FriendDelete(request, 5);
		if(out_msg != null) {
			ArrayList<MemberVO> response=  new ArrayList<MemberVO>();
			response.add(fmvo); //mvo 대신 친구VO였음 좋겠다
			msg.setResponse(response);//데이터 저장
			//이건 자기 자신에 대한 정보를 담는거임.. 오는것과 가는것이 같음...
			//사실 이러면 의미가 없음...  그럼 리스폰스에는 뭘 담아야 될까..?
			//리스폰스는 어레이리스트 멤버VO타입인데, 출력결과가 널값이 아니면 그대로 자기 자신의 정보를 클라이언트에 보내주는것이고.
			//출력결과가 null이면 그냥 빈공간을 보내자
		}
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendFriendSearchResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		ArrayList<MemberVO> response = dao.FriendSearch(request);
		msg.setResponse(response);
		
		try {
			out.writeObject(msg);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
