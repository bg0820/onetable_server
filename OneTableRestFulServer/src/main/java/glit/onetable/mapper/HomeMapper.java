package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.RecipeUserPrice;

@Mapper
public interface HomeMapper {
	public List<RecipeUserPrice> popularRecipe();
	public List<RecipeUserPrice> countRecipe();
	public List<RecipeUserPrice> recentRecipe();
}
