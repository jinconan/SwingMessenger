package messenger._db.dao;

import java.awt.Font;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * 이름은 DAO이지만 DB를 사용하지는 않고, 서버 내부에서 이모티콘 파일을 불러와서 전부 JLabel로 인스턴스화 해주는 역할만 있음.
 * <담당하는 메시지 타입>
 * 	EMOTICON_LOAD
 * @author Jin Lee
 *
 */
public class EmoticonDAO {
//	private DBConnection dbCon = new DBConnection();
	
	private static class LazyHolder {
		private static final EmoticonDAO INSTANCE = new EmoticonDAO();
	}
	
	private EmoticonDAO() {}
	
	/**
	 * 
	 * @return 인스턴스
	 */
 	public static EmoticonDAO getInstance() {
		return LazyHolder.INSTANCE;
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
		URL buildpath = loader.getResource("messenger\\server\\emoticon\\images\\");
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
				iconLabel.setFont(new Font("굴림", Font.BOLD,0));
				list.add(iconLabel);
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return list;
 		
 	}
 	
 	public static void main(String[] args) {
 		EmoticonDAO dao = EmoticonDAO.getInstance();
 		
 		ArrayList<JLabel> list = dao.getEmoticonList();
 		
 		for(JLabel label : list) {
 			System.out.println(label.getText());
 		}
	}
}
