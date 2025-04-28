package com.MoA.moa_back.common.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseDto {
    private String code;
    private String message;
    private Object data; 

    // 기본 생성자
    protected ResponseDto() {
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
        this.data = null;
    }

    // code, message만 설정하는 생성자
    protected ResponseDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // 성공 응답 - 기본
    public static ResponseEntity<ResponseDto> success(HttpStatus status) {
        ResponseDto body = new ResponseDto();
        return ResponseEntity.status(status).body(body);
    }

    // 성공 응답 - data 포함
    public static ResponseEntity<ResponseDto> success(HttpStatus status, Object data) {
        ResponseDto body = new ResponseDto();
        body.data = data;
        return ResponseEntity.status(status).body(body);
    }

    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto body = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> existUserId() {
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> existUserNickname() {
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    public static ResponseEntity<ResponseDto> verifyCodeError(){
      ResponseDto body = new ResponseDto(ResponseCode.VERIFY_CODE_ERROR, ResponseMessage.VERIFY_CODE_ERROR);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> existUserEmail() {
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> existUserPhoneNumber() {
        ResponseDto body = new ResponseDto(ResponseCode.EXIST_USER, ResponseMessage.EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto body = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    public static ResponseEntity<ResponseDto> authorizationFail(String message){
        ResponseDto body = new ResponseDto(ResponseCode.AUTHORIZATION_FAIL, message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistBoard() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_BOARD, ResponseMessage.NO_EXIST_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistDaily() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_DAILY, ResponseMessage.NO_EXIST_DAILY);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistUsedTrade() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_USED_TRADE, ResponseMessage.NO_EXIST_USED_TRADE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistComment() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_COMMENT, ResponseMessage.NO_EXIST_COMMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> invalidTag() {
        ResponseDto body = new ResponseDto(ResponseCode.INVALID_TAG, ResponseMessage.INVALID_TAG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> invalidPageNumber() {
        ResponseDto body = new ResponseDto(ResponseCode.INVALID_PAGE_NUMBER, ResponseMessage.INVALID_PAGE_NUMBER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
    
    public static ResponseEntity<ResponseDto> tokenTimeOut(){
      ResponseDto body = new ResponseDto(ResponseCode.TOKEN_TIME_OUT, ResponseMessage.TOKEN_TIME_OUT);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto body = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistUser() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_USER, ResponseMessage.NO_EXIST_USER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    public static ResponseEntity<ResponseDto> noExistNotice() {
        ResponseDto body = new ResponseDto(ResponseCode.NO_EXIST_NOTICE, ResponseMessage.NO_EXIST_NOTICE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    public static ResponseEntity<ResponseDto> passwordNotRight() {
        ResponseDto body = new ResponseDto(ResponseCode.PASSWORD_NOT_RIGHT, ResponseMessage.PASSWORD_NOT_RIGHT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}