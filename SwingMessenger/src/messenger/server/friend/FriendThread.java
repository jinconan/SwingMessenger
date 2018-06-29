package messenger.server.friend;

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

import messenger._db.dao.FriendMenu;
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
public class FriendThread implements Runnable{
	Socket					socket;
	JTextArea 				jta_log;
	FriendMenu 				fm 			= new FriendMenu();

	public FriendThread(JTextArea jta_log, Socket socket) {
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
			System.out.println(msg.getType());
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
				/*
				 * 받는 인풋스트림이 1개면 보내는 아웃풋 스트림도 무조건 1개여야됨.
				 * 받는 인풋스트림이 2개고 보내는 아웃풋 스트림이 1개일경우 EOFException 에러가 뜸.
				 * 받는건 두개인데 보내는쪽이 한개면 자동으로 사용안한 인풋스트림은 널로 대기상태로 되는줄 알았는데 아니였음..
				 * 
				 */
				switch(msg.getType()) {
				case Message.FRIEND_ALL://친구 전체 조회 1단계 완료. --일단은요..(에러뜨면 컴터 던지고 ㅌㅌ)
					ArrayList<MemberVO> result = fm.FriendSelectALL(request);//서버에서 보낼 정보를 selectAll의 파라미터로 전달.
					response = result;
					msg.setResponse(response);//데이터 저장.
					oout.writeObject(msg);//아웃풋 스트림으로 작성
					oout.flush();//데이터 강제로 보냄.
					break;
				case Message.FRIEND_INSERT:
					MemberVO fmvo = request.get(1);//클라이언트에서 받은 친구정보를 fmvo에 담음.
					fm.FriendInsert(request, 4);
					String out_msg = fm.FriendInsert(request, 4);//이건 유저 자신의 정보를 가져오는거임... 그럼 친구인설트할 정보는... 어디다 담지?
					//친구정보까지 담기면 request 인덱스0에는 유저가 담기고 인덱스 1에는 친구정보가 담기겠지..?
					if(out_msg!=null) {
					response.add(fmvo);//mvo 대신 친구VO였음 좋겠다
					//이건 자기 자신에 대한 정보를 담는거임.. 오는것과 가는것이 같음...
					//사실 이러면 의미가 없음...  그럼 리스폰스에는 뭘 담아야 될까..?
					//리스폰스는 어레이리스트 멤버VO타입인데, 출력결과가 널값이 아니면 그대로 자기 자신의 정보를 클라이언트에 보내주는것이고.
					//출력결과가 null이면 그냥 빈공간을 보내자
					}
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.FRIEND_DELETE:
					MemberVO fmvo2 = request.get(1);//클라이언트에서 받은 친구정보를 fmvo에 담음.
					fm.FriendDelete(request, 5);
					String out_msg2 = fm.FriendDelete(request, 5);
					if(out_msg2!=null) {
						response.add(fmvo2);//mvo 대신 친구VO였음 좋겠다
						//이건 자기 자신에 대한 정보를 담는거임.. 오는것과 가는것이 같음...
						//사실 이러면 의미가 없음...  그럼 리스폰스에는 뭘 담아야 될까..?
						//리스폰스는 어레이리스트 멤버VO타입인데, 출력결과가 널값이 아니면 그대로 자기 자신의 정보를 클라이언트에 보내주는것이고.
						//출력결과가 null이면 그냥 빈공간을 보내자
						}
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.FRIEND_SEARCH:
					response = fm.FriendSearch(request);//친구 검색 결과를 리스폰스에 담음.
					oout.writeObject(msg);
					oout.flush();
					break;
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

}

