package messenger.client.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class SubMenu implements ActionListener{
	///////�����
	//������â
	JDialog				jd_profile = new JDialog();
	JPanel				jp_profile = new JPanel();
	JLabel				jl_profile = new JLabel();
	
	//ģ���˻�â
	JDialog 			jd_fri 	   = new JDialog();
	JTable 				jt_fri 	   = new JTable();
	JTextField 			jtf_fri    = new JTextField();
	JScrollPane 		jsp_fri	   = new JScrollPane(jt_fri);
	DefaultTableModel   dftm_fri   = new DefaultTableModel();
	
	//ä��â
	JDialog 			jd_chat	   = new JDialog();
	JTextField 			jtf_chat   = new JTextField();
	JTextPane 			jtp_chat   = new JTextPane();
	JPanel				jp_chatF   = new JPanel();
	JButton				jbtn_jun   = new JButton("����");
	JButton				jbtn_inv   = new JButton("ģ���ʴ�");
	JButton				jbtn_emti  = new JButton("�̸�Ƽ��");
	JToolBar			jtb_chat   = new JToolBar();
	JScrollPane 		jsp_chatA  = new JScrollPane(jtp_chat);
	JScrollPane 		jsp_chatF  = new JScrollPane(jtf_chat);
	
	//ģ���ʴ�â
	JDialog				jd_inv	   = new JDialog();
	JTable				jt_inv	   = new JTable();
	JTextField			jtf_inv    = new JTextField();
	JButton 			jbtn_gum   = new JButton("�˻�");
	JButton				jbtn_ok    = new JButton("Ȯ��");
	JButton				jbtn_cancel= new JButton("���");
	JScrollPane 		jsp_inv    = new JScrollPane(jt_inv);
	
	//�̸�Ƽ��â
	JDialog				jd_emti    = new JDialog();
	JLabel				jl_emti    = new JLabel();
	String				img_path   = "C:\\Users\\516\\Desktop\\tales_emoticon\\";
	
	//ȸ������ ����â
	CardLayout 	card		= new CardLayout();
	JDialog		jd_upd		= new JDialog();
	JPanel		jp_card		= new JPanel();
	JPanel		jp_cert		= new JPanel();
	JPanel		jp_upd		= new JPanel();
	JLabel 		jl_unick  	= new JLabel("�г��� :");
	JLabel 		jl_upw 		= new JLabel("��й�ȣ :");
	JLabel 		jl_uname 	= new JLabel("�̸� :");
	JLabel 		jl_ugender  = new JLabel("���� :");
	JLabel 		jl_uhp		= new JLabel("�ڵ�����ȣ :");
	JTextField  jtf_cert 	= new JTextField(20);
	JTextField  jtf_unick	= new JTextField(20);
	JTextField  jtf_upw		= new JTextField(20);
	JTextField  jtf_uname 	= new JTextField(20);
	JTextField  jtf_uhp 	= new JTextField(20);
	String[] 	genderList  = {"����","����"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
	JButton		jbtn_cert	= new JButton("�����ϱ�");
	JButton		jbtn_upd	= new JButton("����");
	JButton		jbtn_back	= new JButton("�ڷΰ���");	
	
	//������â ���̾�α�
	public void Profile() {
		jd_profile.setSize(350,500);
		jd_profile.setVisible(true);
		jd_profile.setTitle("������");
	}
	
	//ģ���˻� ���̾�α�
	public void addFriends() {
		jbtn_gum.addActionListener(this);
		jbtn_jun.addActionListener(this);
		jd_fri.setTitle("ģ���˻�");
		jd_fri.setSize(400, 300);
		jd_fri.setVisible(true);
		jd_fri.setLayout(null);
		jd_fri.add(jtf_fri);
		jd_fri.add(jsp_fri);
		jd_fri.add(jbtn_gum);
		jd_fri.add(jt_fri);
		Font f = new Font("����",Font.CENTER_BASELINE,10);
		jbtn_gum.setFont(f);
		jt_fri.setVisible(true);
		jsp_fri.setVisible(true);
		jbtn_gum.setBounds(300, 10, 50, 30);
		jtf_fri.setBounds(20, 10, 280, 30);
		jsp_fri.setBounds(20, 50, 330, 200);
	}
	
	//�� ä�� ���̾�α�
	public void addChatting() {
		int i = 0;
		jbtn_inv.addActionListener(this);
		jbtn_emti.addActionListener(this);
		/*jd_chat.setTitle(chatList);
		String chatList = "";*/
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
	
	//ģ���ʴ�â
	public void Invited() {
		jbtn_ok.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		jd_inv.setTitle("ģ���ʴ�");
		jd_inv.setSize(400, 300);
		jd_inv.setVisible(true);
		jd_inv.setLayout(new GridLayout(4,1,0,0));
		jd_inv.add(jtf_inv);
		jd_inv.add(jt_fri);
		jd_inv.add(jbtn_ok);
		jd_inv.add(jbtn_cancel);
		Font f = new Font("����",Font.CENTER_BASELINE,10);
		jbtn_ok.setFont(f);
		jbtn_cancel.setFont(f);
		jt_inv.setVisible(true);
		jsp_inv.setVisible(true);
	}
	
	//�̸�Ƽ�� ���̾�α�â
	public void Emoticon() {
		jd_emti.setTitle("�̸�Ƽ��");
		jd_emti.setVisible(true);
		jd_emti.setSize(400,300);
		jd_emti.add(jl_emti);
		jl_emti.setVisible(true);
		ArrayList<JLabel> list = new ArrayList<JLabel>();
		for(int i=0;i<list.size();i++) {
			jl_emti.setIcon(new ImageIcon(img_path+list.get(i)));
			
		}
	}
	
	//ȸ������ ���� ���̾�α�â
	public void UpdateInfo() {
		jbtn_upd.addActionListener(this);
		jd_upd.setTitle("ȸ����������");
		jd_upd.setSize(350,400);
		jd_upd.setVisible(true);
		/*//ī���г�
		jp_card.setLayout(card);
		jp_card.add(jp_cert,"����â");
		jp_card.add(jp_upd, "����â");			
		//����â
		jp_cert.add("Center",jtf_cert);
		jp_cert.add("South",jbtn_cert);
		//����â
		jp_upd.setVisible(true);
		jp_upd.setLayout(null);
		jp_upd.add(jbtn_upd);
		jp_upd.add(jbtn_back);
		jp_upd.add(jl_uid);
		jp_upd.add(jl_upw);
		jp_upd.add(jl_uname);
		jp_upd.add(jl_uhp);
		jp_upd.add(jl_ugender);
		jp_upd.add(jtf_uid);
		jp_upd.add(jtf_upw);
		jp_upd.add(jtf_uname);
		jp_upd.add(jtf_ugender); 
		jp_upd.add(jtf_uhp);
		jp_upd.setBackground(Color.YELLOW);*/
		jd_upd.setVisible(true);
		jd_upd.setLayout(null);
		jd_upd.setBackground(Color.YELLOW);
		jd_upd.add(jbtn_upd);
		jd_upd.add(jbtn_back);
		jd_upd.add(jl_unick);
		jd_upd.add(jl_upw);
		jd_upd.add(jl_uname);
		jd_upd.add(jl_uhp);
		jd_upd.add(jl_ugender);
		jd_upd.add(jtf_unick);
		jd_upd.add(jtf_upw);
		jd_upd.add(jtf_uname);
		jd_upd.add(jtf_ugender); 
		jd_upd.add(jtf_uhp);
		jd_upd.setBackground(Color.YELLOW);
		jl_unick.setBounds(30, 30, 80, 20);
		jl_unick.setVisible(true);
		jl_upw.setBounds(30, 70, 80, 20);
		jl_upw.setVisible(true);
		jl_uname.setBounds(30, 110, 80, 20);
		jl_uname.setVisible(true);
		jl_ugender.setBounds(30, 150, 80, 20);
		jl_ugender.setVisible(true);
		jl_uhp.setBounds(30, 190, 80, 20);
		jl_uhp.setVisible(true);
		jl_uhp.setBounds(30, 190, 80, 20);
		jl_uhp.setVisible(true);
		jtf_unick.setBounds(120, 30, 180, 20);
		jtf_unick.setVisible(true);
		jtf_upw.setBounds(120, 70, 180, 20);
		jtf_upw.setVisible(true);
		jtf_uname.setBounds(120, 110, 180, 20);
		jtf_uname.setVisible(true);
		jtf_ugender.setBounds(120, 150, 180, 20);
		jtf_ugender.setVisible(true);
		jtf_uhp.setBounds(120, 190, 180, 20);
		jtf_uhp.setVisible(true);
		jbtn_upd.setBounds(90, 250, 180, 30);
		jbtn_back.setBounds(90, 280, 180, 30);
	}
	
	//��ư �׼Ǻ�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		String msg = jtf_chat.getText();				
		//addChatting
		if(obj == jtf_chat) {
			try {
				//oos.writeObject(Protocol.MESSAGE+"|"+nickName+"|"+msg);
				jtf_chat.setText("");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();//����� �޼��� �̷°���
			}
		}
		//addFriends
		if(e.getActionCommand().equals("�˻�")) {
			System.out.println("�˻�����");
		}
		//Emoticon
		if(e.getActionCommand().equals("����")) {
			System.out.println("���۽���");
		}
		if(e.getActionCommand().equals("�̸�Ƽ��")) {
			System.out.println("�̸�Ƽ�ܽ���");
			Emoticon();
		}
		//Invited
		if(e.getActionCommand().equals("ģ���ʴ�")) {
			System.out.println("�ʴ����");
			Invited();
		}
		if(e.getActionCommand().equals("Ȯ��")) {
			System.out.println("Ȯ�ν���");		
		}
		else if(e.getActionCommand().equals("���")) {
			System.out.println("��ҽ���");
			System.exit(0);
		}
		//ȸ������ ����
		if(e.getActionCommand().equals("�����ϱ�")) {
			card.show(jp_card, "����â");
		}
		else if(e.getActionCommand().equals("����")) {
			System.out.println("MemberUpdate Ŭ���� ȣ��");
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		}
	}
	public static void main(String[] args) {
		SubMenu ad = new SubMenu();
		//ad.Emoticon();
		//ad.addChatting();
		ad.UpdateInfo();
		//ad.Profile();
	}
}
