package messenger.client.chat;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import messenger._db.vo.ChatVO;
import messenger.client.Emoticon.GetEmoticon;
import messenger.protocol.Message;

public class SendChat {
	ObjectOutputStream oos = null;
	
	Socket socket = null;
	
	GetEmoticon getEmo = new GetEmoticon();//ÀÌ¸ğÆ¼ÄÜ ºÒ·¯¿Í¾ß ÇÏ´Ï±î
	HashMap<String, JLabel> list = getEmo.GetEmoticon();	//ºÒ·¯¿Â emoticonÀ» hash¸Ê¿¡ ³ÖÀ½
	Pattern p  = Pattern.compile("\\([°¡-ÆR]*\\_[°¡-ÆR]*\\)");
	
	SendChat(Socket socket){//¿©±â¼­ ¼ÒÄÏ Å¬·¡½ºÀÇ ÁÖ¼Ò¹øÁö¸¦ ¹Ş°í
		this.socket = socket;
	}
	
	
	//UIÅØ½ºÆ® ¿¡¸®¾î¿¡¼­ ÀÔ·ÂÇÑ°É ¹Ş¾Æ chatVO¿¡ ³Ö°í ¼­¹ö¿¡ º¸³»¢aÇÔ
	//UI¿¡¼­ ÀÔ·ÂÇÑ °É ChatVO·Î ÆÄ¶ó¹ÌÅÍ·Î ¹ŞÀ½
	//ÀÌ°Íµµ Ã¤ÆÃ Æ÷Æ®¸¦¾²´Ï±î
	//ui¿¡¼­ ÀÔ·ÂÇÑ °É ¼­¹ö¿¡ º¸³»´Â ¸Ş¼Òµå
	/************************
	 * 
	 * @param cVO »ç¿ëÀÚ°¡ ÀÔ·ÂÇÑ Ã¤ÆÃÁ¤º¸°¡ ´ã±ä 
	 * 
	 */
	public void sendChat(ChatVO cVO) {//ÀÌ°Ç ¸ŞÀÎ ½º·¹µå¿¡¼­ µ¹¾Æ°¨
		Message<ChatVO> msg = new Message<ChatVO>();
		msg.setType(msg.CHAT_SEND);
		ArrayList<ChatVO> list = new ArrayList<ChatVO>();
		list.add(cVO);
		msg.setRequest(list);
		
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//¼­¹ö¿¡ º¸³½°É ´Ù½Ã ¹Ş´Â ¸Ş¼Òµå
	
	public ChatVO getChat(Message<ChatVO> msg) {
		ChatVO cVO = msg.getResponse().get(0);
		return cVO;
	}

	//¹ŞÀº Ã¤ÆÃ¿¡ ÀÌ¸ğÆ¼ÄÜ Ã³¸®ÇÏ´Â ¸Ş¼Òµå
	public void findEmoticon(ChatVO cVO,JTextPane jtp) {//uiÀÇ textpaneÀ» ÆÄ¶ó¹ÌÅÍ·Î ¹ŞÀ½,±×¸®°í getChat¿¡¼­ ¹ŞÀº 
											//chatVOÀÇ chatContent¸¦ ¹ŞÀ½
		
		String s = cVO.getChat_content(); //chatVOÀÇ Ã¤ÆÃ ³»¿ë
		StyledDocument doc = jtp.getStyledDocument();
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		
		//Á¤ÇØÁø ÆĞÅÏ' (ÀÌ¸ğÆ¼ÄÜÀÌ¸§) 'ÀÌ °Ë»öµÇ¾ú´Â°¡?
		while(m.find()) {
			//±× ÆĞÅÏÀ» ÀÌ¸ğÆ¼ÄÜ ¸®½ºÆ®¿¡¼­ °Ë»öÇØº»´Ù. nullÀÌ¸é °Ë»ö ½ÇÆĞÇÑ °ÍÀÌ´Ù.
			JLabel label = list.get(m.group());
			
			//ÀÌ¸ğÆ¼ÄÜÀÌ Á¸ÀçÇÏ¸é
			if(label != null) {
				//°Ë»öµÈ ÀÌ¸ğÆ¼ÄÜÀ» »õ·Ó°Ô ÀÎ½ºÅÏ½ºÈ­ ÇÑ´Ù.
				label = new JLabel(label.getIcon());
				String name = m.group(); //ÀÌ¸ğÆ¼ÄÜ ÀÌ¸§
				int idx = builder.indexOf(name); //°Ë»öµÈ ÀÌ¸ğÆ¼ÄÜÀÇ ½ÃÀÛ ÀÎµ¦½º
				try {
					//°Ë»öµÈ ÀÌ¸ğÆ¼ÄÜÀÇ ÀÎµ¦½º°¡ 0ÀÌ ¾Æ´Ï´Ù.-> ÀÌ¸ğÆ¼ÄÜ ¾Õ¿¡ ¹®ÀÚ¿­ÀÌ Á¸ÀçÇÑ´Ù.
					//±×·¯¹Ç·Î ¸ÕÀú ¾ÕÀÇ ¹®ÀÚ¿­À» ¸ÕÀú È­¸é¿¡ Ãâ·ÂÇØÁØ´Ù.
					if(idx > 0) {
						//builder.substring(0,idx-1) : ÀÌ¸ğÆ¼ÄÜ ¾Õ±îÁöÀÇ ºÎºĞ ¹®ÀÚ¿­.
						doc.insertString(doc.getLength(), builder.substring(0, idx-1), null);
						
						//selectAll : JTextPane¾È¿¡ Æ÷ÇÔµÈ ¸ğµç ÅØ½ºÆ®¸¦ ¼±ÅÃÇÑ´Ù.
						//getSelectionEnd : ¼±ÅÃµÈ ÅØ½ºÆ®¿¡¼­ ¸¶Áö¸· À§Ä¡
						//setSelectionStart : ÅØ½ºÆ® ¼±ÅÃ ¿µ¿ªÀ» ¿Å±è. ¾Æ·¡ ÄÚµå´Â ¸¶Áö¸· ºÎºĞÀ¸·Î ¿Å°ÜÁÜ.
						jtp.selectAll();
						jtp.setSelectionStart(jtp.getSelectionEnd());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				//¾ÕÀÇ ¹®ÀÚ¿­À» Ãâ·ÂÇÑ ´ÙÀ½¿¡´Â ÀÌ¸ğÆ¼ÄÜÀ» Ãâ·ÂÇØÁØ´Ù.
				jtp.insertComponent(label);
				//±âÁ¸ Ã¤ÆÃ³»¿ë¿¡¼­ À§¿¡¼­ Ãâ·ÂÇÑ ºÎºĞÀº Áö¿öÁà¼­ µŞ ºÎºĞÀ» ¾ÕÀ¸·Î ¶¯°ÜÁØ´Ù.
				builder.replace(0, idx+name.length(), "");
				
				//¼öÁ¤ÇÑ ¹®ÀÚ¿­·Î Matcher¸¦ »õ·Î ¼³Á¤ÇØÁØ´Ù.
				m = p.matcher(builder);
				jtp.selectAll();
				jtp.setSelectionStart(jtp.getSelectionEnd());
			}
		}
		//ÀÌ¸ğÆ¼ÄÜ ÃßÃâÀÛ¾÷ÀÌ ³¡³­ÈÄ ³²Àº ¹®ÀÚ¿­À» µÚ¿¡ ºÙ¿©ÁØ´Ù.
		try {
			doc.insertString(doc.getLength(), builder+"\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jtp.selectAll();
		jtp.setSelectionStart(jtp.getSelectionEnd());
		
	}
	
	
	


}
