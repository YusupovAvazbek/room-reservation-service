package com.example.roomreservation.dto;
import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ApiResult <T>{
    /**
     * Response code for defining type of error:
     * <p> -2 - Validation error </p>
     * <p> -1 - Not found </p>
     * <p> 0 - OK </p>
     * <p> 1 - Database error </p>
     * <p>2 - Unexpected error </p>
     */
    private Boolean success;
    private int code;
    private String message;
    private T data;
    private List<ErrorData> errors;

}
