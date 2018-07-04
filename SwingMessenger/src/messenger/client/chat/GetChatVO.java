package messenger.client.chat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger._protocol.Message;
import messenger.client.view.ClientData;
import messenger.client.view.ClientFrame;
import messenger.client.view.dialog.ChatDialog;

public class GetChatVO extends Thread {
	private Socket 				socket;
	private ClientData 			clientData;
	private ClientFrame 		clientFrame;
	private ObjectOutputStream 	out;
	Pattern 					pattern  = Pattern.compile("\\([��-�R]*\\_[��-�R]*\\)");
	
	public GetChatVO(Socket socket, ObjectOutputStream out, ClientData clientData) {
		this.socket 	 = socket;
		this.out 		 = out;
		this.clientData  = clientData;
		this.clientFrame = clientData.clientFrame;
	}

	@Override
	public void run() {
		try (
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(bis);
		) {
			while (true) {
				// ������ ������ ��� �����ؼ� while���� ���ѹݺ���Ŵ
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// ���� ä���� ȭ�鿡 ���
					printChatContent(msg);
					break;
				case Message.CHATROOM_LOAD:
					// ����Ʈ���̺�� ���� addROw�� �ο� �߰��ϰ� JTable�� �����ϰ� �� ��� ���ΰ�ħ.
					getRoomList(msg);
					break;
				case Message.CHATROOM_INVITE:
					//���� ������ ���� �����κ��� ���޹޾Ƽ� ����Ʈ���̺�𵨿� �߰�.
					attendRoom(msg);
					break;
				case Message.CHATROOM_EXIT:
					// ������ ���� �����κ��� ���޹޴µ�, �̰��� ����Ʈ���̺�𵨿��� ã�Ƽ� ����.
					// �� ����Ʈ�� ���ΰ�ħ
					exitRoom(msg);
					break;
				}
			}
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
	 * ���� ä�� �޼����� Ư�� �濡 ����Ѵ�.
	 * @param msg �����κ��� ���� �޼���
	 * @param clientData Ŭ���̾�Ʈ�� ����
	 */
	private void printChatContent(Message<ChatVO> msg) {
		ArrayList<ChatVO> response = (ArrayList<ChatVO>) msg.getResponse();
		ChatVO cVO = (response != null && response.size() > 0) ? response.get(0) : null;
		if(cVO == null) 
			return;
		RoomVO rVO = cVO.getRoomVO();
		if(rVO == null)
			return;
		ChatDialog dialog = clientData.getChatDialog(rVO.getRoom_no());
		if(dialog == null)
			return;
		MemberVO mVO = cVO.getMemVO();
		String chat_content = cVO.getChat_content(); //chatVO�� ä�� ����
		dialog.append(mVO.getMem_name() + " > " +chat_content, pattern);
	}
	
	/**
	 * �����κ��� ���� �� ����Ʈ�� ȭ�鿡 ����Ѵ�.
	 * @param msg �����κ��� ���� �޼���
	 * @param clientFrame Ŭ���̾�Ʈ�� ����
	 */
	public void getRoomList(Message<ChatVO> msg) {
		ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
		ArrayList<RoomVO> rVOList = new ArrayList<RoomVO>();
		
		//ChatVO�� ���� ����Ʈ�� RoomVo�� ����Ʈ�� ��ȯ�Ѵ�.
		if(response != null) {
			for(int i=0; i<response.size();i++) {
				RoomVO roomVO = response.get(i).getRoomVO();
				rVOList.add(roomVO);
			}
		}
		
		//RoomVo�� ����Ʈ�� ������ �� ����� ���ΰ�ħ�Ѵ�..
		clientFrame.refreshRoomList(rVOList);
	}
	
	
	public void attendRoom(Message<ChatVO> msg) {
		//������ ���� ���̾�α׸� ȭ�鿡 ���� �� ����Ʈ ���� �޽��� ����
		ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
		ChatVO chatVO = (response != null) ? response.get(0) : null;
		RoomVO roomVO = (chatVO != null) ? chatVO.getRoomVO() : null;
		int room_no = (roomVO != null) ? roomVO.getRoom_no() : 0;
		
		ChatDialog cDialog = clientData.getChatDialog(room_no);
		if(cDialog != null)
			cDialog.setVisible(true);
		
		clientData.getRoomList();
	}

	public void exitRoom(Message<ChatVO> msg) {
		clientData.getRoomList();
	}
}
