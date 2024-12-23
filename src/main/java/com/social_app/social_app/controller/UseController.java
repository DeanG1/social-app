package com.social_app.social_app.controller;

import com.social_app.social_app.models.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UseController {
    @GetMapping("/users")
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        User user = new User(1,"ivan","ivanov","ivan@gmail.com", "pass123");
        User user1 = new User(2, "petko","ivanov","petko@gmail.com", "pass123");
        users.add(user);
        users.add(user1);
        return users;
    }
    @GetMapping("/users/{userId}")
    public User getUserById(@PathVariable("userId") Integer id){
        User user = new User(1,"ivan","ivanov","ivan@gmail.com", "pass123");
        user.setId(id);
        return user;
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setLastName(user.getLastName());

        newUser.setFirstName(user.getFirstName());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());

        return newUser;
    }
    @PutMapping("/users")
    public User updateUser(@RequestBody User user){
        User user1 = new User(6, "ico", "ivanov", "ico@gmail.com", "pass123");
        if(user.getFirstName() != null){
            user1.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null){
            user1.setLastName(user.getLastName());
        }
        if(user.getEmail() != null){
            user1.setEmail(user.getEmail());
        }

        return user1;
    }
    @DeleteMapping("users/{userId}")
    public String deleteUser(@PathVariable ("userId") Integer userId){
        return "User deleted successfully with id: " + userId;
    }
}
