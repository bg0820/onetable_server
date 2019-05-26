package glit.onetable.model.request;

import java.util.List;

public class Onetable {
	private String title;
	private int userIdx;
	private String type;
	//private RecipeId[] rId;
	private List<Integer> recipes;

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
	public List<Integer> getRecipes() {
		return recipes;
	}
	public void setRecipes(List<Integer> recipes) {
		this.recipes = recipes;
	}
}
