package ManagerThread;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import Util.PrintColor;

public class IgnoreManagerThread extends Thread {
	
	public static LinkedBlockingQueue<String> varietyIgnoreQueue = new LinkedBlockingQueue<String>();
	private BufferedWriter varietyBw;
	

	@Override
	public void run() {
		while (true) {

			try {
				Thread.sleep(20000);
				System.out.println(PrintColor.CYAN_BACKGROUND + PrintColor.WHITE + "[Ignore Queue] 쓰기 작업 " + varietyIgnoreQueue.size() + "개 시작" + PrintColor.RESET);
				
				varietyBw = new BufferedWriter(new FileWriter("ignore.txt", true));
				
				for (int i = 0; i < varietyIgnoreQueue.size(); i++) {
					String s = varietyIgnoreQueue.take();
					varietyBw.append(s);
					varietyBw.newLine();
				}
				varietyBw.close();
				
				
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}


}
