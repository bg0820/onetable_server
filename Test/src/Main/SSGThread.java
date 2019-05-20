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
import Util.PrintColor;

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
		int currentPage = ingredientSubject.getPage();

		try {
			ArrayList<Ingredient> insertIngredientList = new ArrayList<Ingredient>();

			SSGCrawler ssgCrawler = new SSGCrawler(proxyIP);
			int recordCnt = ssgCrawler.getRecordCnt(ingredientSubject.getVariety());
			int maxPageCnt = (int) Math.ceil((double) (recordCnt
					/ 80.0));
			
			if(maxPageCnt > 5)
				maxPageCnt = 5;

			if (recordCnt == 0) {
				System.out.println(PrintColor.RED + "[" + ingredientSubject.getVariety() + "] 조회 결과 없음" + PrintColor.RESET);
				IgnoreManagerThread.varietyIgnoreQueue.offer(ingredientSubject.getVariety());
				return;
			}


			for (; currentPage <= maxPageCnt;) {
				ArrayList<Ingredient> anaVarList =
						ssgCrawler.getItemList(ingredientSubject, currentPage);
				
				
				if (anaVarList != null) {
					for (int j = 0; j < anaVarList.size(); j++) {
						AnalyzeUnit analyzeUnit = HangleAnalyze.getInstance()
								.analyze(anaVarList.get(j).getDisplayName());

						// 분석한 결과값이 null이 아닐경우
						if (analyzeUnit != null) {
							anaVarList.get(j).setAnayUnit(analyzeUnit); // 기존 값에 분석한 결과 추가 해서 삽입
							insertIngredientList.add(anaVarList.get(j));
						}
					}

					System.out.println(PrintColor.GREEN + "[" + ingredientSubject.getVariety() + "] 현재 페이지 : "
							+ currentPage + ", 페이지 끝 : " + maxPageCnt + ", 레코드 개수 : " + recordCnt + PrintColor.RESET);
					ingredientSubject.setPage((++currentPage));
					
				}
				else
					System.out.println(PrintColor.GREEN + "[" + ingredientSubject.getVariety() + "] 401에러 너무 많은 요청" + PrintColor.RESET);

				Thread.sleep(100);
			}

			System.out.println(PrintColor.BLUE_BACKGROUND + PrintColor.YELLOW +  "[" + ingredientSubject.getVariety() + "] 스레드 종료" + PrintColor.RESET);
			DBIngredientsManager.getInstance().queue.offer(insertIngredientList);

		} catch (Exception e) {
			CrawlerManagerThread.getInstance().list.push(ingredientSubject);

			System.out.println(PrintColor.RED_BACKGROUND + PrintColor.WHITE + "[에러] " + ingredientSubject.getVariety() + "(" + proxyIP + ") - "
					+ e.getMessage() + PrintColor.RESET);
		}



	}



}
