package com.MoA.moa_back.common.dto.response;

public interface ResponseMessage {
    String SUCCESS = "Success.";
  
    String VALIDATION_FAIL = "Validation Fail.";
    String EXIST_USER = "Exist User.";
    String VERIFY_CODE_ERROR = "Veryfy Code Error.";
    
    String NO_EXIST_BOARD = "No Exist Board.";
    String NO_EXIST_DAILY = "No Exist Daily.";
    String NO_EXIST_NOTICE = "No Exist Notice.";
    String NO_EXIST_USED_TRADE = "No Exist Used Trade.";
    String NO_EXIST_COMMENT = "No Exist Comment";
    
    String INVALID_TAG = "Invalid Tag.";
    String INVALID_PAGE_NUMBER = "Invalid Page Number.";
  
    String SIGN_IN_FAIL = "Sign in Fail.";

    String AUTHORIZATION_FAIL = "Authorization Fail.";
  
    String NO_PERMISSION = "No Permission.";
  
    String DATABASE_ERROR = "Database Error";

    String NO_EXIST_USER = "No Exist User";

    String TOKEN_TIME_OUT = "Token Time Out";

    String PASSWORD_NOT_RIGHT = "Password Not Right";
  }