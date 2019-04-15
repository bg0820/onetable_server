import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		try {
			ReadExcel re = new ReadExcel();
			ArrayList<String> saveList = re.getVarietyList();


			DBConnection db = new DBConnection();
			db.connection(saveList);

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
	}

}
