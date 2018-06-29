package messenger.test;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;
import messenger.protocol.Port;
public class ChatRoomLoadTest extends JFrame{
	ArrayList<RoomVO> roomList = new ArrayList<RoomVO>();
	ChatRoomLoadTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTable table = new JTable();
		
		add("Center", new JScrollPane(table));
		
		try {
			Socket socket = new Socket("localhost", Port.CHAT);
			
			try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());) {
				ArrayList<messenger._db.vo.ChatVO> request = new ArrayList<messenger._db.vo.ChatVO>();
				MemberVO mem = new MemberVO();
				mem.setMem_no(25);
				request.add(new messenger._db.vo.ChatVO(0, null, null, null, mem));
				Message<messenger._db.vo.ChatVO> msg = new Message<messenger._db.vo.ChatVO>(Message.CHATROOM_LOAD,request,null);
				
				out.writeObject(msg);
				out.flush();
				try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
					 msg = (Message<messenger._db.vo.ChatVO>)in.readObject();
					 List<messenger._db.vo.ChatVO> response = msg.getResponse();
					 DefaultTableModel dtm = new DefaultTableModel();
					 dtm.setColumnIdentifiers(new String[] {"¹æ"});
					for(messenger._db.vo.ChatVO c : response) {
						dtm.addRow(new String[] {c.getRoomVO().getRoom_title()});
					}
					table.setModel(dtm);
					pack();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		new ChatRoomLoadTest();

	}

}
