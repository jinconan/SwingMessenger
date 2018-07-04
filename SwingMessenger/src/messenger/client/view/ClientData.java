package messenger.client.view;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.client.Emoticon.GetEmoticon;
import messenger.client.chat.GetChatVO;
import messenger.client.friend.ClientFriendAdd;
import messenger.client.friend.ClientFriendDelete;
import messenger.client.friend.ClientFriendList;
import messenger.client.friend.ClientFriendSearch;
import messenger.client.view.dialog.ChatDialog;
import messenger.client.view.dialog.SearchDialog;
import messenger.protocol.Message;
import messenger.protocol.Port;
import messenger.protocol.Server;

public class ClientData {
	private MemberVO myData;
	private HashMap<String, JLabel> emoticonMap;
	private HashMap<Integer, ChatDialog> chatDialogMap = new HashMap<Integer, ChatDialog>();
	private ArrayList<MemberVO> friendList;
	private ObjectOutputStream out;
	
	public ClientFrame clientFrame;
	public SearchDialog SearchDialog;
	
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	/***************************************************************************************
	 * @author 이정렬 [18/07/02]
	 * 1. 화면에서 입력된 데이터가 저장되는 클래스 ClientData의 값을 친구관련기능을 구현하는 클래스로 넘김
	 * 		인스턴스화할때, 여기의 자료를 선별하여 읽어내서 넘긴다. ( 즉, 액션리스너에서는 이곳의 메소드를 실행함 )
	 * 2. 친구쪽 메소드에 화면을 처리하는 메소드까지 함께 구현한다
	 **************************************************************************************/
	
	//actionPerformed에서 친구전체조회를 실행하도록 호출할 메소드
	public void actionFriendList() {
//		f_Panel = new FriendPanel(this);//private와 값보내기는 상관없음
		ClientFriendList cfl = new ClientFriendList(myData.getMem_no(),clientFrame);
		cfl.getFriendList();
	}
	/******************************************
	 * actionPerformed에서 친구검색을 실행하도록 호출할 메소드
	 * @param 검색하고자하는 친구아이디
	 ******************************************/
	public void actionFriendSearch(String FriendId) {
//		f_Panel = new FriendPanel(this);//private와 값보내기는 상관없음
		ClientFriendSearch cfc = new ClientFriendSearch(FriendId,clientFrame,SearchDialog);
		cfc.getFriendSearch();
	}
	/******************************************
	 * actionPerformed에서 친구추가를 실행하도록 호출할 메소드
	 * @param 추가하고자하는 친구아이디
	 ******************************************/
	public void actionAddFriend(String FriendId) {
//		f_Panel = new FriendPanel(this);//private와 값보내기는 상관없음
		ClientFriendAdd cfa = new ClientFriendAdd(myData.getMem_id(),FriendId,clientFrame);
		cfa.getFriendAdd();
	}
	/******************************************
	 * actionPerformed에서 친구추가를 실행하도록 호출할 메소드
	 * @param 삭제하고자하는 친구아이디를 넘겨받아와야함
	 ******************************************/
	public void actionDeleteFriend(String FriendId) {
//		f_Panel = new FriendPanel(this);//private와 값보내기는 상관없음
		ClientFriendDelete cfd = new ClientFriendDelete(myData.getMem_id(),FriendId,clientFrame);
		cfd.getFriendDelete();
	}
//■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
	
	public ClientData(ClientFrame frame) {
		this.clientFrame = frame;
	}
	
	/**
	 * 채팅서버와 연결된 OutputStream을 얻는다.
	 * @return : 채팅서버와 연결된 OutputStream
	 */
	public ObjectOutputStream getOut() {
		return out;
	}
	
	/**
	 * 이모티콘 서버에 연결하여 이모티콘을 받아오는 작업을 쓰레드를 사용하여 진행한다.
	 */
	public void getEmoticonFromServer() {
		emoticonMap = (emoticonMap == null) ? new GetEmoticon().method() : emoticonMap;
	}

	/**
	 * 채팅서버에 접속한다. 먼저 방리스트를 요청하면서 출력스트림의 주소번지를 얻고,
	 * 그 다음에 다른 쓰레드(GetChatVO class)를 인스턴스화하고 실행시켜서 들어오는 메시지를 처리할 수 있도록한다.
	 */
	public void initChat() {
		Socket socket = null;
		try {
			socket = new Socket(Server.IP, Port.CHAT);
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			//방 목록 요청하는 메시지 생성
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, null, null, null, myData));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_LOAD, request, null);
			//메시지 전달
			out.writeObject(msg);
			out.flush();
			//메시지 수신을 위한 쓰레드 실행
			new GetChatVO(socket,out, this).start();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				if(out != null)
					out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 서버에 방 리스트를 요청하는 메소드
	 */
	public void getRoomList() {
		ArrayList<ChatVO> request = new ArrayList<ChatVO>();
		ChatVO chatVO = new ChatVO(0, null, null, null, myData);
		request.add(chatVO);
		Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_LOAD, request, null);
		try {
			out.writeObject(msg);
			out.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 멤버서버에 연결하여 로그인을 요청한다.
	 * @param mem_id : 아이디
	 * @param mem_pw : 비밀번호
	 * @return true : 성공, false : 실패
	 */
	public boolean login(String mem_id, String mem_pw) {
		boolean result = false;
		
		//인증 정보를 담은 메시지 생성
		ArrayList<MemberVO> request = new ArrayList<MemberVO>();
		request.add(new MemberVO(0,mem_id, null, null,null,mem_pw,null,null,null));
		Message<MemberVO> msg = new Message<MemberVO>(Message.MEMBER_LOGIN, request, null);
	
		//서버에 연결하여 메시지 전송
		try (
			Socket socket = new Socket(Server.IP, Port.MEMBER);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		){
			out.writeObject(msg);
			//서버로부터 메시지 수신.
			try (
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			){
				msg = (Message<MemberVO>)in.readObject();
				ArrayList<MemberVO> response = (ArrayList<MemberVO>)msg.getResponse();
				//인증에 성공하면 response 안에는 memberVO가 들어있다.
				if(response.size() > 0) {
					myData = response.get(0);
					result = true;
				}
			} catch(Exception e) { 
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 현재 나의 정보를 가져온다.
	 * @return MemberVO타입의 내 정보.
	 */
	public MemberVO getMyData() {
		return myData;
	}
	
	/**
	 * 이름이 s인 이모티콘을 해쉬맵에서 가져온다.
	 * @param s : 이모티콘 이름
	 * @return 이모티콘 객체.(null : 이모티콘을 찾을 수 없음)
	 */
	public JLabel getEmoticon(String s) {
		return emoticonMap.get(s);
		
	}
	
	
	/**
	 * 채팅방에서 사용할 이모티콘 패널을 얻는다.
	 * @param jtf : 채팅방 안에 있는 텍스트필드.
	 * @return 이모티콘 패널
	 */
	public JPanel getEmoticonPanel(JTextField jtf) {
		JPanel result = new JPanel(new GridLayout(6,6));
		//이모티콘 해쉬맵에서 이모티콘을 가져와서 일일히 패널에 추가한다.
		if(emoticonMap != null) {
			for(String s : emoticonMap.keySet()) {
				JLabel jl = getEmoticon(s);
				
				//각각의 이모티콘은 클릭했을때 jtf에 이름을 출력하도록 한다.
				jl.addMouseListener(new MouseAdapter() {
					
				@Override
					public void mouseClicked(MouseEvent e) {
						if(jtf !=null) {
							StringBuilder builder = new StringBuilder(jtf.getText());
							builder.append(jl.getText());
							jtf.setText(builder.toString());
						}
					}	
				});
				result.add(jl);
			}
		}
		System.out.println("이모티콘맵: " + emoticonMap);
		return result;
	}

	/**
	 * 방 번호가 rVO인 채팅방 다이얼로그를 리턴.
	 * @param rVO : 방 번호
	 * @return 채팅방 다이얼로그
	 */
	public synchronized ChatDialog getChatDialog(int rVO) {
		return chatDialogMap.get(rVO);
	}

	/**
	 * 서버로부터 방 리스트를 받으면 채팅방 다이얼로그를 초기화 한다.
	 * 1. 방 리스트에 없는 채팅방 다이얼로그를 해쉬맵에서 제거한다.
	 * 2. 리스트에 새로 추가된 방의 경우에는 채팅방 다이얼로그를 인스턴스화하여 해쉬맵에 추가한다.
	 * @param roomList : 서버로 부터 받은 방(RoomVO) 리스트
	 */
	public synchronized void refreshRoomList(ArrayList<RoomVO> roomList) {
		
		//새로 받아온 방 리스트에 기존 방이 존재하지 않으면 해쉬맵에서 제거한다.
		for(int room_no : chatDialogMap.keySet()) {
			boolean hasRoom = false;
			for(RoomVO rVO : roomList) {
				if(room_no == rVO.getRoom_no()) {
					hasRoom = true;
					break;
				}
			}
			if(hasRoom == false) {
				ChatDialog dialog = chatDialogMap.remove(room_no);
				dialog.dispose();
			}
		}
		
		//해쉬맵에 존재하지 않은 방에 대해서는 추가한다.
		for(RoomVO rVO : roomList) {
			if(chatDialogMap.containsKey(rVO.getRoom_no()) == false) {
				ChatDialog dialog = new ChatDialog(this,  rVO);
				chatDialogMap.put(rVO.getRoom_no(), dialog);
			}
		}
	}
	
	
	
}
