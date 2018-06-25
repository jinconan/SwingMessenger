package messenger._db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import messenger._db.DBConnection;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger.server.chat.ChatServerThreadList;

/**
 * DB의 member테이블에 대해서 SELECT문을 수행하는 Class
 * <담당하는 메시지 타입>
 * 	MEMBER_LOGIN
 *  MEMBER_JOIN
 * @author Jin Lee
 *
 */
public class LoginDAO {
	private DBConnection dbCon = new DBConnection();
	
	private static class LazyHolder {
		private static final LoginDAO INSTANCE = new LoginDAO();
	}
	
	private LoginDAO() {}
	
	/**
	 * 
	 * @return 인스턴스
	 */
 	public static LoginDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	
 	/**
 	 * 계정 정보로 member테이블에 select하여 해당 계정에 대한 상세 정보를 가져오는 메소드
 	 * @param mem : 클라이언트가 전달한 로그인 정보
 	 * @return : 성공시 db에서 얻은 계정 정보, 실패시 null
 	 */
	public synchronized MemberVO login(MemberVO mem) {
		StringBuilder sql = new StringBuilder("SELECT ");
				sql.append("mem_no");
				sql.append(",mem_id" );
				sql.append(",mem_name"); 
				sql.append(",mem_nick");
				sql.append(",mem_gender");  
				sql.append(",mem_pw");  
				sql.append(",mem_hp");  
				sql.append(",mem_profile");  
				sql.append(",mem_background"); 
				sql.append(" FROM member WHERE mem_id=? AND mem_pw=?");  
		
		MemberVO memberVO = null;
		try (
			Connection 			con		= dbCon.getConnection();
			PreparedStatement	pstmt	= con.prepareStatement(sql.toString());
		){
			pstmt.setString(1, mem.getMem_id());
			pstmt.setString(2, mem.getMem_pw());
			try (
				ResultSet rs = pstmt.executeQuery();
			){
				if(rs.next()) {
					int		mem_no = rs.getInt("mem_no");
					String	mem_id = rs.getString("mem_id");
					String	mem_name = rs.getString("mem_name");
					String	mem_nick = rs.getString("mem_nick");
					String	mem_gender = rs.getString("mem_gender");
					String	mem_pw = rs.getString("mem_pw");
					String	mem_hp = rs.getString("mem_hp");
					String	mem_profile_url = rs.getString("mem_profile");
					String	mem_background_url = rs.getString("mem_background");
					JLabel mem_profile = new JLabel(new ImageIcon(mem_profile_url));
					JLabel mem_background = new JLabel(new ImageIcon(mem_background_url));
					
					memberVO = new MemberVO(mem_no, mem_id, mem_name, mem_nick, mem_gender, mem_pw, mem_hp, mem_profile, mem_background);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVO;
	}

	/**
	 * 회원가입을 위해 member테이블에 insert하는 메소드
	 * @param mem : 양식에 채워놓은 회원정보 객체
	 * @return : 회원가입 성공시1, 실패시 0
	 */
	public synchronized int join(MemberVO mem) {
		int result =0;
		StringBuilder sql = new StringBuilder("INSERT INTO member(");
		sql.append("mem_no");
		sql.append(",mem_id" );
		sql.append(",mem_name"); 
		sql.append(",mem_nick");
		sql.append(",mem_gender");  
		sql.append(",mem_pw");  
		sql.append(",mem_hp");  
		sql.append(") VALUES(seq_member.nextval, ?, ?, ?, ?, ?, ?) ");  

		try (
			Connection 			con		= dbCon.getConnection();
			PreparedStatement	pstmt	= con.prepareStatement(sql.toString());
		){
			int i=1;
			pstmt.setString(i++, mem.getMem_id());
			pstmt.setString(i++, mem.getMem_name());
			pstmt.setString(i++, mem.getMem_nick());
			pstmt.setString(i++, mem.getMem_gender());
			pstmt.setString(i++, mem.getMem_pw());
			pstmt.setString(i++, mem.getMem_hp());
			result = pstmt.executeUpdate();
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * member테이블에 해당하는 id가 있는지 여부를 판별하는 메소드
	 * @param mem_id : 회원 아이디
	 * @return : true : 이미 존재함. , false : 없음.
	 */
	public synchronized boolean hasID(String mem_id) {
		boolean result = false;
		StringBuilder sql = new StringBuilder(); 
		sql.append("SELECT mem_id FROM member WHERE mem_id=?");
		
		try(Connection con = dbCon.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
		) {
			int i=1;
			pstmt.setString(i++, mem_id);
			try(ResultSet rs = pstmt.executeQuery();) {
				if(rs.next())
					result = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * member테이블에 대한 update 수행 메소드
	 * @param mem : 수정한 회원 정보
	 * @return
	 */
	public synchronized int modify(MemberVO mem) {
		int result= 0;
		
		return result;
	}
}
