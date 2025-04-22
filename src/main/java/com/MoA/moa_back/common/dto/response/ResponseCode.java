package com.MoA.moa_back.common.dto.response;

public interface ResponseCode {
    String SUCCESS = "SU";
  
    String VALIDATION_FAIL = "VF";
    String EXIST_USER = "EU";
    String VERIFY_CODE_ERROR = "VCE";

    String NO_EXIST_BOARD = "NB";
    String NO_EXIST_DAILY = "ND.";
    String NO_EXIST_NOTICE = "NN"; 
    String NO_EXIST_USED_TRADE = "NU.";
    String NO_EXIST_COMMENT = "NC";

    

    String INVALID_TAG = "IT";
    String INVALID_PAGE_NUMBER = "IP.";
    
    String SIGN_IN_FAIL = "SF";
  
    String NO_PERMISSION = "NP";
  
    String DATABASE_ERROR = "DBE";
    String OPEN_AI_ERROR = "OAE";

    String NO_EXIST_USER = "NEU";
    String TOKEN_TIME_OUT = "TTO";
  }