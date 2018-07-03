package messenger.client.member_table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import messenger._db.vo.MemberVO;

public class MemberVOTableModel extends AbstractTableModel {
	private List<MemberVO> list;
	private String columnName;
	
	public MemberVOTableModel() {
		this(new ArrayList<MemberVO>(), null);
	}
	
	public MemberVOTableModel(List<MemberVO> list) {
		this(list, null);
	}

	public MemberVOTableModel(String columnName) {
		this(new ArrayList<MemberVO>(), columnName);
	}
	
	public MemberVOTableModel(List<MemberVO> list, String columnName) {
		this.list = Collections.synchronizedList(list);
		this.columnName = columnName;
	}

	
	public void addRow(MemberVO mem) {
		list.add(mem);
		fireTableChanged(new TableModelEvent(this));
	}
	
	public MemberVO removeRow(int row) {
		MemberVO result =  list.remove(row); 
		fireTableChanged(new TableModelEvent(this));
		return result;
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
