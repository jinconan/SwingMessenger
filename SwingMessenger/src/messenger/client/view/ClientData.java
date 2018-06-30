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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.client.Emoticon.GetEmoticon;
import messenger.client.chat.GetChatVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class ClientData {
	private MemberVO myData;
	private HashMap<String, JLabel> emoticonMap;
	private HashMap<Integer, ChatDialog> chatDialogMap = new HashMap<Integer, ChatDialog>();
	private ArrayList<MemberVO> friendList;
	private ArrayList<RoomVO> roomList;
	private ObjectOutputStream out;
	
	public ClientFrame clientFrame;
	
	public ClientData(ClientFrame frame) {
		this.clientFrame = frame;
	}
	
	public void getEmoticonFromServer() {
		if(emoticonMap == null) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					emoticonMap = new GetEmoticon().method();
				}
			});
			thread.start();
		}
	}

	public void initChat() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", Port.CHAT);
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, null, null, null, myData));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_LOAD, request, null);
			out.writeObject(msg);
			out.flush();
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

	public ObjectOutputStream getOut() {
		return out;
	}
	
	public boolean login(String mem_id, String mem_pw) {
		boolean result = false;
		ArrayList<MemberVO> request = new ArrayList<MemberVO>();
		request.add(new MemberVO(0,mem_id, null, null,null,mem_pw,null,null,null));
		Message<MemberVO> msg = new Message<MemberVO>(Message.MEMBER_LOGIN, request, null);
		try (Socket socket = new Socket("localhost", Port.LOGIN);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());){
			out.writeObject(msg);
			
			try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
				msg = (Message<MemberVO>)in.readObject();
				ArrayList<MemberVO> response = (ArrayList<MemberVO>)msg.getResponse();
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

	
	public MemberVO getMyData() {
		return myData;
	}
	
	public JLabel getEmoticon(String s) {
		return emoticonMap.get(s);
		
	}
	
	public JPanel getEmoticonPanel(JTextField jtf) {
		JPanel result = new JPanel(new GridLayout(6,6));
		System.out.println("jtf:" + jtf);
		for(String s : emoticonMap.keySet()) {
			JLabel jl = getEmoticon(s);
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
		
		return result;
	}
	
	public synchronized ChatDialog getChatDialog(int rVO) {
		return chatDialogMap.get(rVO);
	}

	public synchronized void refreshRoomList(ArrayList<RoomVO> roomList) {
		
		//새로 받아온 방 리스트에 기존 방이 존재하지 않으면 해쉬맵에서 제거한다.
		for(int room_no : chatDialogMap.keySet()) {
			if(roomList.contains(room_no) == false) {
				ChatDialog dialog = chatDialogMap.remove(room_no);
				dialog.dispose();
			}
		}
		
		//해쉬맵에 존재하지 않은 방에 대해서는 추가한다.
		for(RoomVO rVO : roomList) {
			if(chatDialogMap.containsKey(rVO.getRoom_no()) == false) {
				ChatDialog dialog = new ChatDialog(clientFrame.clientData,  rVO);
				clientFrame.clientData.chatDialogMap.put(rVO.getRoom_no(), dialog);
			}
		}
	}
	
}
