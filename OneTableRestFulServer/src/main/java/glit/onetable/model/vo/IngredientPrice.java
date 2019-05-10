package glit.onetable.model.vo;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientPrice {
	private String ingredientItemId;
	private int price;
	private Timestamp priceDate;

	public String getIngredientItemId() {
		return ingredientItemId;
	}
	public void setIngredientItemId(String ingredientItemId) {
		this.ingredientItemId = ingredientItemId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Timestamp getPriceDate() {
		return priceDate;
	}
	public void setPriceDate(Timestamp priceDate) {
		this.priceDate = priceDate;
	}


}
