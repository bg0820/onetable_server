package glit.onetable.exception;

import glit.onetable.enums.ErrorCode;

public class CustomException extends Exception {

	private ErrorCode errorCode;
	
	public CustomException(ErrorCode errorCode)
	{
		this.errorCode = errorCode;
	}
	
	public ErrorCode getErrorCode() {
		// TODO Auto-generated method stub
		return this.errorCode;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		// TODO Auto-generated method stub
		return super.getStackTrace();
	}

}
