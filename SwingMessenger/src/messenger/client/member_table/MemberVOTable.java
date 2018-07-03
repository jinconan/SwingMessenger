package messenger.client.member_table;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import messenger._db.vo.MemberVO;

public class MemberVOTable extends JTable{
	
	public MemberVOTable() {
		super();
		setDefault();
	}
	
	public MemberVOTable(MemberVOTableModel model) {
		super(model);
		setDefault();
		
	}
	
	private void setDefault() {
		MemberVOCell memberVOCell = new MemberVOCell();
		setDefaultRenderer(MemberVO.class, memberVOCell);
		setDefaultEditor(MemberVO.class, memberVOCell);
		setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		setRowHeight(85);
	}
}
