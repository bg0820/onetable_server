package glit.onetable.mapper;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Evaluate;

@Mapper
public interface EvaluateMapper {

	public void evaluateInsert(Evaluate eval);

}
