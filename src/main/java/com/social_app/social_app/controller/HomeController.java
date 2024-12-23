package com.social_app.social_app.controller;

import org.springframework.web.bind.annotation.*;

@RestController

public class HomeController {
    @GetMapping
    public String homeControllerHandler(){
        return "this is home controller";
    }
//    @GetMapping("/home")
//    public String homeControllerHandler2(){
//        return "this is home controller 2";
//    }
    //@PutMapping
    //@PostMapping
    //@DeleteMapping
}
