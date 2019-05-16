package Model;

import java.util.ArrayList;
import Analyze.HangleAnalyze;

public class Recipe {
	private String mainTitle;
	private String subTitle;

	private int recipePageId;

	private String cookTime = "";
	private double kcal = 0.0;
	private String foodImg;
	private double minServing; // 인분
	private double maxServing;

	private ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
	private ArrayList<Nutrition> nutritionList = new ArrayList<Nutrition>();

	private String methodHTML;
	
	private IngredientUnit ingredientUnit;
	

	public String getMethodHTML() {
		return methodHTML;
	}

	public void setMethodHTML(String methodHTML) {
		this.methodHTML = methodHTML;
	}

	public String getMainTitle() {
		return mainTitle;
	}

	public void setMainTitle(String mainTitle) {
		this.mainTitle = mainTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getCookTime() {
		return cookTime;
	}

	public void setCookTime(String cookTime) {
		this.cookTime = cookTime;
	}

	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}

	public void addIngredientList(Ingredient ing) {
		ingredientList.add(ing);
	}

	public int getIngredientSize()
	{
		return ingredientList.size();
	}
	
	public Ingredient getIngredientList(int idx) {
		return ingredientList.get(idx);
	}

	public void addNutritionList(Nutrition nutrition) {
		nutritionList.add(nutrition);
	}

	public Nutrition getNutritionList(int idx) {
		return nutritionList.get(idx);
	}

	public double getMinServing() {
		return minServing;
	}

	public void setMinServing(double minServing) {
		this.minServing = minServing;
	}

	public double getMaxServing() {
		return maxServing;
	}

	public void setMaxServing(double maxServing) {
		this.maxServing = maxServing;
	}

	public void anaylze() throws Exception {
		/*System.out.println("========================");
		for (int i = 0; i < ingredientList.size(); i++) {
			// 9HangleAnalyze.getInstance().getKeyWord(ingredientList.get(i).getUnitStr());
			HangleAnalyze.getInstance().analyze(ingredientList.get(i).getUnitStr());
		}
		System.out.println("========================");*/
	}

	public void printIngredient() {
		for (int i = 0; i < ingredientList.size(); i++) {
			System.out.println(ingredientList.get(i).getUnitStr()); //뭐지????????
		}
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		sb.append("======================\r\n");
		//sb.append("요리사진: " + foodImg + "\r\n");
		sb.append("메인 이름 : " + mainTitle + "\r\n");
		sb.append("서브 이름 : " + subTitle + "\r\n");
		sb.append("조리 시간 : " + cookTime + "\r\n");
		sb.append("칼로리 : " + kcal + "\r\n");
		sb.append("---------재료---------\r\n");
		for (int i = 0; i < ingredientList.size(); i++) {
			sb.append(ingredientList.get(i).toString() + "\r\n");
		}

		sb.append("======================\r\n");

		return sb.toString();
	}

	public int getRecipePageId() {
		return recipePageId;
	}

	public void setRecipePageId(int recipePageId) {
		this.recipePageId = recipePageId;
	}

	public String getFoodImg() {
		return foodImg;
	}

	public void setFoodImg(String foodImg) {
		this.foodImg = foodImg;
	}

	public IngredientUnit getIngredientUnit() {
		return ingredientUnit;
	}

	public void setIngredientUnit(IngredientUnit ingredientUnit) {
		this.ingredientUnit = ingredientUnit;
	}

}
