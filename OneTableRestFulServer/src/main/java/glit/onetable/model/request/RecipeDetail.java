package glit.onetable.model.request;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;

import glit.onetable.model.vo.RecipeMethod;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RecipeDetail {
	private String recipeName;
	private String userName = "system";
	private String recipeImg;
	private double servingMin;
	private double servingMax;
	private String cookTimeMin;
	private double kcal;
	private int price;

	private ArrayList<RecipeMethod> recipeMethod =
			new ArrayList<RecipeMethod>();

	public ArrayList<RecipeMethod> getRecipeMethod() {
		return recipeMethod;
	}

	public void setRecipeMethod(ArrayList<RecipeMethod> recipeMethod) {
		this.recipeMethod = recipeMethod;
	}

	public ArrayList<RecipeDetailIngredient> recipeIngredient =
			new ArrayList<RecipeDetailIngredient>();

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRecipeImg() {
		return recipeImg;
	}

	public void setRecipeImg(String recipeImg) {
		this.recipeImg = recipeImg;
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

	public String getCookTimeMin() {
		return cookTimeMin;
	}

	public void setCookTimeMin(String cookTimeMin) {
		this.cookTimeMin = cookTimeMin;
	}

	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


}
