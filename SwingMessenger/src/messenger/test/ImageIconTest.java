package messenger.test;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageIconTest extends JFrame{
	ImageIcon icon = new ImageIcon("C:\\Users\\518\\Desktop\\기본 프사.png");
	JLabel jl = new JLabel(icon);
	public ImageIconTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(icon.getImage() != null) {
			System.out.println("이미지 있음");
			System.out.println(icon.getIconWidth());
			System.out.println(icon.getIconHeight());
			
		} else {
			System.out.println("이미지 없음");
			System.out.println(icon.getIconWidth());
			System.out.println(icon.getIconHeight());			
		}
		this.add(jl,BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new ImageIconTest();
	}

}
