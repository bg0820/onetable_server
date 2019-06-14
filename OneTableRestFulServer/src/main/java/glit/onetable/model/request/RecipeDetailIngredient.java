package glit.onetable.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RecipeDetailIngredient {
	private String name;
	private String unitStr;
	private double unitAmount;
	private double calcPrice;
	private String unitDisplayName;

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

	public double getUnitAmount() {
		return unitAmount;
	}

	public void setUnitAmount(double unitAmount) {
		this.unitAmount = unitAmount;
	}

	public double getCalcPrice() {
		return calcPrice;
	}

	public void setCalcPrice(double calcPrice) {
		this.calcPrice = calcPrice;
	}

	public String getUnitDisplayName() {
		return unitDisplayName;
	}

	public void setUnitDisplayName(String unitDisplayName) {
		this.unitDisplayName = unitDisplayName;
	}



}
