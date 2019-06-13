package glit.onetable.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.FavoritesMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Favorites;
import glit.onetable.model.vo.IngredientPriceAll;
import glit.onetable.model.vo.Recipe;
@CrossOrigin
@RestController
@RequestMapping("/favorites")
public class FavoritesController {

	@Autowired
	FavoritesMapper favoritesMapper;

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> delete(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx, @RequestParam int favoriteIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		System.out.println(favoriteIdx);
		System.out.println(userIdx);
		Favorites favorites = new Favorites();
		favorites.setFavoriteIdx(favoriteIdx);
		favorites.setUserIdx(userIdx);

		int success = favoritesMapper.favoritesDelete(favorites);

		resResult.setData(success);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> register(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx, @RequestParam int favoritesType, @RequestParam int favoriteIdx)
			throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Favorites favorites = new Favorites();
		favorites.setFavoriteIdx(favoriteIdx);
		favorites.setUserIdx(userIdx);
		favorites.setFavoritesType(favoritesType);

		int success = favoritesMapper.favoritesInsert(favorites);

		resResult.setData(1);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx, @RequestParam int favoritesType) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Favorites favorites = new Favorites();
		favorites.setUserIdx(userIdx);
		favorites.setFavoritesType(favoritesType);

		//레시피 : 1, 재료 : 2
		if (favoritesType == 1) {
			List<Integer> recipeIdxList = favoritesMapper.myRecipe(favorites);

			if (recipeIdxList != null) {
				List<Recipe> recipeList = favoritesMapper.myRecipeFavorites(recipeIdxList);
				resResult.setData(recipeList);
			}

		} else {
			List<Integer> ingredientIdxList = favoritesMapper.myIngredient(favorites);

			if (ingredientIdxList != null) {
				List<IngredientPriceAll> ipaList = favoritesMapper.myIngredientFavorites(ingredientIdxList);

				resResult.setData(ipaList);
			}
		}

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}
