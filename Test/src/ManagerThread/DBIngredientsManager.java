package ManagerThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import DB.DBConnectionPool;
import Model.AnalyzeVariety;
import Model.UnitVO;

public class DBIngredientsManager extends Thread {
	private Connection conn;

	private static final String DEFAULT_UNIT_UUID = "36B9ED9D6E5811E998CD408D5C5EFA89";

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

	public LinkedBlockingQueue<ArrayList<AnalyzeVariety>> queue =
			new LinkedBlockingQueue<ArrayList<AnalyzeVariety>>();

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("DB 쓰기 : " + queue.size() + "개");

				for (int queueIdx = 0; queueIdx < queue.size(); queueIdx++) {

					ArrayList<AnalyzeVariety> item = queue.take();

					for (int varListIdx = 0; varListIdx < item.size(); varListIdx++) {

						AnalyzeVariety variety = item.get(varListIdx);

						String selectSQL =
								"SELECT unitIdx, unit.unitName, ml, g, cc, cm, amount FROM onetable.unit WHERE unitName = ?";
						PreparedStatement statement = conn.prepareStatement(selectSQL);

						statement.setString(1, variety.getUnitStr());
						ResultSet rs = statement.executeQuery();
						int cnt = 0;
						UnitVO unit = new UnitVO();
						while (rs.next()) {
							unit.setUnitUUID(rs.getString("UnitUUID"));
							cnt++;
						}


						if (cnt == 0) {
							unit.setUnitUUID(DEFAULT_UNIT_UUID);
						}



						String sql =
								"INSERT INTO ingredient (ingredientItemId, IngredientSubjectIdx, unitIdx, unitAmount, displayName, imgURL)"
										+ " VALUES (UNHEX(REPLACE(UUID(), '-', '')), UNHEX(?), UNHEX(?), ?, ?, CURDATE(), ?, ?)";
						statement = conn.prepareStatement(sql);
						statement.setString(1, variety.getIdx());
						statement.setString(2, unit.getUnitUUID());
						statement.setDouble(3, variety.getUnitNum());
						statement.setInt(4, Integer.parseInt(variety.getPrice())); // 2,000
						statement.setString(5, variety.getName());
						statement.setString(6, variety.getImg());
						statement.executeUpdate();

					}
				}

				Thread.sleep(10000);
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
