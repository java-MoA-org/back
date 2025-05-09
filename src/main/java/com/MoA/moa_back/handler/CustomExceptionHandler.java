    package com.MoA.moa_back.handler;

    import org.springframework.http.ResponseEntity;
    import org.springframework.http.converter.HttpMessageNotReadableException;
    import org.springframework.web.bind.MethodArgumentNotValidException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import org.springframework.web.bind.annotation.RestControllerAdvice;

    import com.MoA.moa_back.common.dto.response.ResponseDto;

    @RestControllerAdvice
    public class CustomExceptionHandler {
        @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class
        })
        public ResponseEntity<ResponseDto> validExceptionHandler(Exception e){
            e.printStackTrace();
            return ResponseDto.validationFail();
        }


        @ExceptionHandler(CustomJwtException.class)
        public ResponseEntity<ResponseDto> jwtExceptionHandler(CustomJwtException e) {
            e.printStackTrace();

            switch (e.getErrorCode()) {
                case EXPIRED:
                    return ResponseDto.authorizationFail("토큰이 만료되었습니다.");
                case INVALID:
                    return ResponseDto.authorizationFail("유효하지 않은 토큰입니다.");
                default:
                    return ResponseDto.authorizationFail("인증에 실패했습니다.");
            }
        }
    }
