package io.github.pyrite9.springdeveloper;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestResponseController {

//    http://localhost:8080/test
    @GetMapping("/test")
    public String getTest() {return "Get Test Response";}

    @PostMapping("/test")
    public String postTest() {return "Post Test Response";}

    @DeleteMapping("/test")
    public String deleteTest() {return  "Delete Test Response";}

    @PutMapping("/test")
    public String putTest() {return "Put Test Response";}

}
