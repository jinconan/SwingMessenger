package messenger.client.chat;

import java.util.ArrayList;
import java.util.regex.Pattern;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.client.view.ChatDialog;
import messenger.client.view.ClientData;
import messenger.protocol.Message;

public class PrintChatContent {
	Pattern pattern  = Pattern.compile("\\([°¡-ÆR]*\\_[°¡-ÆR]*\\)");
	
	public void method(Message<ChatVO> msg, ClientData clientData) {
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
	
}
