package messenger.test;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import messenger.protocol.Message;
import messenger.protocol.Port;

/**
 * Á¤±Ô½Ä Å×½ºÆ®
 * ÀÌ¸ğÆ¼ÄÜ ¼­¹ö¿¡¼­ ÀÌ¸ğÆ¼ÄÜ¸®½ºÆ®¸¦ ¸ÕÀú ºÒ·¯¿Í¼­ ¸®½ºÆ®¸¦ Çü¼ºÇÑ´Ù.
 * ÀÌÈÄ Å×½ºÆ® ÇÏ±â¸¦ ¿øÇÏ´Â ¹®ÀÚ¿­À» ¼±¾ğÇØ¼­ ¸ŞÀÎ¿¡¼­ ½ÇÇàÇØ¼­ Å×½ºÆ®ÇÏ¸é µÈ´Ù.
 * 
 * 
 * @author 518
 *
 */
public class RegexTest extends JFrame {
	static HashMap<String, JLabel> list = new HashMap<String, JLabel>();
	Pattern p  = Pattern.compile("\\([°¡-ÆR]*\\_[°¡-ÆR]*\\)");
	JTextPane jtp = new JTextPane();
	RegexTest() {	
		try {
			Socket socket = new Socket("localhost",Port.EMOTICON);
			
			Message<JLabel> msg = new Message<JLabel>();
			msg.setType(Message.EMOTICON_LOAD);
			
			try (
				OutputStream os = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os); 
			) {
				oos.writeObject(msg);
				oos.flush();
				
				try (InputStream is = socket.getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is);
						ObjectInputStream ois = new ObjectInputStream(bis);
				) {
					msg = (Message<JLabel>)ois.readObject();
					ArrayList<JLabel> response = (ArrayList<JLabel>)msg.getResponse();
					for(JLabel label : response) {
						list.put(label.getText(), label);
					}

				} catch(Exception e) {
					e.printStackTrace();
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		jtp.setEditable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);;
		this.setSize(500,500);
		this.add(jtp,BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	//ÀÌ¸ğÆ¼ÄÜÀ» ÀüºÎ ÃßÃâÇÏ¿© ¸ÕÀú Ãâ·ÂÇÏ°í ±× ´ÙÀ½¿¡ Ã¤ÆÃÀ» Ãâ·Â
	void replaceEmoticonPrototype(String s) {
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		while(m.find()) {
			//ÆĞÅÏ°ú ÀÏÄ¡ÇÏ´Â ºÎºĞÀ¸·Î ÀÌ¸ğÆ¼ÄÜ ¸®½ºÆ®¿¡¼­ ÀÌ¸ğÆ¼ÄÜ °Ë»ö.
			JLabel label = list.get(m.group());
			//ÀÌ¸ğÆ¼ÄÜÀÌ Á¸ÀçÇÏ¸é
			if(label != null) {
				//±× ÀÌ¸ğÆ¼ÄÜ ÀÌ¹ÌÁö¸¦ °¡Áö°í »õ·Î ÀÎ½ºÅÏ½ºÈ­ÇÏ¿© JTextPane¿¡ ºÎÂø.
				label = new JLabel(label.getIcon());
				jtp.insertComponent(label);
				//ÀÌ¸ğÆ¼ÄÜ °Ë»ö¿¡ »ç¿ëµÈ ºÎºĞÀ» ÀÔ·Â¹ŞÀº ¹®ÀÚ¿­ ³»¿¡¼­ Á¦°Å
				String name = m.group();
				int idx = builder.indexOf(name);
				builder.replace(idx, idx+name.length(), "");
				//¼öÁ¤ÇÑ ¹®ÀÚ¿­·Î Matcher¸¦ »õ·Î ¼³Á¤.
				m = p.matcher(builder);
			}
		}
		
		//Ã¤ÆÃÀ» StyledDocument¿¡ ÀÌ¾îºÙÀÌ°í Ä¿¼­¸¦ ³¡À¸·Î ¿Å±ä´Ù.
		StyledDocument doc = jtp.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), builder+"\n", null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jtp.selectAll();
		jtp.setSelectionStart(jtp.getSelectionEnd());
	}
	
	//ÀÌ¸ğÆ¼ÄÜÀ» ¿øÇÏ´Â À§Ä¡¿¡ Ãâ·ÂÇÏ´Â ¹öÀü.
	//ÀÌ¸ğÆ¼ÄÜÀ» ¹ß°ßÇÏ¸é ÀÌ¸ğÆ¼ÄÜ ¾Õ±îÁöÀÇ ¹®ÀÚ¿­À» ¸ÕÀú Ãâ·ÂÇÏ°í Ä¿¼­¸¦ µÚ·Î ÀÌµ¿½ÃÅ²´ÙÀ½¿¡ ÀÌ¸ğÆ¼ÄÜÀ» Âï¾îÁÜ
	//Áï, (ÅØ½ºÆ® + ÀÌ¸ğÆ¼ÄÜ) ¹æ½ÄÀ¸·Î ¸Å ¹İº¹½Ã¸¶´Ù Ãâ·ÂÇØÁÜ.
	//¹İº¹¹® Å»Ãâ ÈÄ ¸¶Áö¸·Àº ¹®ÀÚ¿­¸¸ ³²¾ÒÀ¸¹Ç·Î ¹®ÀÚ¿­¸¸ Ãâ·Â.
	void replaceEmoticon(String s) {
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
	
	public static void main(String[] args) {
		RegexTest frame = new RegexTest();
		String s = "±èÅÂÈñ : ¤±¤·¤«¤¤·ÁÀç¤Á (³ª¾ß_³î¶÷)    (¹ÙÀÌ_ÁøÂ¥)     ¤¤¤©¤¤¤·¤©¤¤¤© (³ª¾ß_½½ÇÄ)°¡³ª´Ù¶ó¸¶¹Ù»ç¾ÆÀÚÂ÷Ä«Å¸ÆÄÇÏ";
		frame.replaceEmoticon(s);
		s = "±èÅÂÈñ : ¤±¤·¤«¤¤·ÁÀç¤Á (½Ãº£¸°_Âî¸´)    (¹ÙÀÌ_ÁøÂ¥)     ¤¤¤©¤¤¤·¤©¤¤¤© (³ª¾ß_½½ÇÄ)abcdefghijklmnopqrstuvwxyz";
		frame.replaceEmoticon(s);
		s = "±èÅÂÈñ : (³ª¾ß_³î¶÷)(³ª¾ß_½½ÇÄ)";
		frame.replaceEmoticon(s);
		s = "±èÅÂÈñ : ¤±¤·¤«¤¤·ÁÀç¤Á (³ª¾ß_³î¶÷)    (¹ÙÀÌ_ÁøÂ¥)     ¤¤¤©¤¤¤·¤©¤¤¤© (³ª¾ß_½½ÇÄ)";
		frame.replaceEmoticon(s);
		


		
	}

}
