package messenger.server.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import messenger._db.DBConnection;
import messenger._db.vo.MemberVO;

public class FriendMenu {
	final String _URL = "jdbc:oracle:thin:@192.168.0.235:1521:orcl11";
	final String _USER = "scott";
	final String _PW = "tiger";
	DBConnection		dbcon		 = null;
	Connection 			con 		 = null;
	CallableStatement 	cstmt  		 = null;
	PreparedStatement	pstmt		 = null;
	Statement 			stmt		 = null;
	ResultSet 		  	rs			 = null;
	ObjectOutputStream 	moos		 = null;//�޴��� ��� ��ǲ��Ʈ��(������ ����)
	String 				out_msg		 = null;//���ν����� ó���� ����� ����.
		FriendMenu(){
		System.out.println("con : "+con);
	}
		public ArrayList<MemberVO> FriendSelectALL(ArrayList<MemberVO> fo) {
			ArrayList<MemberVO> list = new ArrayList<MemberVO>();
			//		proc_friend_selectall


			//select * from member where mem_no = (select fri_no from friend where mem_no = 21) 
			//����׽� �����ڵ� ����.
			String sql = "";
			sql +="select mem_id, mem_name, mem_nick from member";
			sql +=" where mem_no = (select fri_no from friend";
			sql +=" where = "+fo.get(0).getMem_id();
			try {
				dbcon.getConnection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					int mem_no = rs.getInt("mem_no");
					String mem_id = rs.getString("mem_id");
					String mem_name = rs.getString("mem_name");
					String mem_nick = rs.getString("mem_nick");
					MemberVO memVO = new MemberVO(mem_no,mem_id,mem_name,mem_nick,null,null,null,null,null);
					list.add(memVO);//ģ�� ��ȣ, ģ�����̵�, ģ���̸�, ģ���г��Ӹ� ����.
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("rs : "+rs);//����׿�
			// ���Ϻκ��� �ƿ�ǲ ��Ʈ������ ������ ������ ���� ��, ������ �ν��Ͻ� ��ǲ���� �ް�
			// �������� �ƿ�ǲ���� Ŭ���̾�Ʈ���� ������Ʈ�� ����� ����.
			// �� ������Ʈ Ÿ���� MemberVOŸ������ �ִ°� �°���..?
			return list;

		}
	

	public String FriendInsert(ArrayList<MemberVO> fo, int option) {
		try {
			dbcon.getConnection();
			//������ ������Ʈ�� �޾Ƽ� String���� ��ȯ�ؼ� �����ͺ��̽��� �Է� ��û.
			//////////////////////////////////////////////////////////
			//  0 : �����ID
			//  1 : ģ��ID
			//  2 : ���(insert)
			//////////////////////////////////////////////////////////
			cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");	
			cstmt.setObject(1, fo.get(0).getMem_name());//ID
			cstmt.setObject(2, fo.get(0).getMem_id());//ģ��ID
			cstmt.setInt(3, option);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
//			rs=cstmt.executeQuery();
			out_msg = cstmt.getString(4);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return out_msg;
	}
	public String FriendDelete(ArrayList<MemberVO> fo, int option) {
		
		try {
			dbcon.getConnection();

			cstmt = con.prepareCall("{call proc_addr_update(?,?,?,?)}");	
			cstmt.setObject(1, fo.get(0).getMem_name());//ID
			cstmt.setObject(2, fo.get(0).getMem_id());//ģ��ID
			cstmt.setInt(3, option);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			out_msg = cstmt.getString(4);
			
				} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return out_msg;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}