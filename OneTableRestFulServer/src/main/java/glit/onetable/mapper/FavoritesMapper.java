package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Favorites;
import glit.onetable.model.vo.IngredientPriceAll;
import glit.onetable.model.vo.Recipe;

@Mapper
public interface FavoritesMapper {
	public int favoritesInsert(Favorites favorites);
	public int favoritesDelete(Favorites favorites);
	public List<Recipe> myRecipeFavorites(Favorites favorites);
	public List<IngredientPriceAll> myIngredientFavorites(List<Integer> list);
	public List<Integer> myIngredient(Favorites favorites);


}
