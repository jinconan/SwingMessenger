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
	ObjectOutputStream 	moos		 = null;//메뉴가 담는 인풋스트림(서버로 보냄)
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
		// 리턴부분은 아웃풋 스트림으로 보내서 서버가 받은 뒤, 서버가 인스턴스 인풋으로 받고
		// 서버에서 아웃풋으로 클라이언트에게 오브젝트된 결과를 보냄.
		// 그 오브젝트 타입은 MemberVO타입으로 주는게 맞겠지..?
		return fo;//
		
	}
	public String FriendInsert(ArrayList<MemberVO> fo, int option) {
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
			//들어오는 오브젝트를 받아서 String으로 변환해서 데이터베이스에 입력 요청.
			//////////////////////////////////////////////////////////
			//  0 : 사용자ID
			//  1 : 친구ID
			//  2 : 기능(delect)
			//////////////////////////////////////////////////////////
	
			pstmt = con.prepareCall("{call proc_addr_update(?,?,?)}");	
			pstmt.setObject(1, fo.get(0).getMem_name());//ID
			pstmt.setObject(2, fo.get(0).getMem_id());//친구ID
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