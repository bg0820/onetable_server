package glit.onetable.model.vo;

import java.util.List;

public class Home {
	private List<Recipe> popular;
	private List<Recipe> count;
	private List<Recipe> recent;

	public List<Recipe> getPopular() {
		return popular;
	}
	public void setPopular(List<Recipe> popular) {
		this.popular = popular;
	}
	public List<Recipe> getCount() {
		return count;
	}
	public void setCount(List<Recipe> count) {
		this.count = count;
	}
	public List<Recipe> getRecent() {
		return recent;
	}
	public void setRecent(List<Recipe> recent) {
		this.recent = recent;
	}

}
