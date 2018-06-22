package SemiProjectChat;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class joindisplay extends JFrame{
	
	
	private JPanel           contentPane;
	        JTextField       jt_Id;
	        JPasswordField   jt_Pw;
	        JTextField       jt_Name;
            JTextField       jt_Hp;
	        JTextField       jt_birth;
	
	private JLabel           jl_label     = new JLabel("\uC0DD\uC77C");
	private JLabel           jl_Id        = new JLabel("\uD68C\uC6D0\uAC00\uC785\uCC3D");
	private JLabel           jl_Pw        = new JLabel("\uC544\uC774\uB514");
	private JLabel           jl_No        = new JLabel("\uBE44\uBC00\uBC88\uD638");
                             
	private JLabel           jl_Hp        = new JLabel("\uC774\uB984");
	private JLabel           jl_birth     = new JLabel("\uD578\uB4DC\uD3F0\uBC88\uD638");
            JButton          jbt_jungbock = new JButton("\uC911\uBCF5\uAC80\uC0AC");
	        JButton          jbt_join     = new JButton("\uD68C\uC6D0\uAC00\uC785");
	private JLabel           lblNewLabel  = new JLabel("New label");
	

	
	public void initdisplay() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 623);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		jl_Id.setFont(new Font("±¼¸²", Font.BOLD | Font.ITALIC, 22));
		jl_Id.setBounds(39, 12, 181, 45);
		contentPane.add(jl_Id);
		
		
		jl_Pw.setForeground(Color.BLACK);
		jl_Pw.setFont(new Font("±¼¸²", Font.BOLD, 17));
		jl_Pw.setBounds(39, 95, 62, 18);
		contentPane.add(jl_Pw);
		
		
		jl_No.setForeground(Color.BLACK);
		jl_No.setFont(new Font("±¼¸²", Font.BOLD, 17));
		jl_No.setBounds(39, 167, 95, 18);
		contentPane.add(jl_No);
		

		
		jl_Hp.setFont(new Font("±¼¸²", Font.BOLD, 17));
		jl_Hp.setBounds(39, 330, 62, 18);
		contentPane.add(jl_Hp);
		
		
		jl_birth.setFont(new Font("±¼¸²", Font.BOLD, 17));
		jl_birth.setBounds(39, 414, 94, 18);
		contentPane.add(jl_birth);
		
		
		jl_label.setFont(new Font("±¼¸²", Font.BOLD, 17));
		jl_label.setBounds(39, 500, 62, 18);
		contentPane.add(jl_label);
		
		jt_Id = new JTextField();
		jt_Id.setBounds(155, 93, 318, 24);
		contentPane.add(jt_Id);
		jt_Id.setColumns(10);
		
		jt_Pw = new JPasswordField();
		jt_Pw.setColumns(10);
		jt_Pw.setBounds(155, 165, 318, 24);
		contentPane.add(jt_Pw);

		jt_Name = new JTextField();
		jt_Name.setColumns(10);
		jt_Name.setBounds(155, 328, 318, 24);
		contentPane.add(jt_Name);
		
		jt_Hp = new JTextField();
		jt_Hp.setColumns(10);
		jt_Hp.setBounds(155, 412, 318, 24);
		contentPane.add(jt_Hp);
		
		jt_birth = new JTextField();
		jt_birth.setColumns(10);
		jt_birth.setBounds(155, 498, 318, 24);
		contentPane.add(jt_birth);
		jbt_jungbock.setBackground(Color.YELLOW);
		jbt_jungbock.setBounds(498, 92, 105, 27);
		contentPane.add(jbt_jungbock);
		jbt_join.setBounds(255, 537, 105, 39);
		contentPane.add(jbt_join);
		lblNewLabel.setIcon(new ImageIcon("D:\\ojdbc6\\Semiproject\\src\\images\\join2.png"));
		lblNewLabel.setBounds(0, 0, 624, 586);
		contentPane.add(lblNewLabel);
		
		this.setVisible(true);
	}
}
