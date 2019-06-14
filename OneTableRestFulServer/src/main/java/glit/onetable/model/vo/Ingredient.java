package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Ingredient {
	private int idx;
	
	private String ingredientItemId;
	private int ingredientSubjectIdx;
	private int unitIdx;
	private double unitAmount;
	
	private int queryCnt;
	private String displayName;
	private String imgUrl;

	public double getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(double unitAmount) {
		this.unitAmount = unitAmount;
	}

	public String getIngredientItemId() {
		return ingredientItemId;
	}

	public void setIngredientItemId(String ingredientItemId) {
		this.ingredientItemId = ingredientItemId;
	}


	public int getQueryCnt() {
		return queryCnt;
	}

	public void setQueryCnt(int queryCnt) {
		this.queryCnt = queryCnt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getIngredientSubjectIdx() {
		return ingredientSubjectIdx;
	}

	public void setIngredientSubjectIdx(int ingredientSubjectIdx) {
		this.ingredientSubjectIdx = ingredientSubjectIdx;
	}

	public int getUnitIdx() {
		return unitIdx;
	}

	public void setUnitIdx(int unitIdx) {
		this.unitIdx = unitIdx;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

}
