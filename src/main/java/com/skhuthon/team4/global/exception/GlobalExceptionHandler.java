package com.skhuthon.team4.global.exception;

import com.skhuthon.team4.global.common.ApiResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseTemplate<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("BusinessException: {}", errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponseTemplate.error(
                        errorCode.getHttpStatus().value(),
                        errorCode.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseTemplate<Void>> handleValidException(
            MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors().get(0)
                .getDefaultMessage();
        return ResponseEntity
                .badRequest()
                .body(ApiResponseTemplate.error(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseTemplate<Void>> handleException(Exception e) {
        log.error("Unexpected error: ", e);
        return ResponseEntity
                .internalServerError()
                .body(ApiResponseTemplate.error(500, "서버 오류가 발생했습니다."));
    }
}