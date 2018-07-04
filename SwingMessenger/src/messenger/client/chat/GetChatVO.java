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
				// ¼­¹öÀÇ ÀÀ´äÀ» °è¼Ó µé¾î¾ßÇØ¼­ while¹®À» ¹«ÇÑ¹İº¹½ÃÅ´
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// ¹ŞÀº Ã¤ÆÃÀ» È­¸é¿¡ Ãâ·Â
					printChatContent(msg);
					break;
				case Message.CHATROOM_LOAD:
					// µğÆúÆ®Å×ÀÌºí¸ğµ¨ ¸¸µé¾î¼­ addROw·Î ·Î¿ì Ãß°¡ÇÏ°í JTable¿¡ ºÎÂøÇÏ°í ¹æ ¸ñ·Ï »õ·Î°íÄ§.
					getRoomList(msg);
					break;
				case Message.CHATROOM_INVITE:
					//»õ·Î Âü°¡ÇÑ ¹æÀ» ¼­¹ö·ÎºÎÅÍ Àü´Ş¹Ş¾Æ¼­ µğÆúÆ®Å×ÀÌºí¸ğµ¨¿¡ Ãß°¡.
					attendRoom(msg);
					break;
				case Message.CHATROOM_EXIT:
					// »èÁ¦µÈ ³ğÀ» ¼­¹ö·ÎºÎÅÍ Àü´Ş¹Ş´Âµ¥, ÀÌ°ÍÀ» µğÆúÆ®Å×ÀÌºí¸ğµ¨¿¡¼­ Ã£¾Æ¼­ Á¦°Å.
					// ¹æ ¸®½ºÆ®¸¦ »õ·Î°íÄ§
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
	 * ¹ŞÀº Ã¤ÆÃ ¸Ş¼¼Áö¸¦ Æ¯Á¤ ¹æ¿¡ Ãâ·ÂÇÑ´Ù.
	 * @param msg ¼­¹ö·ÎºÎÅÍ ¹ŞÀº ¸Ş¼¼Áö
	 * @param clientData Å¬¶óÀÌ¾ğÆ®ÀÇ Á¤º¸
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
		String chat_content = cVO.getChat_content(); //chatVOÀÇ Ã¤ÆÃ ³»¿ë
		dialog.append(mVO.getMem_name() + " > " +chat_content, pattern);
	}
	
	/**
	 * ¼­¹ö·ÎºÎÅÍ ¹ŞÀº ¹æ ¸®½ºÆ®¸¦ È­¸é¿¡ Ãâ·ÂÇÑ´Ù.
	 * @param msg ¼­¹ö·ÎºÎÅÍ ¹ŞÀº ¸Ş¼¼Áö
	 * @param clientFrame Å¬¶óÀÌ¾ğÆ®ÀÇ Á¤º¸
	 */
	public void getRoomList(Message<ChatVO> msg) {
		ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
		ArrayList<RoomVO> rVOList = new ArrayList<RoomVO>();
		
		//ChatVO·Î ¹ŞÀº ¸®½ºÆ®¸¦ RoomVoÀÇ ¸®½ºÆ®·Î º¯È¯ÇÑ´Ù.
		if(response != null) {
			for(int i=0; i<response.size();i++) {
				RoomVO roomVO = response.get(i).getRoomVO();
				rVOList.add(roomVO);
			}
		}
		
		//RoomVoÀÇ ¸®½ºÆ®¸¦ °¡Áö°í ¹æ ¸ñ·ÏÀ» »õ·Î°íÄ§ÇÑ´Ù..
		clientFrame.refreshRoomList(rVOList);
	}
	
	
	public void attendRoom(Message<ChatVO> msg) {
		//Âü°¡ÇÑ ¹æÀÇ ´ÙÀÌ¾ó·Î±×¸¦ È­¸é¿¡ ¶ç¿ì°í ¹æ ¸®½ºÆ® °»½Å ¸Ş½ÃÁö Àü¼Û
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
