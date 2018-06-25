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
 * �̸��� DAO������ DB�� ��������� �ʰ�, ���� ���ο��� �̸�Ƽ�� ������ �ҷ��ͼ� ���� JLabel�� �ν��Ͻ�ȭ ���ִ� ���Ҹ� ����.
 * <����ϴ� �޽��� Ÿ��>
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
	 * @return �ν��Ͻ�
	 */
 	public static EmoticonDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	
 	/**
 	 * ���� ����ҿ��� �̸�Ƽ�� ������ ���� �ҷ��ͼ� ����Ʈ�� ��ȯ�ϴ� �޼ҵ�
 	 * @return JLabel�� ���� �̸�Ƽ�� ����Ʈ
 	 */
 	public ArrayList<JLabel> getEmoticonList() {
 		ArrayList<JLabel> list = new ArrayList<JLabel>(); //�̹��� ������ ����Ʈ
 		
 		//Ŭ���������� �⺻ ��θ� �����´�.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//������ ���� ��θ� ������������ �Ͽ� �̸�Ƽ���� ��� ������ ����� ���.
		URL buildpath = loader.getResource("messenger\\server\\emoticon\\images\\");
		try {
			URI uri = new URI(buildpath.toString());
			File file = new File(uri); //�̸�Ƽ�� ������ �ν��Ͻ�ȭ�ϱ�
			
			//���� ��� + �̸�Ƽ�� �̸��� �̾�ٿ��� �̹��� ������ �ν��Ͻ�ȭ
			for(String s :file.list()) {
				StringBuilder emoticonPath = new StringBuilder(uri.getPath());
				emoticonPath.append(s);
				StringBuilder emoticonName = new StringBuilder("(");
				String[] token = s.split("\\.");
				emoticonName.append(token[0]);
				emoticonName.append(")");
				
				ImageIcon icon = new ImageIcon(emoticonPath.toString());
				JLabel iconLabel = new JLabel(emoticonName.toString(), icon, SwingConstants.CENTER);
				iconLabel.setFont(new Font("����", Font.BOLD,0));
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
