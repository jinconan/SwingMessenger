package messenger.test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MemberImgTest {
	private  JLabel getImageLabel(String url, boolean isProfile) {
		JLabel jl = null;
		//Ŭ���������� �⺻ ��θ� �����´�.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//������ ���� ��θ� ������������ �Ͽ� �̸�Ƽ���� ��� ������ ����� ���.
 		String location = (isProfile == true) ? "messenger\\server\\images\\profile\\" : "messenger\\server\\images\\background\\";
 		
		URL buildpath = loader.getResource(location);
		
		try {
			URI uri = new URI(buildpath.toString());
			StringBuilder imgpath = new StringBuilder(uri.getPath());
			if(url != null)
				imgpath.append(url);
			File file = new File(imgpath.toString());
			if(file.exists() && file.isFile()) {
				System.out.println(imgpath.toString());
				ImageIcon icon = new ImageIcon(imgpath.toString());
				jl = new JLabel(icon);
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return jl;

	}
	public static void main(String[] args) {
		
		JLabel jl = new MemberImgTest().getImageLabel("profile_0.png", true);
		
	}
}
