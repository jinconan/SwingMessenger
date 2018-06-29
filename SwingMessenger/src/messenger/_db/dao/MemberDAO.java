package messenger._db.dao;

import java.awt.Font;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import messenger._db.DBConnection;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.chat.ChatServerThreadList;

/**
 * DB�� member���̺� ���ؼ� SELECT���� �����ϴ� Class
 * <����ϴ� �޽��� Ÿ��>
 * 	MEMBER_LOGIN
 *  MEMBER_JOIN
 * @author Jin Lee
 *
 */
public class MemberDAO {
	private DBConnection dbCon = new DBConnection();
	
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
			CallableStatement	cstmt 	= con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?,?)}");
		){
			int i=1;
			
			cstmt.setObject(i++, 8888);//ȸ����ȣ
			cstmt.setObject(i++, mvo.get(0).getMem_id());//ID
			cstmt.setObject(i++, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(i++, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(i++, mvo.get(0).getMem_gender());//����
			cstmt.setObject(i++, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(i++, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(i++, mvo.get(0).getMem_profile());//����
			cstmt.setObject(i++, mvo.get(0).getMem_background());//����
			cstmt.setObject(i++, Message.MEMBER_JOIN);//�ɼǹ�ȣ
			cstmt.registerOutParameter(i++, java.sql.Types.VARCHAR);//ó���޼���
			cstmt.executeUpdate();
			result = cstmt.getString(--i);
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
			CallableStatement cstmt = con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?,?)}"); 
		){
			//ȸ������ ���� �޼ҵ�
			int i=1;
			cstmt.setObject(i++, 8888);//ȸ����ȣ
			cstmt.setObject(i++, mvo.get(0).getMem_id());//ID
			cstmt.setObject(i++, mvo.get(0).getMem_name());//�̸�
			cstmt.setObject(i++, mvo.get(0).getMem_nick());//�г���
			cstmt.setObject(i++, mvo.get(0).getMem_gender());//����
			cstmt.setObject(i++, mvo.get(0).getMem_pw());//�н�����
			cstmt.setObject(i++, mvo.get(0).getMem_hp());//����ȣ
			cstmt.setObject(i++, mvo.get(0).getMem_profile());//����
			cstmt.setObject(i++, mvo.get(0).getMem_background());//����
			cstmt.setObject(i++, Message.MEMBER_MODIFY);//�ɼǹ�ȣ
			cstmt.registerOutParameter(i++, java.sql.Types.VARCHAR);//ó���޼���
			cstmt.executeUpdate();
			sysmsg = cstmt.getString(--i);
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
	
	
	
	private  JLabel getImageLabel(String url, boolean isProfile) {
		JLabel jl = null;
		//Ŭ���������� �⺻ ��θ� �����´�.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//������ ���� ��θ� ������������ �Ͽ� �̸�Ƽ���� ��� ������ ����� ���.
 		String location = (isProfile == true) ? "messenger\\server\\images\\profile\\" : "messenger\\server\\images\\background\\";
 		
		URL buildpath = loader.getResource(location);
		
		try {
			URI uri = new URI(buildpath.toString());
			StringBuilder imgpath = new StringBuilder(uri.getPath());
			if(url != null)
				imgpath.append(url);
			File file = new File(imgpath.toString());
			if(file.exists() && file.isFile()) {
				System.out.println(imgpath.toString());
				ImageIcon icon = new ImageIcon(imgpath.toString());
				jl = new JLabel(icon);
			}
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return jl;

	}



}
