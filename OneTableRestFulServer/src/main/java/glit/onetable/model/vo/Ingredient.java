package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ingredient {
	private String ingredientItemId;
	private String ingredientSubjectIdx;
	private int queryCnt;
	private String displayName;
	private String imgUrl;
	private double unitAmount;

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

	public String getIngredientSubjectIdx() {
		return ingredientSubjectIdx;
	}

	public void setIngredientSubjectIdx(String ingredientSubjectIdx) {
		this.ingredientSubjectIdx = ingredientSubjectIdx;
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

}
