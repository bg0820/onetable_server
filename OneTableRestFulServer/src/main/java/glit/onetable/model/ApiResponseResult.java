package glit.onetable.model;

import glit.onetable.enums.ErrorCode;
import glit.onetable.enums.ResponseStatus;

public class ApiResponseResult<T> {
	
	private ResponseStatus status;
	private ErrorCode errorCode;
	private String msg;
	private T data;
	
	public ApiResponseResult(ErrorCode errorCode, String msg, T data)
	{
		this.status = ResponseStatus.SUCCESS;
		
		if(errorCode != ErrorCode.SUCCESS)
			this.status = ResponseStatus.FAILED;
		
		this.errorCode = errorCode;
		this.msg = msg;
		this.data = data;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
		
		if(errorCode != ErrorCode.SUCCESS)
			this.status = ResponseStatus.FAILED;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
