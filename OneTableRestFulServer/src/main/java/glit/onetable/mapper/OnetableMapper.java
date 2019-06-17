package glit.onetable.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import glit.onetable.model.vo.OnetableDetail;
import glit.onetable.model.vo.Onetableset;
import glit.onetable.model.vo.Onetablesetrecipe;
import glit.onetable.model.vo.RecipeUserPrice;

@Mapper
public interface OnetableMapper {
	public List<RecipeUserPrice> searchRecipe(String query);
	public void onetablesetInsert(Onetableset onetableset);
	public void onetablerecipeInsert(Onetablesetrecipe onetablerecipe);
	public List<Onetableset> history(int userIdx);
	public List<OnetableDetail> detail(int onetablesetIdx);
	
	public void onetablesetDelete(int onetablesetIdx);
	public int onetablesetrecipeDelete(int onetablesetIdx);

}
