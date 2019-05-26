package glit.onetable.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.OnetableMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.request.Onetable;
import glit.onetable.model.vo.Onetableset;
import glit.onetable.model.vo.Onetablesetrecipe;
import glit.onetable.model.vo.Recipe;
@CrossOrigin
@RestController
@RequestMapping("/onetable")
public class OnetableController {

	@Autowired
	OnetableMapper onetableMapper;

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> register(@RequestHeader(value = "API_Version") String version,
			@RequestBody Onetable parmOnetable) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Onetableset onetable = new Onetableset();
		onetable.setUserIdx(parmOnetable.getUserIdx());
		onetable.setName(parmOnetable.getTitle());
		onetable.setClassify(parmOnetable.getType());

		onetableMapper.onetablesetInsert(onetable);
		int onetablesetIdx = onetable.getOnetablesetIdx();
		//int onetablesetIdx = onetableMapper.onetablesetInsert(onetable);

		for(int i=0; i < parmOnetable.getRecipes().size(); i++) {
			Onetablesetrecipe onerecipe = new Onetablesetrecipe();
			onerecipe.setOnetablesetIdx(onetablesetIdx);
			onerecipe.setRecipeIdx(parmOnetable.getRecipes().get(i));
			//System.out.println(onerecipe.getRecipeIdx());
			onetableMapper.onetablerecipeInsert(onerecipe);
		}

		resResult.setData(onetable);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

	@RequestMapping(value = "/search/recipe", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchRecipe(@RequestHeader(value = "API_Version") String version,
			@RequestParam String query) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<Recipe> recipeList = onetableMapper.searchRecipe(query);
		resResult.setData(recipeList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> history(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<Onetableset> onetableList = onetableMapper.history(userIdx);
		resResult.setData(onetableList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> delete(@RequestHeader(value = "API_Version") String version,
			@RequestParam int onetablesetIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		int success = onetableMapper.onetablesetrecipeDelete(onetablesetIdx);
		onetableMapper.onetablesetDelete(onetablesetIdx);


		resResult.setData(success);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

}
