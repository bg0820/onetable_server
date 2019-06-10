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

					// 레시피 재료 테이블에 넣기전에 기존에 등록된 재료 삭제 하고 재료 넣기
					String deleteSQL_recipeInf = "DELETE FROM onetable.recipe_ingredient WHERE recipeIdx = ?";
					statement = conn.prepareStatement(deleteSQL_recipeInf);
					statement.setInt(1, recipeIdx); //앞에 처음 insert에서 입력한 recipeIdx 넣어야함
					statement.executeUpdate();
					int totalPrice = 0;
					
					for(int i = 0 ; i < item.getIngredientSize(); i++)
					{
						Ingredient ingredientModel = item.getIngredientList(i);
						IngredientUnit iu = ingredientModel.getIngredientUnit();
						
						if(iu == null)
							continue;
						
						double result = iu.getMin();
						if(iu.getSymbol().equals("/"))
							result = iu.getMin() / iu.getMax();	
						
						/*******************************************
						 *		레시피 재료의 단위를 DB에 있는 단위로 맵핑하기
						 *******************************************/
						String selectUnitQuery = "SELECT unitIdx FROM unit WHERE unitName = ?";
						statement = conn.prepareStatement(selectUnitQuery);	
						statement.setString(1, iu.getUnitStr());
						ResultSet unitRs = statement.executeQuery();
						
						int defaultUnitIdx = 1; // 개
						while(unitRs.next())
							defaultUnitIdx = unitRs.getInt("unitIdx");
						
						
						
						/*******************************************
						 *		ingredientSubject IDX 가져오기, 재료명을 기준으로 검색
						 *******************************************/
						String selectSQL_ingredientSubject = "SELECT ingredientSubjectIdx FROM onetable.ingredient_subject WHERE name = ?";
						statement = conn.prepareStatement(selectSQL_ingredientSubject);	
						//int idx문제
						statement.setString(1, ingredientModel.getName());
						ResultSet rs = statement.executeQuery();
						
						int ingredientSubjectIdx = 1;
						while(rs.next()) {
							ingredientSubjectIdx = rs.getInt("ingredientSubjectIdx");
							
							
							// TODO :: ingredientSubject 가 여러개임 
							/*******************************************
							 *		레시피 재료랑 DB에 있는 재료랑 매칭하기
							 *******************************************/
							String select_ingredient_for_subjectIdx = "SELECT price, unitAmount, ingredientIdx, ingredientItemId, unitIdx FROM onetable.ingredient_price_all WHERE ingredientSubjectIdx = ? and unitIdx = ? and displayName LIKE ? order by ingredientIdx limit 1";
							statement = conn.prepareStatement(select_ingredient_for_subjectIdx);
							statement.setInt(1, ingredientSubjectIdx);
							statement.setInt(2, defaultUnitIdx);
							statement.setString(3, "%" + ingredientModel.getName() + "%");
							ResultSet subjectRs = statement.executeQuery();
							int ingredientIdx = 1;
							String ingredientItemId = "0";
							
							while(subjectRs.next()) {
								ingredientIdx = subjectRs.getInt("ingredientIdx");
								ingredientItemId = subjectRs.getString("ingredientItemId");
							}
							
						}
						
						/*
						String ingredient_currentDay = "SELECT price, unitAmount FROM ingredient_price_all where ingredientItemId = ? order by priceDate desc limit 1";
						statement = conn.prepareStatement(ingredient_currentDay);
						statement.setString(1, ingredientItemId);
						ResultSet ingPriceRs = statement.executeQuery();
						int ingPrice = 0;
						int unitAmount = 0;
						int perPrice = 0 ;
						
						
						while(ingPriceRs.next())
						{
							ingPrice = ingPriceRs.getInt("price");	
							unitAmount = ingPriceRs.getInt("unitAmount");
						}
						
						if(unitAmount != 0)
						{
							perPrice = ingPrice / unitAmount;
							totalPrice += perPrice * result;
						}
						else
							totalPrice += 0;
						
						*/
						
						String insertSQL_recipeIngredient = "INSERT INTO recipe_ingredient (recipeIdx, ingredientIdx, unitIdx, minAmount, maxAmount, result, symbol, price, displayName, displayAmount, unitStr ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
						statement = conn.prepareStatement(insertSQL_recipeIngredient);
						statement.setInt(1, recipeIdx); //앞에 처음 insert에서 입력한 recipeIdx 넣어야함
						statement.setInt(2, ingredientIdx); //위에서 얻어온 ingredientIdx
						statement.setInt(3, defaultUnitIdx);
						statement.setDouble(4, iu.getMin());
						statement.setDouble(5, iu.getMax());
											
						statement.setDouble(6, result);
						statement.setString(7, iu.getSymbol());
						statement.setInt(8, perPrice);
						statement.setString(9, ingredientModel.getName());
						statement.setString(10, ingredientModel.getUnitStr());
						statement.setString(11, iu.getUnitStr());
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
