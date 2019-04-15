import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DBConnection {
	public void connection(ArrayList<Variety> varietyList) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://1.240.181.56:3306/sgyy?characterEncoding=UTF-8&amp;serverTimezone=UTC";

		for (Variety v : varietyList) {
			System.out.println(v.getName());
			String sql = "INSERT INTO ingredientssubject(subjectUUID, name, imgUrl) "
					+ "VALUES (UNHEX(REPLACE(UUID(), '-', '')) , " + v.getName() + ", '" + v.getImg() + "')";
			try (Connection conn = DriverManager.getConnection(url, "sgyy", "62066407");
					PreparedStatement statement = conn.prepareStatement(sql)) {
				statement.executeQuery();
			}
		}

	}

}
