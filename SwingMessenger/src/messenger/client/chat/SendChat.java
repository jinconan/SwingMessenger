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
	
	GetEmoticon getEmo = new GetEmoticon();//이모티콘 불러와야 하니까
	HashMap<String, JLabel> list = getEmo.GetEmoticon();	//불러온 emoticon을 hash맵에 넣음
	Pattern p  = Pattern.compile("\\([가-힣]*\\_[가-힣]*\\)");
	
	SendChat(Socket socket){//여기서 소켓 클래스의 주소번지를 받고
		this.socket = socket;
	}
	
	
	//UI텍스트 에리어에서 입력한걸 받아 chatVO에 넣고 서버에 보내줭함
	//UI에서 입력한 걸 ChatVO로 파라미터로 받음
	//이것도 채팅 포트를쓰니까
	//ui에서 입력한 걸 서버에 보내는 메소드
	/************************
	 * 
	 * @param cVO 사용자가 입력한 채팅정보가 담긴 
	 * 
	 */
	public void sendChat(ChatVO cVO) {//이건 메인 스레드에서 돌아감
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
	
	//서버에 보낸걸 다시 받는 메소드
	
	public ChatVO getChat(Message<ChatVO> msg) {
		ChatVO cVO = msg.getResponse().get(0);
		return cVO;
	}

	//받은 채팅에 이모티콘 처리하는 메소드
	public void findEmoticon(ChatVO cVO,JTextPane jtp) {//ui의 textpane을 파라미터로 받음,그리고 getChat에서 받은 
											//chatVO의 chatContent를 받음
		
		String s = cVO.getChat_content(); //chatVO의 채팅 내용
		StyledDocument doc = jtp.getStyledDocument();
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		
		//정해진 패턴' (이모티콘이름) '이 검색되었는가?
		while(m.find()) {
			//그 패턴을 이모티콘 리스트에서 검색해본다. null이면 검색 실패한 것이다.
			JLabel label = list.get(m.group());
			
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
						jtp.selectAll();
						jtp.setSelectionStart(jtp.getSelectionEnd());
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				//앞의 문자열을 출력한 다음에는 이모티콘을 출력해준다.
				jtp.insertComponent(label);
				//기존 채팅내용에서 위에서 출력한 부분은 지워줘서 뒷 부분을 앞으로 땡겨준다.
				builder.replace(0, idx+name.length(), "");
				
				//수정한 문자열로 Matcher를 새로 설정해준다.
				m = p.matcher(builder);
				jtp.selectAll();
				jtp.setSelectionStart(jtp.getSelectionEnd());
			}
		}
		//이모티콘 추출작업이 끝난후 남은 문자열을 뒤에 붙여준다.
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
