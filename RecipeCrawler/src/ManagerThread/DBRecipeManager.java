package ManagerThread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import Crawler.HaemukjaCrawler;
import DB.DBConnectionPool;
import Model.Ingredient;
import Model.IngredientUnit;
import Model.Recipe;

public class DBRecipeManager extends Thread {
	private Connection conn;

	private static final int DEFAULT_UNIT_UUID = 26;
	private static final int KG_UNIT_UUID = 10;
	private static final int G_UNIT_UUID = 27;
	private static final int CM_UNIT_UUID = 30;
	private static final int AMOUNT_UNIT_UUID = 31;
	private static final int L_UNIT_UUID = 12;
	private static final int ML_UNIT_UUID = 28;
	private static final int CC_UNIT_UUID = 29;
	private static final int M_UNIT_UUID = 25;

	// lazy init 로 인한 싱글톤 멀티스레딩 문제 해결
	private static class DBRecipeManagerLazy {
		public static final DBRecipeManager INSTANCE = new DBRecipeManager();
	}

	public static DBRecipeManager getInstance() {
		return DBRecipeManagerLazy.INSTANCE;
	}

	public DBRecipeManager() {
		conn = DBConnectionPool.getInstance().getConnection();
	}

	public LinkedBlockingQueue<Recipe> queue = new LinkedBlockingQueue<Recipe>();

	@Override
	public void run() {
		while (true) {
			try {

				System.out.println("DB 쓰기 : " + queue.size() + "개");

				for (int queueIdx = 0; queueIdx < queue.size(); queueIdx++) {

					Recipe item = queue.take();
					if(item == null)
						continue;
					
					Integer inte = new Integer(item.getRecipePageId());
					item.setFoodImg(HaemukjaCrawler.imgUrl.get(inte));

					System.out.println(item.getMainTitle());

					String insertSQL = "INSERT INTO recipe (userIdx, recipePageId, name, servingMin,servingMax,recipeImg,cookTimeMin,kcal,contentHtml)" +
					" SELECT 1, ?, ?, ?, ?, ?, ?, ?, ? from DUAL WHERE NOT EXISTS (SELECT recipePageId FROM onetable.recipe WHERE recipePageId = ?)";
					//  VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?);
					PreparedStatement statement = conn.prepareStatement(insertSQL);

					statement.setInt(1, item.getRecipePageId());
					statement.setString(2, item.getMainTitle());
					statement.setDouble(3, item.getMinServing());
					statement.setDouble(4, item.getMaxServing());
					statement.setString(5, item.getFoodImg());
					statement.setString(6, item.getCookTime());
					statement.setDouble(7, item.getKcal());
					statement.setString(8, item.getMethodHTML());
					statement.setInt(9, item.getRecipePageId());
					
					statement.executeUpdate();

					String getRecipeIdxQuery = "SELECT recipeIdx FROM recipe WHERE recipePageId = ?";
					statement = conn.prepareStatement(getRecipeIdxQuery);
					statement.setInt(1, item.getRecipePageId());
					ResultSet recipeIdxRs = statement.executeQuery();
					recipeIdxRs.next();
					int recipeIdx = recipeIdxRs.getInt("recipeIdx");
						
					
					int ingredientCnt = item.getIngredientSize();
					System.out.println(ingredientCnt);
					
					// 레시피 재료 테이블에 넣기전에 기존에 등록된 재료 삭제 하고 재료 넣기
					String deleteSQL_recipeInf = "DELETE FROM onetable.recipe_ingredient WHERE recipeIdx = ?";
					statement = conn.prepareStatement(deleteSQL_recipeInf);
					statement.setInt(1, recipeIdx); //앞에 처음 insert에서 입력한 recipeIdx 넣어야함
					statement.executeUpdate();
					
					for(int i = 0 ; i < ingredientCnt; i++)
					{
						Ingredient ingredientModel = item.getIngredientList(i);
						IngredientUnit iu = ingredientModel.getIngredientUnit();
						
						if(iu == null)
							continue;
						
						String selectSQL_ingredientSubject = "SELECT ingredientSubjectIdx FROM onetable.ingredient_subject WHERE variety = ?";
						statement = conn.prepareStatement(selectSQL_ingredientSubject);	
						//int idx문제
						statement.setString(1, item.getIngredientList(i).getName());
						ResultSet rs = statement.executeQuery();
						
						int ingredientSubjectIdx = 1;
						while(rs.next())
							 ingredientSubjectIdx = rs.getInt("ingredientSubjectIdx");
						
						String selectUnitQuery = "SELECT unitIdx FROM unit WHERE unitName = ?";
						statement = conn.prepareStatement(selectUnitQuery);	
						
						switch(iu.getUnitStr())
						{
							
						}
						
						statement.setString(1, iu.getUnitStr());
						ResultSet unitRs = statement.executeQuery();
						
						int defaultUnitIdx = 26; // 개
						while(unitRs.next())
							defaultUnitIdx = unitRs.getInt("unitIdx");
						
						
						
						String select_ingredient_for_subjectIdx = "SELECT ingredientItemId, unitIdx FROM onetable.ingredient WHERE ingredientSubjectIdx = ? and unitIdx = ? order by idx limit 1";
						statement = conn.prepareStatement(select_ingredient_for_subjectIdx);
						statement.setInt(1, ingredientSubjectIdx);
						statement.setInt(2, defaultUnitIdx);
						ResultSet subjectRs = statement.executeQuery();
						String ingredientItemId = "0";
						
						while(subjectRs.next())
							ingredientItemId = subjectRs.getString("ingredientItemId");
						
						String insertSQL_recipeIngredient = "INSERT INTO recipe_ingredient (recipeIdx, ingredientItemId, unitIdx, minAmount, maxAmount, symbol, displayName, displayAmount ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
						statement = conn.prepareStatement(insertSQL_recipeIngredient);
						statement.setInt(1, recipeIdx); //앞에 처음 insert에서 입력한 recipeIdx 넣어야함
						statement.setString(2, ingredientItemId); //위에서 얻어온 ingredientItemIdx
						statement.setInt(3, defaultUnitIdx);
						statement.setDouble(4, iu.getMin());
						statement.setDouble(5, iu.getMax());
						statement.setString(6, iu.getSymbol());
						System.out.println(ingredientModel.getUnitStr());
						statement.setString(7, ingredientModel.getName());
						statement.setString(8, ingredientModel.getUnitStr());
						statement.executeUpdate();
						
						
					}
					
					System.out.println("큐 끝 =============");
					statement.close();
				}
					
			
					/*
					
			            
			            // 각 행을 읽어 리스트에 저장한다.
			            int sizeOfcolumn = metaData.getColumnCount();
			            String column;
			            List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
			            Map<String, Object> map;
			            
			            while(rs.next()){
			                map = new HashMap<String,Object>();
			                
			                for(int indexOfcolumn=0; indexOfcolumn<sizeOfcolumn; indexOfcolumn++){
			                    column = metaData.getColumnName(indexOfcolumn + 1);
			                    map.put(column, rs.getString(column));
			                }
			                tempList.add(map);
			            }

					
					statement.closeOnCompletion();
					
					for(int i =1; i<tempList.size(); i++) {
					String selectSQL_ingredients = "SELECT ingredientItemId FROM onetable.ingredient WHERE ingredientSubjectIdx = ?";
					statement = conn.prepareStatement(selectSQL_ingredients);

					//ingredientSubjectIdx 저장한 리스트에서 값 가져와서 쿼리문에 넣기 +첫번째 재료만 어떻게 가져오지?
					statement.setString(1, tempList);
					
					statement.closeOnCompletion();
					}
					
					String insertSQL_recipeIngredient = "INSERT INTO recipe_ingredient(recipeIdx, ingredientItemId, unitIdx, amount ) VALUES (?, ?, ?, ?);";

					PreparedStatement statement = conn.prepareStatement(insertSQL);

					statement.setInt(1, ); //앞에 처음 insert에서 입력한 recipeIdx 넣어야함
					statement.setInt(2, ); //위에서 얻어온 ingredientItemIdx
					statement.setInt(3, );
					statement.setInt(4, );
				

					statement.closeOnCompletion();
*/
				

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
