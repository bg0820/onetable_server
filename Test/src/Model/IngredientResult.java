package Model;

import java.util.ArrayList;

public class IngredientResult {
	public ArrayList<Ingredient> list = new ArrayList<Ingredient>();
	private int recordCnt = 0;

	public int getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(int recordCnt) {
		this.recordCnt = recordCnt;
	}


}
