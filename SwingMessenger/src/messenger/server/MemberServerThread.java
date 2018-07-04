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
 * 2018.06.20 ������ Ŭ���� �ڵ� ��ž. - ���� ����� �������� ���߱� ������ �̴�� �ڵ� �� ��� ����δ��� ŭ. -
 * ��Ƽ�����忡���� �����ϱ� 2018.06.28 ������ ������ �ؼ� �ٽ� ����� �ڵ��ϱ� ! �н� ��ǥ - ��Ƽ�����忡 ���� �����ϰ� ������
 * �� �ִ�.
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
				jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
			/*
			 * private List<T> request; //Ŭ���̾�Ʈ�� �����͸� ��� �κ� private List<T> response; //������
			 * �����͸� ��� �κ�
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
				case Message.MEMBER_JOIN:// ȸ������
					if(this.jta_log != null)
						jta_log.append("MEMBER_JOIN   | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
					sendJoinResult(msg,oout);
					break;
				case Message.MEMBER_MODIFY:// ȸ������ ����.
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
	 * Ŭ���̾�Ʈ�� �α��� ��û ó�� �޼ҵ�
	 * @param msg : Ŭ���̾�Ʈ�� ���� �޽���
	 */
	private void sendLoginResult(Message<MemberVO> msg, ObjectOutputStream out) {
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
		ArrayList<MemberVO> response = new ArrayList<MemberVO>();
		MemberVO memberVO = dao.login(request.get(0));

		if(memberVO != null) {
			//���⿡ �̹� ���� �������� ������ �κ� �߰�.
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
			//�ߺ�. response ��
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
		MemberVO fmvo = request.get(1); //Ŭ���̾�Ʈ���� ���� ģ�������� fmvo�� ����.
		String out_msg = dao.FriendInsert(request, 4); //�̰� ���� �ڽ��� ������ �������°���... �׷� ģ���μ�Ʈ�� ������... ���� ����?
		//ģ���������� ���� request �ε���0���� ������ ���� �ε��� 1���� ģ�������� ������..?
		if(out_msg != null) {
			ArrayList<MemberVO> response = new ArrayList<MemberVO>();
			response.add(fmvo); //mvo ��� ģ��VO���� ���ڴ�
			msg.setResponse(response); //������ ����.
			//�̰� �ڱ� �ڽſ� ���� ������ ��°���.. ���°Ͱ� ���°��� ����...
			//��� �̷��� �ǹ̰� ����...  �׷� ������������ �� ��ƾ� �ɱ�..?
			//���������� ��̸���Ʈ ���VOŸ���ε�, ��°���� �ΰ��� �ƴϸ� �״�� �ڱ� �ڽ��� ������ Ŭ���̾�Ʈ�� �����ִ°��̰�.
			//��°���� null�̸� �׳� ������� ������
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
		MemberVO fmvo = request.get(1); //Ŭ���̾�Ʈ���� ���� ģ�������� fmvo�� ����.
		String out_msg = dao.FriendDelete(request, 5);
		if(out_msg != null) {
			ArrayList<MemberVO> response=  new ArrayList<MemberVO>();
			response.add(fmvo); //mvo ��� ģ��VO���� ���ڴ�
			msg.setResponse(response);//������ ����
			//�̰� �ڱ� �ڽſ� ���� ������ ��°���.. ���°Ͱ� ���°��� ����...
			//��� �̷��� �ǹ̰� ����...  �׷� ������������ �� ��ƾ� �ɱ�..?
			//���������� ��̸���Ʈ ���VOŸ���ε�, ��°���� �ΰ��� �ƴϸ� �״�� �ڱ� �ڽ��� ������ Ŭ���̾�Ʈ�� �����ִ°��̰�.
			//��°���� null�̸� �׳� ������� ������
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
