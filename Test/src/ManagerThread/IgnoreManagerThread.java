package ManagerThread;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class IgnoreManagerThread extends Thread {
	public static LinkedBlockingQueue<String> varietyIgnoreQueue = new LinkedBlockingQueue<String>();
	private BufferedWriter varietyBw;
	

	@Override
	public void run() {
		while (true) {

			try {
				System.out.println("IgnoreManagerThread Variety Write : " + varietyIgnoreQueue.size() + "개");
				varietyBw = new BufferedWriter(new FileWriter("ignore.txt", true));
				
				for (int i = 0; i < varietyIgnoreQueue.size(); i++) {
					String s = varietyIgnoreQueue.take();
					varietyBw.append(s);
					varietyBw.newLine();
				}
				varietyBw.close();
				
				
				Thread.sleep(10000);

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
