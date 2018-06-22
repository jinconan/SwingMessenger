package messenger.server.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import messenger._db.vo.MemberVO;

public class FriendMenu {
	final String _URL = "jdbc:oracle:thin:@192.168.0.235:1521:orcl11";
	final String _USER = "scott";
	final String _PW = "tiger";
	Connection 	con 		= null;
	PreparedStatement pstmt = null;
	public Connection getConnetion() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//클래스를 메모리에 로딩 클래스 이름을 못찾으면 어떡하지?
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
		String uid = (String)fo.get(0).getMem_id();
		String resultmsg = "";
//		proc_friend_selectall
		try {
			pstmt = con.prepareCall("{proc_friend_selectall(?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_no());//ID
			

			} catch (Exception e) {
			// TODO: handle exception
				
		}
		return fo;
		
	}
	public ArrayList<MemberVO> FriendInsert(ArrayList<MemberVO> fo) {
		try {
			con = this.getConnetion();
			//들어오는 오브젝트를 받아서 String으로 변환해서 데이터베이스에 입력 요청.
			//////////////////////////////////////////////////////////
			//  0 : 사용자ID
			//  1 : 친구ID
			//  2 : 기능(insert)
			//////////////////////////////////////////////////////////
			pstmt = con.prepareCall("{call proc_friend_option(?,?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_name());//ID
			pstmt.setObject(2, fo.get(0).getMem_id());//친구ID
			pstmt.setObject(3, fo.get(0));//기능
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return fo;
	}
	public ArrayList<MemberVO> FriendDelete(ArrayList<MemberVO> fo) {
		try {
			con = this.getConnetion();
			//들어오는 오브젝트를 받아서 String으로 변환해서 데이터베이스에 입력 요청.
			//////////////////////////////////////////////////////////
			//  0 : 사용자ID
			//  1 : 친구ID
			//  2 : 기능(insert)
			//////////////////////////////////////////////////////////
	
			pstmt = con.prepareCall("{call proc_addr_update(?,?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_name());//ID
			pstmt.setObject(2, fo.get(0).getMem_id());//친구ID
			pstmt.setObject(3, fo.get(2));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.toString());
		}
		return fo;
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}