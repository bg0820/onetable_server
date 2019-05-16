package glit.onetable.controller;

import java.io.IOException;
import java.util.List;

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
import glit.onetable.mapper.RecipeMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Recipe;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	RecipeMapper recipeMapper;

	@RequestMapping(value = "/search/all", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchAll(@RequestHeader(value = "API_Version") String version,
			@RequestParam String startNum, @RequestParam String itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Recipe recipe = new Recipe();
		recipe.setLimitIndex(Integer.parseInt(startNum));
		recipe.setLimitCnt(Integer.parseInt(itemNum));

		List<Recipe> recipeList = recipeMapper.searchAll(recipe);

		resResult.setData(recipeList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestParam String query) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Recipe recipe = new Recipe();

		List<Recipe> recipeList = recipeMapper.search(query);

		resResult.setData(recipeList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> detail(@RequestHeader(value = "API_Version") String version,
			@RequestParam int recipeIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Recipe recipe = recipeMapper.detail(recipeIdx);

		resResult.setData(recipe);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}