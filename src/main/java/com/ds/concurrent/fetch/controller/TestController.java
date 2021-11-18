package com.ds.concurrent.fetch.controller;

import com.ds.concurrent.fetch.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/fetch")
    public ResponseEntity<?> fetch() throws InterruptedException {
        return new ResponseEntity<>(testService.fetch(), OK);
    }
}
