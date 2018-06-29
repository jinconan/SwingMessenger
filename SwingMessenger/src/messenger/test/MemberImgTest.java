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
		//클래스파일의 기본 경로를 가져온다.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//위에서 얻은 경로를 시작지점으로 하여 이모티콘이 담긴 폴더의 상대경로 얻기.
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
