package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.Recipe;
import glit.onetable.model.vo.RecipeIngredient;
import glit.onetable.model.vo.Unit;
import glit.onetable.model.vo.User;

@Mapper
public interface RecipeMapper {
	public List<Recipe> searchAll(Recipe recipe);
	public List<Recipe> search(String query);
	public Recipe detail(int recipeIdx);
	public List<RecipeIngredient> recipeIngredientToRecipeIdx(int recipeIdx);
	public Unit getUnitName(int unitIdx);
	
	public IngredientPrice ingredientCurrentDayPrice(String ingredientItemId);
	public Ingredient getIngredientToItemId(String ingredientItemId);
	
	public User getUserInfo(int userIdx);
	
	public int searchCnt(String query );
	public int allCnt();

}
