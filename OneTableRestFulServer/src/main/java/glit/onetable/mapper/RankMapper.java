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

	/*
	 * 
<!-- 
	<select id="searchTeen" resultType="Rank">
		SELECT favoriteIdx,
		count(favoriteIdx) as count FROM
		onetable.favorites where userIdx in
		(select userIdx from user where
		birthday >= 10 and birthday < 20) and
		favoritesType = 1 group by
		favoriteIdx order by count;
	</select>

	<select id="searchTwnt" resultType="Rank">
		SELECT favoriteIdx,
		count(favoriteIdx) as count FROM onetable.favorites where userIdx in
		(select userIdx from user where birthday >= 20 and birthday < 30) and
		favoritesType = 1 group by favoriteIdx order by count;
	</select>

	<select id="searchThree" resultType="Rank">
		SELECT favoriteIdx,
		count(favoriteIdx) as count FROM onetable.favorites where userIdx in
		(select userIdx from user where birthday >= 30 and birthday < 40) and
		favoritesType = 1 group by favoriteIdx order by count;
	</select>

	<select id="searchFour" resultType="Rank">
		SELECT favoriteIdx,
		count(favoriteIdx) as count FROM onetable.favorites
		where userIdx in
		(select userIdx from user where birthday >= 40 and birthday < 50) and
		favoritesType = 1 group by favoriteIdx order by count;
	</select>

	<select id="searchFive" resultType="Rank">
		SELECT favoriteIdx,
		count(favoriteIdx) as count FROM onetable.favorites where userIdx in
		(select userIdx from user where birthday >= 50 and birthday < 60) and
		favoritesType = 1 group by favoriteIdx order by count;
	</select>

	<select id="rankRecipe" resultType="Ranking">
		select userIdx,
		AVG(avgGradePoint) from recipe_avg_grade GROUP BY recipeIdx
	</select>

	<select id="rakeChief" resultType="Ranking">

	</select>
	 -->

	 */
}