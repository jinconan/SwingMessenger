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
	JButton				jbtn_jun   = new JButton("����");
	JButton				jbtn_inv   = new JButton("ģ���ʴ�");
	JButton				jbtn_emti  = new JButton("�̸�Ƽ��");
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
		if(e.getActionCommand().equals("����")) {
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
		if(e.getActionCommand().equals("�̸�Ƽ��")) {
			new EmoticonDialog(clientData, this);
		}
		//Invited
		if(e.getActionCommand().equals("ģ���ʴ�")) {
			System.out.println("�ʴ����");
			//Invited();
		}
	}
	
	/**
	 * �����κ��� ���� ä�� �޽����� ���̾�α� â�� JTextPane�� ����ϴ� �޼ҵ�. �̶�, �̸�Ƽ�ܵ� �ؼ��ؼ� ������ش�.
	 * @param chat_content : �����κ��� ���� ä�ø޽���
	 * @param pattern : �̸�Ƽ�� ����� ����
	 */
	public synchronized void append(String chat_content, Pattern pattern) {
		try {
			StyledDocument doc = jtp_chat.getStyledDocument();
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
							doc.insertString(doc.getLength(), builder.substring(0, idx-1), null);
							
							//selectAll : JTextPane�ȿ� ���Ե� ��� �ؽ�Ʈ�� �����Ѵ�.
							//getSelectionEnd : ���õ� �ؽ�Ʈ���� ������ ��ġ
							//setSelectionStart : �ؽ�Ʈ ���� ������ �ű�. �Ʒ� �ڵ�� ������ �κ����� �Ű���.
							jtp_chat.selectAll();
							jtp_chat.setSelectionStart(jtp_chat.getSelectionEnd());
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					//���� ���ڿ��� ����� �������� �̸�Ƽ���� ������ش�.
					jtp_chat.insertComponent(label);
					//���� ä�ó��뿡�� ������ ����� �κ��� �����༭ �� �κ��� ������ �����ش�.
					builder.replace(0, idx+name.length(), "");
					
					//������ ���ڿ��� Matcher�� ���� �������ش�.
					m = pattern.matcher(builder);
					jtp_chat.selectAll();
					jtp_chat.setSelectionStart(jtp_chat.getSelectionEnd());
				}
			}
			//�̸�Ƽ�� �����۾��� ������ ���� ���ڿ��� �ڿ� �ٿ��ش�.
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
