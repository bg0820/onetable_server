package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.Recipe;
import glit.onetable.model.vo.RecipeComment;
import glit.onetable.model.vo.RecipeIngredient;
import glit.onetable.model.vo.RecipeIngredientPrice;
import glit.onetable.model.vo.RecipeMethod;
import glit.onetable.model.vo.Search;
import glit.onetable.model.vo.Unit;
import glit.onetable.model.vo.User;

@Mapper
public interface RecipeMapper {
	public List<Recipe> searchAll(Recipe recipe);
	public List<Recipe> search(Search query);
	public Recipe detail(int recipeIdx);
	public List<RecipeIngredient> recipeIngredientToRecipeIdx(int recipeIdx);
	public Unit getUnitName(int unitIdx);
	
	public int insertRecipe(Recipe recipe);
	public void insertRecipeMethod(RecipeMethod method);
	public void insertRecipeIngrdient(RecipeIngredient ingredient);
	
	public List<Recipe> history(int userIdx);

	public IngredientPrice ingredientCurrentDayPrice(int ingredientIdx);
	public Ingredient getIngredientToItemId(int ingredientIdx);

	public User getUserInfo(int userIdx);

	public Double getGradePoint(int recipeIdx);
	
	public int searchCnt(String query);
	public int allCnt();

	public List<RecipeMethod> getMethod(int recipeIdx);
	public List<Unit> getUnit();
	
	public List<RecipeIngredientPrice> recipeIngredientPriceDetail(int recipeIdx);
	
	public List<RecipeComment> getRecipeComment(int recipeIdx);

}
