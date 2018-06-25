package SemiProjectChat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Logindisplay extends JFrame implements ActionListener{


	private JPanel contentPane;
	private JTextField JT_ID;
	private JPasswordField JT_PW;
	JButton     jbt_login    = new JButton("로그인");
	JButton     jbt_join     = new JButton("회원가입");
	JLabel      lblNewLabel  = new JLabel("New label");
	joindisplay joindisplay  = new joindisplay();
	DAO         dao          = new DAO();
	MemberVO   memberVO    = new MemberVO();
	String sex              = null;
    final String Sql = "Select Mem_ID from Member  order by Mem_ID asc";
	DBconnection dbcon = new DBconnection();

	Connection        con  = null;
    Statement         st   = null;
    ResultSet         rs   = null;
		
	Logindisplay() {
		initdisplay();
	}
	
	public void initdisplay() {
		joindisplay.jComBox.addActionListener(this);
		joindisplay.jbt_Exit.addActionListener(this);
		jbt_login.addActionListener(this);
		jbt_join.addActionListener(this);
		joindisplay.jbt_join.addActionListener(this);
		joindisplay.jbt_jungbock.addActionListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 664);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JT_ID = new JTextField();
		JT_ID.setBounds(73, 219, 304, 49);
		contentPane.add(JT_ID);
		JT_ID.setColumns(10);
		
		JT_PW = new JPasswordField();
		JT_PW.setBounds(73, 270, 304, 49);
		contentPane.add(JT_PW);
		JT_PW.setColumns(10);
		
		
		jbt_join.setBackground(Color.YELLOW);
		jbt_join.setBounds(104, 590, 119, 27);
		contentPane.add(jbt_join);
		
		jbt_login.setIcon(new ImageIcon("C:/Users/512/git/SwingMessenger/SwingMessenger/src/SemiProjectChat/images/Login2.png"));
		jbt_login.setBounds(73, 329, 304, 49);
		contentPane.add(jbt_login);
		
		
		lblNewLabel.setIcon(new ImageIcon("C:/Users/512/git/SwingMessenger/SwingMessenger/src/SemiProjectChat/images/Login.png"));
		lblNewLabel.setBounds(0, 0, 443, 617);
		contentPane.add(lblNewLabel);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new Logindisplay();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	
		if(arg0.getSource()==jbt_join) {
			System.out.println("jb_join");
			joindisplay.initdisplay();
		}
		else if(arg0.getSource()==joindisplay.jbt_join) {
			int joinOk = 0;
	
		    String result = joindisplay.jt_Id.getText();
			try {
				
				con  = dbcon.Connect();
				st = con.createStatement();
				rs = st.executeQuery(Sql);
				memberVO.setMem_id(joindisplay.jt_Id.getText());
				memberVO.setMem_pw(joindisplay.jt_Pw.getText());
				memberVO.setMem_name(joindisplay.jt_Name.getText());
				memberVO.setMem_hp(joindisplay.jt_Hp.getText());
			    memberVO.setMem_gender(sex);
			    memberVO.setMem_nick("Test");
			    
			while(rs.next()) {
				
                
	             String result1 = rs.getString("Mem_ID");
			
	             if(result.equals(result1)) {
	            	 
	            	 joinOk=1;
	             }
			}
			
			if(joindisplay.jt_Id.getText().equals("")||joindisplay.jt_Id.getText().equals(null)||
		       joindisplay.jt_Pw.getText().equals("")||joindisplay.jt_Pw.getText().equals(null)||
		       joindisplay.jt_Name.getText().equals("")||joindisplay.jt_Name.getText().equals(null)||
		       joindisplay.jt_Hp.getText().equals("")||joindisplay.jt_Hp.getText().equals(null)
		       /*joindisplay.jt_sex.getText().equals("")||joindisplay.jt_sex.getText().equals(null)*/)
			{
				
				JOptionPane.showMessageDialog(this, "모든값을 입력하세요", "경고창",JOptionPane.ERROR_MESSAGE);
			}
			else if(joinOk==1){
				JOptionPane.showMessageDialog(this,"아이디값이 중복입니다", "경고", JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "회원가입완료", "완료창",JOptionPane.CLOSED_OPTION);
			    dao.insert(memberVO);
			    
			    joindisplay.dispose();
			}
			
	
	

			} catch( SQLSyntaxErrorException e) {
				e.printStackTrace();
			}
			  catch( NullPointerException e) {
				e.printStackTrace();
			}
			  catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}

		
		}
		else if (arg0.getSource()==joindisplay.jbt_jungbock) {
	
			System.out.println("1");
			    String result = joindisplay.jt_Id.getText();
			    System.out.println(joindisplay.jt_Id.getText());
			    
				final String Sql = "Select Mem_ID from Member  order by Mem_ID asc";
				DBconnection dbcon = new DBconnection();
  
				int result2 = 0;
				Connection        con  = null;
			    Statement         st   = null;
			    ResultSet         rs   = null;
				

				try {
					con  = dbcon.Connect();
					st = con.createStatement();
					rs = st.executeQuery(Sql);
					
					while(rs.next()) {
						
                        
			             String result1 = rs.getString("Mem_ID");
					
			             if(result.equals(result1)) {
			            	 JOptionPane.showMessageDialog(joindisplay,"아이디값이 중복입니다", "경고", JOptionPane.ERROR_MESSAGE);
			            	 result2=1;
			             }
						}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
				if(result2==0) {
				 JOptionPane.showMessageDialog(this,"중복이 아닙니다.", "알림", JOptionPane.CLOSED_OPTION);
				}
			
		}
		
		else if(arg0.getSource()==jbt_login) {
			
			System.out.println("login 버튼 호출");
			memberVO.setMem_id(JT_ID.getText());
			String[] IdPw = dao.login(memberVO);
	
	        if(JT_PW.getText().equals(IdPw[0])) {
	        	 JOptionPane.showMessageDialog(this,"로그인 성공.", "알림", JOptionPane.CLOSED_OPTION);
	        	 new display(JT_ID.getText(),IdPw[1]);
	        	 this.dispose();
	        	 
	        }
	        else if(JT_PW.getText()!=IdPw[0]){
	        	 JOptionPane.showMessageDialog(this,"아이디와 비밀번호를 다시 확인해주세요", "알림", JOptionPane.CLOSED_OPTION);
	        }
	      
			
		}
		else if(arg0.getSource()==joindisplay.jbt_Exit) {
			joindisplay.dispose();
		}
		else if(arg0.getSource()==joindisplay.jComBox) {
			
			sex= (String)joindisplay.jComBox.getSelectedItem();
		    System.out.println(sex);
		    
		}
	}
	
}
