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
import messenger.client.view.ClientData;
import messenger.client.view.ClientFrame;
import messenger.client.view.dialog.ChatDialog;
import messenger.protocol.Message;

public class GetChatVO extends Thread {
	private Socket 				socket;
	private ClientData 			clientData;
	private ClientFrame 		clientFrame;
	private ObjectOutputStream 	out;
	Pattern 					pattern  = Pattern.compile("\\([°¡-ÆR]*\\_[°¡-ÆR]*\\)");
	
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
				// ¼­¹öÀÇ ÀÀ´äÀ» °è¼Ó µé¾î¾ßÇØ¼­ while¹®À» ¹«ÇÑ¹Ýº¹½ÃÅ´
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// ¹ÞÀº Ã¤ÆÃÀ» È­¸é¿¡ Ãâ·Â
//					PrintChatContent printChatContent = new PrintChatContent();
//					printChatContent.method(msg,clientData);
					printChatContent(msg,clientData);
					break;
				case Message.CHATROOM_LOAD:
					// µðÆúÆ®Å×ÀÌºí¸ðµ¨ ¸¸µé¾î¼­ addROw·Î ·Î¿ì Ãß°¡ÇÏ°í JTable¿¡ ºÎÂøÇÏ°í ¹æ ¸ñ·Ï »õ·Î°íÄ§.
//					GetRoomList getRoomList = new GetRoomList();
//					getRoomList.method(msg, clientFrame);
					getRoomList(msg, clientFrame);
					break;
				case Message.CHATROOM_INVITE:
					//»õ·Î Âü°¡ÇÑ ¹æÀ» ¼­¹ö·ÎºÎÅÍ Àü´Þ¹Þ¾Æ¼­ µðÆúÆ®Å×ÀÌºí¸ðµ¨¿¡ Ãß°¡.
					AttendRoom attendRoom =new AttendRoom();
					/*attendRoom.method(msg);
					break;*/
				case Message.CHATROOM_EXIT:
					// »èÁ¦µÈ ³ðÀ» ¼­¹ö·ÎºÎÅÍ Àü´Þ¹Þ´Âµ¥, ÀÌ°ÍÀ» µðÆúÆ®Å×ÀÌºí¸ðµ¨¿¡¼­ Ã£¾Æ¼­ Á¦°Å.
					// ¹æ ¸®½ºÆ®¸¦ »õ·Î°íÄ§
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
	
	
	private void printChatContent(Message<ChatVO> msg, ClientData clientData) {
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
		String chat_content = cVO.getChat_content(); //chatVOÀÇ Ã¤ÆÃ ³»¿ë
		dialog.append(mVO.getMem_name() + " > " +chat_content, pattern);
	}
	
	
	public void getRoomList(Message<ChatVO> msg, ClientFrame clientFrame) {
		ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
		ArrayList<RoomVO> rVOList = new ArrayList<RoomVO>();
		
		//ChatVO·Î ¹ÞÀº ¸®½ºÆ®¸¦ RoomVoÀÇ ¸®½ºÆ®·Î º¯È¯ÇÑ´Ù.
		if(response != null) {
			for(int i=0; i<response.size();i++) {
				RoomVO roomVO = response.get(i).getRoomVO();
				rVOList.add(roomVO);
			}
		}
		
		//RoomVoÀÇ ¸®½ºÆ®¸¦ °¡Áö°í ¹æ ¸ñ·ÏÀ» »õ·Î°íÄ§ÇÑ´Ù..
		clientFrame.getRoomPanel().refreshRoomList(rVOList);
	}
}
