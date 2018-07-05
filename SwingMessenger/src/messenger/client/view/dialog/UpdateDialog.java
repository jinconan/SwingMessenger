package messenger.client.view.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;
import messenger.client.view.ClientFrame;

public class UpdateDialog extends JDialog implements ActionListener{
//////////�����
	//ȭ���
	ClientFrame clientFrame;

	MemberVO mv;
	JPanel				jp_card 	= new JPanel();
	JPanel				jp_cert 	= new JPanel();
	JPanel				jp_upd 		= new JPanel();
	JLabel				jl_uid		= new JLabel("���̵� :");
	JLabel 				jl_unick 	= new JLabel("���¸޽��� :");
	JLabel 				jl_upw 		= new JLabel("��й�ȣ :");
	JLabel 				jl_uname 	= new JLabel("�̸� :");
	JLabel 				jl_ugender 	= new JLabel("���� :");
	JLabel 				jl_uhp 		= new JLabel("�ڵ�����ȣ :");
	JLabel				jtf_uid		= new JLabel();
	JTextField  		jtf_cert 	= new JTextField(20);
	JTextField  		jtf_unick 	= new JTextField(20);
	JTextField  		jtf_upw 	= new JTextField(20);
	JTextField  		jtf_uname 	= new JTextField(20);
	JTextField  		jtf_uhp 	= new JTextField(20);
	String[] 			genderList  = {"��","��"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
//	JButton				jbtn_cert 	= new JButton("�����ϱ�");
	JButton				jbtn_upd 	= new JButton("����");
	JButton				jbtn_back   = new JButton("�ڷΰ���");	
	
	//��ɺ�
	Socket socket = new Socket();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	Message<MemberVO> message = null;
	List<MemberVO> 	  request = null;
	
	public UpdateDialog(ClientFrame clientFrame) {
		super(clientFrame, "ȸ����������", true);
		this.clientFrame = clientFrame;
		jbtn_upd.addActionListener(this);
		jbtn_back.addActionListener(this);
		mv = clientFrame.getClientData().getMyData();
		jtf_uid.setText(mv.getMem_id());
		this.setLayout(null);
		this.setSize(350,400);
		this.setBackground(new Color(126, 195, 237));
		jl_uid.setBounds(30,10,80,20);
		jl_unick.setBounds(30, 50, 80, 20);
		jl_upw.setBounds(30, 90, 80, 20);
		jl_uname.setBounds(30, 130, 80, 20);
		jl_ugender.setBounds(30, 170, 80, 20);
		jl_uhp.setBounds(30, 210, 80, 20);
		jtf_uid.setBounds(120, 10, 180, 20);
		jtf_unick.setBounds(120, 50, 180, 20);
		jtf_upw.setBounds(120, 90, 180, 20);
		jtf_uname.setBounds(120, 130, 180, 20);
		jtf_ugender.setBounds(120, 170, 180, 20);
		jtf_uhp.setBounds(120, 210, 180, 20);
		jbtn_upd.setBounds(75, 260, 200, 30);
		jbtn_upd.setBackground(new Color(126, 195, 237));
		jbtn_back.setBounds(75, 300, 200, 30);
		jbtn_back.setBackground(new Color(126, 195, 237));
		this.add(jbtn_upd);
		this.add(jbtn_back);
		this.add(jl_uid);
		this.add(jl_unick);
		this.add(jl_upw);
		this.add(jl_uname);
		this.add(jl_uhp);
		this.add(jl_ugender);
		this.add(jtf_uid);
		this.add(jtf_unick);
		this.add(jtf_upw);
		this.add(jtf_uname);
		this.add(jtf_ugender); 
		this.add(jtf_uhp);
		this.setVisible(true);
	}
	
	public void Update() {
		System.out.println("������Ʈ ����");
		try {
						
			socket  = new Socket(Server.IP,Port.MEMBER);
			oos 	= new ObjectOutputStream(socket.getOutputStream());
			message = new Message<MemberVO>();
			request = new ArrayList<MemberVO>();
			MemberVO mv = new MemberVO();
			//MemberVO�� ������ ȸ������ ���
			mv.setMem_id(jtf_uid.getText());
			mv.setMem_nick(jtf_unick.getText());
			mv.setMem_pw(jtf_upw.getText());
			mv.setMem_name(jtf_uname.getText());
			mv.setMem_gender((String)jtf_ugender.getSelectedItem());
			mv.setMem_hp(jtf_uhp.getText());
			mv.setMem_profile(null);
			mv.setMem_background(null);
			request.add(mv);
			message.setType(Message.MEMBER_MODIFY);
			message.setRequest(request);
			oos.writeObject(message);
			System.out.println("������Ʈ Ʈ����");
			System.out.println(jtf_unick.getText());
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.","��������",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("������Ʈ ĳġ");
			
			e.printStackTrace();
		}///////// end try catch

	}///////// end Update()


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("����")) {
			System.out.println("MemberUpdate Ŭ���� ȣ��");
			Update();
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		} else if(e.getActionCommand().equals("�ڷΰ���")) {
			this.dispose();
		}
		
	}
	
	

}
