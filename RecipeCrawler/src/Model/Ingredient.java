package Model;

import Analyze.HangleAnalyze;

public class Ingredient {
	private String name;
	private String unitStr;

	private IngredientUnit ingredientUnit;
	
	public void anaylze() {
		try {
			ingredientUnit = HangleAnalyze.getInstance().analyze(unitStr);
		} catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getUnitStr() {
		return unitStr;
	}

	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[name=" + name + ", unitStr=" + unitStr + "]"; 
	}

	public IngredientUnit getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(IngredientUnit ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

	

}
