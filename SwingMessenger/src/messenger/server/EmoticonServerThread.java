package messenger.server;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import messenger._db.MemberDAO;
import messenger._db.vo.MemberVO;
import messenger._protocol.Message;

public class EmoticonServerThread implements Runnable{
	private Socket 		socket;
	private JTextArea	jta_log;
	
	public EmoticonServerThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
				InputStream			in 	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream	oin = new ObjectInputStream(bin);
			){
				System.out.println("socket.getInputStream()");
				Message<JLabel> msg = (Message<JLabel>)oin.readObject();
				System.out.println("oin.readObject()");
				if(jta_log != null) 
					jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				
				//소켓에서 출력스트림을 얻고, DAO에서 인증 처리를 하여 얻은 결과를 클라이언트에 전송한다.
				try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
				){
					System.out.println("socket.getOutputStream()");
					System.out.println("DAO.getInstance()");
					
					ArrayList<JLabel> response = getEmoticonList();
					msg.setResponse(response);
					oout.writeObject(msg);
					oout.flush();
					System.out.println("oout.writeObject(res_msg);");
					if(jta_log != null) {
						jta_log.append("응답: " + socket.getInetAddress().toString() + ", " + socket.getPort() + ": " +response.size()+ "개\n");
					}
				}catch (Exception e) {
					e.printStackTrace();
					if(jta_log != null) {
						jta_log.append(e.toString()+"\n");
					}
				}
					
			}catch (Exception e) {
				e.printStackTrace();
				if(jta_log != null) {
					jta_log.append(e.toString()+"\n");
				}
			}
	}

	/**
 	 * 내부 저장소에서 이모티콘 파일을 전부 불러와서 리스트로 변환하는 메소드
 	 * @return JLabel로 만든 이모티콘 리스트
 	 */
 	public ArrayList<JLabel> getEmoticonList() {
 		ArrayList<JLabel> list = new ArrayList<JLabel>(); //이미지 아이콘 리스트
 		
 		//클래스파일의 기본 경로를 가져온다.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//위에서 얻은 경로를 시작지점으로 하여 이모티콘이 담긴 폴더의 상대경로 얻기.
		URL buildpath = loader.getResource("messenger\\server\\images\\emoticon\\");
		try {
			URI uri = new URI(buildpath.toString());
			File file = new File(uri); //이모티콘 폴더를 인스턴스화하기
			
			//폴더 경로 + 이모티콘 이름을 이어붙여서 이미지 아이콘 인스턴스화
			for(String s :file.list()) {
				StringBuilder emoticonPath = new StringBuilder(uri.getPath());
				emoticonPath.append(s);
				StringBuilder emoticonName = new StringBuilder("(");
				String[] token = s.split("\\.");
				emoticonName.append(token[0]);
				emoticonName.append(")");
				
				ImageIcon icon = new ImageIcon(emoticonPath.toString());
				JLabel iconLabel = new JLabel(emoticonName.toString(), icon, SwingConstants.CENTER);
				iconLabel.setPreferredSize(new Dimension(50,50));
				iconLabel.setFont(new Font("굴림", Font.BOLD,0));
				list.add(iconLabel);
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return list;
 		
 	}
	
}
