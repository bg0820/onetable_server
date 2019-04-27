import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;

public class FileManager extends Thread {
	public static ArrayDeque<String> queue = new ArrayDeque<String>();
	private BufferedWriter bw;
	

	@Override
	public void run() {
		while (true) {

			try {
				System.out.println("파일 쓰기 : " + queue.size() + "개");
				bw = new BufferedWriter(new FileWriter("ignore.txt", true));
				for (int i = 0; i < queue.size(); i++) {
					String s = queue.pop();
					bw.append(s);
					bw.newLine();
				}
				bw.close();
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
