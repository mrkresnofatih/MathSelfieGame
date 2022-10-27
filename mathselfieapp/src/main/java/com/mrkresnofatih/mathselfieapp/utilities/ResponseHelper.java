package com.mrkresnofatih.mathselfieapp.utilities;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {
    public static <T> ResponseEntity<BaseFuncResponse<T>> BuildOkResponse(T data) {
        return new ResponseEntity<>(new BaseFuncResponse<>(data, null), HttpStatus.OK);
    }

    public static <T> ResponseEntity<BaseFuncResponse<T>> BuildBadResponse(String errorMessage) {
        return new ResponseEntity<>(new BaseFuncResponse<>(null, errorMessage), HttpStatus.BAD_REQUEST);
    }
}
