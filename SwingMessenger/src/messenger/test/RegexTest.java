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
 * ���Խ� �׽�Ʈ
 * �̸�Ƽ�� �������� �̸�Ƽ�ܸ���Ʈ�� ���� �ҷ��ͼ� ����Ʈ�� �����Ѵ�.
 * ���� �׽�Ʈ �ϱ⸦ ���ϴ� ���ڿ��� �����ؼ� ���ο��� �����ؼ� �׽�Ʈ�ϸ� �ȴ�.
 * 
 * 
 * @author 518
 *
 */
public class RegexTest extends JFrame {
	static HashMap<String, JLabel> list = new HashMap<String, JLabel>();
	Pattern p  = Pattern.compile("\\([��-�R]*\\_[��-�R]*\\)");
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
	
	//�̸�Ƽ���� ���� �����Ͽ� ���� ����ϰ� �� ������ ä���� ���
	void replaceEmoticonPrototype(String s) {
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		while(m.find()) {
			//���ϰ� ��ġ�ϴ� �κ����� �̸�Ƽ�� ����Ʈ���� �̸�Ƽ�� �˻�.
			JLabel label = list.get(m.group());
			//�̸�Ƽ���� �����ϸ�
			if(label != null) {
				//�� �̸�Ƽ�� �̹����� ������ ���� �ν��Ͻ�ȭ�Ͽ� JTextPane�� ����.
				label = new JLabel(label.getIcon());
				jtp.insertComponent(label);
				//�̸�Ƽ�� �˻��� ���� �κ��� �Է¹��� ���ڿ� ������ ����
				String name = m.group();
				int idx = builder.indexOf(name);
				builder.replace(idx, idx+name.length(), "");
				//������ ���ڿ��� Matcher�� ���� ����.
				m = p.matcher(builder);
			}
		}
		
		//ä���� StyledDocument�� �̾���̰� Ŀ���� ������ �ű��.
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
	
	//�̸�Ƽ���� ���ϴ� ��ġ�� ����ϴ� ����.
	//�̸�Ƽ���� �߰��ϸ� �̸�Ƽ�� �ձ����� ���ڿ��� ���� ����ϰ� Ŀ���� �ڷ� �̵���Ų������ �̸�Ƽ���� �����
	//��, (�ؽ�Ʈ + �̸�Ƽ��) ������� �� �ݺ��ø��� �������.
	//�ݺ��� Ż�� �� �������� ���ڿ��� �������Ƿ� ���ڿ��� ���.
	void replaceEmoticon(String s) {
		StyledDocument doc = jtp.getStyledDocument();
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		
		//������ ����' (�̸�Ƽ���̸�) '�� �˻��Ǿ��°�?
		while(m.find()) {
			//�� ������ �̸�Ƽ�� ����Ʈ���� �˻��غ���. null�̸� �˻� ������ ���̴�.
			JLabel label = list.get(m.group());
			
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
						jtp.selectAll();
						jtp.setSelectionStart(jtp.getSelectionEnd());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				//���� ���ڿ��� ����� �������� �̸�Ƽ���� ������ش�.
				jtp.insertComponent(label);
				//���� ä�ó��뿡�� ������ ����� �κ��� �����༭ �� �κ��� ������ �����ش�.
				builder.replace(0, idx+name.length(), "");
				
				//������ ���ڿ��� Matcher�� ���� �������ش�.
				m = p.matcher(builder);
				jtp.selectAll();
				jtp.setSelectionStart(jtp.getSelectionEnd());
			}
		}
		//�̸�Ƽ�� �����۾��� ������ ���� ���ڿ��� �ڿ� �ٿ��ش�.
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
		String s = "������ : ������������� (����_���)    (����_��¥)     �������������� (����_����)�����ٶ󸶹ٻ������īŸ����";
		frame.replaceEmoticon(s);
		s = "������ : ������������� (�ú���_�)    (����_��¥)     �������������� (����_����)abcdefghijklmnopqrstuvwxyz";
		frame.replaceEmoticon(s);
		s = "������ : (����_���)(����_����)";
		frame.replaceEmoticon(s);
		s = "������ : ������������� (����_���)    (����_��¥)     �������������� (����_����)";
		frame.replaceEmoticon(s);
		


		
	}

}
