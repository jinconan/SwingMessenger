package messenger.client.view.dialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger._protocol.Message;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;
import messenger.client.view.ClientFrame;

public class CreateRoomDialog extends JDialog implements ActionListener{
	
	private		  ClientFrame		   clientFrame;
	private final JPanel               contentPanel        = new JPanel();
	private       JTextField           jtf_title           = new JTextField();

	private		  MemberVOTableModel   model_friend_list;
	private		  MemberVOTableModel   model_Room_add_list = new MemberVOTableModel("대화할 친구");
	
	private		  MemberVOTable		   friend_list		   = new MemberVOTable();
	private		  MemberVOTable		   Room_add_list	   = new MemberVOTable(model_Room_add_list);
	
	private       JScrollPane          Jsc_friend_list     = new JScrollPane(friend_list);
	private       JScrollPane          Jsc_Room_add_list   = new JScrollPane(Room_add_list);
	
	private       JLabel               lblNewLabel_1       = new JLabel("대화방 제목 :");

	private       JPanel               buttonPane          = new JPanel();
	private       JButton              jbt_add             = new JButton("추가");
	private       JButton              jbt_del             = new JButton("삭제");
	private       JButton              jbt_ok              = new JButton("만들기");
	private       JButton              jbt_Cancel          = new JButton("취소");
	
	public CreateRoomDialog(ClientFrame clientFrame) {
		super(clientFrame,true);
		this.clientFrame = clientFrame;
		
		init();
	}
	
	public void event() {
		jbt_add   .addActionListener(this);
		jbt_del   .addActionListener(this);
		jbt_ok    .addActionListener(this);
		jbt_Cancel.addActionListener(this);
	}
	
	public void init() {
		this.setTitle("친구 초대하기");
		model_friend_list =  (clientFrame != null) ? clientFrame.getFriendTableModel() : new MemberVOTableModel("친구리스트");
		friend_list.setModel(model_friend_list);
		event();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 740, 450);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(126, 195, 237));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNewLabel_1    .setBounds(30, 30, 120, 20);
		jtf_title        .setBounds(125, 30, 285, 20);
		jbt_add          .setBounds(340, 150, 60, 40);
		jbt_del          .setBounds(340, 220, 60, 40);
		Jsc_friend_list  .setBounds(30, 73, 300, 300);
		Jsc_Room_add_list.setBounds(410, 73, 300, 300);
		
		jtf_title.setColumns(10);
		contentPanel.add(lblNewLabel_1);
		contentPanel.add(jtf_title);
		contentPanel.add(jbt_add);
		contentPanel.add(jbt_del);
		contentPanel.add(Jsc_friend_list);
		contentPanel.add(Jsc_Room_add_list);
		
		buttonPane.add(jbt_Cancel);
		buttonPane.setBackground(new Color(126, 195, 237));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.add(jbt_ok);
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getRootPane()   .setDefaultButton(jbt_ok);
		
		jbt_ok	  .setBackground(new Color(126, 195, 237));
		jbt_Cancel.setBackground(new Color(126, 195, 237));
		jbt_add	  .setBackground(new Color(126, 195, 237));
		jbt_del   .setBackground(new Color(126, 195, 237));
		jbt_ok    .setActionCommand("OK");
		jbt_Cancel.setActionCommand("Cancel");
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new CreateRoomDialog(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		   
		if(e.getSource()==jbt_add) {
			int row = friend_list.getSelectedRow();
			if(row == -1)
				return;
			MemberVO mem = model_friend_list.getValueAt(row, 0);
			for(int i=0;i<model_Room_add_list.getRowCount();i++) {
				if(model_Room_add_list.getValueAt(i, 0).equals(mem)){
	            	
	            		JOptionPane.showMessageDialog(this, "이미 추가하셨습니다.", "알림", JOptionPane.CLOSED_OPTION);
	            		return;
	            	}
			}
			model_Room_add_list.addRow(mem);
			
		}
		else if(e.getSource()==jbt_del) {
			int row = Room_add_list.getSelectedRow();
			if(row == -1)
				return;
			model_Room_add_list.removeRow(row);
		}
        else if(e.getSource()==jbt_ok) {
        	//add_list의 멤버를 보내줘야함.
        	
        	if(jtf_title.getText()==null||jtf_title.getText().equals("")) {
        		JOptionPane.showMessageDialog(this, "방제목을 추가해주세요", "알림", JOptionPane.CLOSED_OPTION);
        		return;
        	}
        	//방 생성시 0번째 chatVO에는 내 정보와 방 제목이 포함.
        	ArrayList<ChatVO> request = new ArrayList<ChatVO>();
        	RoomVO roomVO = new RoomVO(0, null, jtf_title.getText());
        	ChatVO chatVO = new ChatVO(0,roomVO,null,null,clientFrame.getClientData().getMyData());
        	request.add(chatVO);
        	
        	//그 다음부터는 선택된 친구들을 chatVO에 담는다.
        	for(int i=0;i<model_Room_add_list.getRowCount();i++) {
        		MemberVO memVO = model_Room_add_list.getValueAt(i, 0);
        		chatVO = new ChatVO(0, null,null,null,memVO);
        		request.add(chatVO);
        	}
        	
        	Message<ChatVO> msg =new Message<ChatVO>(Message.CHATROOM_INVITE,request,null);
        	try {
				clientFrame.getClientData().getOut().writeObject(msg);
				clientFrame.getClientData().getOut().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        	this.dispose();

        }
        else if(e.getSource()==jbt_Cancel) {
        	this.dispose();
        }
	}
}
