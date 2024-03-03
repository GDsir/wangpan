package com.hjk.wangpan.exception;


//import org.junit.platform.commons.logging.Logger;

import com.hjk.wangpan.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 基础异常
     */
//    @ExceptionHandler(BaseException.class)
//    public R baseException(BaseException e) {
//        log.warn(e.getMessage(), e);
//        return R.error(e.getMessage());
//    }
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
