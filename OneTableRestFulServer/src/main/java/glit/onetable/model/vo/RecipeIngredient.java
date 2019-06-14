package glit.onetable.model.vo;

public class RecipeIngredient {
	private int recipeIdx;
	private String ingredientItemId;
	private int unitIdx;
	private int minAmount;
	private int maxAmount;
	private String symbol;
	private String displayName;
	private int idx;
	private String displayAmount;

	private double result = 0.0;

	public int getRecipeIdx() {
		return recipeIdx;
	}

	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}

	public String getIngredientItemId() {
		return ingredientItemId;
	}

	public void setIngredientItemId(String ingredientItemId) {
		this.ingredientItemId = ingredientItemId;
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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		if (symbol.equals("/"))
			this.setResult(this.minAmount
					/ this.maxAmount);
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

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}


}
