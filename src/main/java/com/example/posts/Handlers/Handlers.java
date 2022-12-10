package com.example.posts.Handlers;

import com.example.posts.Response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class Handlers {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtime(RuntimeException e){
        log.debug(e.getMessage());
        return ResponseEntity.ok(ResponseDto.builder().error(e.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        log.debug(e.getMessage());
        return ResponseEntity.ok(ResponseDto.builder().error("You have a problem, please, send us a message"));
    }
}
