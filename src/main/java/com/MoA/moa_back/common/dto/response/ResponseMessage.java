package com.MoA.moa_back.common.dto.response;

public interface ResponseMessage {
    String SUCCESS = "Success.";
  
    String VALIDATION_FAIL = "Validation Fail.";
    String EXIST_USER = "Exist User.";
    
    String NO_EXIST_BOARD = "No Exist Board.";
    String NO_EXIST_DAILY = "No Exist Daily.";
    String NO_EXIST_USED_TRADE = "No Exist Used Trade.";
  
    String SIGN_IN_FAIL = "Sign in Fail.";
  
    String NO_PERMISSION = "No Permission.";
  
    String DATABASE_ERROR = "Database Error";
  }