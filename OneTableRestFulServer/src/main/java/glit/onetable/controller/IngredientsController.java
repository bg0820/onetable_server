package glit.onetable.controller;

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
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Ingredient;
import glit.onetable.model.vo.IngredientsSubject;

@RestController
@RequestMapping("/ingredient")
public class IngredientsController {

	@Autowired
	IngredientMapper ingredientMapper;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search(
			@RequestHeader(value = "API_Version") String version,
			@RequestParam String query) throws CustomException {
		ApiResponseResult<Object> resResult =
				new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		ArrayList<Ingredient> resultList = new ArrayList<Ingredient>();
		
		List<IngredientsSubject> is = ingredientMapper.search(query);
		System.out.println(is.size());
		for (int i = 0; i < is.size(); i++) {
			IngredientsSubject item = is.get(i);
		
			List<Ingredient> ingredientList  = ingredientMapper.ingredientQuery(item.getIngredientsSubjectUUID());
			for(int j = 0  ; j < ingredientList.size(); j++)
				resultList.add(ingredientList.get(j));
			
		}
		
		
		resResult.setData(resultList);
		
		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}


}
