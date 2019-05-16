package Crawler;

import java.io.IOException;
import java.util.ArrayList;

import Analyze.HangleAnalyze;
import ManagerThread.DBRecipeManager;
import Model.IngredientUnit;
import Model.Recipe;

public class CollectionThread extends Thread {

	@Override
	public void run() {
		HaemukjaCrawler hc = new HaemukjaCrawler();
		try {
			int totalCnt = hc.getRecipeRecordCnt();
			int lastPageCnt = (int) Math.ceil(totalCnt
					/ 12.0);

			System.out.println("마지막 페이지 : "  + lastPageCnt);
			for (int i = 1; i <= lastPageCnt; i++) {
				ArrayList<String> recipeList = hc.getRecipeList(i);
				for (int j = 0; j < recipeList.size(); j++) {
					Recipe recipe = hc.getRecipeInfo(recipeList.get(j));
					//System.out.println(recipe.getMainTitle());
					
					//IngredientUnit ingredientUnit = recipe.anaylze();
					//recipe.setIngredientUnit(ingredientUnit);
					
					DBRecipeManager.getInstance().queue.offer(recipe);
					Thread.sleep(50);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
