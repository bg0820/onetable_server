package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IngredientSubject {
	private int ingredientSubjectIdx;
	private String variety;
	private long categoryNum;

	public int getIngredientSubjectIdx() {
		return ingredientSubjectIdx;
	}

	public void setIngredientSubjectIdx(int ingredientSubjectIdx) {
		this.ingredientSubjectIdx = ingredientSubjectIdx;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public long getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(long categoryNum) {
		this.categoryNum = categoryNum;
	}


}
