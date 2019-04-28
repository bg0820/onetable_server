package glit.onetable.controller;

import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import glit.onetable.enums.ErrorCode;
import glit.onetable.model.ApiResponseResult;

@RestController
@RequestMapping("/s")
public class MainRouterController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ApiResponseResult index() {
		return new ApiResponseResult(ErrorCode.SUCCESS, "", null);
	}
}
