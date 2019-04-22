package ingredients;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {
	public ArrayList<String> getVarietyList() {
		String path = "prod/prodId_20190309.xls";
		ArrayList<String> saveList = new ArrayList<String>();
		File file = new File(path);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);

			HSSFWorkbook hworkbook = new HSSFWorkbook(inputStream);

			HSSFSheet curSheet = hworkbook.getSheetAt(0);
			int row = curSheet.getPhysicalNumberOfRows();
			for (int i = 1; i < row; i++) {
				System.out.println(i);
				ItemInfo itemInfo = new ItemInfo();
				HSSFRow curRow = curSheet.getRow(i);
				if (curRow != null) {

					HSSFCell itemCell = curRow.getCell(0);
					HSSFCell valCell = curRow.getCell(1);

					String item = "";
					if (itemCell != null)
						item = String.valueOf(curRow.getCell(0));
					String val = "";
					if (valCell != null)
						val = String.valueOf(curRow.getCell(1));

					if (item.equals("") || val.equals(""))
						continue;
					if (item.contains("기타"))
						continue;
					if (val.contains("기타"))
						continue;

					if (!saveList.contains(val)) {
						saveList.add(item + "#" + val);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return saveList;
	}

}
