package messenger.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class Test extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTextPane textPane = new JTextPane();
		JScrollPane jsp = new JScrollPane(textPane);
		textPane.setEditable(false);
		StyledDocument doc = textPane.getStyledDocument();
		
		try {
			doc.insertString(doc.getLength(), "테스트\n", null);
			doc.insertString(doc.getLength(), "테스트\n", null);
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
			textPane.insertComponent(new JButton("버튼"));
		
			
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane.add(jsp, BorderLayout.CENTER);
		

	}

}
