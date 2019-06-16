package glit.onetable.model.vo;

public class RecipeIngredient {
	private int recipeIngredientIdx;
	
	private int recipeIdx;
	private int ingredientIdx;
	private int unitIdx;
	private int minAmount;
	private int maxAmount;
	private double result;
	private String symbol;
	private String displayName;
	private int idx;
	private String displayAmount;
	private String unitStr;
	private int price;

	public int getRecipeIdx() {
		return recipeIdx;
	}

	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}

	public int getIngredientIdx() {
		return ingredientIdx;
	}

	public void setIngredientIdx(int ingredientIdx) {
		this.ingredientIdx = ingredientIdx;
	}

	public int getUnitIdx() {
		return unitIdx;
	}

	public void setUnitIdx(int unitIdx) {
		this.unitIdx = unitIdx;
	}

	public int getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRecipeIngredientIdx() {
		return recipeIngredientIdx;
	}

	public void setRecipeIngredientIdx(int recipeIngredientIdx) {
		this.recipeIngredientIdx = recipeIngredientIdx;
	}



}
