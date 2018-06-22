package messenger._db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

import messenger._db.DBConnection;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;

public class FriendDAO {
//	private static FriendDAO dao = null;
//	private DBConnection dbCon = new DBConnection();
//	
//	private FriendDAO() {
//		
//	}
//	
// 	public static FriendDAO getInstance() {
//		if(dao == null)
//			dao = new FriendDAO();
//		return dao;
//	}
//	
// 	/**
// 	 * 
// 	 * @param mem : 클라이언트가 전달한 로그인 정보
// 	 * @return : 성공시 db에서 얻은 계정 정보, 실패시 null
// 	 */
//	synchronized public MemberVO login(MemberVO mem) {
//		String sql = "SELECT mem_no, mem_id, mem_pw FROM member WHERE mem_id=? AND mem_pw=?";
//		MemberVO out = null;
//		try (Connection con = dbCon.getConnection();
//				PreparedStatement pstmt = con.prepareStatement(sql);
//		){
//			pstmt.setString(1, mem.getMem_id());
//			pstmt.setString(2, mem.getMem_pw());
//			try (ResultSet rs = pstmt.executeQuery();){
//				if(rs.next()) {
//					int		mem_no = rs.getInt("mem_no");
//					String	mem_id = rs.getString("mem_id");
//					String	mem_pw = rs.getString("mem_pw");
//					
//					out = new MemberVO(mem_no, mem_id, mem_pw);
//				}
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return out;
//	}
//
//	/**
//	 * 
//	 * @param chat : 한 클라이언트가 전달한 대화로그
//	 * @return : 성공시 param과 같은 값, 실패시 null
//	 */
//	synchronized public ChatVO sendChat(ChatVO chat) {
//		String sql = "INSERT INTO chat_log(chat_logno, chat_roomno, chat_content, chat_date1, chat_date2, chat_partic)" + 
//					 " VALUES(seq_chat_log.nextval, ?, ?, ?, ?, ?);";
//		int out = 0;
//		try (
//			Connection con = dbCon.getConnection();
//			PreparedStatement pstmt = con.prepareStatement(sql);
//		){
//			int i = 1;
//			SimpleDateFormat ymd_format = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat hms_format = new SimpleDateFormat("hh:mm:ss");
//			pstmt.setInt(i++, chat.getChat_roomno());
//			pstmt.setString(i++, chat.getChat_content());
//			pstmt.setString(i++, chat.getChat_date1());
//			pstmt.setString(i++, chat.getChat_date2());
//			pstmt.setString(i++, chat.getChat_partic());
//			
//			out = pstmt.executeUpdate();
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		return (out != 0) ? chat : null;
//	}
//
//	/**
//	 * 
//	 * @param member_list : 클라이언트가 전달한 생성될 방의 참가자 리스트
//	 */
//	public void createRoom(List<MemberVO> member_list) {
//		
//	}
//	
//	/**
//	 * 
//	 * @param mem : 방 참여 현황을 알고자 하는 클라이언트의 정보
//	 */
//	public void getRoomList(MemberVO mem) {
//		
//	}
//	
//	/**
//	 * 기존 방에 사람을 초대
//	 */
//	public void invite() {
//		
//	}
//	
//	/**
//	 * 
//	 */
}
