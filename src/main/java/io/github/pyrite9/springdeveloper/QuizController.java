package io.github.pyrite9.springdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    // request URL and request Method(Get, Post, Patch, Put, Delete) mapping
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!"); // 201(Created)
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!"); // 400(badRequest)
            default:
                return ResponseEntity.ok().body("OK!"); // 200(OK)
        }

    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~["+code+"]");
        switch (code.value()) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }



}


record Code(int value) {}