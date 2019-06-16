package glit.onetable.model.request;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import glit.onetable.model.vo.RecipeIngredientPrice;
import glit.onetable.model.vo.RecipeMethod;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RecipeDetail {
	private String recipeName;
	private String nickName = "SYSTEM";
	private String recipeImg;
	private double servingMin;
	private double servingMax;
	private String cookTimeMin;
	private double kcal;
	private int price;

	public List<RecipeMethod> recipeMethod;
	public List<RecipeIngredientPrice> recipeIngredient;
	
	
	
	

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<RecipeMethod> getRecipeMethod() {
		return recipeMethod;
	}

	public void setRecipeMethod(List<RecipeMethod> recipeMethod) {
		this.recipeMethod = recipeMethod;
	}

	public List<RecipeIngredientPrice> getRecipeIngredient() {
		return recipeIngredient;
	}

	public void setRecipeIngredient(List<RecipeIngredientPrice> recipeIngredient) {
		this.recipeIngredient = recipeIngredient;
	}


}
