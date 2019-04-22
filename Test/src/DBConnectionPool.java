import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionPool {
	private static DBConnectionPool instance;
	private static Connection conn;

	public static DBConnectionPool getInstance() {
		if (instance == null) {
			instance = new DBConnectionPool();

			try {
				Class.forName("com.mysql.jdbc.Driver");

				String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";
				conn = DriverManager.getConnection(url, "onetable", "62066407");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return instance;
	}

	public Connection getConnection() {
		return conn;
	}

}
