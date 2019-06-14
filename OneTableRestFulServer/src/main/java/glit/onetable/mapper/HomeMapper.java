package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Recipe;

@Mapper
public interface HomeMapper {
	public List<Recipe> popularRecipe();
	public List<Recipe> countRecipe();
	public List<Recipe> recentRecipe();
}
