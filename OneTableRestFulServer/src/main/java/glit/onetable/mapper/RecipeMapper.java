package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Recipe;

@Mapper
public interface RecipeMapper {
	public List<Recipe> searchAll(Recipe recipe);
	public List<Recipe> search(String query);
	public Recipe detail(int recipeIdx);
	public List<Recipe> insert(Recipe recipe);
	public List<Recipe> insertRecipeMethod(Recipe recipe);
	public List<Recipe> insertRecipeIngrdient(Recipe recipe);

}
