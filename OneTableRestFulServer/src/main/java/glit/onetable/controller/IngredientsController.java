package glit.onetable.controller;

import java.io.IOException;

import java.util.ArrayList;
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
import glit.onetable.mapper.IngredientMapper;
import glit.onetable.model.AnalyzeUnit;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.HangleAnalyze;
import glit.onetable.model.IngredientModel;
import glit.onetable.model.SSGCrawler;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientPrice;
import glit.onetable.model.vo.IngredientPriceAll;
import glit.onetable.model.vo.IngredientSubject;
import glit.onetable.model.vo.Search;
@CrossOrigin
@RestController
@RequestMapping("/ingredient")
public class IngredientsController {

	@Autowired
	IngredientMapper ingredientMapper;

	@RequestMapping(value = "/search/all", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchAll(@RequestHeader(value = "API_Version") String version,
			@RequestParam int page,
			@RequestParam int itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		// 1, 80 => 0, 80
		// 2, 80 => 80, 80
		int pageIndex = (page - 1) * itemNum;
		
		IngredientPriceAll ipa = new IngredientPriceAll();
		ipa.setLimitIndex(pageIndex);
		ipa.setLimitCnt(itemNum);

		List<IngredientPriceAll> ipaList = ingredientMapper.searchAll(ipa);

		resResult.setData(ipaList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestParam String query,
			@RequestParam int page,
			@RequestParam int itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

/*
		IngredientSubject is = new IngredientSubject();
		is.setVariety(query);
		// 품종 없을때 품종 삽입
		int success = ingredientMapper.varietyNotExistsInsert(is);

		if(success != 0)
		{
			// 품종 삽입후 품종 아이디 가져오기
			int ingredientSubjectIdx =  ingredientMapper.getIngredientSubjectIdx(query);

			is.setIngredientSubjectIdx(ingredientSubjectIdx);

			SSGCrawler ssgCrawler = new SSGCrawler();
			ArrayList<IngredientModel> modelIngredient = ssgCrawler.getItemList(is, 1);

			if(modelIngredient != null)
			{
				for(int i = 0 ; i < modelIngredient.size(); i++)
				{
					IngredientModel im = modelIngredient.get(i);
					AnalyzeUnit analyzeUnit = HangleAnalyze.getInstance()
							.analyze(im.getDisplayName());

					if(analyzeUnit !=null)
					{
						modelIngredient.get(i).setAnayUnit(analyzeUnit);

						int unitIdx = ingredientMapper.unitSearch(analyzeUnit.getUnitStr());

						Ingredient in = new Ingredient();
						in.setDisplayName(im.getDisplayName());
						in.setIngredientItemId(im.getIngredientItemId());
						in.setImgUrl(im.getImgUrl());
						in.setIngredientSubjectIdx(ingredientSubjectIdx);
						in.setUnitIdx(unitIdx);
						in.setUnitAmount(analyzeUnit.getUnitAmount());

						int ingredientIdx = ingredientMapper.ingredientNotExistsInsert(in);
						IngredientPrice ingredientPrice = new IngredientPrice();

						ingredientPrice.setIngredientItemId(im.getIngredientItemId());
						ingredientPrice.setPrice(im.getPrice());

						int ingredientPriceIdx = ingredientMapper.insertIngredientPrice(ingredientPrice);
					}
				}
			}

		}
		*/
		
		// 1, 80 => 0, 80
		// 2, 80 => 80, 80
		int pageIndex = (page - 1) * itemNum;

		Search search = new Search();
		search.setQuery(query);
		search.setStartNum(pageIndex);
		search.setItemNum(itemNum);
		List<IngredientPriceAll> ingredientPriceAll = ingredientMapper.search(search);

		resResult.setData(ingredientPriceAll);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/price", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> priceHistroy(@RequestHeader(value = "API_Version") String version,
			@RequestParam String ingredientItemId) throws CustomException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<IngredientPrice> resultList = ingredientMapper.priceHistroy(ingredientItemId);
		if (resultList.size() == 0)
			throw new CustomException(ErrorCode.NOT_FOUND_INGREDIENT);

		resResult.setData(resultList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

}
