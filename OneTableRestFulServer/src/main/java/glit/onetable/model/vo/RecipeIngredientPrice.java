package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RecipeIngredientPrice {
	private int ingredientIdx;
	private int recipeIdx;
	private String ingredientDisplayName;
	private String imgUrl;
	private int originalPrice;
	private int unitIdx;
	private double result;
	private String displayName;
	private String displayAmount;
	private String unitStr;
	private double recipeIngredientPrice;
	private String originalUnitStr;


	public int getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(int originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getOriginalUnitStr() {
		return originalUnitStr;
	}

	public void setOriginalUnitStr(String originalUnitStr) {
		this.originalUnitStr = originalUnitStr;
	}

	public int getIngredientIdx() {
		return ingredientIdx;
	}

	public void setIngredientIdx(int ingredientIdx) {
		this.ingredientIdx = ingredientIdx;
	}

	public int getRecipeIdx() {
		return recipeIdx;
	}

	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}

	public String getIngredientDisplayName() {
		return ingredientDisplayName;
	}

	public void setIngredientDisplayName(String ingredientDisplayName) {
		this.ingredientDisplayName = ingredientDisplayName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public int getUnitIdx() {
		return unitIdx;
	}

	public void setUnitIdx(int unitIdx) {
		this.unitIdx = unitIdx;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayAmount() {
		return displayAmount;
	}

	public void setDisplayAmount(String displayAmount) {
		this.displayAmount = displayAmount;
	}

	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	public double getRecipeIngredientPrice() {
		return recipeIngredientPrice;
	}

	public void setRecipeIngredientPrice(double recipeIngredientPrice) {
		this.recipeIngredientPrice = recipeIngredientPrice;
	}



}
