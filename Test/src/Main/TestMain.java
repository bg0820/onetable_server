package Main;

import DB.DBConnection;
import ManagerThread.CrawlerManagerThread;
import ManagerThread.DBIngredientsManager;
import ManagerThread.IgnoreManagerThread;
import ManagerThread.IngredientSubjectListManager;
import Model.HangleAnalyze;

public class TestMain {

	public static boolean start = true;

	public static void main(String[] args) {

		IgnoreManagerThread ifmt = new IgnoreManagerThread();
		ifmt.start();
		DBIngredientsManager.getInstance().start();

		System.out.println("형태소 분석기 초기화");
		HangleAnalyze.getInstance().initilze();
		
		IngredientSubjectListManager islm = new IngredientSubjectListManager();
		boolean first = false;
		
		while (true) {
			
			System.out.println("크롤러 관리 스레드 Initilize");
			CrawlerManagerThread.getInstance().initilize();
			
			System.out.println("DB 연결");
			DBConnection db = new DBConnection();

			CrawlerManagerThread.getInstance().list = db.connection();
			System.out.println("List 가져옴 : " + CrawlerManagerThread.getInstance().list.size());
			
			

			if(!first) {
				System.out.println("크롤러 관리 스레드 시작");
				CrawlerManagerThread.getInstance().start();
				
				islm.start();
				first = true;
			}

			System.out.println("성공");
			try {
				Thread.sleep(86400 * 1000);
				
				start = false;
				
				System.out.println("10초뒤 시작합니다.");
				Thread.sleep(10000);
				System.out.println("시작");
				start = true;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
