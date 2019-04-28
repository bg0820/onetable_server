import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

public class IgnoreManagerThread extends Thread {
	public static ArrayDeque<String> varietyIgnoreQueue = new ArrayDeque<String>();
	public static ArrayDeque<String> proxyIgnoreQueue = new ArrayDeque<String>();
	private BufferedWriter varietyBw;
	private BufferedWriter proxyBw;
	

	@Override
	public void run() {
		while (true) {

			try {
				System.out.println("IgnoreManagerThread Variety Write : " + varietyIgnoreQueue.size() + "개");
				varietyBw = new BufferedWriter(new FileWriter("ignore.txt", true));
				
				for (int i = 0; i < varietyIgnoreQueue.size(); i++) {
					String s = varietyIgnoreQueue.pop();
					varietyBw.append(s);
					varietyBw.newLine();
				}
				varietyBw.close();
				
				
				System.out.println("IgnoreManagerThread Proxy Write : " + varietyIgnoreQueue.size() + "개");
				proxyBw = new BufferedWriter(new FileWriter("proxySuccess.txt", true));
				for (int i = 0; i < proxyIgnoreQueue.size(); i++) {
					String s = proxyIgnoreQueue.pop();
					proxyBw.append(s);
					proxyBw.newLine();
				}
				proxyBw.close();
				
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
