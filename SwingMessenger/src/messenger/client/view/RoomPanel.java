package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;

public class RoomPanel extends JPanel implements ActionListener {
	private ClientFrame 		clientFrame;
	private JButton				jb_newRoom 	= new JButton("방 만들기");
	private DefaultTableModel 	dtm  		= new DefaultTableModel(new String[] {"번호", "제목"}, 0) {
											 	@Override
											 	public boolean isCellEditable(int row, int column) {
											 		// TODO Auto-generated method stub
											 		return false;
											 	}
			 								};
	private JTable				jt_room 	= new JTable(dtm);
	private JScrollPane 		jsp_room 	= new JScrollPane(jt_room, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
	
	public RoomPanel(ClientFrame clientFrame) {
		this.clientFrame = clientFrame;
		this.setLayout(new BorderLayout());
		jt_room.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		jt_room.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == 3) {
					int row = jt_room.getSelectedRow();
					int room_no = Integer.parseInt((String)jt_room.getValueAt(row, 0));
					ChatDialog dialog = clientFrame.clientData.getChatDialog(room_no);
					System.out.println(dialog);
					if(dialog != null) 
						dialog.setVisible(true);
				}
			}
		});
		jb_newRoom.addActionListener(this);
		this.add("North", jb_newRoom);
		this.add("Center", jsp_room);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		ArrayList<ChatVO> request = new ArrayList<ChatVO>();
//		
//		ChatVO chatVO = new ChatVO(0, null, null, null, clientFrame.clientData.myData);
//		request.add(chatVO);
//		MemberVO mem = new MemberVO();
//		mem.setMem_no(35);
//		chatVO = new ChatVO(0,null,null,null,mem);
//		request.add(chatVO);
//		Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_INVITE,request,null);
//		
//		try {
//			clientFrame.clientData.out.writeObject(msg);
//			clientFrame.clientData.out.flush();
//			System.out.println("보냄");
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
		
	}
	
	public void refreshRoomList(ArrayList<RoomVO> rVOList) {
		clientFrame.clientData.refreshRoomList(rVOList);
		
		dtm.setRowCount(0);
		for(RoomVO rVO : rVOList) {
			dtm.addRow(new String[] {Integer.toString(rVO.getRoom_no()), rVO.getRoom_title()});
		}
	}
}
