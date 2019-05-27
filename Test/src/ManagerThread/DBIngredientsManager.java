package ManagerThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import DB.DBConnectionPool;
import Model.Ingredient;
import Model.UnitVO;
import Util.PrintColor;

public class DBIngredientsManager extends Thread {
	private Connection conn;

	private static final int DEFAULT_UNIT_UUID = 1;

	// lazy init 로 인한 싱글톤 멀티스레딩 문제 해결
	private static class DBIngredientsManagerLazy {
		public static final DBIngredientsManager INSTANCE = new DBIngredientsManager();
	}

	public static DBIngredientsManager getInstance() {
		return DBIngredientsManagerLazy.INSTANCE;
	}

	public DBIngredientsManager() {
		conn = DBConnectionPool.getInstance().getConnection();
	}

	public LinkedBlockingQueue<ArrayList<Ingredient>> queue =
			new LinkedBlockingQueue<ArrayList<Ingredient>>();

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(20000);
				System.out.println(PrintColor.CYAN_BACKGROUND + PrintColor.WHITE
						+ "[DB Queue] 쓰기 작업 " + queue.size() + "개 시작" + PrintColor.RESET);

				for (int queueIdx = 0; queueIdx < queue.size(); queueIdx++) {

					ArrayList<Ingredient> item = queue.take();

					for (int varListIdx = 0; varListIdx < item.size(); varListIdx++) {

						Ingredient variety = item.get(varListIdx);

						String selectSQL =
								"SELECT unitIdx, unitName, amount FROM onetable.unit WHERE unitName = ?";
						PreparedStatement statement = conn.prepareStatement(selectSQL);

						statement.setString(1, variety.getAnayUnit().getUnitStr());

						ResultSet rs = statement.executeQuery();

						int cnt = 0;
						UnitVO unit = new UnitVO();
						while (rs.next()) {
							unit.setUnitIdx(rs.getInt("unitIdx"));
							cnt++;
						}

						// 아무 타입도 속하지 않는경우
						if (cnt == 0)
							unit.setUnitIdx(DEFAULT_UNIT_UUID);


						String sql = "INSERT INTO onetable.ingredient (ingredientItemId, ingredientSubjectIdx, unitIdx, unitAmount, displayName, imgURL) " + 
								"SELECT ?, ?, ?, ?, ?, ? FROM DUAL WHERE NOT EXISTS (SELECT ingredientItemId FROM onetable.ingredient WHERE ingredientSubjectIdx = ? and ingredientItemId = ?)";
						
						statement = conn.prepareStatement(sql);
						statement.setString(1, variety.getIngredientItemId());
						statement.setInt(2,
								variety.getIngredientSubject().getIngredientSubjectIdx());
						statement.setInt(3, unit.getUnitIdx());
						statement.setDouble(4, variety.getAnayUnit().getUnitAmount());
						statement.setString(5, variety.getDisplayName());
						statement.setString(6, variety.getImgUrl());

						statement.setInt(7,
								variety.getIngredientSubject().getIngredientSubjectIdx());
						statement.setString(8,
								variety.getIngredientItemId());
						statement.executeUpdate();


						String priceSql =
								"INSERT INTO ingredient_price (ingredientItemId, price, priceDate) SELECT "
										+ "?, ?, CURDATE() FROM DUAL WHERE NOT EXISTS ( SELECT ingredientItemId FROM "
										+ "ingredient_price where ingredientItemId = ? and priceDate = CURDATE())";
						statement = conn.prepareStatement(priceSql);
						statement.setString(1, variety.getIngredientItemId());
						statement.setInt(2, variety.getPrice());
						statement.setString(3, variety.getIngredientItemId());
						statement.executeUpdate();

						statement.close();


					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
