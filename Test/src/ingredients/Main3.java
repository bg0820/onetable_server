package ingredients;
import java.util.ArrayList;

public class Main3 {

	public static void main(String[] args) {
		try {
			ReadExcel re = new ReadExcel();
			ArrayList<String> saveList = re.getVarietyList();

			for (int i = 0; i < saveList.size(); i++) {
				RequestThread rt = new RequestThread(saveList.get(i));
				rt.start();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) { // TODO Auto-generated
					e.printStackTrace();
				}
			}

			//ArrayList<Variety> varietyList = RequestThread.hashMap;
/*
			DBConnection db = new DBConnection();
			db.connection(varietyList);
*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
