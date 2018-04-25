package mrhi.adventure.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataDAO {
	protected Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	
	public ResultSet executeQuery(String sql, Object... objs)
	{
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			
			for(int i=1; i<=objs.length; i++)
			{
				if(objs[i-1] instanceof Integer)
					pstmt.setInt(i, (Integer)objs[i-1]);
				else if(objs[i-1] instanceof String)
					pstmt.setString(i, (String)objs[i-1]);
			}
			
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public int executeUpdate(String sql, Object... objs)
	{
		Connection conn = DBManager.getConnection();
		int result = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			for(int i=1; i<=objs.length; i++)
			{
				if(objs[i-1] instanceof Integer)
					pstmt.setInt(i, (Integer)objs[i-1]);
				else if(objs[i-1] instanceof String)
					pstmt.setString(i, (String)objs[i-1]);
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
