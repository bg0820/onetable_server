package glit.onetable.exception;

import java.sql.SQLException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import glit.onetable.enums.ErrorCode;
import glit.onetable.model.ApiResponseResult;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponseResult> handleViolationError(ConstraintViolationException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
		responseResult.setErrorCode(ErrorCode.EMAIL_TYPE_INVALID);

		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ApiResponseResult> handleParameterError(MissingServletRequestParameterException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.PRECONDITION_FAILED;
		responseResult.setErrorCode(ErrorCode.PARAMETER_NOT_INVALID);
		responseResult.setMsg(e.getParameterName() + " 파라미터 값이 없습니다.");

		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ApiResponseResult> handleSQLError(DataAccessException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;

		if(e instanceof DuplicateKeyException)
		{
			responseResult.setErrorCode(ErrorCode.CREATE_DUPLICATE);
			responseResult.setMsg("이미 존재하는 사용자 또는 값 입니다.");
		}

		return new ResponseEntity<ApiResponseResult>(responseResult, hs);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseResult> handleCustomError(CustomException e)
	{
		ApiResponseResult responseResult = new ApiResponseResult(ErrorCode.SUCCESS, "", null);
		HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
		responseResult.setErrorCode(e.getErrorCode());

		switch(e.getErrorCode())
		{
			case API_VERSION_INVAILD:
				responseResult.setMsg("API_VERSION 헤더가 맞지 않습니다.");
				hs = HttpStatus.PRECONDITION_FAILED;
				break;
			case LOGIN_FAILED:
				responseResult.setMsg("아이디 또는 비밀번호가 잘못 되었습니다.");
				hs = HttpStatus.CONFLICT;
				break;
			case NON_REGISTERED:
				responseResult.setMsg("가입되어 있지 않은 사용자 입니다.");
				hs = HttpStatus.CONFLICT;
				break;
			case NOT_FOUND_INGREDIENT:
				responseResult.setMsg("존재하지 않는 재료입니다.");
				hs = HttpStatus.INTERNAL_SERVER_ERROR;
				break;
			default :
				responseResult.setErrorCode(ErrorCode.UNKNOWN);
				responseResult.setMsg(e.getMessage());
				hs = HttpStatus.INTERNAL_SERVER_ERROR;
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
