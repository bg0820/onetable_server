import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static HashMap<String, ArrayList<Variety>> hashMap = new HashMap<String, ArrayList<Variety>>();

	public static void main(String[] args) throws Exception {
		try {
			FileManager fm = new FileManager();
			fm.start();
			ProxyManager pm = new ProxyManager();
			pm.start();
			DBIngredientsManager.getInstance().start();
			
			ArrayList<String> readList = new ArrayList<String>();
			try {
				BufferedReader br = new BufferedReader(new FileReader("ignore.txt"));
				String line = "";
				while((line = br.readLine()) != null)
				{
					readList.add(line);
				}
			} catch (IOException er) {

			}
			
			System.out.println("DB 연결");
			DBConnection db = new DBConnection();
			ArrayList<Variety> list = db.connection();
			System.out.println("List 가져옴 : " + list.size());
			
			SSGThread[] rt = new SSGThread[list.size()];

			ArrayList<String> proxyList = new ArrayList<String>();
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(new FileReader("proxyList.txt"));
            String line = "";
            while((line = bufReader.readLine()) != null){
            	proxyList.add(line);
            }
            
            bufReader.close();

            ArrayList<String> ignoreProxyList = new ArrayList<String>();
            //입력 버퍼 생성
            BufferedReader ignoreBufReader = new BufferedReader(new FileReader("proxyIgnoreList.txt"));
            String successline = "";
            while((successline = ignoreBufReader.readLine()) != null){
            	ignoreProxyList.add(line);
            }
            
            ignoreBufReader.close();
            
            
            
            int proxyIndex = 0;
			for (int i = 0; i < list.size(); i++) {
				String proxyIP = proxyList.get(proxyIndex);
				if(ignoreProxyList.contains(proxyIP))
					continue;
				
				
				if(readList.contains(list.get(i).getVariety()))
					continue;
				
				rt[i] = new SSGThread(list.get(i), proxyIP);
				rt[i].start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) { // TODO Auto-generated
					e.printStackTrace();
				}

				if(proxyIndex + 1 > list.size() - 1)
					proxyIndex = 0;
				proxyIndex++;
			}



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("성공");
	}

}
