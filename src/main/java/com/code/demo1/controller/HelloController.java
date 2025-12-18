package com.code.demo1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 贾育权~
 * @version 1.0
 */
@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String hello() {
        return "Hello World";
    }

}
