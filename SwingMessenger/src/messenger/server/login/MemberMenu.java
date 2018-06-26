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
			cstmt.setObject(1, 8888);//ȸ����ȣ
			cstmt.setObject(2, mvo.get(0).getMem_id());//ID
			cstmt.setObject(3, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(4, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(5, mvo.get(0).getMem_gender());//����
			cstmt.setObject(6, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(7, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(8, mvo.get(0).getMem_profile());//����
			cstmt.setObject(9, mvo.get(0).getMem_background());//����
			cstmt.setObject(10, Message.MEMBER_JOIN);//�ɼǹ�ȣ
			cstmt.registerOutParameter(11, java.sql.Types.VARCHAR);//ó���޼���
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
			cstmt.setObject(1, 8888);//ȸ����ȣ
			cstmt.setObject(2, mvo.get(0).getMem_id());//ID
			cstmt.setObject(3, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(4, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(5, mvo.get(0).getMem_gender());//����
			cstmt.setObject(6, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(7, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(8, mvo.get(0).getMem_profile());//����
			cstmt.setObject(9, mvo.get(0).getMem_background());//����
			cstmt.setObject(10, Message.MEMBER_MODIFY);//�ɼǹ�ȣ
			cstmt.registerOutParameter(11, java.sql.Types.VARCHAR);//ó���޼���
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
