package glit.onetable.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

	@Autowired
	FavoritesMapper favoritesMapper;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchAll(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx,
			@RequestParam int favoritesType,
			@RequestParam int favoriteIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Favorites favorites = new Favorites();
		favorites.setFavoriteIdx(favoriteIdx);
		favorites.setUserIdx(userIdx);
		favorites.setFavoritesType(favoritesType);

		//같은게 있는지 select문으로 userIdx, favoriteIdx받아와서 비교하고 있으면 delete, 없으면 insert
		

		List<IngredientPriceAll> ipaList = ingredientMapper.searchAll(ipa);

		resResult.setData(ipaList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}
