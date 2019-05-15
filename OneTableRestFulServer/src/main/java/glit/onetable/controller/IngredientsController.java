package glit.onetable.controller;

import java.io.IOException;
import java.util.ArrayList;
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

@RestController
@RequestMapping("/ingredient")
public class IngredientsController {

	@Autowired
	IngredientMapper ingredientMapper;

	@RequestMapping(value = "/search/all", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> searchAll(@RequestHeader(value = "API_Version") String version,
			@RequestParam String startNum,
			@RequestParam String itemNum) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		IngredientPriceAll ipa = new IngredientPriceAll();
		ipa.setLimitIndex(Integer.parseInt(startNum));
		ipa.setLimitCnt(Integer.parseInt(itemNum));

		List<IngredientPriceAll> ipaList = ingredientMapper.searchAll(ipa);

		resResult.setData(ipaList);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(@RequestHeader(value = "API_Version") String version,
			@RequestParam String query) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);


		IngredientSubject is = new IngredientSubject();
		is.setVariety(query);
		// 품종 없을때 품종 삽입
		ingredientMapper.varietyNotExistsInsert(is);
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

		List<IngredientPriceAll> ingredientPriceAll = ingredientMapper.search(query);

		resResult.setData(ingredientPriceAll);

		//ArrayList<IngredientSubject>>


		/*
		Changer ic = new Changer();

		ArrayList<Ingredient> resultList = new ArrayList<Ingredient>();

		//사용자가 입력하면 ssg에 검색
		SSGCrawler sc = new SSGCrawler();
		int recordCnt = sc.getRecordCnt(query);
		int maxPageCnt = (int) Math.ceil(recordCnt
				/ 80.0);

		if (recordCnt == 0) {
			System.out.println(query + " : 조회 결과 없음");
			//return;
		}
		ArrayList<AnalyzeVariety> avList = new ArrayList<AnalyzeVariety>();
		ArrayList<IngredientPrice> inPrice = new ArrayList<IngredientPrice>();

		//query ssg에 검색
		for(int i=0; i< maxPageCnt; i++) {
			avList = sc.crawler(query, i);
		}
		//db에 사용자가 찾는게 있는지 검색
		List<IngredientSubject> is = ingredientMapper.search(query);


		//db에 사용자가 검색한 재료가 없을때
		if (is.size() == 0) {
			//ingredient_subject 테이블에 재료 삽입
			ingredientMapper.subjectInsert(query);

			ArrayList<Ingredient> inList = ic.ingredientChanger(avList);
			inPrice = ic.priceChanger(avList);

			// db에 없으면 ssg.com에 다시 검색하고 db에 저장하는 함수 호출, 다시 search하는 문장
		}

		// 검색한거랑 db에 있는거랑 비교하고 다르면 검색한거 db에 저장하는 함수 호출
		else {
			// System.out.println(is.size());
			for (int i = 0; i < is.size(); i++) {
				IngredientSubject item = is.get(i);

				List<Ingredient> ingredientList = ingredientMapper.ingredientQuery(item.getIngredientSubjectIdx());
				for (int j = 0; j < ingredientList.size(); j++)
					resultList.add(ingredientList.get(j));

			}
		}

		resResult.setData(resultList);
*/
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
