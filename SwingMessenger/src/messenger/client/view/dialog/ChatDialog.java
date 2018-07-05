package messenger.client.view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import messenger._protocol.Message;
import messenger.client.view.ClientData;

public class ChatDialog extends JDialog implements ActionListener, KeyListener{
	ClientData			clientData;
	RoomVO				room;

	//ä�ù�
	JTextField 			jtf_South  = new JTextField();
	JTextPane 			jtp_Center = new JTextPane();
	JPanel				jp_North   = new JPanel();
	JPanel				jp_South   = new JPanel();
	JToolBar			jtb_North  = new JToolBar();
	JButton				jbtn_emti  = new JButton("�̸�Ƽ��");
	JButton				jbtn_exit  = new JButton("������");
	JButton				jbtn_jun   = new JButton("����");
	JScrollPane 		jsp_North  = new JScrollPane(jtb_North);
	JScrollPane 		jsp_Center = new JScrollPane(jtp_Center,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
															   ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JScrollPane 		jsp_South  = new JScrollPane(jtf_South,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
															  ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//�̸�Ƽ�� â
	JDialog				jd_emoticon = new JDialog(this, false);
	JPanel				jp_emoticon;
	JScrollPane 		jsp_emoticon = new JScrollPane();
	
	public ChatDialog(ClientData clientData, RoomVO room) {
		this.clientData = clientData;
		this.room = room;
		this.setSize(360,550);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
		String title = (room != null) ? room.getRoom_no() + "�� ��) " + room.getRoom_title() : "null";
		this.setTitle(title);
		jbtn_emti.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_jun.addActionListener(this);
		jtf_South.addKeyListener(this);
		jtb_North.setLayout(new GridLayout(1,3));
		jtb_North.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_emti);
		jbtn_emti.setBackground(new Color(126, 195, 237));
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
		//�̸�Ƽ�� ���̾�α� Ȱ��ȭ.
		if(e.getActionCommand().equals("�̸�Ƽ��")) {
			jd_emoticon.setVisible(true);
		}
		
		else if(e.getActionCommand().equals("������")) {
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
		
		else if(e.getActionCommand().equals("����")) {
			if(jtf_South.getText().length() > 0) {
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
	}
	
	/**
	 * �����κ��� ���� ä�� �޽����� ���̾�α� â�� JTextPane�� ����ϴ� �޼ҵ�. �̶�, �̸�Ƽ�ܵ� �ؼ��ؼ� ������ش�.
	 * @param chat_content : �����κ��� ���� ä�ø޽���
	 * @param pattern : �̸�Ƽ�� ����� ����
	 */
	public synchronized void append(String chat_content, Pattern pattern) {
		try {
			StyledDocument doc = jtp_Center.getStyledDocument();
			StringBuilder builder = new StringBuilder(chat_content);
			Matcher m = pattern.matcher(builder);
			
			//������ ����' (�̸�Ƽ���̸�) '�� �˻��Ǿ��°�?
			while(m.find()) {
				//�� ������ �̸�Ƽ�� ����Ʈ���� �˻��غ���. null�̸� �˻� ������ ���̴�.
				JLabel label = clientData.getEmoticon(m.group());
				
				//�̸�Ƽ���� �����ϸ�
				if(label != null) {
					//�˻��� �̸�Ƽ���� ���Ӱ� �ν��Ͻ�ȭ �Ѵ�.
					label = new JLabel(label.getIcon());
					String name = m.group(); //�̸�Ƽ�� �̸�
					int idx = builder.indexOf(name); //�˻��� �̸�Ƽ���� ���� �ε���
					try {
						//�˻��� �̸�Ƽ���� �ε����� 0�� �ƴϴ�.-> �̸�Ƽ�� �տ� ���ڿ��� �����Ѵ�.
						//�׷��Ƿ� ���� ���� ���ڿ��� ���� ȭ�鿡 ������ش�.
						if(idx > 0) {
							//builder.substring(0,idx-1) : �̸�Ƽ�� �ձ����� �κ� ���ڿ�.
							doc.insertString(doc.getLength(), builder.substring(0, idx), null);
							
							//selectAll : JTextPane�ȿ� ���Ե� ��� �ؽ�Ʈ�� �����Ѵ�.
							//getSelectionEnd : ���õ� �ؽ�Ʈ���� ������ ��ġ
							//setSelectionStart : �ؽ�Ʈ ���� ������ �ű�. �Ʒ� �ڵ�� ������ �κ����� �Ű���.
							jtp_Center.selectAll();
							jtp_Center.setSelectionStart(jtp_Center.getSelectionEnd());
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					//���� ���ڿ��� ����� �������� �̸�Ƽ���� ������ش�.
					jtp_Center.insertComponent(label);
					//���� ä�ó��뿡�� ������ ����� �κ��� �����༭ �� �κ��� ������ �����ش�.
					builder.replace(0, idx+name.length(), "");
					
					//������ ���ڿ��� Matcher�� ���� �������ش�.
					m = pattern.matcher(builder);
					jtp_Center.selectAll();
					jtp_Center.setSelectionStart(jtp_Center.getSelectionEnd());
				}
			}
			//�̸�Ƽ�� �����۾��� ������ ���� ���ڿ��� �ڿ� �ٿ��ش�.
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

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() =='\n') {
			if(jtf_South.getText().length() > 0) {
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
		
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	
}
