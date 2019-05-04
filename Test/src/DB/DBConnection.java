package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import Model.Variety;

public class DBConnection {
	public ArrayDeque<Variety> connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";

		try (Connection conn = DriverManager.getConnection(url, "onetable", "62066407")) {


			String sql = "SELECT HEX(IngredientsSubjectUUID) as isUUID, variety FROM ingredientssubject";

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			ArrayDeque<Variety> isList = new ArrayDeque<Variety>();
			//ArrayList<Variety> isList = new ArrayList<Variety>();
			while (rs.next()) {
				Variety is = new Variety();
				
				is.setUUID(rs.getString("isUUID"));
				is.setVariety(rs.getString("variety"));
				
				isList.add(is);

			}

			return isList;

		} catch (Exception er) {
			er.printStackTrace();
			return null;
		}

	}

	public void insetIngredient(ArrayList<Variety> lis)
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
			
			
			

		} catch (Exception er) {
			er.printStackTrace();
		}
	}

}
