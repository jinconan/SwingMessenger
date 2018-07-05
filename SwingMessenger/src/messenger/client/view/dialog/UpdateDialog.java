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
//////////선언부
	//화면부
	ClientFrame clientFrame;

	MemberVO mv;
	JPanel				jp_card 	= new JPanel();
	JPanel				jp_cert 	= new JPanel();
	JPanel				jp_upd 		= new JPanel();
	JLabel				jl_uid		= new JLabel("아이디 :");
	JLabel 				jl_unick 	= new JLabel("상태메시지 :");
	JLabel 				jl_upw 		= new JLabel("비밀번호 :");
	JLabel 				jl_uname 	= new JLabel("이름 :");
	JLabel 				jl_ugender 	= new JLabel("성별 :");
	JLabel 				jl_uhp 		= new JLabel("핸드폰번호 :");
	JLabel				jtf_uid		= new JLabel();
	JTextField  		jtf_cert 	= new JTextField(20);
	JTextField  		jtf_unick 	= new JTextField(20);
	JTextField  		jtf_upw 	= new JTextField(20);
	JTextField  		jtf_uname 	= new JTextField(20);
	JTextField  		jtf_uhp 	= new JTextField(20);
	String[] 			genderList  = {"남","여"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
//	JButton				jbtn_cert 	= new JButton("인증하기");
	JButton				jbtn_upd 	= new JButton("수정");
	JButton				jbtn_back   = new JButton("뒤로가기");	
	
	//기능부
	Socket socket = new Socket();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	Message<MemberVO> message = null;
	List<MemberVO> 	  request = null;
	
	public UpdateDialog(ClientFrame clientFrame) {
		super(clientFrame, "회원정보수정", true);
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
		System.out.println("업데이트 실행");
		try {
						
			socket  = new Socket(Server.IP,Port.MEMBER);
			oos 	= new ObjectOutputStream(socket.getOutputStream());
			message = new Message<MemberVO>();
			request = new ArrayList<MemberVO>();
			MemberVO mv = new MemberVO();
			//MemberVO에 수정한 회원정보 담기
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
			System.out.println("업데이트 트라이");
			System.out.println(jtf_unick.getText());
			JOptionPane.showMessageDialog(this, "수정되었습니다.","수정성공",JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("업데이트 캐치");
			
			e.printStackTrace();
		}///////// end try catch

	}///////// end Update()


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("수정")) {
			System.out.println("MemberUpdate 클래스 호출");
			Update();
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		} else if(e.getActionCommand().equals("뒤로가기")) {
			this.dispose();
		}
		
	}
	
	

}
