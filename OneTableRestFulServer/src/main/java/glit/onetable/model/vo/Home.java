package glit.onetable.model.vo;

import java.util.List;

public class Home {
	private List<RecipeUserPrice> popular;
	private List<RecipeUserPrice> count;
	private List<RecipeUserPrice> recent;

	public List<RecipeUserPrice> getPopular() {
		return popular;
	}

	public void setPopular(List<RecipeUserPrice> popular) {
		this.popular = popular;
	}

	public List<RecipeUserPrice> getCount() {
		return count;
	}

	public void setCount(List<RecipeUserPrice> count) {
		this.count = count;
	}

	public List<RecipeUserPrice> getRecent() {
		return recent;
	}

	public void setRecent(List<RecipeUserPrice> recent) {
		this.recent = recent;
	}

}
