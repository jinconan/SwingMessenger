
package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class Login extends JFrame implements ActionListener {
	//�����
	CardLayout 	card		= new CardLayout();
	//JFrame 		jf_login 	= new JFrame();
	JPanel 		jp_card		= new JPanel();
	JPanel 		jp_login 	= new JPanel();
	JLabel		jp_img 		= new JLabel();
	JPanel 		jp_gaip		= new JPanel();
	JPanel 		jp_list		= new JPanel();
	JLabel 		jl_id 		= new JLabel("���̵�");
	JLabel 		jl_pw 		= new JLabel("��й�ȣ");
	JTextField 	jtf_id   	= new JTextField();
	JTextField 	jtf_pw 		= new JTextField();
	JButton 	jbtn_log 	= new JButton("�α���");
	JButton 	jbtn_gaip 	= new JButton("ȸ������");
	JLabel		jta_error	= new JLabel();
	
	//����â �г�
	JLabel 		jl_gid  	= new JLabel("���̵� :");
	JLabel 		jl_gpw 		= new JLabel("��й�ȣ :");
	JLabel 		jl_gname 	= new JLabel("�̸� :");
	JLabel 		jl_ggender  = new JLabel("���� :");
	JLabel 		jl_ghp		= new JLabel("�ڵ�����ȣ :");
	JTextField  jtf_gid		= new JTextField(20);
	JTextField  jtf_gpw		= new JTextField(20);
	JTextField  jtf_gname 	= new JTextField(20);
	JTextField  jtf_ghp 	= new JTextField(20);
	String[] 	genderList  = {"����","����"};
	JComboBox   jtf_ggender = new JComboBox(genderList);
	JButton		jbtn_get	= new JButton("����");
	JButton		jbtn_back	= new JButton("�ڷΰ���");	
	JButton		jbtn_bok	= new JButton("�ߺ��˻�");
	String		imgPath		= "E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\";
	ArrayList<MemberVO>  request    = null;
	Socket socket =null;
	ObjectInputStream ois = null;
     ObjectOutputStream oos = null;
	   
     Message<MemberVO> msg  = null;
     List<MemberVO>   msg1  = null;
     MemberVO         ss    = null;
	boolean isView = false;
	
	//ȭ���
	public void initDisplay() {
		//���� �׼�
		jbtn_gaip.addActionListener(this);
		jbtn_log.addActionListener(this);
		jbtn_get.addActionListener(this);
		jbtn_back.addActionListener(this);
		jbtn_bok.addActionListener(this);
		//ī�� �г�
		jp_card.setLayout(card);
		jp_card.add(jp_login,"�α���â");
		jp_card.add(jp_gaip,"����â");
		//jp_card.add(jp_list,"���â");
		//������
		this.setTitle("�ڽ�����");
		this.setSize(380, 550);
		this.setVisible(true);
		this.add(jp_card, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//�α���â�г�
		jp_login.add(jp_img);
		jp_login.add(jl_id);
		jp_login.add(jl_pw);
		jp_login.add(jtf_id);
		jp_login.add(jtf_pw);
		jp_login.add(jta_error);
		jp_login.add(jbtn_log);
		jp_login.add(jbtn_gaip);
		jp_login.setLayout(null);
		jp_login.setBackground(Color.YELLOW);
		jp_img.setBounds(10, 10, 336, 255);
		jp_img.setIcon(new ImageIcon(imgPath+"kakao.jpg"));
		jl_id.setBounds(50, 300, 80, 20);
		jp_img.setVisible(true);
		jl_id.setVisible(true);
		jtf_id.setBounds(130, 300, 160, 20);
		jtf_id.setVisible(true);
		jta_error.setBounds(150, 320, 200, 20);
		jta_error.setText("������ ���ڿ��Դϴ�.");
		Font e = new Font("����ü",Font.CENTER_BASELINE,9);
		jta_error.setFont(e);
		jta_error.setForeground(Color.RED);
		jta_error.setVisible(isView);
		jl_pw.setBounds(50, 340, 80, 20);
		jl_pw.setVisible(true);
		jtf_pw.setBounds(130, 340, 160, 20);
		jtf_pw.setVisible(true);
		jbtn_log.setBounds(80, 380, 180, 20);
		jbtn_log.setVisible(true);
		jbtn_log.setBackground(Color.YELLOW);
		jbtn_gaip.setVisible(true);
		jbtn_gaip.setBounds(130, 480, 120, 20);
		jbtn_gaip.setBackground(Color.YELLOW);
		jp_login.setVisible(true);
		
	//����â �г�
		
		jp_gaip.setLayout(null);
		//jp_gaip.setVisible(true);
		jp_gaip.add(jbtn_get);
		jp_gaip.add(jbtn_back);
		jp_gaip.add(jbtn_bok);
		jp_gaip.add(jl_gid);
		jp_gaip.add(jl_gpw);
		jp_gaip.add(jl_gname);
		jp_gaip.add(jl_ghp);
		jp_gaip.add(jl_ggender);
		jp_gaip.add(jtf_gid);
		jp_gaip.add(jtf_gpw);
		jp_gaip.add(jtf_gname);
		jp_gaip.add(jtf_ggender); 
		jp_gaip.add(jtf_ghp);
		jp_gaip.setBackground(Color.YELLOW);
		jl_gid.setBounds(30, 30, 80, 20);
		jl_gid.setVisible(true);
		jbtn_bok.setBounds(250, 30, 60, 20);
		jbtn_bok.setVisible(true);
		jl_gpw.setBounds(30, 70, 80, 20);
		jl_gpw.setVisible(true);
		jl_gname.setBounds(30, 110, 80, 20);
		jl_gname.setVisible(true);
		jl_ggender.setBounds(30, 150, 80, 20);
		jl_ggender.setVisible(true);
		jl_ghp.setBounds(30, 190, 80, 20);
		jl_ghp.setVisible(true);
		jl_ghp.setBounds(30, 190, 80, 20);
		jl_ghp.setVisible(true);
		jtf_id.setBounds(120, 30, 120, 20);
		jtf_gid.setVisible(true);
		jtf_gpw.setBounds(120, 70, 180, 20);
		jtf_gpw.setVisible(true);
		jtf_gname.setBounds(120, 110, 180, 20);
		jtf_gname.setVisible(true);
		jtf_ggender.setBounds(120, 150, 180, 20);
		jtf_ggender.setVisible(true);
		jtf_ghp.setBounds(120, 190, 180, 20);
		jtf_ghp.setVisible(true);
		jbtn_get.setBounds(90, 450, 180, 30);
		jbtn_back.setBounds(90, 480, 180, 30);
						
		//�α���â
		jp_list.setVisible(true);
		jp_list.setBackground(Color.YELLOW);
		
		//�������ڿ� �޼ҵ�
		jtf_id.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(jta_error.getText() != " " && jta_error.getText() != "/" && jta_error.getText() != "-"
						&& jta_error.getText() != "=" && jta_error.getText() != "|" 
						&& jta_error.getText() != "," && jta_error.getText() != "."
						&& jta_error.getText() != "[" && jta_error.getText() != "]"
						&& jta_error.getText() != "`" ) {
					jta_error.setVisible(false);
				}
				super.keyPressed(e);
				System.out.println("Key Released: "+ e.getKeyCode());
				switch(e.getKeyCode()) {
				case 32:
					try {
						System.out.println("NUMPAD0: "+ e.getKeyCode());
						System.out.println("������ ���ڿ��Դϴ�.");
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
					jta_error.setVisible(true);
					break;
				case 44:
					jta_error.setVisible(true);
					break;
				case 45:
					jta_error.setVisible(true);
					break;
				case 46:
					jta_error.setVisible(true);
					break;
				case 47:
					jta_error.setVisible(true);
					break;
				case 61:
					jta_error.setVisible(true);
					break;
				case 91:
					jta_error.setVisible(true);
					break;
				case 92:
					jta_error.setVisible(true);
					break;
				case 93:
					jta_error.setVisible(true);
					break;
				case 192:
					jta_error.setVisible(true);
					break;
				default:
				break;
				}///////////// end switch
			}///////////// end keyPressed
		});/////////// end addKeyListener
	}///////////// end initDisplay
	
		
	//���� �ڽ�
	public String getGender() {
		if("����".equals(jtf_ggender.getSelectedItem())) return "1";
		else return "2";
	}
	public void setGender(String gender) {
	if("����".equals(gender)) jtf_ggender.setSelectedItem("����");
	else jtf_ggender.setSelectedItem("����");
	}////////////// end Gender

	//���θ޼ҵ�
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login ui = new Login();
		ui.initDisplay();
		
	}////////////// end main
	
	
	//��ư �׼Ǻ�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("�ڷΰ���")) {
			//card.first(jp_card);
			card.show(jp_card,"�α���â"); 
		}
		else if(e.getActionCommand().equals("ȸ������")) {

			card.show(jp_card,"����â");
			
			
		}
		else if (e.getSource()==jbtn_get) {
			msg  = new Message<MemberVO>();
			msg1 = new ArrayList<MemberVO>();
			ss   = null;
			ss = new MemberVO();
			ss.setMem_id(jtf_gid.getText());
			ss.setMem_pw(jtf_gpw.getText());
			ss.setMem_name(jtf_gname.getText());
			ss.setMem_gender((String)jtf_ggender.getSelectedItem());
			ss.setMem_hp(jtf_ghp.getText());
		
			msg1.add(ss);
			
			msg.setType(msg.MEMBER_LOGIN);
			msg.setRequest(msg1);
			
			try {
				socket = new Socket("192.168.0.235",Port.LOGIN);
				oos = new ObjectOutputStream(socket.getOutputStream());
			
				oos.writeObject(msg);
				ois  = new ObjectInputStream(socket.getInputStream());
				try {
					msg = (Message<MemberVO>) ois.readObject();
				
		
				    
					if(msg.getResponse().size()==1) {
						System.out.println(msg);
						card.show(jp_card,"�α���â");
						
						JOptionPane.showMessageDialog(this, "ȸ������ �Ϸ�", "�˸�", JOptionPane.CANCEL_OPTION);
						socket.close();
						oos.close();
						ois.close();
					}
					else {
						
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getActionCommand().equals("�α���")) {
			
			msg  = new Message<MemberVO>();
			msg1 = new ArrayList<MemberVO>();
			ss = null;
			ss = new MemberVO();
			ss.setMem_id(jtf_id.getText());
			ss.setMem_pw(jtf_pw.getText());
			msg1.add(ss);
			
			msg.setType(msg.MEMBER_LOGIN);
			msg.setRequest(msg1);
			
			try {
				socket = new Socket("192.168.0.235",Port.LOGIN);
				oos = new ObjectOutputStream(socket.getOutputStream());
			
				oos.writeObject(msg);
				ois  = new ObjectInputStream(socket.getInputStream());
				try {
					msg = (Message<MemberVO>) ois.readObject();
				
				
					if(msg.getResponse().size()==1) {
						System.out.println(msg);
						this.dispose();
						MainMenu main = new MainMenu(this);
						main.start();
						main.initDisplay();
						
					}
					else {
						JOptionPane.showMessageDialog(this, "���̵�� ��й�ȣ�� Ʋ�Ƚ��ϴ�.", "�˸�", JOptionPane.CANCEL_OPTION);
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		
		}
		else if(e.getActionCommand().equals("�ߺ��˻�")) {
			System.out.println("�ߺ��˻� ����");
			Jungbok jb = new Jungbok();
			jb.Gumsa();
		}
			
	//��ư�̺�Ʈ����->�׼��̺�Ʈ->card.show
	}///////////// end actionPerformed
}//////////// end class
