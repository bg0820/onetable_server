package ManagerThread;

import Util.PrintColor;

public class IngredientSubjectListManager extends Thread {

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println(PrintColor.WHITE_BACKGROUND + PrintColor.GREEN + CrawlerManagerThread.getInstance().list.size() + "개 남았음" + PrintColor.RESET);
				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
