package messenger.client.chat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import messenger._db.vo.ChatVO;
import messenger.client.view.ClientData;
import messenger.client.view.ClientFrame;
import messenger.protocol.Message;

public class GetChatVO extends Thread {
	private Socket 				socket;
	private ClientData 			clientData;
	private ClientFrame 		clientFrame;
	private ObjectOutputStream 	out;
	
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
					PrintChatContent printChatContent = new PrintChatContent();
					printChatContent.method(msg,clientData);
					break;
				case Message.CHATROOM_LOAD:
					// ����Ʈ���̺�� ���� addROw�� �ο� �߰��ϰ� JTable�� �����ϰ� �� ��� ���ΰ�ħ.
					GetRoomList getRoomList = new GetRoomList();
					getRoomList.method(msg, clientFrame);
					break;
				case Message.CHATROOM_INVITE:
					//���� ������ ���� �����κ��� ���޹޾Ƽ� ����Ʈ���̺�𵨿� �߰�.
					AttendRoom attendRoom =new AttendRoom();
					attendRoom.method(msg);
					break;
				case Message.CHATROOM_EXIT:
					// ������ ���� �����κ��� ���޹޴µ�, �̰��� ����Ʈ���̺�𵨿��� ã�Ƽ� ����.
					// �� ����Ʈ�� ���ΰ�ħ
					ExitRoom exitRoom = new ExitRoom();
					exitRoom.method(msg);
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
	
}
