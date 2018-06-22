package messenger.client.view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class addMenu implements ActionListener{
	JDialog 			jd_fri 	 = new JDialog();
	JDialog 			jd_chat	 = new JDialog();
	JDialog				jd_emti  = new JDialog();
	JDialog				jd_inv	 = new JDialog();
	JPanel				jp_chatF = new JPanel();
	JTextField 			jtf_fri  = new JTextField();
	JTextField			jtf_inv  = new JTextField();
	JTextField 			jtf_chat = new JTextField();
	JTextArea 			jta_chat = new JTextArea();
	JTable 				jt_fri 	 = new JTable();
	JTable				jt_inv	 = new JTable();
	JTable				jt_emti	 = new JTable();
	JButton 			jbtn_gum = new JButton("검색");
	JButton				jbtn_jun = new JButton("전송");
	JButton				jbtn_ok  = new JButton("확인");
	JButton				jbtn_cancel=new JButton("취소");
	DefaultTableModel   dftm_fri = new DefaultTableModel();
	JScrollPane 		jsp_fri	 = new JScrollPane(jt_fri);
	JScrollPane 		jsp_chatA= new JScrollPane(jta_chat);
	JScrollPane 		jsp_chatF= new JScrollPane(jtf_chat);
	JScrollPane 		jsp_inv  = new JScrollPane(jt_inv);
	JToolBar			jtb_chat = new JToolBar();
	JButton				jbtn_emti= new JButton("이모티콘");
	JButton				jbtn_inv = new JButton("친구초대");
	
	
	//친구검색창
	public void addFriends() {
		jbtn_gum.addActionListener(this);
		jbtn_jun.addActionListener(this);
		jd_fri.setTitle("친구검색");
		jd_fri.setSize(400, 300);
		jd_fri.setVisible(true);
		jd_fri.setLayout(null);
		jd_fri.add(jtf_fri);
		jd_fri.add(jsp_fri);
		jd_fri.add(jbtn_gum);
		jd_fri.add(jt_fri);
		Font f = new Font("굴림",Font.CENTER_BASELINE,10);
		jbtn_gum.setFont(f);
		jt_fri.setVisible(true);
		jsp_fri.setVisible(true);
		jbtn_gum.setBounds(300, 10, 50, 30);
		jtf_fri.setBounds(20, 10, 280, 30);
		jsp_fri.setBounds(20, 50, 330, 200);
	}
	
	//새로운 채팅창
	public void addChatting() {
		jbtn_inv.addActionListener(this);
		jbtn_emti.addActionListener(this);
		jd_chat.setSize(360,550);
		jd_chat.setVisible(true);
		jsp_chatA.setVisible(true);
		jsp_chatF.setVisible(true);
		jtb_chat.setVisible(true);
		jd_chat.add("North", jtb_chat);
		jd_chat.add("Center",jsp_chatA);
		jd_chat.add("South",jsp_chatF);
		jtb_chat.setLayout(new GridLayout(1,4,10,10));
		jtb_chat.add(jbtn_emti);
		jtb_chat.add(jbtn_inv);
		//jp_chatF.add(jsp_chatF);
		//jp_chatF.add("East",jbtn_jun);		
		/*jp_chatF.setLayout(null);
		jsp_chatF.setBounds(0, 360, 360, 40);
		jbtn_jun.setBounds(360, 360, 40, 40);*/
	}
	
	//친구초대창
	public void Invited() {
		jbtn_ok.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		jd_inv.setTitle("친구초대");
		jd_inv.setSize(400, 300);
		jd_inv.setVisible(true);
		jd_inv.setLayout(new GridLayout(4,1,0,0));
		jd_inv.add(jtf_inv);
		jd_inv.add(jt_fri);
		jd_inv.add(jbtn_ok);
		jd_inv.add(jbtn_cancel);
		Font f = new Font("굴림",Font.CENTER_BASELINE,10);
		jbtn_ok.setFont(f);
		jbtn_cancel.setFont(f);
		jt_inv.setVisible(true);
		jsp_inv.setVisible(true);
	}
	
	public void Emoticon() {
		jd_emti.setTitle("이모티콘");
		jd_emti.setVisible(true);
		jd_emti.setSize(400,300);
		jd_emti.add(jt_emti);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(e.getActionCommand().equals("검색")) {
			System.out.println("검색실행");
		}
		if(e.getActionCommand().equals("전송")) {
			System.out.println("전송실행");
		}
		if(e.getActionCommand().equals("이모티콘")) {
			System.out.println("이모티콘실행");
			Emoticon();
		}
		if(e.getActionCommand().equals("친구초대")) {
			System.out.println("초대실행");
			Invited();
		}
		if(e.getActionCommand().equals("확인")) {
			System.out.println("확인실행");
		}
		else if(e.getActionCommand().equals("취소")) {
			System.out.println("취소실행");
		}
	}
}
