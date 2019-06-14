package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Onetablesetrecipe {
	private int onetableRecipeIdx;
	private int onetablesetIdx;
	private int recipeIdx;
	private double x;
	private double y;

	public int getOnetableRecipeIdx() {
		return onetableRecipeIdx;
	}

	public void setOnetableRecipeIdx(int onetableRecipeIdx) {
		this.onetableRecipeIdx = onetableRecipeIdx;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getOnetablesetIdx() {
		return onetablesetIdx;
	}

	public void setOnetablesetIdx(int onetablesetIdx) {
		this.onetablesetIdx = onetablesetIdx;
	}

	public int getRecipeIdx() {
		return recipeIdx;
	}

	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}


}
