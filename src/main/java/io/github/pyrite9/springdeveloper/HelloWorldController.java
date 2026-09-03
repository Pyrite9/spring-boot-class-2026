package io.github.pyrite9.springdeveloper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

//    if request "/hello", call hello() method
//    http://localhosts:8080/hello
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
