package Main;

import java.util.ArrayList;
import ManagerThread.CrawlerManagerThread;
import ManagerThread.DBIngredientsManager;
import ManagerThread.IgnoreManagerThread;
import Model.AnalyzeUnit;
import Model.HangleAnalyze;
import Model.Ingredient;
import Model.IngredientSubject;
import Model.SSGCrawler;

public class SSGThread extends Thread {

	private IngredientSubject ingredientSubject;
	private String proxyIP;

	public SSGThread(IngredientSubject ingredientSubject,
			String proxyIP) {
		// TODO Auto-generated constructor stub
		this.ingredientSubject = ingredientSubject;
		this.proxyIP = proxyIP;
	}

	@Override
	public void run() {
		try {
			ArrayList<Ingredient> insertIngredientList = new ArrayList<Ingredient>();

			SSGCrawler ssgCrawler = new SSGCrawler(proxyIP);
			int recordCnt = ssgCrawler.getRecordCnt(ingredientSubject.getVariety());
			int maxPageCnt = (int) Math.ceil((double) (recordCnt
					/ 80.0));

			if (recordCnt == 0) {
				System.out.println(ingredientSubject.getVariety() + " : 조회 결과 없음");
				IgnoreManagerThread.varietyIgnoreQueue.offer(ingredientSubject.getVariety());
				return;
			}

			for (int i = 0; i < maxPageCnt; i++) {
				ArrayList<Ingredient> anaVarList = ssgCrawler.getItemList(ingredientSubject, i);
				if (anaVarList != null) {
					for (int j = 0; j < anaVarList.size(); j++) {
						AnalyzeUnit analyzeUnit = HangleAnalyze.getInstance()
								.analyze(anaVarList.get(j).getDisplayName());
						if (analyzeUnit != null) {
							anaVarList.get(j).setAnayUnit(analyzeUnit);
							// System.out.println(anaVarList.get(j).getDisplayName() + " - " +
							// analyzeUnit.toString());

							insertIngredientList.add(anaVarList.get(j));
						}
					}

					System.out.println("[" + i + "] " + ingredientSubject.getVariety() + " - "
							+ recordCnt + " - " + anaVarList.size() + "개");
				}
				Thread.sleep(4000);
			}

			System.out.println(ingredientSubject.getVariety() + " 조회 끝, 종료");
			DBIngredientsManager.getInstance().queue.offer(insertIngredientList);

		} catch (Exception e) {
			CrawlerManagerThread.getInstance().list.push(ingredientSubject);
			System.out.println("[Exception]" + ingredientSubject.getVariety() + " : " + proxyIP
					+ " : 연결 실패 - " + e.getMessage());
			// e.printStackTrace();
		}



	}



}
