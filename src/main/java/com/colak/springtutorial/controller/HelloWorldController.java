package com.colak.springtutorial.controller;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/hello")

@Slf4j
public class HelloWorldController {


    @GetMapping
    @Retry(name="backendA",fallbackMethod = "fallback")
    public ResponseEntity<String> showHelloWorld(){
        throw  new HttpServerErrorException(HttpStatusCode.valueOf(101));
    }

    public ResponseEntity<String> fallback(Throwable e){
        log.error("fallback exception , {}",e.getMessage());
        return new ResponseEntity<>("your request failed even with retries", HttpStatus.OK);
    }

}
