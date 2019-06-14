package glit.onetable.model.request;

import java.util.List;

import glit.onetable.model.vo.Onetablesetrecipe;

public class Onetable {
	private String title;
	private int userIdx;
	private String type;
	//private RecipeId[] rId;
	private List<Onetablesetrecipe> recipes;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public List<Onetablesetrecipe> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Onetablesetrecipe> recipes) {
		this.recipes = recipes;
	}
}
