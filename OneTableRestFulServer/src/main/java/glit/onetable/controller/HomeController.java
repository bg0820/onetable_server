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
import org.springframework.web.bind.annotation.RestController;
import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.HomeMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Home;
import glit.onetable.model.vo.RecipeUserPrice;

@CrossOrigin
@RestController
@RequestMapping("/home")
public class HomeController {
	@Autowired
	HomeMapper homeMapper;

	@RequestMapping(value = "/popular/recipe", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> popular(@RequestHeader(value = "API_Version") String version) throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		List<RecipeUserPrice> recipeList1 = homeMapper.popularRecipe();
		List<RecipeUserPrice> recipeList2 = homeMapper.countRecipe();
		List<RecipeUserPrice> recipeList3 = homeMapper.recentRecipe();
		
		Home home = new Home();
		home.setPopular(recipeList1);
		home.setCount(recipeList2);
		home.setRecent(recipeList3);


		resResult.setData(home);

		return new ResponseEntity<ApiResponseResult>(resResult, hs);
	}

}
