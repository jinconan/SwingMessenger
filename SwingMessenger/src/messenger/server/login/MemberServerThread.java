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

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.chat.ChatServerThreadList;
/*******************************
 * 2018.06.20 쓰레드 클래스 코딩 스탑.
 * 	 - 아직 제대로 이해하지 못했기 때문에 이대로 코딩 할 경우 위험부담이 큼.
 *   - 멀티쓰레드에대해 이해하기 
 * 2018.06.28 쓰레드 마스터 해서 다시 제대로 코딩하기
 *  ! 학습 목표
 *    - 멀티쓰레드에 대해 이해하고 응용할 수 있다. 
 * 
 *******************************/
public class MemberServerThread implements Runnable{
	Socket					socket;
	JTextArea 				jta_log;
	MemberMenu 				mm 			= new MemberMenu();

	public MemberServerThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public synchronized void run() {
		try (	
				InputStream			in 	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream	oin = new ObjectInputStream(bin);

				){

			Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
			msg.getType();//메세지오브젝트 안에 들어있는 메세지 타입을 불러옴
			if(jta_log != null)
				jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
			/*private List<T> request;	//클라이언트가 데이터를 담는 부분
				private List<T> response;	//서버가 데이터를 담는 부분
			 */				
			try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
					){
				ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
				ArrayList<MemberVO> response = new ArrayList<MemberVO>();
				MemberVO mvo = request.get(0);//클라이언트에서 받은 정보를 mvo에 담음.
				switch(msg.getType()) {
				case Message.MEMBER_JOIN://회원가입
					mm.MemberInsert(request, Message.MEMBER_JOIN);
					response.add(mvo);
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.MEMBER_MODIFY://회원정보 수정.
					mm.MemberUpdate(request, Message.MEMBER_MODIFY);
					request.get(0).getMem_background();//
					request.get(0).getMem_profile();
					response.add(mvo);
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.MEMBER_IDCHECK:
					mm.MemberOverlap(request);
					if(mm.MemberOverlap(request)==1) {
						response=null;
						oout.writeObject(msg);
						oout.flush();
					}
					else {
						response.add(mvo);
						oout.writeObject(msg);
						oout.flush();
					}
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

}

