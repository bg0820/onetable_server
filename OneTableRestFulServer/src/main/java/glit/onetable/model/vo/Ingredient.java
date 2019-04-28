package glit.onetable.model.vo;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ingredient {
	private String IngredientsUUID;
	private String IngredientsSubjectUUID;
	private int currentPrice;
	private Date priceDate;
	private int queryCnt;
	private String displayName;
	private String imgUrl;

	public String getIngredientsUUID() {
		return IngredientsUUID;
	}

	public void setIngredientsUUID(String ingredientsUUID) {
		IngredientsUUID = ingredientsUUID;
	}

	public String getIngredientsSubjectUUID() {
		return IngredientsSubjectUUID;
	}

	public void setIngredientsSubjectUUID(String ingredientsSubjectUUID) {
		IngredientsSubjectUUID = ingredientsSubjectUUID;
	}

	public int getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public int getQueryCnt() {
		return queryCnt;
	}

	public void setQueryCnt(int queryCnt) {
		this.queryCnt = queryCnt;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
