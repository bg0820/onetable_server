package ManagerThread;

import java.util.ArrayList;
import java.util.Stack;

import Main.SSGThread;
import Model.AnalyzeVariety;

public class CrawlerManagerThread extends Thread {

	public Stack<AnalyzeVariety> list;

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
		while (true) {
			String proxyIP = proxyList.get(proxyIndex);
			AnalyzeVariety var = list.pop();

			if (var == null)
				break;

			System.out.println("남은 조회해야할 항목 개수 : " + list.size());


			if (varietyIgnoreList.contains(var.getVariety())) {
				continue;
			}

			System.out.println(var.getVariety() + " 조회 스레드 실행");
			SSGThread ssgThread = new SSGThread(var, proxyIP);
			ssgThread.start();

			try {
				Thread.sleep(500);
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
