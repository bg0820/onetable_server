package glit.onetable.exception;

import java.sql.SQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import glit.onetable.enums.ErrorCode;
import glit.onetable.model.ApiResponseResult;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ApiResponseResult> handleSQLError(DataAccessException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
		
		if(e instanceof DuplicateKeyException)
		{
			responseResult.setErrorCode(ErrorCode.CREATE_DUPLICATE);
		}
		
		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseResult> handleCustomError(CustomException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;

		switch(e.getErrorCode())
		{
			case API_VERSION_INVAILD:
				responseResult.setErrorCode(ErrorCode.API_VERSION_INVAILD);
				responseResult.setMsg("API_VERSION 헤더가 맞지 않습니다.");
				hs = HttpStatus.PRECONDITION_FAILED;
				break;
			case LOGIN_FAILED:
				responseResult.setErrorCode(ErrorCode.LOGIN_FAILED);
				responseResult.setMsg("아이디 또는 비밀번호가 잘못 되었습니다.");
				hs = HttpStatus.CONFLICT;
				break;
		
		}
				
		
		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}
	
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ApiResponseResult> handleMissingRequestHeaderError(MissingRequestHeaderException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.PRECONDITION_FAILED;
		responseResult.setErrorCode(ErrorCode.HEADER_NON_VALUE);
		responseResult.setMsg("헤더를 입력해주세요.");
		
		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}
}
