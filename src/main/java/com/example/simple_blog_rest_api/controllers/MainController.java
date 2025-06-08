package com.example.simple_blog_rest_api.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    protected ResponseEntity<?> response(Object info, HttpStatusCode statusCode)
    {
        Map<String, Object> responseInfo = new HashMap<>();

        responseInfo.put("messages", info);
        responseInfo.put("status_code", statusCode.value());

        return new ResponseEntity<>(responseInfo, statusCode);
    }

    protected Map<String, String> getErrorMessages(BindingResult result)
    {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return errors;
    }

}
