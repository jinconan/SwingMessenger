package messenger.client.view.dialog;
import java.awt.BorderLayout;
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
	private		  MemberVOTableModel   model_Room_add_list = new MemberVOTableModel("��ȭ�� ģ��");
	
	private		  MemberVOTable		   friend_list		   = new MemberVOTable();
	private		  MemberVOTable		   Room_add_list	   = new MemberVOTable(model_Room_add_list);
	
	private       JScrollPane          Jsc_friend_list     = new JScrollPane(friend_list);
	private       JScrollPane          Jsc_Room_add_list   = new JScrollPane(Room_add_list);
	
	private       JLabel               lblNewLabel_1       = new JLabel("����");

	private       JPanel               buttonPane          = new JPanel();
	private       JButton              jbt_add             = new JButton("Add");
	private       JButton              jbt_del             = new JButton("Del");
	private       JButton              jbt_ok              = new JButton("OK");
	private       JButton              jbt_Cancel          = new JButton("Cancel");
	
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
		model_friend_list =  (clientFrame != null) ? clientFrame.getFriendTableModel() : new MemberVOTableModel("ģ������Ʈ");
		friend_list.setModel(model_friend_list);
		event();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 890, 475);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNewLabel_1    .setBounds(12, 33, 57, 15);
		jtf_title        .setBounds(72, 30, 146, 21);
		jbt_add          .setBounds(405, 88, 64, 23);
		jbt_del          .setBounds(405, 182, 64, 23);
		Jsc_friend_list  .setBounds(12, 73, 380, 320);
		Jsc_Room_add_list.setBounds(482, 73, 380, 320);
		
		jtf_title.setColumns(10);
		contentPanel.add(lblNewLabel_1);
		contentPanel.add(jtf_title);
		contentPanel.add(jbt_add);
		contentPanel.add(jbt_del);
		contentPanel.add(Jsc_friend_list);
		contentPanel.add(Jsc_Room_add_list);
		
		buttonPane.add(jbt_Cancel);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.add(jbt_ok);
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getRootPane()   .setDefaultButton(jbt_ok);
		
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
	            	
	            		JOptionPane.showMessageDialog(this, "�̹� �߰��ϼ̽��ϴ�.", "�˸�", JOptionPane.CLOSED_OPTION);
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
        	//add_list�� ����� ���������.
        	
        	if(jtf_title.getText()==null||jtf_title.getText().equals("")) {
        		JOptionPane.showMessageDialog(this, "�������� �߰����ּ���", "�˸�", JOptionPane.CLOSED_OPTION);
        		return;
        	}
        	//�� ������ 0��° chatVO���� �� ������ �� ������ ����.
        	ArrayList<ChatVO> request = new ArrayList<ChatVO>();
        	RoomVO roomVO = new RoomVO(0, null, jtf_title.getText());
        	ChatVO chatVO = new ChatVO(0,roomVO,null,null,clientFrame.getClientData().getMyData());
        	request.add(chatVO);
        	
        	//�� �������ʹ� ���õ� ģ������ chatVO�� ��´�.
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
