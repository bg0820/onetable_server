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
import glit.onetable.mapper.RecipeMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.request.RecipeDetail;
import glit.onetable.model.request.RecipeDetailIngredient;
import glit.onetable.model.request.ResultIncCnt;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.Recipe;
import glit.onetable.model.vo.RecipeIngredient;
import glit.onetable.model.vo.Unit;
import glit.onetable.model.vo.User;
@CrossOrigin
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


		ResultIncCnt ric = new ResultIncCnt();

		Recipe recipe = new Recipe();
		recipe.setLimitIndex(Integer.parseInt(startNum));
		recipe.setLimitCnt(Integer.parseInt(itemNum));

		int allQueryCnt = recipeMapper.allCnt();
		List<Recipe> recipeList = recipeMapper.searchAll(recipe);

		ric.setCnt(allQueryCnt);
		ric.setObj(recipeList);
		
		
		resResult.setData(ric);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestParam String query, @RequestParam int page,
			@RequestParam int itemCnt) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Recipe recipe = new Recipe();
		ResultIncCnt ric = new ResultIncCnt();
		
		int searchQueryCnt = recipeMapper.searchCnt(query);
		List<Recipe> recipeList = recipeMapper.search(query);
		
		ric.setCnt(searchQueryCnt); 
		ric.setObj(recipeList);
		
		resResult.setData(ric);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> detail(@RequestHeader(value = "API_Version") String version,
			@RequestParam int recipeIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);
		
		RecipeIngredient recipeIngredient = new RecipeIngredient();
		recipeIngredient.setRecipeIdx(recipeIdx);
		
		Recipe recipe = recipeMapper.detail(recipeIdx);
		
		RecipeDetail resultData = new RecipeDetail();
		resultData.setCookTimeMin(recipe.getCookTimeMin());
		resultData.setKcal(recipe.getKcal());
		resultData.setRecipeImg(recipe.getRecipeImg());
		resultData.setRecipeName(recipe.getName());
		resultData.setServingMax(recipe.getServingMax());
		resultData.setServingMin(recipe.getServingMin());
		resultData.setPrice(recipe.getPrice());
		resultData.setContentHtml(recipe.getContentHtml());
		
		User user = recipeMapper.getUserInfo(recipe.getUserIdx());
		resultData.setUserName(user.getNickname());
		
		
		List<RecipeIngredient> recipeIngreList = recipeMapper.recipeIngredientToRecipeIdx(recipeIdx);
		int totalPrice = 0;
		
		for(int i = 0; i < recipeIngreList.size(); i++)
		{
			RecipeIngredient item = recipeIngreList.get(i);
			
			IngredientPrice ingredientPrice = recipeMapper.ingredientCurrentDayPrice(item.getIngredientItemId());
			if(ingredientPrice == null)
				continue;
			// 재료에 대한 유닛당 가격
			Ingredient ingredientUnit = recipeMapper.getIngredientToItemId(item.getIngredientItemId());
			if(ingredientUnit == null)
				continue;
			double pricePerUnit = ingredientPrice.getPrice() / ingredientUnit.getUnitAmount();
			// 레시피 재료에 대한 유닛 조회
			Unit unit = recipeMapper.getUnitName(item.getUnitIdx());
			double recipeIngredientPrice = (double)item.getMinAmount() * pricePerUnit;

			RecipeDetailIngredient rdi = new RecipeDetailIngredient();
			rdi.setName(item.getDisplayName());
			rdi.setCalcPrice(recipeIngredientPrice);
			rdi.setUnitStr(unit.getUnitName());
			rdi.setUnitDisplayName(item.getDisplayAmount());
			
			
			totalPrice += recipeIngredientPrice;
			
			if(item.getResult() != 0.0)
				rdi.setUnitAmount(item.getResult());
			else
				rdi.setUnitAmount(item.getMinAmount());
			
			resultData.recipeIngredient.add(rdi);
		}

		resResult.setData(resultData);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}