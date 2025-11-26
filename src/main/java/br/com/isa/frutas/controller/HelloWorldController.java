package br.com.isa.frutas.controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/hello-world")
public class HelloWorldController {
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

     @PutMapping
    public String putTest() {
        return "Put test";
    }

    @PostMapping
    public String post() {
        return "Test post";
    }

    @PostMapping("/test")
    public String postTest() {
        return "Test post2";
    }

}

