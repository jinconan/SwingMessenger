package messenger.server.login;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.sql.*;
import messenger._db.DBConnection;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

public class MemberMenu {
	CallableStatement 	cstmt  		 = null;
	DBConnection		dbcon = null;
	Connection 			con =null;
	public String MemberInsert(ArrayList<MemberVO> mvo, int option) {
		try {
			dbcon.getConnection();
			cstmt = con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?,?)}");	
			cstmt.setObject(1, 8888);//회원번호
			cstmt.setObject(2, mvo.get(0).getMem_id());//ID
			cstmt.setObject(3, mvo.get(0).getMem_name());//이름
			cstmt.setObject(4, mvo.get(0).getMem_nick());//닉네임
			cstmt.setObject(5, mvo.get(0).getMem_gender());//성별
			cstmt.setObject(6, mvo.get(0).getMem_pw());//패스워드
			cstmt.setObject(7, mvo.get(0).getMem_hp());//폰번호
			cstmt.setObject(8, mvo.get(0).getMem_profile());//프사
			cstmt.setObject(9, mvo.get(0).getMem_background());//배경사
			cstmt.setObject(10, Message.MEMBER_JOIN);//옵션번호
			cstmt.registerOutParameter(11, java.sql.Types.VARCHAR);//처리메세지
			cstmt.executeUpdate();
//			rs=cstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}
	public String MemberUpdate(ArrayList<MemberVO> mvo, int option) {
		String sysmsg = "";
		try {
			dbcon.getConnection();
			cstmt = con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?,?)}");	
			cstmt.setObject(1, 8888);//회원번호
			cstmt.setObject(2, mvo.get(0).getMem_id());//ID
			cstmt.setObject(3, mvo.get(0).getMem_name());//이름
			cstmt.setObject(4, mvo.get(0).getMem_nick());//닉네임
			cstmt.setObject(5, mvo.get(0).getMem_gender());//성별
			cstmt.setObject(6, mvo.get(0).getMem_pw());//패스워드
			cstmt.setObject(7, mvo.get(0).getMem_hp());//폰번호
			cstmt.setObject(8, mvo.get(0).getMem_profile());//프사
			cstmt.setObject(9, mvo.get(0).getMem_background());//배경사
			cstmt.setObject(10, Message.MEMBER_MODIFY);//옵션번호
			cstmt.registerOutParameter(11, java.sql.Types.VARCHAR);//처리메세지
			cstmt.executeUpdate();
//			rs=cstmt.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
