package edu.uptc.proyecto.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class CustomValidation {

    public static Map<String, Object> getErrorsMap(BindingResult result) {

        Map<String, Object> errors = new HashMap<>();

        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }

}
