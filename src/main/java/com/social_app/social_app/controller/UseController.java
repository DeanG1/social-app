package com.social_app.social_app.controller;

import com.social_app.social_app.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UseController {
    @GetMapping("/users")
    public List<User> getUser(){
        List<User> users = new ArrayList<>();
        User user = new User("ivan","ivanov","ivan@gmail.com", "pass123");
        User user1 = new User("petko","ivanov","petko@gmail.com", "pass123");
        users.add(user);
        users.add(user1);
        return users;
    }

}
