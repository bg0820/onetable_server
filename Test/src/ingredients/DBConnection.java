package ingredients;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DBConnection {
	public void connection(ArrayList<String> saveList) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";

		System.out.println(saveList.size());
		try (Connection conn = DriverManager.getConnection(url, "onetable", "62066407")) {

			for (int i = 0; i < saveList.size(); i++) {

				//System.out.println(saveList.get(i));
				/*
				String[] itemArr = saveList.get(i).split("#");
				String item = itemArr[0]; // 폼목
				*/
				String vair = saveList.get(i); // 품종

				String sql = "INSERT INTO ingredientssubject (IngredientsSubjectUUID, variety) VALUES (UNHEX(REPLACE(UUID(), '-', '')), '" +vair + "')";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.executeUpdate();

			}

		} catch (Exception er) {
			er.printStackTrace();
		}
		/*
		 * for (Variety v : varietyList) { System.out.println(v.getName()); String sql =
		 * "INSERT INTO ingredientssubject(subjectUUID, name, imgUrl) " +
		 * "VALUES (UNHEX(REPLACE(UUID(), '-', '')) , " + v.getName() + ", '" +
		 * v.getImg() + "')"; try (Connection conn = DriverManager.getConnection(url,
		 * "sgyy", "62066407"); PreparedStatement statement =
		 * conn.prepareStatement(sql)) { statement.executeQuery(); } }
		 */

	}

}
