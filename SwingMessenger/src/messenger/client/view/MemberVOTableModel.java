package messenger.client.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.MemberVO;

public class MemberVOTableModel extends AbstractTableModel {
	private List<MemberVO> list;
	private String columnName;
	
	public MemberVOTableModel() {
		this(new ArrayList<MemberVO>(), null);
	}
	
	public MemberVOTableModel(List<MemberVO> list) {
		this(list, null);
		DefaultTableModel dtm;
	}

	public MemberVOTableModel(List<MemberVO> list, String columnName) {
		this.list = list;
		this.columnName = columnName;
	}

	
	public void addRow(MemberVO mem) {
		list.add(mem);
	}
	
	public MemberVO removeRow(int row) {
		return list.remove(row);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return MemberVO.class;
	}
	@Override
	public String getColumnName(int columnIndex) {
		return columnName;
	}
	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public MemberVO getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		return (list.size()-1 >= row) ? list.get(row) : null;
	}

	
}
