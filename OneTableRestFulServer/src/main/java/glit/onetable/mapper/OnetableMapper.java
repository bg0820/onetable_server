package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Onetableset;
import glit.onetable.model.vo.Onetablesetrecipe;
import glit.onetable.model.vo.Recipe;

@Mapper
public interface OnetableMapper {
	public List<Recipe> searchRecipe(String query);
	public void onetablesetInsert(Onetableset onetableset);
	public void onetablerecipeInsert(Onetablesetrecipe onetablerecipe);
	public List<Onetableset> history(int userIdx);
	public void onetablesetDelete(int onetablesetIdx);
	public int onetablesetrecipeDelete(int onetablesetIdx);

}
