package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionPool {
	private static DBConnectionPool instance;
	private static Connection conn;
	
	public DBConnectionPool() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://222.238.100.247:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";
			conn = DriverManager.getConnection(url, "onetable", "62066407");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// lazy init 로 인한 싱글톤 멀티스레딩 문제 해결
	private static class DBConnectionPoolLazy {
		public static final DBConnectionPool INSTANCE = new DBConnectionPool();
	}
	
	public static DBConnectionPool getInstance() {
		return DBConnectionPoolLazy.INSTANCE;
	}

	public Connection getConnection() {
		return conn;
	}

}
