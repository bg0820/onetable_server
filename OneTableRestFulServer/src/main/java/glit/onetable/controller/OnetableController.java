package glit.onetable.controller;

import java.io.IOException;

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
import glit.onetable.mapper.OnetableMapper;
import glit.onetable.model.ApiResponseResult;
@CrossOrigin
@RestController
@RequestMapping("/onetable")
public class OnetableController {

	@Autowired
	OnetableMapper onetableMapper;

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> delete(@RequestHeader(value = "API_Version") String version,
			@RequestParam int userIdx, @RequestParam String name) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);



		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

}
