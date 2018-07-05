package messenger._db;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;

/**
 * DB�� member���̺� ���ؼ� SELECT���� �����ϴ� Class
 * <����ϴ� �޽��� Ÿ��>
 * 	MEMBER_LOGIN
 *  MEMBER_JOIN
 * @author Jin Lee
 *
 */
public class MemberDAO {
	private DBConnection dbCon = DBConnection.getInstance();
	
	private static class LazyHolder {
		private static final MemberDAO INSTANCE = new MemberDAO();
	}
	
	private MemberDAO() {}
	
	/**
	 * 
	 * @return �ν��Ͻ�
	 */
 	public static MemberDAO getInstance() {
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
					JLabel 	mem_profile = getImageLabel(mem_profile_url, true);
					JLabel 	mem_background = getImageLabel(mem_background_url, false);
					
					System.out.println(mem_profile_url);
					System.out.println(mem_background_url);
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

	
	public synchronized String MemberInsert(ArrayList<MemberVO> mvo, int option) {
		String result = "";
		try (
			Connection 			con		= dbCon.getConnection();
			CallableStatement	cstmt 	= con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?)}");
		){
			cstmt.setObject(1, mvo.get(0).getMem_id());//ID
			cstmt.setObject(2, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(3, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(4, mvo.get(0).getMem_gender());//����
			cstmt.setObject(5, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(6, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(7, null);//����
			cstmt.setObject(8, null);//����
			cstmt.setObject(9, Message.MEMBER_JOIN);//�ɼǹ�ȣ
			cstmt.registerOutParameter(10, java.sql.Types.VARCHAR);//ó���޼���
			cstmt.executeUpdate();
			result = cstmt.getString(10);
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

	
	public synchronized String MemberUpdate(ArrayList<MemberVO> mvo, int option) {
		String sysmsg = "";
		try (
			Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?)}"); 
		){
			//ȸ������ ���� �޼ҵ�
			cstmt.setObject(1, mvo.get(0).getMem_id());//ID
			cstmt.setObject(2, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(3, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(4, mvo.get(0).getMem_gender());//����
			cstmt.setObject(5, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(6, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(7, mvo.get(0).getMem_profile());//����
			cstmt.setObject(8, mvo.get(0).getMem_background());//����
			cstmt.setObject(9, Message.MEMBER_MODIFY);//�ɼǹ�ȣ
			cstmt.registerOutParameter(10, java.sql.Types.VARCHAR);//ó���޼���
			cstmt.executeUpdate();
			sysmsg = cstmt.getString(10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sysmsg;
	}

	/**
	 * ���̵� �ߺ��˻� �޼ҵ�
	 * @param mvo
	 * @return
	 */
	public synchronized int MemberOverlap(ArrayList<MemberVO> mvo) {
		int overMsg = 0;
		//�ߺ��˻� ����� �ϴ� �޼ҵ�
		try (Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_member_overlap(?,?)}");
		){
			cstmt.setObject(1, mvo.get(0).getMem_id());//ȸ�����Խ� ���̵�
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);//ó���޼���
			cstmt.executeUpdate();
			overMsg = cstmt.getInt(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return overMsg;
	}
	
	
	public synchronized ArrayList<MemberVO> FriendSelectALL(ArrayList<MemberVO> fo) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		// proc_friend_selectall
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append("mem_no, mem_id, mem_name, mem_nick, mem_gender, mem_hp, mem_profile, mem_background FROM member ");
		sql.append("WHERE mem_no IN (SELECT fri_no FROM friend ");
		sql.append("WHERE mem_no = ?)");

		try (
			Connection con = dbCon.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql.toString());	
		){
			pstmt.setInt(1,fo.get(0).getMem_no());
			try (
				ResultSet rs = pstmt.executeQuery();
			){
				while (rs.next()) {
					int    mem_no				= rs.getInt("mem_no");
					String mem_id				= rs.getString("mem_id");
					String mem_name				= rs.getString("mem_name");
					String mem_nick				= rs.getString("mem_nick");
					String mem_gender			= rs.getString("mem_gender");
					String mem_hp 				= rs.getString("mem_hp");
					String mem_profile_url		= rs.getString("mem_profile");
					String mem_background_url	= rs.getString("mem_background");
					JLabel jl_profile			= getImageLabel(mem_profile_url, true);
					JLabel jl_background		= getImageLabel(mem_background_url, false);
					
					MemberVO memVO = new MemberVO(mem_no, mem_id, mem_name, mem_nick, mem_gender, null, mem_hp, jl_profile, jl_background);
					list.add(memVO);// ģ�� ��ȣ, ģ�����̵�, ģ���̸�, ģ���г��Ӹ� ����.
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// ���Ϻκ��� �ƿ�ǲ ��Ʈ������ ������ ������ ���� ��, ������ �ν��Ͻ� ��ǲ���� �ް�
		// �������� �ƿ�ǲ���� Ŭ���̾�Ʈ���� ������Ʈ�� ����� ����.
		// �� ������Ʈ Ÿ���� MemberVOŸ������ �ִ°� �°���..?
		return list;

	}
	
	public synchronized String FriendInsert(ArrayList<MemberVO> fo, int option) {
		String out_msg = null;
		try (
			Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");
		){
			// ������ ������Ʈ�� �޾Ƽ� String���� ��ȯ�ؼ� �����ͺ��̽��� �Է� ��û.
			//////////////////////////////////////////////////////////
			// 0 : �����ID
			// 1 : ģ��ID
			// 2 : ���(insert)
			//////////////////////////////////////////////////////////
			cstmt.setObject(1, fo.get(0).getMem_id());// ���� ID
			cstmt.setObject(2, fo.get(1).getMem_id());// ģ�� ID
			cstmt.setInt(3, option);
			
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			out_msg = cstmt.getString(4);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out_msg;
	}
	
	public synchronized String FriendDelete(ArrayList<MemberVO> fo, int option) {
		String out_msg = null;
		try (
			Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");
		){
			cstmt.setObject(1, fo.get(0).getMem_id());// ID
			cstmt.setObject(2, fo.get(1).getMem_id());// ģ��ID
			cstmt.setInt(3, option);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.executeUpdate();
			out_msg = cstmt.getString(4);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return out_msg;
	}
	
	public synchronized ArrayList<MemberVO> FriendSearch(ArrayList<MemberVO> fo) {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		StringBuilder sql = new StringBuilder("SELECT ");
		
		sql.append("mem_id, mem_nick, mem_profile FROM member ");
		sql.append("WHERE mem_id = ?");
		try (
			Connection con = dbCon.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql.toString());
		){
			pstmt.setString(1, fo.get(0).getMem_id());
			
			try(
				ResultSet rs = pstmt.executeQuery();
			) {
				while (rs.next()) {
					String mem_id = rs.getString("mem_id");
					String mem_nick = rs.getString("mem_nick");
					String mem_profile_url = rs.getString("mem_profile");
					JLabel mem_profile = getImageLabel(mem_profile_url, true);
					MemberVO memVO = new MemberVO(0, mem_id, null, mem_nick, null, null, null, mem_profile, null);
					list.add(memVO);// ģ�� ��ȣ, ģ�����̵�, ģ���̸�, ģ���г��Ӹ� ����.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * Ŭ�����н��� ����η� �Ͽ� �̹����� ã�Ƽ� JLabel�� �����ϴ� �޼ҵ�
	 * @param url �̹��� ���� �̸�
	 * @param isProfile true : �����ʻ��� false : ������
	 * @return �̹����������� �߰��� JLabel ��ü
	 */
	private  JLabel getImageLabel(String url, boolean isProfile) {
		JLabel jl = null;
		String type = (isProfile == true) ? "profile/" : "background/";
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(MemberDAO.class.getResource("/messenger/server/images/"+type+url));
			if(icon.getIconWidth() == -1)
				throw new NullPointerException();
		} catch(NullPointerException e) {
			if(isProfile == true)
				icon = new ImageIcon(MemberDAO.class.getResource("/messenger/server/images/profile/profile_0.png"));
			else
				icon = new ImageIcon(MemberDAO.class.getResource("/messenger/server/images/background/background_0.jpg"));
		}
		jl = new JLabel(icon);
		return jl;
	}



}
