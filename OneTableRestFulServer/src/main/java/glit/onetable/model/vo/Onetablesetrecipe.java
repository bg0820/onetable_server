package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Onetablesetrecipe {
	public int onetablesetIdx;
	public int recipeIdx;

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
