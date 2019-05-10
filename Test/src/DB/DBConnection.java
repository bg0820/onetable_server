package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Stack;
import Model.Ingredient;
import Model.IngredientSubject;

public class DBConnection {
	public Stack<IngredientSubject> connection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://1.240.181.56:3306/onetable?characterEncoding=UTF-8&amp;serverTimezone=UTC&useSSL=false";

		try (Connection conn = DriverManager.getConnection(url, "onetable", "62066407")) {


			String sql = "SELECT ingredientSubjectIdx, variety FROM ingredient_subject";

			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			Stack<IngredientSubject> isList = new Stack<IngredientSubject>();
			//ArrayList<Variety> isList = new ArrayList<Variety>();
			while (rs.next()) {
				IngredientSubject is = new IngredientSubject();
				
				is.setIngredientSubjectIdx(rs.getInt("ingredientSubjectIdx"));
				is.setVariety(rs.getString("variety"));
				
				isList.push(is);
			}

			return isList;
		} catch (Exception er) {
			er.printStackTrace();
			return null;
		}

	}


}
