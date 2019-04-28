import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static ArrayDeque<Variety> list;

	public static void main(String[] args) throws Exception {
		try {
			IgnoreManagerThread ifmt = new IgnoreManagerThread();
			ifmt.start();

			DBIngredientsManager.getInstance().start();



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

				Variety var = list.pop();

				System.out.println("남은 조회해야할 항목 개수 : " + list.size());
				if (varietyIgnoreList.contains(var.getVariety())) {
					continue;
				}
				
				
				SSGThread ssgThread = new SSGThread(var, proxyIP);
				ssgThread.start();

				if (list.size() == 0)
					break;

				
				try {
					Thread.sleep(1000);
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
