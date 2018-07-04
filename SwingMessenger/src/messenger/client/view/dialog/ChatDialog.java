package messenger.client.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import messenger.client.view.ClientData;
import messenger.protocol.Message;

public class ChatDialog extends JDialog implements ActionListener{
	ClientData			clientData;
	RoomVO				room;

	//채팅방
	JTextField 			jtf_South  = new JTextField();
	JTextPane 			jtp_Center = new JTextPane();
	JPanel				jp_North   = new JPanel();
	JPanel				jp_South   = new JPanel();
	JToolBar			jtb_North  = new JToolBar();
	JButton				jbtn_emti  = new JButton("이모티콘");
	JButton				jbtn_inv   = new JButton("친구초대");
	JButton				jbtn_exit  = new JButton("나가기");
	JButton				jbtn_jun   = new JButton("전송");
	JScrollPane 		jsp_North  = new JScrollPane(jtb_North);
	JScrollPane 		jsp_Center = new JScrollPane(jtp_Center,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
															   ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane 		jsp_South  = new JScrollPane(jtf_South,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
															  ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	//이모티콘 창
	JDialog				jd_emoticon = new JDialog(this, false);
	JPanel				jp_emoticon;
	JScrollPane 		jsp_emoticon = new JScrollPane();
	
	public ChatDialog(ClientData clientData, RoomVO room) {
		this.clientData = clientData;
		this.room = room;
		this.setSize(360,550);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		String title = (room != null) ? room.getRoom_no() + "번 방) " + room.getRoom_title() : "null";
		this.setTitle(title);
		jbtn_emti.addActionListener(this);
		jbtn_inv.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_jun.addActionListener(this);
		
		jtb_North.setLayout(new GridLayout(1,3));
		jtb_North.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_emti);
		jbtn_emti.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_inv);
		jbtn_inv.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_exit);
		jbtn_exit.setBackground(new Color(126, 195, 237));
		
		jp_South.setLayout(new BorderLayout());
		jbtn_jun.setBackground(new Color(126, 195, 237));
		jp_South.add("Center",jsp_South);
		jp_South.add("East",jbtn_jun);
		
		jtp_Center.setEditable(false);
		this.add("North", jtb_North);
		this.add("Center",jsp_Center);
		this.add("South",jp_South);
		this.setVisible(false);
		
		jp_emoticon = (clientData != null) ? clientData.getEmoticonPanel(jtf_South) : new JPanel();
		jsp_emoticon = new JScrollPane(jp_emoticon, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jd_emoticon.setLayout(new BorderLayout());
		jd_emoticon.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		jd_emoticon.setTitle(title);
		jd_emoticon.setSize(360,550);
		jd_emoticon.add("Center",jsp_emoticon);
		jd_emoticon.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//이모티콘 다이얼로그 활성화.
		if(e.getActionCommand().equals("이모티콘")) {
			jd_emoticon.setVisible(true);
		}

		else if(e.getActionCommand().equals("친구초대")) {
			System.out.println("초대실행");
			//Invited();
		}
		
		else if(e.getActionCommand().equals("나가기")) {
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, room, null, null, clientData.getMyData()));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_EXIT, request, null);
			
			jd_emoticon.setVisible(false);
			this.setVisible(false);
			
			try {
				clientData.getOut().writeObject(msg);
				clientData.getOut().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
		else if(e.getActionCommand().equals("전송")) {
			ArrayList<ChatVO> request = new ArrayList<ChatVO>();
			request.add(new ChatVO(0, room, jtf_South.getText(), null, clientData.getMyData()));
			Message<ChatVO> msg = new Message<ChatVO>(Message.CHAT_SEND, request, null);
			try {
				clientData.getOut().writeObject(msg);
				clientData.getOut().flush();
				jtf_South.setText("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 서버로부터 받은 채팅 메시지를 다이얼로그 창의 JTextPane에 출력하는 메소드. 이때, 이모티콘도 해석해서 출력해준다.
	 * @param chat_content : 서버로부터 받은 채팅메시지
	 * @param pattern : 이모티콘 추출용 패턴
	 */
	public synchronized void append(String chat_content, Pattern pattern) {
		try {
			StyledDocument doc = jtp_Center.getStyledDocument();
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
							doc.insertString(doc.getLength(), builder.substring(0, idx), null);
							
							//selectAll : JTextPane안에 포함된 모든 텍스트를 선택한다.
							//getSelectionEnd : 선택된 텍스트에서 마지막 위치
							//setSelectionStart : 텍스트 선택 영역을 옮김. 아래 코드는 마지막 부분으로 옮겨줌.
							jtp_Center.selectAll();
							jtp_Center.setSelectionStart(jtp_Center.getSelectionEnd());
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					//앞의 문자열을 출력한 다음에는 이모티콘을 출력해준다.
					jtp_Center.insertComponent(label);
					//기존 채팅내용에서 위에서 출력한 부분은 지워줘서 뒷 부분을 앞으로 땡겨준다.
					builder.replace(0, idx+name.length(), "");
					
					//수정한 문자열로 Matcher를 새로 설정해준다.
					m = pattern.matcher(builder);
					jtp_Center.selectAll();
					jtp_Center.setSelectionStart(jtp_Center.getSelectionEnd());
				}
			}
			//이모티콘 추출작업이 끝난후 남은 문자열을 뒤에 붙여준다.
			try {
				doc.insertString(doc.getLength(), builder+"\n", null);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			jtp_Center.selectAll();
			jtp_Center.setSelectionStart(jtp_Center.getSelectionEnd());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ChatDialog c = new ChatDialog(null, null);
		c.setVisible(true);
	}
}
