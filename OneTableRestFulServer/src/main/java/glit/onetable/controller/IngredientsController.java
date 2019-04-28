package glit.onetable.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.User;
import glit.onetable.util.Util;

@RestController
@RequestMapping("/ingredient")
public class IngredientsController {
	
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> search() {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;
		
		

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

	
}
