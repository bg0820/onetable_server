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
import glit.onetable.mapper.EvaluateMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Evaluate;


@RestController
@RequestMapping("/evaluate")
public class EvaluateController {

	@Autowired
	EvaluateMapper EvaluateMapper;

	@RequestMapping(value = "/evaluate", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> evaluate(@RequestHeader(value = "API_Version") String version,
			@RequestParam double gradepoint, @RequestParam int userIdx, @RequestParam int recipeIdx) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		Evaluate eval = new Evaluate();
		eval.setUserIdx(userIdx);
		eval.setRecipeIdx(recipeIdx);
		eval.setGradePoint(gradepoint);
		
		EvaluateMapper.evaluateInsert(eval);
		
		resResult.setData(eval);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}
}