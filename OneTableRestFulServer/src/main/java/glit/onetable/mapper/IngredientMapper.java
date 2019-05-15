package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.IngredientPriceAll;
import glit.onetable.model.vo.IngredientSubject;
import glit.onetable.model.vo.PageLimit;

@Mapper
public interface IngredientMapper {

	public List<IngredientPriceAll> searchAll(IngredientPriceAll pageLimit);
	
	public int firstSearch(String query);
	public List<IngredientSubject> search(String query);
	public List<Ingredient> ingredientQuery(int idx);
	public List<IngredientPrice> priceHistroy(String ingredientItemId);
	//public List<Ingredient> ingredientUpload();
	public int subjectInsert(String query);
	public int varietyNotExistsInsert(IngredientSubject keyword);

}
