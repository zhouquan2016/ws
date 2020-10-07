package com.zhqn.chat.config;

import com.zhqn.chat.dto.ErrorDto;
import com.zhqn.chat.ex.BaseServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalControllerAdvise {

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        }
        return false;
    }

    private ResponseEntity<ErrorDto> buildResponseEntity (int code, Object msg) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(code);
        errorDto.setMsg(msg);
        return ResponseEntity.status(code).body(errorDto);
    }



    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        int code = 601;
        String msg = "系统异常";
        log.error(msg, e);
        return buildResponseEntity(code, msg);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleBaseServiceException(BaseServiceException baseServiceException) {
        int code = 602;
        String msg = baseServiceException.getMessage();
        log.info("业务异常code:{},msg:{}", code, msg);
        return buildResponseEntity(code, msg);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleBindException(BindException bindException) {
        int code = 603;
        Object msg;
        List<FieldError> fieldErrors = bindException.getFieldErrors();
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            msg = "校验错误信息为空";
        }else {
            msg = fieldErrors;
        }
        log.info("校验异常code:{},msg:{}", code, msg);
        return buildResponseEntity(code, msg);
    }

}
