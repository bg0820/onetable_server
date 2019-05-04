package Main;
import java.util.ArrayDeque;
import java.util.ArrayList;
import DB.DBConnection;
import ManagerThread.DBIngredientsManager;
import ManagerThread.FileManager;
import ManagerThread.IgnoreManagerThread;
import Model.AnalyzeVariety;
import Model.HangleAnalyze;

public class Main {

	public static ArrayDeque<AnalyzeVariety> list;

	public static void main(String[] args) throws Exception {
		try {
			IgnoreManagerThread ifmt = new IgnoreManagerThread();
			ifmt.start();

			DBIngredientsManager.getInstance().start();
			HangleAnalyze.getInstance().initilze();

			
			
			System.out.println("DB 연결");
			DBConnection db = new DBConnection();
			list = db.connection();
			System.out.println("List 가져옴 : " + list.size());

			SSGThread[] rt = new SSGThread[list.size()];

			FileManager fileManager = new FileManager();
			ArrayList<String> varietyIgnoreList = fileManager.readFile("ignore.txt");
			ArrayList<String> proxyList = fileManager.readFile("koreaProxy.txt");

			int proxyIndex = 0;
			while (list.size() != 0) {
				String proxyIP = proxyList.get(proxyIndex);

				AnalyzeVariety var = list.pop();

				System.out.println("남은 조회해야할 항목 개수 : " + list.size());
				if (varietyIgnoreList.contains(var.getVariety())) {
					continue;
				}
				
				
				SSGThread ssgThread = new SSGThread(var, proxyIP);
				ssgThread.start();

				if (list.size() == 0)
					break;

				
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) { // TODO Auto-generated
					e.printStackTrace();
				}
				
				if (proxyIndex + 1 > proxyList.size() - 1)
					proxyIndex = 0;
				proxyIndex++;
			}

			/*
			 * for (int i = 0; i < list.size(); i++) { String proxyIP = proxyList.get(proxyIndex);
			 * 
			 * if(varietyIgnoreList.contains(list.get(i).getVariety())) continue;
			 * 
			 * rt[i] = new SSGThread(list.get(i), proxyIP); rt[i].start(); try { Thread.sleep(1000);
			 * } catch (InterruptedException e) { // TODO Auto-generated e.printStackTrace(); }
			 * 
			 * if(proxyIndex + 1 > proxyList.size() - 1) proxyIndex = 0; proxyIndex++; }
			 */



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("성공");
	}

}
