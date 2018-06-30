package messenger.client.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import messenger._db.vo.MemberVO;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;

public class FriendPanel extends JPanel{
	private ClientFrame clientFrame;

	MemberVOTable myTable = new MemberVOTable();
	MemberVOTable friendTable = new MemberVOTable();
	JScrollPane jsp_friendTable = new JScrollPane(friendTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public FriendPanel(ClientFrame clientFrame) {
		this.clientFrame = clientFrame;
		this.setLayout(new BorderLayout());
		myTable.setModel(new MemberVOTableModel());
		this.add("North", myTable);
		this.add("Center", jsp_friendTable);
	}
	
	public synchronized void refreshMyTable(MemberVO mVO) {
		if(myTable.getModel().getRowCount() > 0)
			((MemberVOTableModel)(myTable.getModel())).removeRow(0);
		((MemberVOTableModel)myTable.getModel()).addRow(mVO);
	}
}
