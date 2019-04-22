import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {
	public ArrayList<IngredientsSubject> connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";

		try (Connection conn = DriverManager.getConnection(url, "onetable", "62066407")) {

			// String sql = "DELETE FROM ingredientssubject WHERE variety='007'";
			// + "SELECT HEX(IngredientsSubjectUUID) as isUUID, variety FROM
			// ingredientssubject";

			// PreparedStatement statement = conn.prepareStatement(sql);
			// statement.execute();

			String sql = "SELECT HEX(IngredientsSubjectUUID) as isUUID, variety FROM ingredientssubject";

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			ArrayList<IngredientsSubject> isList = new ArrayList<IngredientsSubject>();
			while (rs.next()) {
				IngredientsSubject is = new IngredientsSubject();
				is.setUUID(rs.getString("isUUID"));
				is.setVariety(rs.getString("variety"));
				isList.add(is);

			}

			return isList;

		} catch (Exception er) {
			er.printStackTrace();
			return null;
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

	public void insetIngredient(HashMap<String, ArrayList<Variety>> hs)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";

		//System.out.println(list.size());
		try (Connection conn = DriverManager.getConnection(url, "onetable", "62066407")) {

			for(Map.Entry<String, ArrayList<Variety>> entry : hs.entrySet())
			{
				String key = entry.getKey();
				ArrayList<Variety> value = entry.getValue();

					for(Variety variety : value) {
						String sql = "INSERT INTO ingredients (IngredientsUUID, IngredientsSubjectUUID, currentPrice, priceDate, displayName, imgURL)" +
					" VALUES (UNHEX(REPLACE(UUID(), '-', '')), UNHEX(?), ?, CURDATE(), ?, ?)";
						PreparedStatement statement = conn.prepareStatement(sql);
						statement.setString(1, variety.getUUID());
						statement.setInt(2, Integer.parseInt(variety.getPrice())); // 2,000
						statement.setString(3, variety.getName());
						statement.setString(4, variety.getImg());
						statement.executeUpdate();
					}


			}

		} catch (Exception er) {
			er.printStackTrace();
		}
	}

}
