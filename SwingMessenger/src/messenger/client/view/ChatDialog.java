package messenger.client.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import messenger._db.vo.ChatVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;

public class ChatDialog extends JDialog implements ActionListener{
	
	JPanel				jp_chatF   = new JPanel();
	JTextField 			jtf_chat   = new JTextField();
	JTextPane 			jtp_chat   = new JTextPane();
	JButton				jbtn_jun   = new JButton("전송");
	JButton				jbtn_inv   = new JButton("친구초대");
	JButton				jbtn_emti  = new JButton("이모티콘");
	JToolBar			jtb_chat   = new JToolBar();
	
	JScrollPane 		jsp_chatA  = new JScrollPane(jtp_chat);
	JScrollPane 		jsp_chatF  = new JScrollPane(jtf_chat);
	
	ClientData			clientData;
	RoomVO				room;
	
	public ChatDialog(ClientData clientData, RoomVO room) {
		this.clientData = clientData;
		this.room = room;
		
		this.setTitle(room.getRoom_title());
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		jbtn_inv.addActionListener(this);
		jbtn_emti.addActionListener(this);
		jbtn_jun.addActionListener(this);
		
		this.setSize(360,550);
		this.setVisible(false);
		jsp_chatA.setVisible(true);
		jsp_chatF.setVisible(true);
		jtb_chat.setVisible(true);
		this.add("North", jtb_chat);
		this.add("Center",jsp_chatA);
		this.add("South",jsp_chatF);
		jtb_chat.setLayout(new GridLayout(1,4,10,10));
		jtb_chat.add(jbtn_emti);
		jtb_chat.add(jbtn_inv);
		jtb_chat.add(jbtn_jun);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("전송")) {
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, room, jtf_chat.getText(), null, clientData.getMyData()));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHAT_SEND, request, null);
			try {
				clientData.getOut().writeObject(msg);
				clientData.getOut().flush();
				jtf_chat.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getActionCommand().equals("이모티콘")) {
			new EmoticonDialog(clientData, this);
		}
		//Invited
		if(e.getActionCommand().equals("친구초대")) {
			System.out.println("초대실행");
			//Invited();
		}
	}
	
	/**
	 * 서버로부터 받은 채팅 메시지를 다이얼로그 창의 JTextPane에 출력하는 메소드. 이때, 이모티콘도 해석해서 출력해준다.
	 * @param chat_content : 서버로부터 받은 채팅메시지
	 * @param pattern : 이모티콘 추출용 패턴
	 */
	public synchronized void append(String chat_content, Pattern pattern) {
		try {
			StyledDocument doc = jtp_chat.getStyledDocument();
			StringBuilder builder = new StringBuilder(chat_content);
			Matcher m = pattern.matcher(builder);
			
			//정해진 패턴' (이모티콘이름) '이 검색되었는가?
			while(m.find()) {
				//그 패턴을 이모티콘 리스트에서 검색해본다. null이면 검색 실패한 것이다.
				JLabel label = clientData.getEmoticon(m.group());
				
				//이모티콘이 존재하면
				if(label != null) {
					//검색된 이모티콘을 새롭게 인스턴스화 한다.
					label = new JLabel(label.getIcon());
					String name = m.group(); //이모티콘 이름
					int idx = builder.indexOf(name); //검색된 이모티콘의 시작 인덱스
					try {
						//검색된 이모티콘의 인덱스가 0이 아니다.-> 이모티콘 앞에 문자열이 존재한다.
						//그러므로 먼저 앞의 문자열을 먼저 화면에 출력해준다.
						if(idx > 0) {
							//builder.substring(0,idx-1) : 이모티콘 앞까지의 부분 문자열.
							doc.insertString(doc.getLength(), builder.substring(0, idx-1), null);
							
							//selectAll : JTextPane안에 포함된 모든 텍스트를 선택한다.
							//getSelectionEnd : 선택된 텍스트에서 마지막 위치
							//setSelectionStart : 텍스트 선택 영역을 옮김. 아래 코드는 마지막 부분으로 옮겨줌.
							jtp_chat.selectAll();
							jtp_chat.setSelectionStart(jtp_chat.getSelectionEnd());
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					//앞의 문자열을 출력한 다음에는 이모티콘을 출력해준다.
					jtp_chat.insertComponent(label);
					//기존 채팅내용에서 위에서 출력한 부분은 지워줘서 뒷 부분을 앞으로 땡겨준다.
					builder.replace(0, idx+name.length(), "");
					
					//수정한 문자열로 Matcher를 새로 설정해준다.
					m = pattern.matcher(builder);
					jtp_chat.selectAll();
					jtp_chat.setSelectionStart(jtp_chat.getSelectionEnd());
				}
			}
			//이모티콘 추출작업이 끝난후 남은 문자열을 뒤에 붙여준다.
			try {
				doc.insertString(doc.getLength(), builder+"\n", null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			jtp_chat.selectAll();
			jtp_chat.setSelectionStart(jtp_chat.getSelectionEnd());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
