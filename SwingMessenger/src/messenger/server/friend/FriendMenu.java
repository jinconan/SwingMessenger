package messenger.server.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import messenger._db.vo.MemberVO;

public class FriendMenu {
	final String _URL = "jdbc:oracle:thin:@192.168.0.235:1521:orcl11";
	final String _USER = "scott";
	final String _PW = "tiger";
	Connection 			con 		 = null;
	PreparedStatement 	pstmt  		 = null;
	ResultSet 		  	rs			 = null;
	ObjectOutputStream 	moos		 = null;//�޴��� ��� ��ǲ��Ʈ��(������ ����)
	public Connection getConnetion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//Ŭ������ �޸𸮿� �ε� Ŭ���� �̸��� ��ã���� �����?
			con = DriverManager.getConnection(_URL, _USER, _PW);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}
		return con;
	}
	FriendMenu(){
		
		System.out.println("con : "+con);
	}

	public ArrayList<MemberVO> FriendSelectALL(ArrayList<MemberVO> fo) {
		
//		proc_friend_selectall
		try {
			pstmt = con.prepareCall("{proc_friend_selectall(?)}");	
			pstmt.setObject(1, fo.get(0).getMem_no());//ID
			rs = pstmt.executeQuery();
			System.out.println("rs : "+rs);
			rs=pstmt.executeQuery();
									
			} catch (Exception e) {
			// TODO: handle exception
				
		}
		// ���Ϻκ��� �ƿ�ǲ ��Ʈ������ ������ ������ ���� ��, ������ �ν��Ͻ� ��ǲ���� �ް�
		// �������� �ƿ�ǲ���� Ŭ���̾�Ʈ���� ������Ʈ�� ����� ����.
		// �� ������Ʈ Ÿ���� MemberVOŸ������ �ִ°� �°���..?
		return fo;//
		
	}
	public String FriendInsert(ArrayList<MemberVO> fo, int option) {
		try {
			con = this.getConnetion();
			//������ ������Ʈ�� �޾Ƽ� String���� ��ȯ�ؼ� �����ͺ��̽��� �Է� ��û.
			//////////////////////////////////////////////////////////
			//  0 : �����ID
			//  1 : ģ��ID
			//  2 : ���(insert)
			//////////////////////////////////////////////////////////
			pstmt = con.prepareCall("{call proc_friend_option(?,?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_name());//ID
			pstmt.setObject(2, fo.get(0).getMem_id());//ģ��ID
			pstmt.setInt(3, option);
			rs=pstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return rs.toString();
	}
	public String FriendDelete(ArrayList<MemberVO> fo, int option) {
		try {
			con = this.getConnetion();
			//������ ������Ʈ�� �޾Ƽ� String���� ��ȯ�ؼ� �����ͺ��̽��� �Է� ��û.
			//////////////////////////////////////////////////////////
			//  0 : �����ID
			//  1 : ģ��ID
			//  2 : ���(delect)
			//////////////////////////////////////////////////////////
	
			pstmt = con.prepareCall("{call proc_addr_update(?,?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_name());//ID
			pstmt.setObject(2, fo.get(0).getMem_id());//ģ��ID
			pstmt.setInt(3, option);
			rs=pstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return rs.toString();
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}