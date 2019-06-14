package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.RecipeComment;

@Mapper
public interface RecipeCommentMapper {
	
		public List<RecipeComment> listAll(RecipeComment recipeCmt);
		public void commentInsert(RecipeComment recipeCmt);

}
