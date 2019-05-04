package ManagerThread;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import DB.DBConnectionPool;
import Model.Variety;

public class DBIngredientsManager extends Thread {
	private Connection conn;
	
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

	public ArrayDeque<ArrayList<Variety>> queue = new ArrayDeque<ArrayList<Variety>>();
	
	@Override
	public void run() {
		while (true) {
			try {
				System.out.println("DB 쓰기 : " + queue.size() + "개");

				for (int queueIdx = 0; queueIdx < queue.size(); queueIdx++) {
					
					ArrayList<Variety> item = queue.pop();
					
					for (int varListIdx = 0; varListIdx < item.size(); varListIdx++) {
						
						Variety variety = item.get(varListIdx);
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
