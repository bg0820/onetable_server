package glit.onetable.model.vo;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class IngredientPriceAll {
	private int idx;
	private String displayName;
	private int price;
	private String imgUrl;
	private String ingredientItemId;
	private Date priceDate;
	private int queryCnt;

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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getIngredientItemId() {
		return ingredientItemId;
	}

	public void setIngredientItemId(String ingredientItemId) {
		this.ingredientItemId = ingredientItemId;
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

}
