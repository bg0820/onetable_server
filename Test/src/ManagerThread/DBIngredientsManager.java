package ManagerThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import DB.DBConnectionPool;
import Model.AnalyzeUnit;
import Model.Ingredient;
import Model.UnitVO;

public class DBIngredientsManager extends Thread {
	private Connection conn;

	private static final int DEFAULT_UNIT_UUID = 26;

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
				System.out.println("DB 쓰기 : " + queue.size() + "개");

				for (int queueIdx = 0; queueIdx < queue.size(); queueIdx++) {

					ArrayList<Ingredient> item = queue.take();

					for (int varListIdx = 0; varListIdx < item.size(); varListIdx++) {

						Ingredient variety = item.get(varListIdx);

						String selectSQL =
								"SELECT unitIdx, unitName, ml, g, cc, cm, amount FROM onetable.unit WHERE unitName = ?";
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
						


						String sql =
								"INSERT INTO ingredient (ingredientItemId, ingredientSubjectIdx, UnitUUID, unitAmount, currentPrice, priceDate, displayName, imgURL)"
										+ " VALUES (?, ?, ?, ?, ?, CURDATE(), ?, ?)";
						statement = conn.prepareStatement(sql);
						statement.setString(1, variety.getIngredientItemId());
						statement.setInt(2, variety.getIngredientSubject().getIngredientSubjectIdx());
						statement.setInt(3, unit.getUnitIdx());
						statement.setDouble(4, variety.getAnayUnit().getUnitAmount());
						
						statement.setInt(5, variety.getPrice()); // 2,000
						statement.setString(6, variety.getDisplayName());
						statement.setString(7, variety.getImgUrl());
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
