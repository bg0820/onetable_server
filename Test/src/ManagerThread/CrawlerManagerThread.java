package ManagerThread;

import java.util.ArrayList;
import java.util.Stack;
import Main.SSGThread;
import Main.TestMain;
import Model.Ingredient;
import Model.IngredientSubject;
import Util.PrintColor;

public class CrawlerManagerThread extends Thread {
	
	public Stack<IngredientSubject> list;
	
	private static class CrawlerManagerThreadLazy {
		public static final CrawlerManagerThread INSTANCE = new CrawlerManagerThread();
	}

	public static CrawlerManagerThread getInstance() {
		return CrawlerManagerThreadLazy.INSTANCE;
	}

	private ArrayList<String> varietyIgnoreList;
	private ArrayList<String> proxyList;
	private int proxyIndex = 0;

	public void initilize() {
		FileManager fileManager = new FileManager();
		varietyIgnoreList = fileManager.readFile("ignore.txt");
		proxyList = fileManager.readFile("koreaProxy.txt");
	}

	@Override
	public void run() {
		while (TestMain.start) {
			String proxyIP = proxyList.get(proxyIndex);
			
			if(list.size() == 0)
				continue;
			
			IngredientSubject var = list.pop();

			if(var.getCategoryNum() == 1)
				continue;
			
			if (var == null)
				continue;

			if (varietyIgnoreList.contains(var.getName()))
				continue;

			System.out.println(PrintColor.BLUE_BACKGROUND + PrintColor.YELLOW + var.getName() + " 조회 스레드 실행" + PrintColor.RESET);
			SSGThread ssgThread = new SSGThread(var, proxyIP);
			ssgThread.start();
 
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) { // TODO Auto-generated
				e.printStackTrace();
			}

			if (proxyIndex + 1 > proxyList.size() - 1)
				proxyIndex = 0;
			else
				proxyIndex++;
		}
	}

}
