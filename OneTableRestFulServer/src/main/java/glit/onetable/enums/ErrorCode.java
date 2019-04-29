package glit.onetable.enums;

public enum ErrorCode {
    SUCCESS, // 정상 처리
    UNAUTHORIZED, // 인증이 제대로 되지 않음
    UNTOKENIZED, //Token 맞지 않음
    API_VERSION_INVAILD, // API_VERSION 틀림
    CREATE_DUPLICATE,
    LOGIN_FAILED,
    HEADER_NON_VALUE,
    NON_REGISTERED,
    UNKNOWN,
    EMAIL_TYPE_INVALID,
    PARAMETER_NOT_INVALID // 파라미터 없음
}
