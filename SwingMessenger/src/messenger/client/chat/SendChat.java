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
	
	GetEmoticon getEmo = new GetEmoticon();//�̸�Ƽ�� �ҷ��;� �ϴϱ�
	HashMap<String, JLabel> list = getEmo.GetEmoticon();	//�ҷ��� emoticon�� hash�ʿ� ����
	Pattern p  = Pattern.compile("\\([��-�R]*\\_[��-�R]*\\)");
	
	SendChat(Socket socket){//���⼭ ���� Ŭ������ �ּҹ����� �ް�
		this.socket = socket;
	}
	
	
	//UI�ؽ�Ʈ ������� �Է��Ѱ� �޾� chatVO�� �ְ� ������ �����a��
	//UI���� �Է��� �� ChatVO�� �Ķ���ͷ� ����
	//�̰͵� ä�� ��Ʈ�����ϱ�
	//ui���� �Է��� �� ������ ������ �޼ҵ�
	/************************
	 * 
	 * @param cVO ����ڰ� �Է��� ä�������� ��� 
	 * 
	 */
	public void sendChat(ChatVO cVO) {//�̰� ���� �����忡�� ���ư�
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
	
	//������ ������ �ٽ� �޴� �޼ҵ�
	
	public ChatVO getChat(Message<ChatVO> msg) {
		ChatVO cVO = msg.getResponse().get(0);
		return cVO;
	}

	//���� ä�ÿ� �̸�Ƽ�� ó���ϴ� �޼ҵ�
	public void findEmoticon(ChatVO cVO,JTextPane jtp) {//ui�� textpane�� �Ķ���ͷ� ����,�׸��� getChat���� ���� 
											//chatVO�� chatContent�� ����
		
		String s = cVO.getChat_content(); //chatVO�� ä�� ����
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
	
	
	


}
