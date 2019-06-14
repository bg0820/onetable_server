package Model;

public class IngredientSubject {
	private int ingredientSubjectIdx;
	private String  name;
	private long categoryNum;
	
	private int page = 1;

	public int getIngredientSubjectIdx() {
		return ingredientSubjectIdx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(long categoryNum) {
		this.categoryNum = categoryNum;
	}

	public void setIngredientSubjectIdx(int ingredientSubjectIdx) {
		this.ingredientSubjectIdx = ingredientSubjectIdx;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}


}
