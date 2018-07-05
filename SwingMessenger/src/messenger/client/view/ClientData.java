package messenger.client.view;

import java.awt.Font;
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
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;
import messenger.client.Emoticon.GetEmoticon;
import messenger.client.chat.GetChatVO;
import messenger.client.friend.ClientFriendAdd;
import messenger.client.friend.ClientFriendDelete;
import messenger.client.friend.ClientFriendList;
import messenger.client.friend.ClientFriendSearch;
import messenger.client.view.dialog.ChatDialog;
import messenger.client.view.dialog.SearchDialog;

public class ClientData {
	private MemberVO myData;
	private HashMap<String, JLabel> emoticonMap;
	private HashMap<Integer, ChatDialog> chatDialogMap = new HashMap<Integer, ChatDialog>();
	private ArrayList<MemberVO> friendList;
	private ObjectOutputStream out;
	
	public ClientFrame clientFrame;
	public SearchDialog SearchDialog;
	
//��������������������������������������������������������������������������������������������������
	/***************************************************************************************
	 * @author ������ [18/07/02]
	 * 1. ȭ�鿡�� �Էµ� �����Ͱ� ����Ǵ� Ŭ���� ClientData�� ���� ģ�����ñ���� �����ϴ� Ŭ������ �ѱ�
	 * 		�ν��Ͻ�ȭ�Ҷ�, ������ �ڷḦ �����Ͽ� �о�� �ѱ��. ( ��, �׼Ǹ����ʿ����� �̰��� �޼ҵ带 ������ )
	 * 2. ģ���� �޼ҵ忡 ȭ���� ó���ϴ� �޼ҵ���� �Բ� �����Ѵ�
	 **************************************************************************************/
	
	//actionPerformed���� ģ����ü��ȸ�� �����ϵ��� ȣ���� �޼ҵ�
	public void actionFriendList() {
		ClientFriendList cfl = new ClientFriendList(myData.getMem_no(),clientFrame);
		cfl.getFriendList();
	}
	/******************************************
	 * actionPerformed���� ģ���˻��� �����ϵ��� ȣ���� �޼ҵ�
	 * @param �˻��ϰ����ϴ� ģ�����̵�
	 ******************************************/
	public void actionFriendSearch(String FriendId) {
		ClientFriendSearch cfc = new ClientFriendSearch(FriendId,clientFrame, SearchDialog);
		cfc.getFriendSearch();
	}
	/******************************************
	 * actionPerformed���� ģ���߰��� �����ϵ��� ȣ���� �޼ҵ�
	 * @param �߰��ϰ����ϴ� ģ�����̵�
	 ******************************************/
	public void actionAddFriend(String FriendId) {
		ClientFriendAdd cfa = new ClientFriendAdd(myData.getMem_id(),FriendId,clientFrame);
		cfa.getFriendAdd();
	}
	/******************************************
	 * actionPerformed���� ģ���߰��� �����ϵ��� ȣ���� �޼ҵ�
	 * @param �����ϰ����ϴ� ģ�����̵� �Ѱܹ޾ƿ;���
	 ******************************************/
	public void actionDeleteFriend(String FriendId) {
		ClientFriendDelete cfd = new ClientFriendDelete(myData.getMem_id(),FriendId,clientFrame);
		cfd.getFriendDelete();
	}
//��������������������������������������������������������������������������������������������������
	
	public ClientData(ClientFrame frame) {
		this.clientFrame = frame;
	}
	
	/**
	 * ä�ü����� ����� OutputStream�� ��´�.
	 * @return : ä�ü����� ����� OutputStream
	 */
	public ObjectOutputStream getOut() {
		return out;
	}
	
	/**
	 * �̸�Ƽ�� ������ �����Ͽ� �̸�Ƽ���� �޾ƿ��� �۾��� �����带 ����Ͽ� �����Ѵ�.
	 */
	public void getEmoticonFromServer() {
		emoticonMap = (emoticonMap == null) ? new GetEmoticon().method() : emoticonMap;
	}

	/**
	 * ä�ü����� �����Ѵ�. ���� �渮��Ʈ�� ��û�ϸ鼭 ��½�Ʈ���� �ּҹ����� ���,
	 * �� ������ �ٸ� ������(GetChatVO class)�� �ν��Ͻ�ȭ�ϰ� ������Ѽ� ������ �޽����� ó���� �� �ֵ����Ѵ�.
	 */
	public void initChat() {
		Socket socket = null;
		try {
			socket = new Socket(Server.IP, Port.CHAT);
			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			//�� ��� ��û�ϴ� �޽��� ����
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, null, null, null, myData));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_LOAD, request, null);
			//�޽��� ����
			out.writeObject(msg);
			out.flush();
			//�޽��� ������ ���� ������ ����
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
	 * ������ �� ����Ʈ�� ��û�ϴ� �޼ҵ�
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
	 * ��������� �����Ͽ� �α����� ��û�Ѵ�.
	 * @param mem_id : ���̵�
	 * @param mem_pw : ��й�ȣ
	 * @return true : ����, false : ����
	 */
	public boolean login(String mem_id, String mem_pw) {
		boolean result = false;
		
		//���� ������ ���� �޽��� ����
		ArrayList<MemberVO> request = new ArrayList<MemberVO>();
		request.add(new MemberVO(0,mem_id, null, null,null,mem_pw,null,null,null));
		Message<MemberVO> msg = new Message<MemberVO>(Message.MEMBER_LOGIN, request, null);
	
		//������ �����Ͽ� �޽��� ����
		try (
			Socket socket = new Socket(Server.IP, Port.MEMBER);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		){
			out.writeObject(msg);
			//�����κ��� �޽��� ����.
			try (
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			){
				msg = (Message<MemberVO>)in.readObject();
				ArrayList<MemberVO> response = (ArrayList<MemberVO>)msg.getResponse();
				//������ �����ϸ� response �ȿ��� memberVO�� ����ִ�.
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
	 * ���� ���� ������ �����´�.
	 * @return MemberVOŸ���� �� ����.
	 */
	public MemberVO getMyData() {
		return myData;
	}
	
	/**
	 * �̸��� s�� �̸�Ƽ���� �ؽ��ʿ��� �����´�.
	 * @param s : �̸�Ƽ�� �̸�
	 * @return �̸�Ƽ�� ��ü.(null : �̸�Ƽ���� ã�� �� ����)
	 */
	public JLabel getEmoticon(String s) {
		return emoticonMap.get(s);
		
	}
	
	/**
	 * ä�ù濡�� ����� �̸�Ƽ�� �г��� ��´�.
	 * @param jtf : ä�ù� �ȿ� �ִ� �ؽ�Ʈ�ʵ�.
	 * @return �̸�Ƽ�� �г�
	 */
	public JPanel getEmoticonPanel(JTextField jtf) {
		JPanel result = new JPanel(new GridLayout(6,6));
		//�̸�Ƽ�� �ؽ��ʿ��� �̸�Ƽ���� �����ͼ� ������ �гο� �߰��Ѵ�.
		if(emoticonMap != null) {
			Set<String> keySet = emoticonMap.keySet();
			Iterator<String> iter = keySet.iterator();
			while(iter.hasNext()) {
				String s = iter.next();
				JLabel loadedEmoticon = getEmoticon(s);
				ImageIcon icon = (ImageIcon)loadedEmoticon.getIcon();
				String emo_name = loadedEmoticon.getText();
				JLabel jl = new JLabel(icon);
				jl.setText(emo_name);
				jl.setFont(new Font("����", Font.BOLD,0));
				
				//������ �̸�Ƽ���� Ŭ�������� jtf�� �̸��� ����ϵ��� �Ѵ�.
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
		return result;
	}

	/**
	 * �� ��ȣ�� rVO�� ä�ù� ���̾�α׸� ����.
	 * @param rVO : �� ��ȣ
	 * @return ä�ù� ���̾�α�
	 */
	public synchronized ChatDialog getChatDialog(int rVO) {
		return chatDialogMap.get(rVO);
	}

	/**
	 * �����κ��� �� ����Ʈ�� ������ ä�ù� ���̾�α׸� �ʱ�ȭ �Ѵ�.
	 * 1. �� ����Ʈ�� ���� ä�ù� ���̾�α׸� �ؽ��ʿ��� �����Ѵ�.
	 * 2. ����Ʈ�� ���� �߰��� ���� ��쿡�� ä�ù� ���̾�α׸� �ν��Ͻ�ȭ�Ͽ� �ؽ��ʿ� �߰��Ѵ�.
	 * @param roomList : ������ ���� ���� ��(RoomVO) ����Ʈ
	 */
	public void refreshRoomList(ArrayList<RoomVO> roomList) {
		Set<Integer> keySet = chatDialogMap.keySet();
		Iterator<Integer> iter = keySet.iterator();
		//���� �޾ƿ� �� ����Ʈ�� ���� ���� �������� ������ �ؽ��ʿ��� �����Ѵ�.

		while(iter.hasNext()) {
			int room_no = iter.next();
			boolean hasRoom = false;
			for(RoomVO rVO : roomList) {
				if(room_no == rVO.getRoom_no()) {
					hasRoom = true;
					break;
				}
			}
			if(hasRoom == false) {
				ChatDialog dialog = chatDialogMap.get(room_no);
				iter.remove();
				dialog.dispose();
			}
		}
		
		//�ؽ��ʿ� �������� ���� �濡 ���ؼ��� �߰��Ѵ�.
		for(RoomVO rVO : roomList) {
			if(chatDialogMap.containsKey(rVO.getRoom_no()) == false) {
				ChatDialog dialog = new ChatDialog(this,  rVO);
				chatDialogMap.put(rVO.getRoom_no(), dialog);
			}
		}
	}

	
}
