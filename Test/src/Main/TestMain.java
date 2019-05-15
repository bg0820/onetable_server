package Main;

import DB.DBConnection;
import ManagerThread.CrawlerManagerThread;
import ManagerThread.DBIngredientsManager;
import ManagerThread.IgnoreManagerThread;
import ManagerThread.IngredientSubjectListManager;
import Model.HangleAnalyze;

public class TestMain {



	public static void main(String[] args) {
		IgnoreManagerThread ifmt = new IgnoreManagerThread();
		ifmt.start();

		DBIngredientsManager.getInstance().start();
		System.out.println("형태소 분석기 초기화");
		HangleAnalyze.getInstance().initilze();

		System.out.println("DB 연결");
		DBConnection db = new DBConnection();

		CrawlerManagerThread.getInstance().list = db.connection();
		System.out.println("List 가져옴 : " + CrawlerManagerThread.getInstance().list.size());
		System.out.println("크롤러 관리 스레드 Initilize");
		CrawlerManagerThread.getInstance().initilize();
		System.out.println("크롤러 관리 스레드 시작");
		CrawlerManagerThread.getInstance().start();

		IngredientSubjectListManager islm = new IngredientSubjectListManager();
		islm.start();
		
		System.out.println("성공");
	}

}
