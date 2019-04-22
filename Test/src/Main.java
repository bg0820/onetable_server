import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static HashMap<String, ArrayList<Variety>> hashMap = new HashMap<String, ArrayList<Variety>>();

	public static void main(String[] args) throws Exception {
		DBConnectionPool.getInstance();
		RequestThread[] rt;
		try {
			// ReadExcel re = new ReadExcel();
			// ArrayList<String> saveList = re.getVarietyList();
			System.out.println("DB 연결");
			DBConnection db = new DBConnection();
			ArrayList<IngredientsSubject> list = db.connection();
			System.out.println("List 가져옴 : " + list.size());
			rt = new RequestThread[list.size()];
			// System.out.println(list.size());
			ArrayList<String> proxyList = new ArrayList<String>();
			//파일 객체 생성
            File file = new File("proxyList.txt");
            //입력 스트림 생성
            FileReader filereader = new FileReader(file);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
            	proxyList.add(line);
            }
            //.readLine()은 끝에 개행문자를 읽지 않는다.
            bufReader.close();

            int proxyIndex = 0;
			for (int i = 0; i < list.size(); i++) {

				String proxyIP = proxyList.get(proxyIndex);
				rt[i] = new RequestThread(list.get(i), proxyIP);
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

			for(int i = 0; i< list.size(); i++)
			{
				rt[i].join();
			}

			DBConnection db2 = new DBConnection();
			db2.insetIngredient(hashMap);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * Main main = new Main(); ReadExcel re = new ReadExcel(); ArrayList<ItemInfo>
		 * itemlist = new ArrayList<ItemInfo>(); itemlist = re.readExcel(); // Scanner
		 * scanner = new Scanner(System.in); // System.out.println("검색어 입력 : "); //
		 * String search = scanner.next();
		 *
		 * System.out.println(itemlist.size()); for (ItemInfo info : itemlist) { //
		 * System.out.println(info.getVariety()); // test.getSSG(info.getVariety());
		 * RequestThread rt = new RequestThread(info.getVariety()); rt.start(); try {
		 * Thread.sleep(3000); } catch (InterruptedException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } }
		 */
		System.out.println("성공");
	}

}
