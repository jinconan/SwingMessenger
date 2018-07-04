package messenger.client.view.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import messenger.client.friend.ClientFriendAdd;
import messenger.client.friend.ClientFriendSearch;
import messenger.client.view.ClientFrame;
import oracle.net.aso.e;

public class SearchDialog extends JDialog implements ActionListener, KeyListener{
	ClientFrame clientFrame;
	JTextField  jtf_shfri = new JTextField();
	String[]	cols	=	{"아이디", "이름", "상태메세지"};
	DefaultTableModel dtm = new DefaultTableModel(cols, 0);
	JTable		jtb_result = new JTable(dtm);
	JScrollPane	jsp_result = new JScrollPane(jtb_result);
	JButton 	jbtn_gum = new JButton("검색");
	JButton 	jbtn_chu = new JButton("추가");
	
	public SearchDialog(ClientFrame clientFrame) {
		super(clientFrame, "친구 찾기", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.clientFrame = clientFrame;
		jbtn_gum.addActionListener(this);
		this.setLayout(null);
		this.setSize(300, 210);
		jtf_shfri.addKeyListener(this);
		
		jtb_result.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		Font f = new Font("굴림", Font.CENTER_BASELINE, 10);
		jtf_shfri.setBounds(5,135, 210, 30);
		jbtn_gum.setFont(f);
		jbtn_gum.setBounds(220, 135, 60, 30);
		jbtn_chu.setFont(f);
		jbtn_chu.setBounds(5,5,275,30);
		jsp_result.setBounds(5,40,275,90);
		
		this.add(jtf_shfri);
		this.add(jsp_result);
		this.add(jbtn_gum);
		this.add(jbtn_chu);
		this.setVisible(true);
	}

	public void setDialog(String[][] datas) {
		dtm.setRowCount(0);
//		dtm.addRow(datas);
		
		for(int i=0;i<datas.length;i++) {
			Vector<String> v = new Vector();
			v.add(datas[i][0]);
			v.add(datas[i][1]);
			v.add(datas[i][2]);
			dtm.addRow(v);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		int answer = 0;
		
		if(e.getActionCommand().equals("검색")) {
			String id = jtf_shfri.getText();
			ClientFriendSearch cfs = new ClientFriendSearch(id, clientFrame, this);
			cfs.getFriendSearch();
//			try {
//				// 검색 요청 클라이언트
//				// 성공하면
//				answer = JOptionPane.showConfirmDialog(this, jtf_shfri.getText() + "님을 추가하시겠습니까?", "친구추가",
//						JOptionPane.YES_NO_OPTION);
//				if (answer == JOptionPane.YES_OPTION) {
//					try {
//						// 친구 추가 클라이언트
//						// 추가하면
//						JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "님이 추가되었습니다. \n 목록을 갱신합니다.",
//								"친구추가", JOptionPane.OK_OPTION);
//					} catch (Exception fe2) {
//						// TODO: handle exception
//						JOptionPane.showMessageDialog(this, "친구추가에 실패하였습니다.", "Error", JOptionPane.ERROR_MESSAGE);
//					}
//				} else if (answer == JOptionPane.NO_OPTION) {
//					this.dispose();
//				}
//			} catch (Exception fe) {
//				JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "님을 찾을 수 없습니다.", "검색실패",
//						JOptionPane.ERROR_MESSAGE);
//			}
		}
		else if(e.getActionCommand().equals("추가")) {
			int row = jtb_result.getSelectedRow();
			if(row >= 0) {
				String id = (String)jtb_result.getValueAt(row,  0);
				ClientFriendAdd cfa = new ClientFriendAdd(clientFrame.getClientData().getMyData().getMem_id(), id,clientFrame);
				cfa.getFriendAdd();
				this.dispose();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new SearchDialog(null);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
		if(Character.isLetterOrDigit(e.getKeyChar()) == false) {
			e.consume();
			return;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}


	

}
