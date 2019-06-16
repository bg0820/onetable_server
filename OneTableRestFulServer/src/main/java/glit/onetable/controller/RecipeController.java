package glit.onetable.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.RecipeCommentMapper;
import glit.onetable.mapper.RecipeMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.request.RecipeDetail;
import glit.onetable.model.request.RecipeDetailIngredient;
import glit.onetable.model.request.ResultIncCnt;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.Recipe;
import glit.onetable.model.vo.RecipeComment;
import glit.onetable.model.vo.RecipeIngredient;
import glit.onetable.model.vo.RecipeMethod;
import glit.onetable.model.vo.Search;
import glit.onetable.model.vo.Unit;
import glit.onetable.model.vo.User;
@CrossOrigin




@RestController
@RequestMapping("/recipe")
public class RecipeController {

	@Autowired
	RecipeMapper recipeMapper;
	@Autowired
	RecipeCommentMapper recipeCommentMapper;

	@RequestMapping(value = "/search/all", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchAll(@RequestHeader(value = "API_Version") String version,
			@RequestParam int page, @RequestParam int itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		ResultIncCnt ric = new ResultIncCnt();

		// 1, 80 => 0, 80
		// 2, 80 => 80, 80
		int pageIndex = (page - 1) * itemNum;

		Recipe recipe = new Recipe();
		recipe.setLimitIndex(pageIndex);
		recipe.setLimitCnt(itemNum);

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
			@RequestParam int itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Recipe recipe = new Recipe();
		ResultIncCnt ric = new ResultIncCnt();

		// 1, 80 => 0, 80
		// 2, 80 => 80, 80
		int pageIndex = (page - 1) * itemNum;


		Search search = new Search();
		search.setQuery(query);
		search.setItemNum(itemNum);
		search.setStartNum(pageIndex);

		int searchQueryCnt = recipeMapper.searchCnt(query);
		List<Recipe> recipeList = recipeMapper.search(search);

		ric.setCnt(searchQueryCnt);
		ric.setObj(recipeList);

		resResult.setData(ric);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> history(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<Recipe> recipeList = recipeMapper.history(userIdx);
		resResult.setData(recipeList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestBody Recipe recipe)
			throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);


		int recipeIdx = recipeMapper.insertRecipe(recipe);

		System.out.println(recipeIdx);

		for(int i=0; i<recipe.getRecipeIngredient().size(); i++) {
			recipeMapper.insertRecipeIngrdient(recipe.getRecipeIngredient().get(i));
		}

		for(int i=0; i<recipe.getRecipeMethod().size(); i++) {
			recipeMapper.insertRecipeMethod(recipe.getRecipeMethod().get(i));
		}

		resResult.setData(recipe);


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

		ArrayList<RecipeMethod> recipeMethod = recipeMapper.getMethod(recipeIdx);
		resultData.setRecipeMethod(recipeMethod);

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
			double recipeIngredientPrice = item.getMinAmount() * pricePerUnit;

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

    @RequestMapping(value = "/image/{imageName}.{extension}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(
        @PathVariable(name = "imageName") String imageName,
        @PathVariable(name = "extension", required = false) String extension,
        HttpServletRequest request) throws IOException {

        InputStream imageStream = new FileInputStream("uploads/" + imageName + "." + extension);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public ResponseEntity<ApiResponseResult> register(@RequestHeader(value = "API_Version") String version,
			MultipartFile multipartFile) throws CustomException, IOException, FileNotFoundException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		if(multipartFile.isEmpty())
			throw new CustomException(ErrorCode.FILE_NOT_UPLOADED);

		byte[] bytes = multipartFile.getBytes();

		File fi = new File("uploads/" + new Timestamp(System.currentTimeMillis()).getTime() + "_" + multipartFile.getOriginalFilename());

		FileUtils.writeByteArrayToFile(fi, bytes);

		System.out.println(fi.getAbsolutePath());
		System.out.println(fi.getName());
		resResult.setData("/recipe/image/" + fi.getName());


		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/unit", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> unit(@RequestHeader(value = "API_Version") String version) throws CustomException, IOException, FileNotFoundException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<Unit> unitList = recipeMapper.getUnit();

		resResult.setData(unitList);


		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> comment(@RequestHeader(value = "API_Version") String version,
			@RequestParam int recipeIdx, @RequestParam int userIdx, @RequestParam String comment)
			throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		RecipeComment recipeCmt = new RecipeComment();
		recipeCmt.setRecipeIdx(recipeIdx);
		recipeCmt.setUserIdx(userIdx);
		recipeCmt.setComment(comment);

		recipeCommentMapper.commentInsert(recipeCmt);

		List<RecipeComment> recipe = recipeCommentMapper.listAll(recipeCmt);
		resResult.setData(recipe);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}