package messenger.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LabelTest extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LabelTest frame = new LabelTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LabelTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ClassLoader loader = this.getClass().getClassLoader();
		URL buildpath = loader.getResource("messenger\\server\\emoticon\\images\\");
		ImageIcon[] icons = null;
		try {
			URI uri = new URI(buildpath.toString());
			File file = new File(uri);
			icons = new ImageIcon[file.list().length];
			
			for(int i=0 ;i< icons.length;i++) {
				System.out.println(uri.getPath()+file.list()[i]);
				icons[i] = new ImageIcon(uri.getPath()+file.list()[i]);
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane.setLayout(new GridLayout(5, 5, 0, 0));
		
		
		JLabel[] jl = new JLabel[25];
		
		for(int i=0;i<jl.length;i++) {
			jl[i] = new JLabel("이름",icons[i], SwingConstants.CENTER);
			jl[i].setFont(new Font("굴림", Font.PLAIN, 0));
			System.out.println(jl[i].getText());;
			jl[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					System.out.println("이모티콘");
				}
			});
			contentPane.add(jl[i]);
		}
		
		


	}
}
