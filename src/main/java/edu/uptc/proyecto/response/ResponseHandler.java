package edu.uptc.proyecto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Boolean status, String message, Object responseObject) {

        Map<String, Object> map = new HashMap<>();

        map.put("status", status);
        map.put("message", message);
        map.put("data", responseObject);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
