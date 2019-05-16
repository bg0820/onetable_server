package glit.onetable.model.vo;

public class Recipe {
	public int recipeIdx;
	public int recipePageId;
	public int userIdx;
	public String name;
	public int price;
	public double servingMin;
	public double servingMax;
	public String recipeImg;
	public int cookTimeSec;
	public double kcal;
	public String contentHtml;

	private int limitIndex;
	private int limitCnt;


	public int getLimitIndex() {
		return limitIndex;
	}

	public void setLimitIndex(int limitIndex) {
		this.limitIndex = limitIndex;
	}

	public int getLimitCnt() {
		return limitCnt;
	}

	public void setLimitCnt(int limitCnt) {
		this.limitCnt = limitCnt;
	}

	public int getRecipeIdx() {
		return recipeIdx;
	}
	public void setRecipeIdx(int recipeIdx) {
		this.recipeIdx = recipeIdx;
	}
	public int getRecipePageId() {
		return recipePageId;
	}
	public void setRecipePageId(int recipePageId) {
		this.recipePageId = recipePageId;
	}
	public int getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public double getServingMin() {
		return servingMin;
	}
	public void setServingMin(double servingMin) {
		this.servingMin = servingMin;
	}
	public double getServingMax() {
		return servingMax;
	}
	public void setServingMax(double servingMax) {
		this.servingMax = servingMax;
	}
	public String getRecipeImg() {
		return recipeImg;
	}
	public void setRecipeImg(String recipeImg) {
		this.recipeImg = recipeImg;
	}
	public int getCookTimeSec() {
		return cookTimeSec;
	}
	public void setCookTimeSec(int cookTimeSec) {
		this.cookTimeSec = cookTimeSec;
	}
	public double getKcal() {
		return kcal;
	}
	public void setKcal(double kcal) {
		this.kcal = kcal;
	}
	public String getContentHtml() {
		return contentHtml;
	}
	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}


}
