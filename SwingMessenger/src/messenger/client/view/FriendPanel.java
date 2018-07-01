package messenger.client.view;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import messenger._db.vo.MemberVO;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;

public class FriendPanel extends JPanel{
	private ClientData clientData;
	
	private MemberVOTableModel myTableModel = new MemberVOTableModel("��");
	private MemberVOTableModel friendTableModel = new MemberVOTableModel("ģ�� ����Ʈ");
	
	private MemberVOTable myTable = new MemberVOTable();
	private MemberVOTable friendTable = new MemberVOTable();
	
	private JScrollPane jsp_friendTable = new JScrollPane(friendTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																 	 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public FriendPanel(ClientData clientData) {
		this.clientData = clientData;
		this.setLayout(new BorderLayout());
		myTable.setModel(myTableModel);
		friendTable.setModel(friendTableModel);
		this.add("North", myTable);
		this.add("Center", jsp_friendTable);
	}
	
	/**
	 * �� ������ �����ִ� ���̺��� �����Ѵ�.
	 * @param mVO : �� ����
	 */
	public synchronized void refreshMyTable(MemberVO mVO) {
		if(myTableModel.getRowCount() > 0)
			myTableModel.removeRow(0);
		myTableModel.addRow(mVO);
	}
	
	/**
	 * ģ�� ��� ���̺��� �����Ѵ�.
	 * @param mVOs : ģ�� ����Ʈ
	 */
	public synchronized void refreshFriendTable(ArrayList<MemberVO> mVOs) {
		while(friendTableModel.getRowCount() > 0)
			friendTableModel.removeRow(0);
		for(MemberVO mVO : mVOs)
			friendTableModel.addRow(mVO);
	}
	
	/**
	 * ģ�� �� ���� ���̺�𵨿� �߰��Ѵ�.
	 * @param mVO
	 */
	public synchronized void addFriendRow(MemberVO mVO) {
		friendTableModel.addRow(mVO);
	}
	
	/**
	 * ���̺� �𵨿��� ģ�� �� ���� �����Ѵ�.
	 * @param mVO
	 */
	public synchronized void removeFriendRow(MemberVO mVO) {
		for(int i=0;i<friendTableModel.getRowCount();i++) {
			MemberVO f_mVO = friendTableModel.getValueAt(i, 0);
			if(f_mVO.getMem_no() == mVO.getMem_no()) {
				friendTableModel.removeRow(i);
				return;
			}
		}
	}
}
