package glit.onetable.model;

import glit.onetable.model.vo.IngredientSubject;

public class IngredientModel {

	private String ingredientItemId;

	private String displayName;
	private String imgUrl;
	private int queryCnt;
	private int price;

	// db - ingredient_subject
	private IngredientSubject ingredientSubject;

	// Hangle Anay
	private AnalyzeUnit anayUnit;



	public String getIngredientItemId() {
		return ingredientItemId;
	}

	public void setIngredientItemId(String ingredientItemId) {
		this.ingredientItemId = ingredientItemId;
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

	public int getQueryCnt() {
		return queryCnt;
	}

	public void setQueryCnt(int queryCnt) {
		this.queryCnt = queryCnt;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


	/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[displayName=" + displayName + ", unitAmount=" + unitAmount + ", unitStr=" + unitStr
				+ ", unitIdx=" + unitIdx + "]";
	}
*/
	public IngredientSubject getIngredientSubject() {
		return ingredientSubject;
	}

	public void setIngredientSubject(IngredientSubject ingredientSubect) {
		this.ingredientSubject = ingredientSubect;
	}

	public AnalyzeUnit getAnayUnit() {
		return anayUnit;
	}

	public void setAnayUnit(AnalyzeUnit anayUnit) {
		this.anayUnit = anayUnit;
	}



}
