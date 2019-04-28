package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientsSubject;

@Mapper
public interface IngredientMapper {
	
	public List<IngredientsSubject> search(String query);
	public List<Ingredient> ingredientQuery(String uuid);
	
}
