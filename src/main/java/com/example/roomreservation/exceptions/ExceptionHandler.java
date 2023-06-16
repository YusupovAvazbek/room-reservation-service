package com.example.roomreservation.exceptions;

import com.example.roomreservation.additional.StatusCodes;
import com.example.roomreservation.additional.StatusMessages;
import com.example.roomreservation.dto.ApiResult;
import com.example.roomreservation.dto.ErrorData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ApiResult<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ApiResult.<Void>builder()
                        .code(StatusCodes.OK_CODE)
                        .message(StatusMessages.VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors().stream()
                                .map(f-> new ErrorData(f.getField(),f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }
}
