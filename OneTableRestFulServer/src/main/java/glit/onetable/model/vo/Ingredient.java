package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ingredient {
	private String ingredientIdx;
	private String ingredientSubjectIdx;
	private int queryCnt;
	private String displayName;
	private String imgUrl;

	public String getIngredientsIdx() {
		return ingredientIdx;
	}

	public void setIngredientIdx(String ingredientIdx) {
		this.ingredientIdx = ingredientIdx;
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
