package messenger._db.dao;

import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import messenger._db.DBConnection;
import messenger._db.vo.MemberVO;

public class FriendMenu {
	private DBConnection dbCon = new DBConnection();
	Connection con = null;
	CallableStatement cstmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	ObjectOutputStream moos = null;// �޴��� ��� ��ǲ��Ʈ��(������ ����)
	String out_msg = null;// ���ν����� ó���� ����� ����.

	public ArrayList<MemberVO> FriendSelectALL(ArrayList<MemberVO> fo) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		// proc_friend_selectall

		// select * from member where mem_no = (select fri_no from friend where mem_no =
		// 21)
		// ����׽� �����ڵ� ����.
		String sql = "";
		sql += "select mem_no, mem_id, mem_name, mem_nick from member";
		sql += " where mem_no in (select fri_no from friend";
		sql += " where mem_no = ?)";
		try {
			con = dbCon.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,fo.get(0).getMem_no());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int mem_no = rs.getInt("mem_no");
				String mem_id = rs.getString("mem_id");
				String mem_name = rs.getString("mem_name");
				String mem_nick = rs.getString("mem_nick");
				MemberVO memVO = new MemberVO(mem_no, mem_id, mem_name, mem_nick, null, null, null, null, null);
				
				System.out.println(mem_no + ", " + mem_name + ", " + mem_nick);
				list.add(memVO);// ģ�� ��ȣ, ģ�����̵�, ģ���̸�, ģ���г��Ӹ� ����.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// ���Ϻκ��� �ƿ�ǲ ��Ʈ������ ������ ������ ���� ��, ������ �ν��Ͻ� ��ǲ���� �ް�
		// �������� �ƿ�ǲ���� Ŭ���̾�Ʈ���� ������Ʈ�� ����� ����.
		// �� ������Ʈ Ÿ���� MemberVOŸ������ �ִ°� �°���..?
		return list;

	}

	public ArrayList<MemberVO> FriendSearch(ArrayList<MemberVO> fo) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String sql = "";
		sql += "select mem_id, mem_name, mem_nick from member";
		sql += " where mem_id = ?";
		try {
			con = dbCon.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fo.get(0).getMem_id());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String mem_id = rs.getString("mem_id");
				String mem_name = rs.getString("mem_name");
				String mem_nick = rs.getString("mem_nick");
				MemberVO memVO = new MemberVO(0, mem_id, mem_name, mem_nick, null, null, null, null, null);
				list.add(memVO);// ģ�� ��ȣ, ģ�����̵�, ģ���̸�, ģ���г��Ӹ� ����.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public String FriendInsert(ArrayList<MemberVO> fo, int option) {
		try {
			con = dbCon.getConnection();
			// ������ ������Ʈ�� �޾Ƽ� String���� ��ȯ�ؼ� �����ͺ��̽��� �Է� ��û.
			//////////////////////////////////////////////////////////
			// 0 : �����ID
			// 1 : ģ��ID
			// 2 : ���(insert)
			//////////////////////////////////////////////////////////
			cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");
			cstmt.setObject(1, fo.get(0).getMem_id());// ���� ID
			cstmt.setObject(2, fo.get(1).getMem_id());// ģ�� ID
			cstmt.setInt(3, option);
			
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			// rs=cstmt.executeQuery();
			cstmt.executeUpdate();
			out_msg = cstmt.getString(4);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		} finally {
			try {
				if(cstmt != null) cstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return out_msg;
	}

	public String FriendDelete(ArrayList<MemberVO> fo, int option) {

		try {
			con = dbCon.getConnection();
			cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");
			cstmt.setObject(1, fo.get(0).getMem_id());// ID
			cstmt.setObject(2, fo.get(1).getMem_id());// ģ��ID
			cstmt.setInt(3, option);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			out_msg = cstmt.getString(4);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} finally {
			try {
				if(cstmt != null) cstmt.close();
				if(con != null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return out_msg;
	}
}