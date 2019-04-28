package glit.onetable.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IngredientMapper {
	
	public void search(String query);
	
}
