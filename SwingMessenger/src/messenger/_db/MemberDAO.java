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
import messenger.protocol.Message;

/**
 * DB의 member테이블에 대해서 SELECT문을 수행하는 Class
 * <담당하는 메시지 타입>
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
	 * @return 인스턴스
	 */
 	public static MemberDAO getInstance() {
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
			result = cstmt.getString(11);
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

	
	public synchronized String MemberUpdate(ArrayList<MemberVO> mvo, int option) {
		String sysmsg = "";
		try (
			Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_member_option(?,?,?,?,?,?,?,?,?,?,?)}"); 
		){
			//회원정보 수정 메소드
			
			cstmt.setObject(1, mvo.get(0).getMem_no());//회원번호
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
			sysmsg = cstmt.getString(11);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sysmsg;
	}

	/**
	 * 아이디 중복검사 메소드
	 * @param mvo
	 * @return
	 */
	public synchronized int MemberOverlap(ArrayList<MemberVO> mvo) {
		int overMsg = 0;
		//중복검사 기능을 하는 메소드
		try (Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_member_overlap(?,?)}");
		){
			cstmt.setObject(1, mvo.get(0).getMem_id());//회원가입시 아이디
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);//처리메세지
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
					int    mem_no			= rs.getInt("mem_no");
					String mem_id			= rs.getString("mem_id");
					String mem_name			= rs.getString("mem_name");
					String mem_nick			= rs.getString("mem_nick");
					String mem_gender		= rs.getString("mem_gender");
					String mem_hp 			= rs.getString("mem_hp");
					String mem_profile		= rs.getString("mem_profile");
					String mem_background	= rs.getString("mem_background");
					
					String path = "src\\messenger\\server\\images\\";
					ImageIcon icon		= new ImageIcon(path+"profile\\"+mem_profile);
					if(icon.getIconWidth() == -1) {
						icon			= new ImageIcon(path+"profile\\profile_0.png");
					}
					JLabel jl_prof		= new JLabel(icon);
					icon				= new ImageIcon(path+"background\\"+mem_background);
					if(icon.getIconWidth() == -1) {
						icon			= new ImageIcon(path+"background\\background_0.jpg");
					}
					JLabel jl_back		= new JLabel(icon);
					MemberVO memVO = new MemberVO(mem_no, mem_id, mem_name, mem_nick, mem_gender, null, mem_hp, jl_prof, jl_back);
					
					System.out.println(mem_no + ", " + mem_name + ", " + mem_nick);
					list.add(memVO);// 친구 번호, 친구아이디, 친구이름, 친구닉네임만 담음.
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 리턴부분은 아웃풋 스트림으로 보내서 서버가 받은 뒤, 서버가 인스턴스 인풋으로 받고
		// 서버에서 아웃풋으로 클라이언트에게 오브젝트된 결과를 보냄.
		// 그 오브젝트 타입은 MemberVO타입으로 주는게 맞겠지..?
		return list;

	}
	
	public synchronized String FriendInsert(ArrayList<MemberVO> fo, int option) {
		String out_msg = null;
		try (
			Connection con = dbCon.getConnection();
			CallableStatement cstmt = con.prepareCall("{call proc_friend_option(?,?,?,?)}");
		){
			// 들어오는 오브젝트를 받아서 String으로 변환해서 데이터베이스에 입력 요청.
			//////////////////////////////////////////////////////////
			// 0 : 사용자ID
			// 1 : 친구ID
			// 2 : 기능(insert)
			//////////////////////////////////////////////////////////
			cstmt.setObject(1, fo.get(0).getMem_id());// 유저 ID
			cstmt.setObject(2, fo.get(1).getMem_id());// 친구 ID
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
			cstmt.setObject(2, fo.get(1).getMem_id());// 친구ID
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
		
		sql.append("mem_id, mem_name, mem_nick FROM member ");
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
					String mem_name = rs.getString("mem_name");
					String mem_nick = rs.getString("mem_nick");
					MemberVO memVO = new MemberVO(0, mem_id, mem_name, mem_nick, null, null, null, null, null);
					list.add(memVO);// 친구 번호, 친구아이디, 친구이름, 친구닉네임만 담음.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private  JLabel getImageLabel(String url, boolean isProfile) {
		JLabel jl = null;
		//클래스파일의 기본 경로를 가져온다.
 		ClassLoader loader = this.getClass().getClassLoader();
 		
 		//위에서 얻은 경로를 시작지점으로 하여 이모티콘이 담긴 폴더의 상대경로 얻기.
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
