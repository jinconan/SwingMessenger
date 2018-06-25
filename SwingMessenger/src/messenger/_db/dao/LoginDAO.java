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
 * DB�� member���̺� ���ؼ� SELECT���� �����ϴ� Class
 * <����ϴ� �޽��� Ÿ��>
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
	 * @return �ν��Ͻ�
	 */
 	public static LoginDAO getInstance() {
		return LazyHolder.INSTANCE;
	}
	
 	/**
 	 * ���� ������ member���̺� select�Ͽ� �ش� ������ ���� �� ������ �������� �޼ҵ�
 	 * @param mem : Ŭ���̾�Ʈ�� ������ �α��� ����
 	 * @return : ������ db���� ���� ���� ����, ���н� null
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
	 * ȸ�������� ���� member���̺� insert�ϴ� �޼ҵ�
	 * @param mem : ��Ŀ� ä������ ȸ������ ��ü
	 * @return : ȸ������ ������1, ���н� 0
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
	 * member���̺� �ش��ϴ� id�� �ִ��� ���θ� �Ǻ��ϴ� �޼ҵ�
	 * @param mem_id : ȸ�� ���̵�
	 * @return : true : �̹� ������. , false : ����.
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
	 * member���̺� ���� update ���� �޼ҵ�
	 * @param mem : ������ ȸ�� ����
	 * @return
	 */
	public synchronized int modify(MemberVO mem) {
		int result= 0;
		
		return result;
	}
}
