package glit.onetable.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import glit.onetable.enums.ErrorCode;
import glit.onetable.exception.CustomException;
import glit.onetable.mapper.RankMapper;
import glit.onetable.model.ApiResponseResult;
import glit.onetable.model.vo.Rank;

@RestController
@RequestMapping("/rank")
public class RankController {

	@Autowired
	RankMapper RankMapper;

	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public ResponseEntity<ApiResponseResult> rank(@RequestHeader(value = "API_Version") String version)
			throws CustomException, IOException {
		ApiResponseResult<Object> resResult = new ApiResponseResult<Object>(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.OK;

		if (!version.equals("1.0"))
			throw new CustomException(ErrorCode.API_VERSION_INVAILD);

		
		
		List<Rank> rankListTeen = RankMapper.searchTeen();
		List<Rank> rankListTwnt = RankMapper.searchTwnt();
		List<Rank> rankListThree = RankMapper.searchThree();
		List<Rank> rankListFour = RankMapper.searchFour();
		List<Rank> rankListFive = RankMapper.searchFive();

		List<Rank> rankListRecipe = RankMapper.rankRecipe();
		List<Rank> rankListChief = RankMapper.rankChief();

		return new ResponseEntity<ApiResponseResult>(resResult, hs);

	}

}
