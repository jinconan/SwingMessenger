package messenger.client.view.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.MemberVO;
import messenger.client.friend.ClientFriendAdd;
import messenger.client.friend.ClientFriendSearch;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;
import messenger.client.view.ClientFrame;

public class SearchDialog extends JDialog implements ActionListener, KeyListener{
	ClientFrame 		clientFrame;
	JTextField  		jtf_shfri = new JTextField();
	JButton 			jbtn_gum = new JButton("검색");
	JButton 			jbtn_chu = new JButton("추가");
	
	MemberVOTableModel	mvoModel = new MemberVOTableModel("검색결과");
	MemberVOTable		mvoTable = new MemberVOTable(mvoModel);
	JScrollPane			jsp_result = new JScrollPane(mvoTable);
	
	public SearchDialog(ClientFrame clientFrame) {
		super(clientFrame, "친구 찾기", true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.clientFrame = clientFrame;
		jbtn_gum.addActionListener(this);
		this.setLayout(null);
		this.setSize(300, 240);
		jtf_shfri.addKeyListener(this);
		
		Font f = new Font("굴림", Font.CENTER_BASELINE, 10);
		jtf_shfri.setBounds(5,165, 210, 30);
		jbtn_gum.setFont(f);
		jbtn_gum.setBounds(220, 165, 60, 30);
		jbtn_chu.setFont(f);
		jbtn_chu.setBounds(5,5,275,30);
		jsp_result.setBounds(5,40,275,120);
		
		this.add(jtf_shfri);
		this.add(jsp_result);
		this.add(jbtn_gum);
		this.add(jbtn_chu);
		this.setVisible(true);
	}

	public void setDialog(List<MemberVO> mvoList) {
		//테이블 모델의 값을 전부 지운다.
		while(mvoModel.getRowCount() > 0)
			mvoModel.removeRow(0);
		
		//테이블 모델에 결과를 출력한다.
		Iterator<MemberVO> iter = mvoList.iterator();
		while(iter.hasNext()) {
			MemberVO mvo = iter.next();
			mvoModel.addRow(mvo);
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("검색")) {
			String id = jtf_shfri.getText();
			ClientFriendSearch cfs = new ClientFriendSearch(id, clientFrame, this);
			cfs.getFriendSearch();
		}
		
		else if(e.getActionCommand().equals("추가")) {
			int row = mvoTable.getSelectedRow();
			if(row >= 0) {
				MemberVO mvo = mvoModel.getValueAt(row,  0);
				ClientFriendAdd cfa = new ClientFriendAdd(clientFrame.getClientData().getMyData().getMem_id(), mvo.getMem_id(),clientFrame);
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
