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
 * 정규식 테스트
 * 이모티콘 서버에서 이모티콘리스트를 먼저 불러와서 리스트를 형성한다.
 * 이후 테스트 하기를 원하는 문자열을 선언해서 메인에서 실행해서 테스트하면 된다.
 * 
 * 
 * @author 518
 *
 */
public class RegexTest extends JFrame {
	static HashMap<String, JLabel> list = new HashMap<String, JLabel>();
	Pattern p  = Pattern.compile("\\([가-힣]*\\_[가-힣]*\\)");
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
	
	//이모티콘을 전부 추출하여 먼저 출력하고 그 다음에 채팅을 출력
	void replaceEmoticonPrototype(String s) {
		StringBuilder builder = new StringBuilder(s);
		Matcher m = p.matcher(builder);
		while(m.find()) {
			//패턴과 일치하는 부분으로 이모티콘 리스트에서 이모티콘 검색.
			JLabel label = list.get(m.group());
			//이모티콘이 존재하면
			if(label != null) {
				//그 이모티콘 이미지를 가지고 새로 인스턴스화하여 JTextPane에 부착.
				label = new JLabel(label.getIcon());
				jtp.insertComponent(label);
				//이모티콘 검색에 사용된 부분을 입력받은 문자열 내에서 제거
				String name = m.group();
				int idx = builder.indexOf(name);
				builder.replace(idx, idx+name.length(), "");
				//수정한 문자열로 Matcher를 새로 설정.
				m = p.matcher(builder);
			}
		}
		
		//채팅을 StyledDocument에 이어붙이고 커서를 끝으로 옮긴다.
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
	
	//이모티콘을 원하는 위치에 출력하는 버전.
	//이모티콘을 발견하면 이모티콘 앞까지의 문자열을 먼저 출력하고 커서를 뒤로 이동시킨다음에 이모티콘을 찍어줌
	//즉, (텍스트 + 이모티콘) 방식으로 매 반복시마다 출력해줌.
	//반복문 탈출 후 마지막은 문자열만 남았으므로 문자열만 출력.
	void replaceEmoticon(String s) {
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
	
	public static void main(String[] args) {
		RegexTest frame = new RegexTest();
		String s = "김태희 : ㅁㅇㄻㄴ려재ㅑ (나야_놀람)    (바이_진짜)     ㄴㄹㄴㅇㄹㄴㄹ (나야_슬픔)가나다라마바사아자차카타파하";
		frame.replaceEmoticon(s);
		s = "김태희 : ㅁㅇㄻㄴ려재ㅑ (시베린_찌릿)    (바이_진짜)     ㄴㄹㄴㅇㄹㄴㄹ (나야_슬픔)abcdefghijklmnopqrstuvwxyz";
		frame.replaceEmoticon(s);
		s = "김태희 : (나야_놀람)(나야_슬픔)";
		frame.replaceEmoticon(s);
		s = "김태희 : ㅁㅇㄻㄴ려재ㅑ (나야_놀람)    (바이_진짜)     ㄴㄹㄴㅇㄹㄴㄹ (나야_슬픔)";
		frame.replaceEmoticon(s);
		


		
	}

}
