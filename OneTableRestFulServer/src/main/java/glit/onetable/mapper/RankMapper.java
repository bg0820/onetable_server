package glit.onetable.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import glit.onetable.model.vo.Rank;

@Mapper
public interface RankMapper {
	public List<Rank> searchTeen();
	public List<Rank> searchTwnt();
	public List<Rank> searchThree();
	public List<Rank> searchFour();
	public List<Rank> searchFive();
	
	public List<Rank> rankRecipe();
	
	public List<Rank> rankChief();

}