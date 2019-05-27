package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.IngredientPriceAll;
import glit.onetable.model.vo.IngredientSubject;
import glit.onetable.model.vo.Search;

@Mapper
public interface IngredientMapper {

	public List<IngredientPriceAll> searchAll(IngredientPriceAll pageLimit);
	public List<IngredientPriceAll> search(Search query);
	public List<IngredientSubject> ingredientSubjectSearch(String variety);


	public int getIngredientSubjectIdx(String query);

	public List<Ingredient> ingredientQuery(int idx);
	public List<IngredientPrice> priceHistroy(String ingredientItemId);
	//public List<Ingredient> ingredientUpload();
	public int subjectInsert(String query);
	public int varietyNotExistsInsert(IngredientSubject keyword);
	public int unitSearch(String unitStr);
	public int ingredientNotExistsInsert(Ingredient ingredient);

	public int insertIngredientPrice(IngredientPrice ingredientPrice);

}
