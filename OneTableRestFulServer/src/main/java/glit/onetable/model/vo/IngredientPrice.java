package glit.onetable.model.vo;

import java.sql.Date;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientPrice {
	private int ingredientPriceIdx;

	private String ingredientItemId;
	private int price;
	private Date priceDate;

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

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public int getIngredientPriceIdx() {
		return ingredientPriceIdx;
	}

	public void setIngredientPriceIdx(int ingredientPriceIdx) {
		this.ingredientPriceIdx = ingredientPriceIdx;
	}


}
