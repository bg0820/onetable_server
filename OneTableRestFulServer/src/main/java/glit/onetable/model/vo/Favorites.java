package glit.onetable.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Favorites {
	int userIdx;
	int favoriteIdx;
	int favoritesType;
	int alterPrice;

	public int getUserIdx() {
		return userIdx;
	}
	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}
	public int getFavoriteIdx() {
		return favoriteIdx;
	}
	public void setFavoriteIdx(int favoriteIdx) {
		this.favoriteIdx = favoriteIdx;
	}
	public int getFavoritesType() {
		return favoritesType;
	}
	public void setFavoritesType(int favoritesType) {
		this.favoritesType = favoritesType;
	}
	public int getAlterPrice() {
		return alterPrice;
	}
	public void setAlterPrice(int alterPrice) {
		this.alterPrice = alterPrice;
	}


}
