package com.ssg.bidssgket.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @GetMapping("/test")
    public ResponseEntity<String> test(){

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
