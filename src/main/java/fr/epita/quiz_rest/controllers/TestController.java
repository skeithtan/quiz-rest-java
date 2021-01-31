package fr.epita.quiz_rest.controllers;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class TestController {

    @RequestMapping("/")
    public String home() {
        return "OK";
    }
}