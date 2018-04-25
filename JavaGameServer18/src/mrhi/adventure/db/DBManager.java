package mrhi.adventure.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {
	public static Connection getConnection() {
		
		Connection conn = null;
		String url = "jdbc:postgresql://localhost:5432/adventure";
		String id = "postgres";
		String pw = "0000";
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection conn)
	{
		try {
			if(conn!=null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt)
	{
		close(conn);
		try {
			if(pstmt!=null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
	{
		close(conn, pstmt);
		try {
			if(rs!=null && !rs.isClosed())
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
