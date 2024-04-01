package com.hjk.wangpan.exception;


import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 基础异常
     */

    @ExceptionHandler(BaseException.class)
    public Map<String, Object> baseException(BaseException e) {
        log.warn(e.getMessage(), e);
        return StatusCode.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Map<String, Object> businessException(BusinessException e) {
        log.warn(e.getMessage(), e);
        if (e.getCode() == null) {
            return StatusCode.error(e.getMessage());
        }
        return StatusCode.error(e.getCode(), e.getMessage());
    }
}
