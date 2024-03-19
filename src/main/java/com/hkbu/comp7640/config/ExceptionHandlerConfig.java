package com.hkbu.comp7640.config;

import com.hkbu.comp7640.exception.UnAuthorizationException;
import com.hkbu.comp7640.response.ResponseEnum;
import com.hkbu.comp7640.response.ServerResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RestControllerAdvice
public class ExceptionHandlerConfig {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ServerResponseEntity<List<String>>> methodArgumentNotValidExceptionHandler(Exception e) {
        log.error("methodArgumentNotValidExceptionHandler", e);
        List<FieldError> fieldErrors = null;
        if (e instanceof MethodArgumentNotValidException) {
            fieldErrors = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            fieldErrors = ((BindException) e).getBindingResult().getFieldErrors();
        }
        if (fieldErrors == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ServerResponseEntity.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID));
        }
        ArrayList<String> defaultMessage = new ArrayList<>(fieldErrors.size());
        for (FieldError fieldError : fieldErrors) {
            defaultMessage.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponseEntity.fail(ResponseEnum.METHOD_ARGUMENT_NOT_VALID, defaultMessage));
    }

    @ExceptionHandler({UnAuthorizationException.class})
    public ResponseEntity<ServerResponseEntity<?>> unauthorizedExceptionHandler(UnAuthorizationException e) {
        log.error("unauthorizedExceptionHandler", e);

        ServerResponseEntity<?> serverResponseEntity = e.getServerResponseEntity();
        if (serverResponseEntity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(serverResponseEntity);
        }
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponseEntity.fail(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ServerResponseEntity<Objects>> exceptionHandler(Exception e) {
        log.error("exceptionHandler", e);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ServerResponseEntity.fail(ResponseEnum.EXCEPTION));
    }

}
