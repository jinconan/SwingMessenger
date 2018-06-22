package SemiProjectChat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

public class DAO {

	
	private Connection        con  = null;
	private PreparedStatement ppst = null;


	DBconnection dbcon = new DBconnection();
	
	public int insert(MemberDTO dto) {
		
		final String Sql = "insert into member(MEM_ID,MEM_PW,MEM_NO,MEM_NAME,MEM_HP,MEM_HIREDATE) values(?,?,seq_member_no.nextval,?,?,?)";
		int result = 0;
		try {

			con  = dbcon.Connect();
			ppst = con.prepareStatement(Sql);
			ppst.setString(1, dto.getMEM_ID());
			ppst.setString(2, dto.getMEM_PW());
			ppst.setString(3, dto.getMEM_NAME());
			ppst.setString(4, dto.getMEM_HP());
			ppst.setString(5, dto.getMEM_HIREDATE());
			result = ppst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
		
	
		
	}
	public String[] login(MemberDTO dto) {
		
		String sql = "select mem_Pw,mem_name from member where mem_id = ?";
		ResultSet rs = null;
		String result1[] = {"",""};
		try {
			
			con = dbcon.Connect();
            ppst = con.prepareStatement(sql);

            ppst.setString(1, dto.getMEM_ID());
          
            
            rs = ppst.executeQuery();
            rs.next();
		    result1[0] = rs.getString("mem_Pw");
		    result1[1] = rs.getString("mem_name");
		    ppst.executeQuery();


			
		} catch (Exception e) {
		System.out.println(e.toString());
		}

		return result1;
		
	}
	public Vector friend(MemberDTO dto) {//친구리스트를 가져오는 메소드 !! 
		
		Vector list = new Vector();
		
		String sql = "select Friend_name from Friend where mem_no = "
				+ "(select mem_no from member where mem_id = ?) order by Friend_name asc";

		
		ResultSet rs = null;
        try {
			
			con = dbcon.Connect();
            ppst = con.prepareStatement(sql);

            ppst.setString(1, dto.getMEM_ID());
    
            rs = ppst.executeQuery();
            
           while(rs.next()) {
          
            list.add(rs.getString("Friend_name"));
            
           }
			
		} catch (Exception e) {
		System.out.println(e.toString());
		}
		
		return list;
		
	}
	public Vector friend_add_index() {
		
		String sql = "Select friend from FRIEND ";
		ResultSet rs = null;
		Vector list = new Vector();
		try {
			
			con = dbcon.Connect();
			ppst = con.prepareStatement(sql);
			
			
			rs = ppst.executeQuery();
		
			while(rs.next()) {
				list.add(rs.getInt("mem_no"));
			}
			
			ppst.executeQuery();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return list;
		
	}
       public Vector friend_add_select2(MemberDTO dto) {
		
		String sql = "select mem_name,mem_no,mem_hiredate from member where mem_hp =?";
		Vector list = new Vector();
		ResultSet rs = null;
         try {
			
			con = dbcon.Connect();
			ppst = con.prepareStatement(sql);
			
			ppst.setString(1, dto.getMEM_HP());
			
			
			rs = ppst.executeQuery();
			rs.next();
			list.add(rs.getString("mem_name"));
			list.add(rs.getInt("mem_no"));
			list.add(rs.getString("mem_hiredate"));
		
			ppst.executeQuery();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return list;
	}
	public Vector friend_add_select(MemberDTO dto) {
		
		String sql = "select mem_name,mem_no,mem_hiredate from member where mem_id =?";
		Vector list = new Vector();
		ResultSet rs = null;
         try {
			
			con = dbcon.Connect();
			ppst = con.prepareStatement(sql);
			
			ppst.setString(1, dto.getMEM_ID());
			
			
			rs = ppst.executeQuery();
			rs.next();
			list.add(rs.getString("mem_name"));
			list.add(rs.getInt("mem_no"));
			list.add(rs.getString("mem_hiredate"));
		
			ppst.executeQuery();
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return list;
	}
	public int friend_add(FriendDTO dto,FriendDTO dto1) {
	

		String sql = "insert into friend(FRiend_name,friend_hiredate,friend_no,friend,mem_no) values(?,?,SEQ_friend.nextval,?,?)";

		
		int result = 0;
		try {

			con  = dbcon.Connect();
			ppst = con.prepareStatement(sql);
			ppst.setString(1, dto.getFRIEND_NAME());
			ppst.setString(2, dto.getFRIEND_HIREDATE());
			ppst.setInt(3, dto.FRIEND);
			ppst.setInt(4, dto.MEM_NO);
			result = ppst.executeUpdate();
			
		        if(result==1) {
				result = 0;
				ppst = con.prepareStatement(sql);
				ppst.setString(1, dto1.getFRIEND_NAME());
				ppst.setString(2, dto1.getFRIEND_HIREDATE());
				ppst.setInt(3, dto1.FRIEND);
				ppst.setInt(4, dto1.MEM_NO);
				result = ppst.executeUpdate();
			}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null,"친구가 존재하지않습니다.","알림",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
		
		
	}
	public int RoomCreate(TalkDTO dto) {
		
		final String Sql = "insert into member values(seq_talk.nextval,?,?)";
		int result = 0;
		try {
			
			con  = dbcon.Connect();
			ppst = con.prepareStatement(Sql);
			ppst.setString(1, dto.TALK_NAME);
			ppst.setInt(2, dto.TALK_CODE);

		
			result = ppst.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
		
	}
	

	
}
