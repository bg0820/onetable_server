import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {
	public ArrayList<String> getVarietyList() {
		String path = "C:\\Users\\khj\\prodId_20190309.xls";
		ArrayList<String> saveList = new ArrayList<String>();
		File file = new File(path);
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(file);

			HSSFWorkbook hworkbook = new HSSFWorkbook(inputStream);

			HSSFSheet curSheet = hworkbook.getSheetAt(0);
			int row = curSheet.getPhysicalNumberOfRows();

			for (int i = 1; i < row; i++) {
				ItemInfo itemInfo = new ItemInfo();
				HSSFRow curRow = curSheet.getRow(i);

				String item = String.valueOf(curRow.getCell(0));
				String val = String.valueOf(curRow.getCell(1));

				if(item.equals("") || val.equals(""))
					continue;
				if (item.contains("기타"))
					continue;
				if (val.contains("기타"))
					continue;

				if (!saveList.contains(val)) {
					saveList.add(item + "#" + val);
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

	/*
	 * public ArrayList<ItemInfo> readExcel() { String path =
	 * "C:\\Users\\khj\\prodId_20190309.xls"; ArrayList<ItemInfo> itemlist = new
	 * ArrayList<ItemInfo>(); ArrayList<String> duplicateName = new
	 * ArrayList<String>(); try { File file = new File(path); FileInputStream
	 * inputStream = new FileInputStream(file); HSSFWorkbook hworkbook = new
	 * HSSFWorkbook(inputStream);
	 *
	 * HSSFCell curCell;
	 *
	 * int sheetNumber = hworkbook.getNumberOfSheets();
	 *
	 * while (sheetNumber != 0) { sheetNumber--;
	 *
	 * curSheet = hworkbook.getSheetAt(sheetNumber); int row =
	 * curSheet.getPhysicalNumberOfRows();
	 *
	 * for (int i = 1; i < row; i++) { ItemInfo iteminfo = new ItemInfo(); curRow =
	 * curSheet.getRow(i); // System.out.println(row);
	 * iteminfo.setItem(String.valueOf(curRow.getCell(0))); //
	 * System.out.println(String.valueOf(curRow.getCell(0)));
	 * iteminfo.setVariety(String.valueOf(curRow.getCell(1))); //
	 * System.out.println(String.valueOf(curRow.getCell(1))); //
	 * System.out.println(iteminfo.getItem()+" "+iteminfo.getVariety());
	 *
	 * if (!duplicateName.contains(String.valueOf(curRow.getCell(1)))) {
	 * itemlist.add(iteminfo); duplicateName.add(String.valueOf(curRow.getCell(1)));
	 * }
	 *
	 * }
	 *
	 * } } catch (FileNotFoundException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); } return itemlist; }
	 */
}
